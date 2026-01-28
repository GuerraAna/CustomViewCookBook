package com.example.customviewcookbook.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel @JvmOverloads constructor(
    application: Application,
    private val useCase: HomeUseCase = HomeUseCase()
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)

    /**
     *
     */
    val state: StateFlow<HomeState> = _state.asStateFlow()

    /**
     *
     */
    fun initialize() {
        viewModelScope.launch {
            delay(2000)

            try {
                val response = useCase.getFeatures()
                _state.value = HomeState.Success(response)
            } catch (error: Exception) {
                _state.value = HomeState.Error(error.message ?: "Unknown error")
            }
        }
    }

    /**
     *
     */
    fun retry() {
        initialize()
    }
}
