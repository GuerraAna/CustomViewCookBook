package com.example.customviewcookbook.features.animation

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ActivityAnimationBinding
import com.example.customviewcookbook.features.components.button.vibrateClick
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 */
internal class AnimationActivity : AppCompatActivity() {

    private val binding: ActivityAnimationBinding by lazy {
        ActivityAnimationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boomAnim = AnimationUtils.loadAnimation(this, R.anim.boom_button)
        binding.footerFirst.text = "Abrir a documentação do Material"

        val scaleAnimator = AnimatorInflater.loadAnimator(
            this,
            R.animator.btn_click_scale
        )

        binding.footerFirst.setOnClickListener { view ->
            lifecycleScope.launch {
                scaleAnimator.setTarget(view)
                scaleAnimator.start()
                view.startAnimation(boomAnim)
                delay(boomAnim.duration)
            }
        }

        binding.footerVibration.setOnClickListener { view ->
            lifecycleScope.launch {
                vibrateClick()
                delay(boomAnim.duration)
            }
        }
    }
}