package com.example.standard.features.favorities.presentation.dispatchers

import android.view.View
import com.example.standard.features.favorities.domain.FavoriteRecipe

interface FavoriteRecipeEventDispatcher {
    fun onFavoriteRecipePressed(recipe: FavoriteRecipe, view: View)

    fun onFavoriteRecipeLongPressed(recipe: FavoriteRecipe)
}
