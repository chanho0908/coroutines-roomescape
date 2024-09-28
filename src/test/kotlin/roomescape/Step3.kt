package roomescape

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import roomescape.assertion.assertHashcode

class Step3 {

    @Test
    fun `코루틴 예외 전파`() = runTest {
        // given
        val actual: StringBuilder = StringBuilder()

        // when
        val job = launch {
            try {
                launch {
                    delay(150)
                    actual.append(1)
                }
                supervisorScope {
                    val deferred = async {
                        delay(100)
                        throw RuntimeException("E2")
                    }
                    launch {
                        delay(200)
                        throw RuntimeException("E3")
                    }
                    deferred.await()
                }
            } catch (e: Exception) {
                actual.append(e.message)
            }
        }
        job.join()

        // then
        val expected = "E21" // TODO: 결과값 예상
        /*
            TODO: 간단한 풀이과정 작성
            100ms 후 E2 예외 발생하여 밑에 있는 launch 실행 전 scope 취소 됨
            150ms 후 1 추가
         */

        // assert문 수정하지 마세요!
        assertHashcode(actual, expected)
    }
}
