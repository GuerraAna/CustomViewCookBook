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

    private val _bannerState = MutableStateFlow<BannerState>(
            BannerState.Loading(current = null, total = null)
    )

    private var currentItemsList: List<String> = emptyList()
    private val _itemsList = MutableStateFlow(currentItemsList)

    /**
     *
     */
    val bannerState: StateFlow<BannerState> = _bannerState.asStateFlow()

    /**
     *
     */
    val itemsList: StateFlow<List<String>> = _itemsList.asStateFlow()

    /**
     * Start the banner count up process
     */
    fun startBannerCountUp() {
        viewModelScope.launch {
            delay(3000)

            try {
                counterUseCase.startCountUp().collect { (current, totalValue, item) ->
                    _bannerState.emit(BannerState.Loading(current, totalValue))
                    currentItemsList = currentItemsList + item
                    _itemsList.emit(currentItemsList)
                }

                _bannerState.emit(BannerState.Success)
            } catch (error: Throwable) {
                _bannerState.emit(BannerState.Error)
            }
        }
    }
}
