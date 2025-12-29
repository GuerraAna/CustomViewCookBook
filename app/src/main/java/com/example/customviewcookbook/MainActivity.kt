package com.example.customviewcookbook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.customviewcookbook.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

internal class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by lazy { MainViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        adjustSystemInsets()
        setupViewModel()
    }

    private fun adjustSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            )

            insets
        }
    }

    private fun setupViewModel() {
        viewModel.startBannerCountUp()

        lifecycleScope.launch {
            viewModel.state.collect { bannerState ->
                when (bannerState) {
                    is BannerState.Loading -> onBannerLoading(
                            current = bannerState.current,
                            total = bannerState.total
                    )

                    is BannerState.Success -> onBannerSuccess()
                    is BannerState.Error -> onBannerError()
                }
            }
        }
    }

    private fun onBannerLoading(current: Int?, total: Int?) {
        val hasValidValues = current != null && total != null

        if (hasValidValues) {
            binding.bannerHighlight.title = getString(R.string.banner_start_counter_title)
            binding.bannerHighlight.description = getString(
                    R.string.banner_start_counter_description,
                    current,
                    total
            )
        } else {
            binding.bannerHighlight.title = getString(R.string.banner_initialize_counter_title)
            binding.bannerHighlight.description = getString(
                    R.string.banner_initialize_counter_description
            )
        }

        binding.bannerHighlight.hasCloseButton = false
        binding.bannerHighlight.hasProgressIndicator = true
    }

    private fun onBannerSuccess() {
        binding.bannerHighlight.hasProgressIndicator = false

        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                /* context = */ this,
                /* resId = */ R.drawable.outline_check
        )

        binding.bannerHighlight.strokeColor = null
        binding.bannerHighlight.title = getString(R.string.banner_success_title)
        binding.bannerHighlight.description = getString(R.string.banner_success_description)
        binding.bannerHighlight.hasCloseButton = false
    }

    private fun onBannerError() {
        binding.bannerHighlight.hasProgressIndicator = false
        binding.bannerHighlight.onCloseClickListener = { binding.bannerHighlight.isVisible = false }

        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                /* context = */ this,
                /* resId = */ R.drawable.ic_outline_error
        )

        binding.bannerHighlight.strokeColor = ContextCompat.getColor(this, R.color.red)
        binding.bannerHighlight.title = getString(R.string.banner_error_title)
        binding.bannerHighlight.description = getString(R.string.banner_error_description)
        binding.bannerHighlight.hasCloseButton = true
    }
}
