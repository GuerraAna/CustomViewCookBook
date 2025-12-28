package com.example.customviewcookbook.banner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
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

    // Infla o layout usando ViewBinding
    private val binding: ViewBannerHighlightBinding =
        ViewBannerHighlightBinding.inflate(
                /* inflater = */ LayoutInflater.from(context),
                /* parent = */ this,
                /* attachToParent = */ true
        )

    /**
     *
     */
    var icon: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_launcher_foreground)
        set(value) {
            field = value
            updateIcon(value)
        }

    /**
     *
     */
    var title: String? = context.getString(R.string.banner_title_test)
        set(value) {
            field = value
            updateTile()
        }

    /**
     *
     */
    var description: String? = context.getString(R.string.banner_description_test)
        set(value) {
            field = value
            updateDescription()
        }

    /**
     *
     */
    var isError: Boolean? = null
        set(value) {
            field = value
            updateErrorState()
        }

    /**
     *
     */
    var onCloseClickListener: (() -> Unit)? = null

    init {
        binding.closeButton.setOnClickListener { onCloseClickListener?.invoke() }
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

            banner.icon = getDrawable(
                    R.styleable.BannerHighlight_icon
            ) ?: banner.icon

            banner.title = getString(
                    R.styleable.BannerHighlight_title
            ) ?: banner.title

            banner.description = getString(
                    R.styleable.BannerHighlight_description
            ) ?: banner.description

            banner.isError = banner.isError?.let {
                getBoolean(
                        R.styleable.BannerHighlight_isError,
                        it
                )
            }
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
        binding.icon.setImageDrawable(drawable)
    }

    private fun updateErrorState() {
        if (isError == true) {
            binding.cardContainer.strokeColor = ContextCompat.getColor(context, R.color.red)
            binding.icon.setImageDrawable(
                    AppCompatResources.getDrawable(
                            context,
                            R.drawable.ic_close
                    )
            )
            binding.closeButton.isVisible = true
        } else {
            binding.cardContainer.strokeColor = ContextCompat.getColor(context, R.color.white)
            binding.icon.setImageDrawable(icon)
            binding.closeButton.isVisible = false
        }
    }
}
