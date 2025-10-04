package com.pillsquad.yakssok.core.firebase

import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RemoteConfigProviderTest {

    @Test
    fun `버전 정보를 정상적으로 가져온다`() = runTest {
        // given
        val mockRemoteConfig = mockk<FirebaseRemoteConfig>()

        // fetchAndActivate()가 성공했다고 가정
        every { mockRemoteConfig.fetchAndActivate() } returns Tasks.forResult(true)

        // 지정한 버전값 리턴하도록 세팅
        every { mockRemoteConfig.getLong("minSupportedVersion") } returns 17L
        every { mockRemoteConfig.getLong("recommendedVersion") } returns 17L

        val provider = RemoteConfigProviderImpl(mockRemoteConfig)

        // when
        val result = provider.getRemoteVersion()

        println(result)

        // then
        assert(result.isSuccess)
        val version = result.getOrNull()
        assertEquals(17, version?.minSupportedVersion)
        assertEquals(17, version?.recommendedVersion)
    }

    @Test
    fun `fetch 실패 시 Result가 실패로 반환된다`() = runTest {
        val mockRemoteConfig = mockk<FirebaseRemoteConfig>()

        // fetch 실패 시 예외 발생하도록
        coEvery { mockRemoteConfig.fetchAndActivate() } throws RuntimeException("Network error")

        val provider = RemoteConfigProviderImpl(mockRemoteConfig)

        val result = provider.getRemoteVersion()

        assert(result.isFailure)
    }
}