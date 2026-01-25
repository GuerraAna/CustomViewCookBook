package com.example.customviewcookbook.features.components.banner

internal sealed class BannerState {
    data class Loading(val current: Int?, val total: Int?) : BannerState()
    object Error : BannerState()
    object Success : BannerState()
}
