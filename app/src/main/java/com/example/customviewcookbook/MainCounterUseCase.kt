package com.example.customviewcookbook

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MainCounterUseCase(
        val provideDelayMillis: Long = 1000
) {

    /**
     *
     */
    fun startCountUp(): Flow<Pair<Int, Int>> {
//        return throw Exception()
        val total = getTotalCountValue()

        return flow {
            for (i in 1..total) {
                emit(i to total)
                delay(provideDelayMillis)
            }
        }
    }

    private fun getTotalCountValue(): Int = 10
}
