package com.example.standard.features.favorities.presentation.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.standard.core.utils.loadImage
import com.example.standard.databinding.ViewFavoriteRecipeBinding
import com.example.standard.features.favorities.domain.FavoriteRecipe
import com.example.standard.features.favorities.presentation.dispatchers.FavoriteRecipeEventDispatcher

class FavoriteRecipeViewHolder(
    private val binding: ViewFavoriteRecipeBinding,
    private val eventDispatcher: FavoriteRecipeEventDispatcher
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(
            parent: ViewGroup,
            eventDispatcher: FavoriteRecipeEventDispatcher
        ): FavoriteRecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewFavoriteRecipeBinding.inflate(inflater, parent, false)
            return FavoriteRecipeViewHolder(binding, eventDispatcher)
        }
    }

    fun bind(recipe: FavoriteRecipe, isSelecting: Boolean) {
        binding.cardView.transitionName = recipe.id.toString()
        binding.tvName.text = recipe.data.title
        loadImage(binding.ivRecipe, recipe.data.imageUrl)

        binding.checkbox.isVisible = isSelecting
        binding.selectingOverlay.isVisible = isSelecting

        binding.checkbox.isChecked = recipe.isSelected && isSelecting

        binding.overlayView.setOnClickListener {
            eventDispatcher.onFavoriteRecipePressed(recipe, binding.cardView)
        }
        binding.overlayView.setOnLongClickListener {
            eventDispatcher.onFavoriteRecipeLongPressed(recipe)
            true
        }
    }

    object FavoriteRecipeComparator : DiffUtil.ItemCallback<FavoriteRecipe>() {
        override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem == newItem
        }
    }
}