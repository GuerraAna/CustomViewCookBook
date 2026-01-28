package com.example.customviewcookbook.home

internal class HomeUseCase() {

    /**
     *
     */
    fun getFeatures(): FeaturesResponse {
        return FeaturesResponse(features = Feature.entries)
    }
}