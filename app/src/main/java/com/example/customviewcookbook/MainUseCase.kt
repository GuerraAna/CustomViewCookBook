package com.example.customviewcookbook

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MainUseCase() {

    /**
     *
     */
    fun startCountUp(total: Int): Flow<Pair<Int, Int>> {
//        return throw Exception()

        return flow {
            for (i in 1..total) {
                emit(i to total)
                delay(1000)
            }
        }
    }
}
