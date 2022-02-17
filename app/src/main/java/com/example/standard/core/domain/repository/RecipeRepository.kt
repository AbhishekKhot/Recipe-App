package com.example.standard.core.domain.repository

import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.error.Failure
import com.example.standard.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int
    ): Either<Failure, List<Recipe>>

    suspend fun searchRecipes(
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int,
        options: Map<String, String>
    ): Either<Failure, List<Recipe>>

    suspend fun requestRecipeInformation(id: Int): Either<Failure, Recipe>

    fun requestFavoriteRecipes(): Flow<List<Recipe>>

    suspend fun saveFavoriteRecipe(recipe: Recipe)

    fun requestFavoriteRecipeById(id: Int): Flow<Recipe?>

    suspend fun deleteFavoriteRecipe(recipe: Recipe)

    suspend fun deleteMultipleFavorites(recipes: List<Recipe>)
}