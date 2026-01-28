package com.example.customviewcookbook.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.customviewcookbook.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch
import androidx.core.net.toUri

internal class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val featuresAdapter by lazy { FeaturesAdapter() }

    private val viewModel: HomeViewModel by lazy { HomeViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        setupWindoInsets()
        setupListeners()
        initializeViewModel()
    }

    private fun setupWindoInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListeners() {
        binding.materialDocBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = "https://developer.android.com/jetpack/compose/documentation".toUri()
            startActivity(intent)
        }
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
    }
}