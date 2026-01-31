package com.example.customviewcookbook.features.navigation

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private val binding: ActivitySecondBinding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
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

        if (requestCode == 123) {
            binding.title.text = data?.getStringExtra("terceira_tela")
        }
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            setResult(
                    123,
                    Intent().apply {
                        putExtra("segunda_tela","${binding.inputText.text.toString()} | ${binding.title.text}")
                    }
            )

            finish()
        }

        binding.button.setOnClickListener {
            val intent = ThirdActivity.createIntent(this)
            val requestCode = 123
            startActivityForResult(intent, requestCode)
        }
    }

    companion object {

        /**
         * Creates an [Intent] for [SecondActivity].
         */
        fun createIntent(context: Context): Intent = Intent(
                context,
                SecondActivity::class.java
        )
    }
}
