package com.pillsquad.yakssok.core.network

import com.pillsquad.yakssok.core.network.adapter.ApiResponseCallAdapterFactory
import com.pillsquad.yakssok.core.network.api.UserApi
import com.pillsquad.yakssok.core.network.model.ApiResponse
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiResponseCallAdapterTest {

    private val mockWebServer = MockWebServer()
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    private lateinit var retrofit: Retrofit
    private lateinit var api: UserApi

    @Before
    fun setup() {
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()

        api = retrofit.create(UserApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `success response should return ApiResponse_Success`() = runTest {
        val json = """
            {
              "code": 0,
              "message": "성공",
              "body": {
                "id": 1,
                "name": "테스터"
              }
            }
        """
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = api.searchUser("테스터")

        println("response: $response")

        assert(response is ApiResponse.Success)
        val data = (response as ApiResponse.Success).data
        assertEquals("테스터", data.name)
    }

    @Test
    fun `success response with null body should return ApiResponse_Success(Unit)`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("""
            {
              "code": 0,
              "message": "성공",
              "body": null
            }
        """.trimIndent()))

        val response = api.searchUser("테스터")
        assert(response is ApiResponse.Success)
        assertEquals(Unit, (response as ApiResponse.Success).data)
    }

    @Test
    fun `success response with code not 0 should return HttpError`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("""
            {
              "code": 1001,
              "message": "잘못된 요청",
              "body": null
            }
        """.trimIndent()))

        val response = api.searchUser("잘못된")
        assert(response is ApiResponse.Failure.HttpError)
        assertEquals(1001, (response as ApiResponse.Failure.HttpError).code)
    }

    @Test
    fun `error response should return ApiResponse_HttpError`() = runTest {
        val json = """
            {
              "code": 1000,
              "message": "인증 실패",
              "body": null
            }
        """
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200)) // ← 여기도 200임 주의!

        val response = api.searchUser("누구?")

        println("failure response: $response")

        assert(response is ApiResponse.Failure.HttpError)
        val error = response as ApiResponse.Failure.HttpError
        assertEquals(1000, error.code)
        assertEquals("인증 실패", error.message)
    }

    @Test
    fun `failure response with malformed error JSON should still return HttpError`() = runTest {
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(500)
            .setBody("""{ "invalid": [1, 2, 3 }"""))

        val response = api.searchUser("에러")
        assert(response is ApiResponse.Failure.HttpError)
        assert((response as ApiResponse.Failure.HttpError).message.contains("파싱 실패"))
    }

    @Test
    fun `malformed body for BaseResponse should return UnknownApiError`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("""
            {
              "code": 0,
              "message": "성공",
              "body": { "id": "not_a_number", "name": "테스터" }
            }
        """.trimIndent()))

        val response = api.searchUser("테스터")
        assert(response is ApiResponse.Failure.UnknownApiError)
    }

    @Test
    fun `network failure should return NetworkError`() = runTest {
        mockWebServer.shutdown()

        val response = api.searchUser("네트워크")
        assert(response is ApiResponse.Failure.NetworkError)
    }

    @Test
    fun `unknown exception during parsing should return UnknownApiError`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("not even json"))

        val response = api.searchUser("쓰레기")
        assert(response is ApiResponse.Failure.UnknownApiError)
    }
}