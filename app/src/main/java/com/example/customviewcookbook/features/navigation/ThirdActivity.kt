package com.example.customviewcookbook.features.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private val binding: ActivityThirdBinding by lazy {
        ActivityThirdBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupWindowInsets()
        setupListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.continueButton.setOnClickListener {
            Toast.makeText(this, "Confirmado", Toast.LENGTH_SHORT).show()
            setResult(
                    123,
                    Intent().apply {
                        putExtra("terceira_tela", binding.inputText.text.toString())
                    }
            )

           finish()
        }
    }

    companion object {

        /**
         * Creates an [Intent] for [SecondActivity].
         */
        fun createIntent(context: Context): Intent = Intent(
                context,
                ThirdActivity::class.java
        )
    }
}