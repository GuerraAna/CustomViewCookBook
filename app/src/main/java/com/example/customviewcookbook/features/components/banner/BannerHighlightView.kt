package com.example.customviewcookbook.features.components.banner

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
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
     * @see [R.styleable.BannerHighlightView_icon]
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            updateIconAndProgressIndicator()
        }

    /**
     * Whether to show a progress indicator instead of the icon.
     * @see [R.styleable.BannerHighlightView_hasProgressIndicator]
     */
    var hasProgressIndicator: Boolean = false
        set(value) {
            field = value
            updateIconAndProgressIndicator()
        }

    /**
     * The title text.
     * @see [R.styleable.BannerHighlightView_title]
     */
    var title: String = context.getString(R.string.dolores_perferendis_title)
        set(value) {
            field = value
            updateTitleAndDescription()
        }

    /**
     * The description text.
     * @see [R.styleable.BannerHighlightView_description]
     */
    var description: String = context.getString(R.string.dolores_perferendis_description)
        set(value) {
            field = value
            updateTitleAndDescription()
        }

    /**
     * Whether to show the close button.
     * @see [R.styleable.BannerHighlightView_hasCloseButton]
     */
    var hasCloseButton: Boolean = false
        set(value) {
            field = value
            updateCloseButtonVisibility()
        }

    /**
     * Whether to show the close button.
     * @see [R.styleable.BannerHighlightView_hasRightIcon]
     */
    var hasRightIcon: Boolean = false
        set(value) {
            field = value
            updateRightButtonVisibility()
        }

    /**
     * The color of the border stroke. If null, a default color will be used.
     * @see [R.styleable.BannerHighlightView_strokeColor]
     */
    var strokeColor: Int? = null
        set(value) {
            field = value
            updateStrokeColor()
        }

    /**
     * Callback to be invoked when the close button is clicked.
     */
    var onCloseClickListener: (() -> Unit)? = null

    init {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.BannerHighlightView,
            defStyleAttr = defStyleAttr,
            defStyleRes = defStyleRes
        ) {
            setupStyledAttributesView()
        }
    }

    private fun TypedArray.setupStyledAttributesView() {
        icon = getDrawable(R.styleable.BannerHighlightView_icon) ?: icon
        title = getString(R.styleable.BannerHighlightView_title) ?: title
        description = getString(R.styleable.BannerHighlightView_description) ?: description
        hasCloseButton = getBoolean(R.styleable.BannerHighlightView_hasCloseButton, hasCloseButton)

        hasProgressIndicator = getBoolean(
            R.styleable.BannerHighlightView_hasProgressIndicator,
            hasProgressIndicator
        )

        if (hasValue(R.styleable.BannerHighlightView_strokeColor)) {
            strokeColor = getColor(R.styleable.BannerHighlightView_strokeColor, 0)
        }

    }

    private fun updateTitleAndDescription() {
        binding.title.text = title
        ViewCompat.setAccessibilityHeading(binding.title, true)
        binding.description.text = description
    }

    private fun updateIconAndProgressIndicator() {
        if (hasProgressIndicator) {
            binding.icon.isVisible = false
            binding.progressIndicator.isVisible = true
        } else {
            binding.progressIndicator.isVisible = false

            if (icon != null ) {
                binding.icon.setImageDrawable(icon)
                binding.icon.isVisible = true
            } else {
                binding.icon.isVisible = false
            }
        }
    }

    private fun updateCloseButtonVisibility() {
        if (hasCloseButton) {
            binding.closeButton.setOnClickListener { onCloseClickListener?.invoke() }

            ViewCompat.setAccessibilityDelegate(
                /* v = */ binding.closeButton,
                /* delegate = */ getButtonAccessibilityDelegate()
            )
        }

        binding.closeButton.isVisible = hasCloseButton
    }

    private fun updateRightButtonVisibility() {
        binding.rightIcon.isVisible = hasRightIcon
    }

    private fun updateStrokeColor() {
        binding.cardContainer.setStrokeColor(
            ColorStateList.valueOf(
                strokeColor ?: ContextCompat.getColor(context, R.color.white)
            )
        )
    }

    private fun getButtonAccessibilityDelegate() = object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            info.contentDescription = context.getString(R.string.close_content_description)
            info.className = Button::class.java.name
        }
    }
}
