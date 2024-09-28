package roomescape

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import roomescape.assertion.assertHashcode

class Step4 {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `StateFlow와 SharedFlow`() = runTest {
        // given
        val actual: StringBuilder = StringBuilder()

        val a = MutableStateFlow(1)
        val b = MutableStateFlow(true)
        val c = MutableSharedFlow<Boolean>()

        // when
        val collectorJob = launch {
            a
                .flatMapLatest { b.filter { it } }
                .flatMapLatest { c.filter { it } }
                .onEach { actual.append(it) }
                .collect()
        }
        val emitterJob = launch {
            delay(100)
            c.emit(true)
            b.value = false
            a.value = 10
            c.emit(false)
            b.value = true
            a.value = 5
        }
        emitterJob.join()
        collectorJob.cancelAndJoin()

        // then
        val expected = "true" // TODO: 결과값 예상
        /*
            TODO: 간단한 풀이과정 작성
            b와 c에 모두 true가 방출된 경우만 onEach 블록을 탈 수 있음
            맨 처음 c에 true가 emit 되었을 때 true 가 추가되고 이후는 X
         */

        // assert문 수정하지 마세요!
        assertHashcode(actual, expected)
    }
}
