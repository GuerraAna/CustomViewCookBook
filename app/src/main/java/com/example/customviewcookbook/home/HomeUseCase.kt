package com.example.customviewcookbook.home

import com.example.customviewcookbook.features.item.Feature
import com.example.customviewcookbook.features.item.FeaturesResponse

internal class HomeUseCase() {

    /**
     *
     */
    fun getFeatures(): FeaturesResponse {
        return FeaturesResponse(features = Feature.entries)
    }
}