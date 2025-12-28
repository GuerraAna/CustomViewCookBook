package com.example.customviewcookbook

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewcookbook.databinding.ActivityMainBinding

internal class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val banner = binding.bannerHighlight
        banner.icon = AppCompatResources.getDrawable(this, R.drawable.ic_launcher_foreground)
        banner.title = "Novo Título"
        banner.description = "Sit totam omnis reiciendis voluptas qui dolore. Animi explicabo quod odio vitae assumenda consequuntur sed explicabo. Amet ut rerum aliquid corrupti incidunt voluptatem. Voluptates autem eveniet iusto officia possimus dolorem quod voluptatem. Minima aliquam nobis et qui ratione minus. Nulla fugit necessitatibus voluptas voluptas. Et id et nihil consequatur quae aut. Quis dolores nam provident nam earum ut."
        banner.isError = true
        banner.onCloseClickListener = { banner.visibility = View.GONE }
    }
}
