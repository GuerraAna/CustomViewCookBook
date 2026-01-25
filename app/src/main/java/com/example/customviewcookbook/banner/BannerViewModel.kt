package com.example.customviewcookbook.banner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job // Importe o Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class BannerViewModel @JvmOverloads constructor(
    application: Application,
    val counterUseCase: BannerCounterUseCase = BannerCounterUseCase()
) : AndroidViewModel(application) {

    private val _bannerState = MutableStateFlow<BannerState>(
            BannerState.Loading(current = null, total = null)
    )

    private var currentItemsList: List<String> = emptyList()
    private val _itemsListState: MutableStateFlow<List<String>> = MutableStateFlow(currentItemsList)
    private var bannerJob: Job? = null

    /**
     *
     */
    val bannerState: StateFlow<BannerState> = _bannerState.asStateFlow()

    /**
     *
     */
    val itemsListState: StateFlow<List<String>> = _itemsListState.asStateFlow()

    /**
     * Start the banner count up process
     */
    fun startBannerCountUp() {
        bannerJob?.cancel()

        bannerJob = viewModelScope.launch {
            delay(3000L)

            try {
                counterUseCase.startCountUp().collect { (current, totalValue, item) ->
                    _bannerState.emit(BannerState.Loading(current, totalValue))
                    currentItemsList = currentItemsList + item
                    _itemsListState.emit(currentItemsList)
                }

                _bannerState.emit(BannerState.Success)
            } catch (error: Throwable) {
                if (error !is CancellationException) {
                    _bannerState.emit(BannerState.Error)
                }
            }
        }
    }

    /**
     *
     */
    fun restartBannerCountUp() {
        currentItemsList = emptyList()
        _itemsListState.value = currentItemsList
        _bannerState.value = BannerState.Loading(current = null, total = null)

        startBannerCountUp()
    }

    /**
     *
     */
    fun getBannerException() {
        bannerJob?.cancel()

        bannerJob = viewModelScope.launch {
            _itemsListState.value = emptyList()
            _bannerState.value = BannerState.Loading(current = null, total = null)
            delay(3000L)
            _bannerState.emit(BannerState.Error)
        }
    }
}
