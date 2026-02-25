package com.example.customviewcookbook.home

import android.animation.AnimatorInflater
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.customviewcookbook.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.R
import com.example.customviewcookbook.features.components.button.CustomFooterView
import com.example.customviewcookbook.features.components.button.vibrateClick
import com.example.customviewcookbook.features.item.Feature
import com.example.customviewcookbook.features.item.FeaturesAdapter
import com.example.customviewcookbook.features.item.FeaturesResponse
import com.example.customviewcookbook.features.item.FeaturesAdapter.FeatureClickListener
import kotlinx.coroutines.delay

/**
 *
 */
internal class HomeActivity :
    AppCompatActivity(),
    FeatureClickListener {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val featuresAdapter by lazy { FeaturesAdapter(this) }

    private val viewModel: HomeViewModel by lazy { HomeViewModel(application) }

    override fun onFeatureClick(feature: Class<out AppCompatActivity>) {
        val intent = Intent(this, feature)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupWindowInsets()
        setupListeners()
        initializeViewModel()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
//        val boomAnim = AnimationUtils.loadAnimation(this, R.anim.boom_button)
//        binding.footer.text = "Abrir a documentação do Material"
//
//        binding.footer.setOnClickListener {
//            lifecycleScope.launch {
//                it.startAnimation(boomAnim)
//                delay(boomAnim.duration)
//                onMainActionClicked()
//            }
//        }

        val boomAnim = AnimationUtils.loadAnimation(this, R.anim.boom_button)
        binding.footer.text = "Abrir a documentação do Material"

        val scaleAnimator = AnimatorInflater.loadAnimator(
            this,
            R.animator.btn_click_scale
        )

        binding.footer.setOnClickListener { view ->
            lifecycleScope.launch {
                scaleAnimator.setTarget(view)
                scaleAnimator.start()
                vibrateClick()
//                it.startAnimation(boomAnim)
                delay(boomAnim.duration)
//                onMainActionClicked()
            }
        }
    }

    private fun onMainActionClicked() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = "https://developer.android.com/jetpack/compose/documentation".toUri()
        startActivity(intent)
    }

    private fun initializeViewModel() {
        viewModel.initialize()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeState.Loading -> onLoading()
                    is HomeState.Success -> onSuccess(response = state.response)
                    is HomeState.Error -> onError(message = state.message)
                }
            }
        }
    }

    private fun onLoading() {
        binding.progressIndicator.isVisible = true
        binding.featuresList.isVisible = false
    }

    private fun onSuccess(response: FeaturesResponse) {
        showFeatures(features = response.features)
        binding.progressIndicator.isVisible = false
    }

    private fun onError(message: String) {
        binding.progressIndicator.isVisible = false
        binding.featuresList.isVisible = false
    }

    private fun showFeatures(features: List<Feature>) {
        featuresAdapter.submitList(features)
        binding.featuresList.adapter = featuresAdapter
        binding.featuresList.isVisible = true

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        binding.featuresList.addItemDecoration(SpacingItemDecoration(spacing))
    }
}

class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spacing
        outRect.right = spacing
        outRect.bottom = spacing

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing
        }
    }
}
