package com.example.customviewcookbook.features.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class FeaturesResponse(
    val features: List<Feature>
) : Parcelable
