package com.example.customviewcookbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

internal class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<BannerState>(BannerState.InitialLoading)

    /**
     *
     */
    val state: StateFlow<BannerState> = _state.asStateFlow()

    /**
     *
     */
    fun startBannerCountUp(total: Int) {
        viewModelScope.launch {
            delay(2000)

            // A contagem original pode ser mantida como privada
            startCountUp(total).collect { (current, totalValue) ->
                // Emite um novo estado de Loading a cada contagem
                _state.value = BannerState.Loading(current, totalValue)
            }
            // Quando o flow terminar, emite o estado de Success
            _state.value = BannerState.Success
        }
    }

    private fun startCountUp(total: Int): Flow<Pair<Int, Int>> = flow {
        for (i in 1..total) {
            emit(i to total)
            delay(1000)
        }
    }
}
