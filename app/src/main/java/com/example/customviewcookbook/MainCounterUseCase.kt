package com.example.customviewcookbook

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
1. Primeira chamada (uma vez): busca o intervalo de polling
2. Segunda chamada (repetida): busca currentCount e maxCount
3. Terceira chamada (quando currentCount < maxCount): busca a lista de dados
4. Parada: quando currentCount >= maxCount, finaliza o polling

. Se houver algum erro, devemos emitir um estado de erro apenas quando tentamos buscar novamente 5 vezes sem sucesso. Ai sim, mostramos o estado de erro corretamente. Enquanto isso, não, tentaremos buscar os dados novamente.
 */

internal class MainCounterUseCase(
    val provideDelayMillis: Long = 1000
) {

    /**
     *
     */
    fun startCountUp(): Flow<Triple<Int, Int, String>> {
//        return throw Exception()
        val total = getTotalCountValue()

        return flow {
            for (i in 1..total) {
                emit(Triple(i, total, "Count $i"))
                delay(provideDelayMillis)
            }
        }
    }

    private fun getTotalCountValue(): Int = 10
}
