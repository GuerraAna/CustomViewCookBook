package com.example.customviewcookbook.banner

internal sealed class BannerState {
    data class Loading(val current: Int?, val total: Int?) : BannerState()
    object Error : BannerState()
    object Success : BannerState()
}
