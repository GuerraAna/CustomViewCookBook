package com.example.customviewcookbook.features.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class FeatureDetails(
    val name: String,
    val description: String
) : Parcelable
