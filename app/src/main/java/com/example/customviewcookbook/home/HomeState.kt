package com.example.customviewcookbook.home

import com.example.customviewcookbook.features.item.FeaturesResponse

internal sealed class HomeState() {
    object Loading : HomeState()
    data class Success(val response: FeaturesResponse) : HomeState()
    data class Error(val message: String) : HomeState()
}
