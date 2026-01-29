package com.example.customviewcookbook.home

import androidx.recyclerview.widget.RecyclerView
import com.example.customviewcookbook.databinding.FeaturesCardItemBinding

internal class FeaturesViewHolder(
    private val binding: FeaturesCardItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feature: Feature, listener: FeaturesAdapter.FeatureClickListener) {

        binding.root.setOnClickListener {
            listener.onFeatureClick(feature)
        }

//        binding.imageView.setImageDrawable(resource)
//        binding.titleTextView
//        binding.descriptionTextView


//        when (feature) {
//            Feature.BANNER_COMPONENT -> {
//                binding.imageView
//                binding.titleTextView
//                binding.descriptionTextView
//            }
//
//            Feature.NAVIGATION -> {
//                binding.imageView
//                binding.titleTextView
//                binding.descriptionTextView
//            }
//        }
    }
}
