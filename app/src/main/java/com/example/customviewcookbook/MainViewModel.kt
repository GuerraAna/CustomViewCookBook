package com.example.customviewcookbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class MainViewModel @JvmOverloads constructor(
    application: Application,
    val counterUseCase: MainCounterUseCase = MainCounterUseCase()
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<BannerState>(BannerState.InitialLoading)

    /**
     *
     */
    val state: StateFlow<BannerState> = _state.asStateFlow()

    /**
     * Start the banner count up process
     */
    fun startBannerCountUp() {
        viewModelScope.launch {
            delay(3000)

            try {
                counterUseCase.startCountUp().collect { (current, totalValue) ->
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
