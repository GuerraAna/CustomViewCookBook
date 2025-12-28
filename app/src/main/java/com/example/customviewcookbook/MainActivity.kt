package com.example.customviewcookbook

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        initializeViewModel()
        observeViewModel()
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

    private fun observeViewModel() {
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
        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_foreground
        )

        binding.bannerHighlight.title = "Iniciando a Contagem..."
        binding.bannerHighlight.description = "A contagem começará em breve."
        binding.bannerHighlight.isError = false
    }

    private fun onBannerLoading(current: Int, total: Int) {
        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_foreground
        )

        binding.bannerHighlight.title = "Contando..."
        binding.bannerHighlight.description = "Item $current de $total"
        binding.bannerHighlight.isError = false
    }

    private fun onBannerSuccess() {
        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_foreground
        )

        binding.bannerHighlight.title = "Concluído!"
        binding.bannerHighlight.description = "A contagem terminou com sucesso."
        binding.bannerHighlight.isError = false
    }

    private fun onBannerError() {
        binding.bannerHighlight.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.ic_launcher_foreground
        )

        binding.bannerHighlight.title = "Erro!"
        binding.bannerHighlight.description = "Ocorreu um erro durante a contagem."

        binding.bannerHighlight.onCloseClickListener = {
            binding.bannerHighlight.visibility = View.GONE
        }

        binding.bannerHighlight.isError = true
    }

    private fun initializeViewModel() {
        viewModel.startBannerCountUp(10)
    }
}
