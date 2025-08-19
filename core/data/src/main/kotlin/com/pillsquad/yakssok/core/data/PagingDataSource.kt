package com.pillsquad.yakssok.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

internal class PagingDataSource<T : Any>(
    private val perPage: Int,
    private val fetcher: suspend (startKey: Long?, perPage: Int) -> Result<List<T>>,
    private val keySelector: (T) -> Long
) : PagingSource<Long, T>() {
    override fun getRefreshKey(state: PagingState<Long, T>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, T> {
        val startKey = params.key
        val response = fetcher(startKey, perPage)

        return when {
            response.isSuccess -> {
                var data = response.getOrNull().orEmpty()

                if (startKey != null) {
                    data = data.filter { keySelector(it) < startKey }
                }

                val nextKey = if (data.isEmpty() || data.size < perPage) {
                    null
                } else {
                    data.lastOrNull()?.let(keySelector)
                }

                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = nextKey
                )
            }
            else -> {
                LoadResult.Error(response.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    companion object {
        private const val DEFAULT_PER_PAGE = 20

        fun <T : Any> createPager(
            pageSize: Int = DEFAULT_PER_PAGE,
            keySelector: (T) -> Long,
            fetcher: suspend (startKey: Long?, perPage: Int) -> Result<List<T>>,
        ) = Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                PagingDataSource(
                    perPage = pageSize,
                    fetcher = fetcher,
                    keySelector = keySelector
                )
            }
        ).flow
    }
}