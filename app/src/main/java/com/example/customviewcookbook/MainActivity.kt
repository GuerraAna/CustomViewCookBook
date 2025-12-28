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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Infla o layout usando ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Acessa a view através do binding
        val banner = binding.bannerHighlight
        banner.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_launcher_foreground))
        banner.setTitle("Novo Título")
        banner.setDescription("Nova descrição")
        banner.setStrokeColor(ContextCompat.getColor(this, R.color.red))

        // Define listener para o botão de fechar
        banner.setCloseButtonVisibility(true)
        banner.onCloseClickListener = {
            banner.visibility = View.GONE
            // Ou remova a view: (banner.parent as? ViewGroup)?.removeView(banner)
        }
    }
}
