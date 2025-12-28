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
 *
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
     *
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            updateIcon(value)
        }

    /**
     *
     */
    var title: String = context.getString(R.string.banner_title_test)
        set(value) {
            field = value
            updateTile()
        }

    /**
     *
     */
    var description: String = context.getString(R.string.banner_description_test)
        set(value) {
            field = value
            updateDescription()
        }

    /**
     *
     */
    var hasCloseButton: Boolean = false
        set(value) {
            field = value
            updateCloseButtonVisibility()
        }

    /**
     *
     */
    var strokeColor: Int? = null
        set(value) {
            field = value
            updateStrokeColor()
        }

    /**
     *
     */
    var hasProgressIndicator: Boolean = false
        set(value) {
            field = value
            updateProgressIndicator()
        }

    /**
     *
     */
    var onCloseClickListener: (() -> Unit)? = null

    init {
        setupListeners()
        initializeArguments(attrs, defStyleAttr, defStyleRes)
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener { onCloseClickListener?.invoke() }
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
                    R.styleable.BannerHighlight_title
            ) ?: banner.title

            banner.description = getString(
                    R.styleable.BannerHighlight_description
            ) ?: banner.description

            banner.hasCloseButton = getBoolean(
                    R.styleable.BannerHighlight_hasCloseButton,
                    banner.hasCloseButton
            )

            banner.strokeColor = banner.strokeColor?.let {
                getColor(
                        R.styleable.BannerHighlight_strokeColor,
                        it
                )
            }

            banner.hasProgressIndicator = getBoolean(
                    R.styleable.BannerHighlight_hasProgressIndicator,
                    banner.hasProgressIndicator
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

    private fun updateIcon(drawable: Drawable?) {
        binding.progressIndicator.isVisible = false
        binding.icon.setImageDrawable(drawable)
        binding.icon.isVisible = true
    }

    private fun updateCloseButtonVisibility() {
        binding.closeButton.isVisible = hasCloseButton == true
    }

    private fun updateStrokeColor() {
        binding.cardContainer.strokeColor = strokeColor?.let {
            ContextCompat.getColor(context, it)
        } ?: ContextCompat.getColor(context, R.color.white)
    }

    private fun updateProgressIndicator() {
        binding.icon.isVisible = false
        binding.progressIndicator.isVisible = hasProgressIndicator == true
    }
}
