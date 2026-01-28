package com.example.customviewcookbook.home

import androidx.appcompat.app.AppCompatActivity
import com.example.customviewcookbook.features.components.banner.BannerActivity
import com.example.customviewcookbook.features.navigation.FirstActivity

internal enum class Feature(activity: Class<out AppCompatActivity>) {
    BANNER_COMPONENT(activity = BannerActivity::class.java),
    NAVIGATION(activity = FirstActivity::class.java)
}
