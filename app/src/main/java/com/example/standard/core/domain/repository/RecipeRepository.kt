package com.example.standard.core.domain.repository

import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.error.Failure
import com.example.standard.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    /**
     * Search recipes by [query].
     */
    suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int
    ): Either<Failure, List<Recipe>>

    /**
     * Search recipes by [options].
     */
    suspend fun searchRecipes(
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int,
        options: Map<String, String>
    ): Either<Failure, List<Recipe>>

    /**
     * Request the recipe information for the given [id].
     */
    suspend fun requestRecipeInformation(id: Int): Either<Failure, Recipe>

    /**
     * Request favorite recipes from the local database.
     */
    fun requestFavoriteRecipes(): Flow<List<Recipe>>

    /**
     * Save [recipe] in the database.
     */
    suspend fun saveFavoriteRecipe(recipe: Recipe)

    /**
     * Search for a specific favorite recipe.
     */
    fun requestFavoriteRecipeById(id: Int): Flow<Recipe?>

    /**
     * Delete [recipe] from the database.
     */
    suspend fun deleteFavoriteRecipe(recipe: Recipe)

    /**
     * Delete all [recipes] from the database.
     */
    suspend fun deleteMultipleFavorites(recipes: List<Recipe>)
}