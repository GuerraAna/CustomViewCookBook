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
                    is BannerState.InitialLoading -> onBannerInitialLoading()

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

    private fun onBannerInitialLoading() {
        binding.bannerHighlight.icon = null
        binding.bannerHighlight.title = "Iniciando a Contagem..."
        binding.bannerHighlight.description = "A contagem começará em breve."
        binding.bannerHighlight.hasProgressIndicator = true
    }

    private fun onBannerLoading(current: Int, total: Int) {
        binding.bannerHighlight.icon = null
        binding.bannerHighlight.title = "Contando..."
        binding.bannerHighlight.description = "Item $current de $total"
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
        binding.bannerHighlight.title = "Concluído!"
        binding.bannerHighlight.description = "A contagem terminou com sucesso."
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
        binding.bannerHighlight.title = "Ocorreu um erro!"
        binding.bannerHighlight.description = "Desculpe, ocorreu um erro durante a contagem."
        binding.bannerHighlight.hasCloseButton = true
    }
}
