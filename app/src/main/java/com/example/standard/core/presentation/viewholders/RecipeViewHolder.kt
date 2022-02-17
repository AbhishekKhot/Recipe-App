package com.example.standard.core.presentation.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.standard.R
import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.presentation.dispatchers.RecipeEventDispatcher
import com.example.standard.core.utils.loadImage
import com.example.standard.databinding.ViewRecipeBinding

class RecipeViewHolder(
    private val binding: ViewRecipeBinding,
    private val eventDispatcher: RecipeEventDispatcher
): RecyclerView.ViewHolder(binding.root)  {
    companion object {
        fun create(parent: ViewGroup, eventDispatcher: RecipeEventDispatcher): RecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_recipe, parent, false)
            val binding = ViewRecipeBinding.bind(view)
            return RecipeViewHolder(binding, eventDispatcher)
        }
    }

    fun bind(recipe: Recipe) {
        val defaultAuthor = itemView.context.getString(R.string.unknown)
        binding.recipeContainer.transitionName = recipe.id.toString()
        binding.textName.text = recipe.title
        binding.textAuthor.text = itemView.context.getString(
            R.string.by_source,
            recipe.sourceName ?: defaultAuthor
        )
        binding.textTime.text = itemView.context.getString(
            R.string.minutes_label,
            recipe.readyInMinutes ?: 0
        )
        loadImage(binding.ivRecipe, recipe.imageUrl)
        binding.cardRecipe.setOnClickListener {
            eventDispatcher.onRecipePressed(recipe, binding.recipeContainer)
        }
    }

    object RecipeComparator : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}