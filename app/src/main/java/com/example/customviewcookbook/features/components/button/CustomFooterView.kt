package com.example.customviewcookbook.features.components.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.CustomFooterBinding

/**
 * Custom footer view.
 */
internal class CustomFooterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomFooterBinding = CustomFooterBinding.inflate(
        /* inflater = */ LayoutInflater.from(context),
        /* parent = */ this,
        /* attachToParent = */ true
    )

    /**
     * @see R.styleable.CustomFooterView_secondaryButtonText
     */
    var secondaryButtonText: String? = null
        set(value) {
            field = value
            setSecondaryButtonText()
        }

    /**
     *
     */
    var secondaryListener: (() -> Unit)? = null
        set(value) {
            field = value
            setSecondaryListener()
        }

    /**
     *
     */
    var mainButtonListener: (() -> Unit)? = null
        set(value) {
            field = value
            setMainButtonListener()
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
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.CustomFooterView,
            defStyleAttr = defStyleAttr,
            defStyleRes = defStyleRes
        ) {
            setupStyledAttributesView()
        }
    }

    private fun TypedArray.setupStyledAttributesView() {
        secondaryButtonText = getString(
            R.styleable.CustomFooterView_secondaryButtonText
        ) ?: secondaryButtonText

        mainButtonText = getString(
            R.styleable.CustomFooterView_mainButtonText
        ) ?: mainButtonText
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

    private fun setMainButtonListener() {
        binding.footerMainAction.setOnClickListener { mainButtonListener?.invoke() }
    }

    private fun setSecondaryListener() {
        binding.footerSecondaryAction.setOnClickListener { secondaryListener?.invoke() }
    }
}
