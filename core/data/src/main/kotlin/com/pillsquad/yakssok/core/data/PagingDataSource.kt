package com.pillsquad.yakssok.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

internal class PagingDataSource<T : Any>(
    private val perPage: Int,
    private val fetcher: suspend (startKey: String?, perPage: Int) -> Result<List<T>>,
    private val keySelector: (T) -> String
) : PagingSource<String, T>() {
    override fun getRefreshKey(state: PagingState<String, T>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, T> {
        val start = params.key
        val response = fetcher(start, perPage)

        return when {
            response.isSuccess -> {
                val data = response.getOrNull().orEmpty()
                LoadResult.Page(
                    data = data,
                    prevKey = null, // 최신순 로딩이므로 이전키 없음
                    nextKey = data.lastOrNull()?.let(keySelector), // 오래된 메시지의 ID
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
            keySelector: (T) -> String,
            fetcher: suspend (startKey: String?, perPage: Int) -> Result<List<T>>,
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