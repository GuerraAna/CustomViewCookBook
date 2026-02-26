package com.example.customviewcookbook.features.animation

import android.animation.AnimatorInflater
import android.os.Bundle
import android.os.VibrationEffect
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.customviewcookbook.R
import com.example.customviewcookbook.databinding.ActivityAnimationBinding
import com.example.customviewcookbook.features.components.button.VibrationStrength
import com.example.customviewcookbook.features.components.button.playSoundFromAssets
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
        setupWindowInsets()
        setupListeners()
    }

    private fun setupListeners() {
        setupBoomListener()
        setupVibrateListener()
        setupSoundListener()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupBoomListener(): Animation? {
        val boomAnim = AnimationUtils.loadAnimation(this, R.anim.boom_button)

        val scaleAnimator = AnimatorInflater.loadAnimator(
            this,
            R.animator.btn_click_scale
        )

        binding.footerBoom.setOnClickListener { view ->
            lifecycleScope.launch {
                scaleAnimator.setTarget(view)
                scaleAnimator.start()
                view.startAnimation(boomAnim)
                delay(boomAnim.duration)
            }
        }
        return boomAnim
    }

    private fun setupVibrateListener() {
        binding.footerSimpleVibration.setOnClickListener { vibrateClick() }
        binding.footerHardVibration.setOnClickListener { vibrateClick(VibrationStrength.MAXIMO) }
    }

    private fun setupSoundListener() {
        binding.footerSound.setOnClickListener {
            playSoundFromAssets("click-sound.mp3")
        }
    }
}