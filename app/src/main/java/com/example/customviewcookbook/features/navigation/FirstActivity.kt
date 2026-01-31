package com.example.customviewcookbook.features.navigation

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
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
        val isFromSecondActivity = 123

        if (requestCode == isFromSecondActivity) {
            binding.title.text = data?.getStringExtra("segunda_tela")
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            // Go to SecondActivity
            val intent = SecondActivity.createIntent(this)
            val requestCode = 123
            startActivityForResult(intent, requestCode)
        }
    }
}
