package com.example.customviewcookbook.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ViewBannerHighlightBinding

/**
 * A custom view that displays a banner highlight with an icon, title, description,
 * optional close button, optional progress indicator, and customizable border stroke color.
 */
class BannerHighlightView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewBannerHighlightBinding = ViewBannerHighlightBinding.inflate(
            /* inflater = */ LayoutInflater.from(context),
            /* parent = */ this,
            /* attachToParent = */ true
    )

    /**
     * The icon drawable.
     * @see [R.styleable.BannerHighlight_icon]
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            updateIcon()
        }

    /**
     * The title text.
     * @see [R.styleable.BannerHighlight_title]
     */
    var title: String = context.getString(R.string.dolores_perferendis_title)
        set(value) {
            field = value
            updateTile()
        }

    /**
     * The description text.
     * @see [R.styleable.BannerHighlight_description]
     */
    var description: String = context.getString(R.string.dolores_perferendis_description)
        set(value) {
            field = value
            updateDescription()
        }

    /**
     * Whether to show the close button.
     * @see [R.styleable.BannerHighlight_hasCloseButton]
     */
    var hasCloseButton: Boolean = false
        set(value) {
            field = value
            updateCloseButtonVisibility()
        }

    /**
     * The color of the border stroke. If null, a default color will be used.
     * @see [R.styleable.BannerHighlight_strokeColor]
     */
    var strokeColor: Int? = null
        set(value) {
            field = value
            updateStrokeColor()
        }

    /**
     * Whether to show a progress indicator instead of the icon.
     * @see [R.styleable.BannerHighlight_hasProgressIndicator]
     */
    var hasProgressIndicator: Boolean = false
        set(value) {
            field = value
            updateProgressIndicator()
        }

    /**
     * Callback to be invoked when the close button is clicked.
     */
    var onCloseClickListener: (() -> Unit)? = null

    init {
        initializeArguments(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeArguments(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.withStyledAttributes(
                attrs,
                R.styleable.BannerHighlight,
                defStyleAttr,
                defStyleRes
        ) {
            val banner = this@BannerHighlightView

            banner.icon?.let { bannerIcon ->
                banner.icon = getDrawable(R.styleable.BannerHighlight_icon)
            } ?: { binding.icon.isVisible = false }

            banner.title = getString(
                    /* index = */ R.styleable.BannerHighlight_title
            ) ?: banner.title

            banner.description = getString(
                    /* index = */ R.styleable.BannerHighlight_description
            ) ?: banner.description

            banner.hasCloseButton = getBoolean(
                    /* index = */ R.styleable.BannerHighlight_hasCloseButton,
                    /* defValue = */ banner.hasCloseButton
            )

            banner.strokeColor = banner.strokeColor?.let { color ->
                getColor(
                        /* index = */ R.styleable.BannerHighlight_strokeColor,
                        /* defValue = */ color
                )
            }

            banner.hasProgressIndicator = getBoolean(
                    /* index = */ R.styleable.BannerHighlight_hasProgressIndicator,
                    /* defValue = */ banner.hasProgressIndicator
            )
        }
    }

    private fun updateTile() {
        binding.title.text = title
        binding.title.contentDescription = title
    }

    private fun updateDescription() {
        binding.description.text = description
        binding.description.contentDescription = description
    }

    private fun updateIcon() {
        binding.progressIndicator.isVisible = false
        binding.icon.setImageDrawable(icon)
        binding.icon.isVisible = true
    }

    private fun updateCloseButtonVisibility() {
        binding.closeButton.setOnClickListener { onCloseClickListener?.invoke() }
        binding.closeButton.isVisible = hasCloseButton == true
    }

    private fun updateStrokeColor() {
        binding.cardContainer.strokeColor = strokeColor
            ?: ContextCompat.getColor(context, R.color.white)
    }

    private fun updateProgressIndicator() {
        binding.icon.isVisible = false
        binding.progressIndicator.isVisible = hasProgressIndicator == true
    }
}
