package com.example.customviewcookbook.banner

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ViewBannerHighlightBinding
import com.google.android.material.card.MaterialCardView

class BannerHighlight @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // Infla o layout usando ViewBinding
    private val binding: ViewBannerHighlightBinding =
        ViewBannerHighlightBinding.inflate(LayoutInflater.from(context), this, true)

    // Listener para o botão de fechar
    var onCloseClickListener: (() -> Unit)? = null

    init {
        // Processa atributos customizados
        attrs?.let { processAttributes(it, defStyleAttr) }

        // Configura listener do botão de fechar
        binding.closeButton.setOnClickListener {
            onCloseClickListener?.invoke()
        }
    }

    private fun processAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BannerHighlight,
            defStyleAttr,
            0
        )

        try {
            // Cor da borda
            val strokeColor = typedArray.getColor(
                    R.styleable.BannerHighlight_strokeColor,
                    ContextCompat.getColor(context, R.color.red)
            )
            setStrokeColor(strokeColor)

            // Ícone
            val iconDrawable = typedArray.getDrawable(R.styleable.BannerHighlight_icon)
            setIcon(iconDrawable)

            // Textos
            val title = typedArray.getString(R.styleable.BannerHighlight_title)
            val description = typedArray.getString(R.styleable.BannerHighlight_description)
            setTitle(title)
            setDescription(description)

            // Botão de fechar
            val showClose = typedArray.getBoolean(
                    R.styleable.BannerHighlight_showCloseButton,
                    true
            )
            setCloseButtonVisibility(showClose)
        } finally {
            typedArray.recycle()
        }
    }

    // Métodos públicos para configurar a view programaticamente
    /**
     *
     */
    fun setTitle(title: String?) {
        binding.title.text = title
        binding.title.visibility = if (title.isNullOrEmpty()) GONE else VISIBLE
    }

    /**
     *
     */
    fun setDescription(description: String?) {
        binding.description.text = description
        binding.description.visibility = if (description.isNullOrEmpty()) GONE else VISIBLE
    }

    /**
     *
     */
    fun setIcon(drawable: Drawable?) {
        binding.icon.setImageDrawable(drawable)
        binding.icon.visibility = if (drawable != null) VISIBLE else GONE
    }

    /**
     *
     */
    fun setStrokeColor(@ColorInt color: Int) {
        binding.cardContainer.strokeColor = color
    }

    /**
     *
     */
    fun setCloseButtonVisibility(visible: Boolean) {
        binding.closeButton.visibility = if (visible) VISIBLE else GONE
    }
}
