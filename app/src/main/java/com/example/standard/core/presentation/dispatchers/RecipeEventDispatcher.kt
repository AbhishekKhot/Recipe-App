package com.example.standard.core.presentation.dispatchers

import android.view.View
import com.example.standard.core.domain.model.Recipe

interface RecipeEventDispatcher {
    fun onRecipePressed(recipe: Recipe, view: View)
}