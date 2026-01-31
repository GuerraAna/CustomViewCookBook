package com.example.customviewcookbook.features.navigation

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val binding: ActivitySecondBinding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }

    private val firstText: String by lazy {
        intent.getStringExtra(Extras.FIRST_TEXT).orEmpty()
    }

    private var thirdText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupWindowInsets()
        setupListeners()
        setupFirstText()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        setupTextResult(requestCode, data)
    }

    private fun setupFirstText() {
        binding.firstText.text = firstText
    }

    private fun setupTextResult(
        requestCode: Int,
        data: Intent?
    ) {
        if (requestCode == RequestCodes.NAVIGATION_FLOW) {
            thirdText = data?.getStringExtra(ExtrasResultCode.THIRD_TEXT).orEmpty()
            binding.thirdText.text = thirdText
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

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
        onBackPressedDispatcher.addCallback { goBackToFirstActivity() }
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.button.setOnClickListener { goToThirdActivity() }
    }

    private fun goBackToFirstActivity() {
        setResult(
                ResultCodes.NAVIGATION_FLOW,
                Intent().apply {
                    val secondText = binding.inputText.text.toString()

                    putExtra(
                            ExtrasResultCode.SECOND_TEXT,
                            if (secondText == "") null else secondText
                    )

                    putExtra(
                            ExtrasResultCode.THIRD_TEXT,
                            thirdText
                    )
                }
        )

        finish()
    }

    private fun goToThirdActivity() {
        val firstText = binding.firstText.text.toString()
        val secondText = binding.inputText.text.toString()

        val intent = ThirdActivity.createIntent(
                context = this,
                firstText = firstText,
                secondText = secondText
        )

        startActivityForResult(intent, RequestCodes.NAVIGATION_FLOW)
    }

    private object RequestCodes {
        const val NAVIGATION_FLOW = 123
    }

    private object ResultCodes {
        const val NAVIGATION_FLOW = 123
    }

    private object Extras {
        const val FIRST_TEXT = "firstText"
    }

    private object ExtrasResultCode {
        const val SECOND_TEXT = "second_text"
        const val THIRD_TEXT = "third_text"
    }

    companion object {

        /**
         * Creates an [Intent] for [SecondActivity].
         */
        fun createIntent(
            context: Context,
            firstText: String
        ): Intent = Intent(
                context,
                SecondActivity::class.java
        ).apply {
            putExtra(Extras.FIRST_TEXT, firstText)
        }
    }
}
