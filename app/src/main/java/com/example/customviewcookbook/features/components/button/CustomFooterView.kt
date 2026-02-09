package com.example.customviewcookbook.features.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.CustomFooterBinding
import com.example.yourapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

internal class CustomFooterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CustomFooterBinding = CustomFooterBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        LayoutInflater.from(context).inflate(R.layout.custom_footer, this, true)

        orientation = VERTICAL

        // Aplica atributos XML, se existirem
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomFooterView,
            0,
            0
        ).apply {
            try {
                binding.footerText.text = getString(
                    R.styleable.CustomFooterView_footerText
                ) ?: title.text

                binding.footerSecondaryAction.text = getString(
                    R.styleable.CustomFooterView_secondaryButtonText
                ) ?: secondaryButtonText.text

                binding.footerMainAction.text = getString(
                    R.styleable.CustomFooterView_mainButtonText
                ) ?: mainButtonText.text
            } finally {
                recycle()
            }
        }
    }

    fun setFooterText(text: CharSequence) {
        this.text.text = text
    }
}
