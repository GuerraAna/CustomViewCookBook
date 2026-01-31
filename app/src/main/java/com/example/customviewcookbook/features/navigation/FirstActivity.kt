package com.example.customviewcookbook.features.navigation

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ActivityFirstBinding

internal class FirstActivity : AppCompatActivity() {

    private val binding: ActivityFirstBinding by lazy {
        ActivityFirstBinding.inflate(layoutInflater)
    }

    private var firstText = ""

    private var secondTextResult = ""

    private var thirdTextResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupWindowInsets()
        setupListeners()
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

    private fun setupTextResult(
        requestCode: Int,
        data: Intent?
    ) {
        if (requestCode == RequestCode.NAVIGATION_FLOW) {
            firstText = binding.inputText.text.toString()

            secondTextResult = data?.getStringExtra(
                    ExtraResultsCode.SECOND_TEXT
            ) ?: "Tá fazio!"

            thirdTextResult = data?.getStringExtra(
                    ExtraResultsCode.THIRD_TEXT
            ) ?: "Tá fazio!"

            binding.textResult.text = getTextResult()
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
        onBackPressedDispatcher.addCallback { goBackToHomeActivity() }
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.button.setOnClickListener { goToSecondActivity() }
    }

    private fun goBackToHomeActivity() {
        Log.d("Results", "FirstActivity - ${getTextResult()}")
        finish()
    }

    private fun goToSecondActivity() {
        val firstText = binding.inputText.text.toString()

        val intent = SecondActivity.createIntent(
                context = this,
                firstText = firstText
        )

        startActivityForResult(intent, RequestCode.NAVIGATION_FLOW)
    }

    private fun getTextResult(): String = getString(
            R.string.total_text_result,
            firstText,
            secondTextResult,
            thirdTextResult
    )

    private object RequestCode {
        const val NAVIGATION_FLOW = 123
    }

    private object ExtraResultsCode {
        const val SECOND_TEXT = "second_text"
        const val THIRD_TEXT = "third_text"
    }
}
