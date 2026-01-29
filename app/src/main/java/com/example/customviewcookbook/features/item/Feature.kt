package com.example.customviewcookbook.features.item

import androidx.appcompat.app.AppCompatActivity
import com.example.customviewcookbook.R
import com.example.customviewcookbook.features.components.banner.BannerActivity
import com.example.customviewcookbook.features.navigation.FirstActivity

internal enum class Feature(
    val activity: Class<out AppCompatActivity>,
    val featureDetails: FeatureDetails
) {
    BANNER_COMPONENT(
            activity = BannerActivity::class.java,
            featureDetails = FeatureDetails(
                    resourceId = R.drawable.bank_icon,
                    name = "Componente Banner",
                    description = "Banner para exemplo de uso com Pooling"
            )
    ),
    NAVIGATION(
            activity = FirstActivity::class.java,
            featureDetails = FeatureDetails(
                    resourceId = R.drawable.bank_icon,
                    name = "Lógica de navegação entre telas",
                    description = "Uso do resultCode e requestCode, em breve Navigation."
            )
    )
}
