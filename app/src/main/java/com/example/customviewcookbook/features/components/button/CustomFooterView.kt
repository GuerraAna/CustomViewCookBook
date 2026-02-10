package com.example.customviewcookbook.features.components.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.CustomFooterBinding

/**
 * Custom footer view.
 */
internal class CustomFooterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomFooterBinding =
        CustomFooterBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * @see R.styleable.CustomFooterView_footerText
     */
    var footerText: String? = null
        set(value) {
            field = value
            setFooterText()
        }

    /**
     * @see R.styleable.CustomFooterView_secondaryButtonText
     */
    var secondaryButtonText: String? = null
        set(value) {
            field = value
            setSecondaryButtonText()
        }

    /**
     * @see R.styleable.CustomFooterView_mainButtonText
     */
    var mainButtonText: String? = null
        set(value) {
            field = value
            setMainButtonText()
        }

    init {
        LayoutInflater.from(context).inflate(
            /* resource = */ R.layout.custom_footer,
            /* root = */ this,
            /* attachToRoot = */ true
        )

        orientation = VERTICAL

        context.theme.obtainStyledAttributes(
            /* set = */ attrs,
            /* attrs = */ R.styleable.CustomFooterView,
            /* defStyleAttr = */ 0,
            /* defStyleRes = */ 0
        ).apply {
            setupStyledAttributesView()
        }
    }

    private fun TypedArray.setupStyledAttributesView() {
        try {
            binding.footerText.text = getString(
                R.styleable.CustomFooterView_footerText
            ) ?: footerText

            binding.footerSecondaryAction.text = getString(
                R.styleable.CustomFooterView_secondaryButtonText
            ) ?: secondaryButtonText

            binding.footerMainAction.text = getString(
                R.styleable.CustomFooterView_mainButtonText
            ) ?: mainButtonText
        } finally {
            recycle()
        }
    }

    private fun setFooterText() {
        footerText?.let {
            binding.footerText.text = footerText
            binding.footerText.isVisible = true
        } ?: run {
            binding.footerText.isVisible = false
        }
    }

    private fun setSecondaryButtonText() {
        secondaryButtonText?.let {
            binding.footerSecondaryAction.text = secondaryButtonText
            binding.footerSecondaryAction.isVisible = true
        } ?: run {
            binding.footerSecondaryAction.isVisible = false
        }
    }

    private fun setMainButtonText() {
        mainButtonText?.let {
            binding.footerMainAction.text = mainButtonText
            binding.footerMainAction.isVisible = true
        } ?: run {
            binding.footerMainAction.isVisible = false
        }
    }
}
