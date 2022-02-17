package com.example.standard.core.data.database.dao

import androidx.room.*
import com.example.standard.core.data.database.entities.RecipeIngredient
import com.example.standard.core.data.database.entities.RecipeInstructions
import com.example.standard.core.data.database.entities.DatabaseRecipe
import com.example.standard.core.data.database.entities.DatabaseRecipeInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Transaction
    @Query("SELECT * FROM recipes ORDER BY created_at DESC")
    fun getRecipesWithInformation(): Flow<List<DatabaseRecipeInformation>>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): Flow<DatabaseRecipeInformation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(
        recipe: DatabaseRecipe,
        ingredients: List<RecipeIngredient>,
        instructions: List<RecipeInstructions>
    )

    @Delete
    suspend fun deleteRecipe(
        recipe: DatabaseRecipe,
        ingredients: List<RecipeIngredient>,
        instructions: List<RecipeInstructions>
    )

    @Delete
    suspend fun deleteMultipleRecipes(
        recipes: List<DatabaseRecipe>,
        ingredients: List<RecipeIngredient>,
        instructions: List<RecipeInstructions>
    )
}