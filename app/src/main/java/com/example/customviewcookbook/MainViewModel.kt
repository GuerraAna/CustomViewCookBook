package com.example.customviewcookbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class MainViewModel(
    application: Application,
    val useCase: MainUseCase = MainUseCase()
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
            delay(3000)

            try {
                useCase.startCountUp(total).collect { (current, totalValue) ->
                    // Emite um novo estado de Loading a cada contagem
                    _state.emit(BannerState.Loading(current, totalValue))
                }

                _state.emit(BannerState.Success)
            } catch (error: Throwable) {
                _state.emit(BannerState.Error)
            }
        }
    }
}
