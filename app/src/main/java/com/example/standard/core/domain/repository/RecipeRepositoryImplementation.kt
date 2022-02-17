package com.example.standard.core.domain.repository

import com.example.standard.core.data.database.dao.RecipeDao
import com.example.standard.core.data.database.entities.RecipeIngredient
import com.example.standard.core.data.database.entities.RecipeInstructions
import com.example.standard.core.data.database.entities.DatabaseRecipe
import com.example.standard.core.data.database.entities.asDomainModel
import com.example.standard.core.data.network.model.asDomainModel
import com.example.standard.core.data.network.services.RecipeAPI
import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.domain.model.toDatabaseModel
import com.example.standard.core.error.Failure
import com.example.standard.core.error.toFailure
import com.example.standard.core.utils.Either
import com.example.standard.core.utils.left
import com.example.standard.core.utils.right
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImplementation @Inject constructor(
    private val API: RecipeAPI,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int
    ): Either<Failure, List<Recipe>> {
        return try {
            val result = API.searchRecipes(query, addRecipeInformation, number, offset)
            val recipes = result.results.map { it.asDomainModel() }
            right(recipes)
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun searchRecipes(
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int,
        options: Map<String, String>
    ): Either<Failure, List<Recipe>> {
        return try {
            val result = API.searchRecipes(addRecipeInformation, number, offset, options)
            val recipes = result.results.map { it.asDomainModel() }
            right(recipes)
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun requestRecipeInformation(
        id: Int
    ): Either<Failure, Recipe> {
        return try {
            val response = API.requestRecipeInformation(id)
            right(response.asDomainModel())
        } catch (e: Throwable) {
            left(e.toFailure())
        }
    }

    override fun requestFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipesWithInformation().map { dbRecipe ->
            dbRecipe.map { it.asDomainModel() }
        }
    }

    override suspend fun saveFavoriteRecipe(recipe: Recipe) {
        val model = recipe.toDatabaseModel()
        recipeDao.insertRecipe(
            recipe = model.recipe,
            ingredients = model.ingredients,
            instructions = model.instructions
        )
    }

    override fun requestFavoriteRecipeById(id: Int): Flow<Recipe?> {
        return recipeDao.getRecipeById(id).map { it?.asDomainModel() }
    }

    override suspend fun deleteFavoriteRecipe(recipe: Recipe) {
        val model = recipe.toDatabaseModel()
        recipeDao.deleteRecipe(
            recipe = model.recipe,
            ingredients = model.ingredients,
            instructions = model.instructions
        )
    }

    override suspend fun deleteMultipleFavorites(recipes: List<Recipe>) {
        val dbRecipes = mutableListOf<DatabaseRecipe>()
        val dbIngredients = mutableListOf<RecipeIngredient>()
        val dbInstructions = mutableListOf<RecipeInstructions>()

        for (recipe in recipes) {
            val model = recipe.toDatabaseModel()
            dbRecipes.add(model.recipe)
            dbIngredients.addAll(model.ingredients)
            dbInstructions.addAll(model.instructions)
        }

        recipeDao.deleteMultipleRecipes(dbRecipes, dbIngredients, dbInstructions)
    }
}