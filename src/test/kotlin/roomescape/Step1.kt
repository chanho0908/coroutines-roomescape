package roomescape

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import roomescape.assertion.assertHashcode

class Step1 {

    @Test
    fun `종류별 Scope`() = runTest {
        // given
        val actual: StringBuilder = StringBuilder()

        // when
        val deferred = async {
            delay(500)
            actual.append(1)
        }
        launch {
            delay(200)
            actual.append(2)
        }
        coroutineScope {
            launch {
                delay(300)
                actual.append(3)
            }
            actual.append(4)
        }
        deferred.await()
        actual.append(5)

        // then
        val expected = "42315" // TODO: 결과값 예상
        /*
            TODO: 간단한 풀이과정 작성
            가장 딜레이 시간이 적은 4가 먼저 추가됨
            이후 순서대로 딜레이 시간에 따라 2, 3, 1 추가됨
            5는 deferred의 결과인 1이 추가될 때까지 suspend 되어 있다가 마지막에 추가됨
         */

        // assert문 수정하지 마세요!
        assertHashcode(actual, expected)
    }
}
