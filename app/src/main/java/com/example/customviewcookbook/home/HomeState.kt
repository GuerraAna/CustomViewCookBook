package com.example.customviewcookbook.home

internal sealed class HomeState() {
    object Loading : HomeState()
    data class Success(val response: FeaturesResponse) : HomeState()
    data class Error(val message: String) : HomeState()
}
