package com.example.customviewcookbook.features.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.databinding.ActivityThirdBinding

internal class ThirdActivity : AppCompatActivity() {

    private val binding: ActivityThirdBinding by lazy {
        ActivityThirdBinding.inflate(layoutInflater)
    }

    private val firstText: String by lazy {
        intent.getStringExtra(Extras.FIRST_TEXT).orEmpty()
    }

    private val secondText: String by lazy {
        intent.getStringExtra(Extras.SECOND_TEXT).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupWindowInsets()
        setupListeners()
        setupFirstText()
        setupSecondText()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                    /* left = */ systemBars.left,
                    /* top = */ systemBars.top,
                    /* right = */ systemBars.right,
                    /* bottom = */ systemBars.bottom
            )

            insets
        }
    }

    private fun setupListeners() {
        onBackPressedDispatcher.addCallback { goBackToSecondActivity() }
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.continueButton.mainButtonListener = { onBackPressedDispatcher.onBackPressed() }

    }

    private fun goBackToSecondActivity() {
        setResult(
                ResultCode.THIRD_SCREEN,
                Intent().apply {
                    val thirdText = binding.inputText.text.toString()

                    putExtra(
                            ExtrasResultCode.THIRD_TEXT,
                            if (thirdText == "") null else thirdText
                    )
                }
        )

        finish()
    }

    private fun setupFirstText() {
        binding.firstText.text = firstText
    }

    private fun setupSecondText() {
        binding.secondText.text = secondText
    }

    private object ExtrasResultCode {
        const val THIRD_TEXT = "third_text"
    }

    private object Extras {
        const val FIRST_TEXT = "first_text"
        const val SECOND_TEXT = "second_text"
    }

    private object ResultCode {
        const val THIRD_SCREEN = 456
    }

    companion object {

        /**
         * Creates an [Intent] for [SecondActivity].
         */
        fun createIntent(
            context: Context,
            firstText: String,
            secondText: String
        ): Intent = Intent(
                context,
                ThirdActivity::class.java
        ).apply {
            putExtra(Extras.FIRST_TEXT, firstText)
            putExtra(Extras.SECOND_TEXT, secondText)
        }
    }
}
