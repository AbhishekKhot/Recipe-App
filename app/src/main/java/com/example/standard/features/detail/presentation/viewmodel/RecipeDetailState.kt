package com.example.standard.features.detail.presentation.viewmodel

import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.error.Failure

sealed class RecipeDetailState {
    abstract val recipe: Recipe?

    data class Loading(override val recipe: Recipe?) : RecipeDetailState()

    data class Error(
        override val recipe: Recipe?,
        val failure: Failure
    ) : RecipeDetailState()

    data class Success(override val recipe: Recipe?) : RecipeDetailState()
}
