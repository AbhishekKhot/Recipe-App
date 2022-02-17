package com.example.standard.core.data.database.entities

import androidx.room.*
import com.example.standard.core.domain.model.Ingredient
import com.example.standard.core.domain.model.Instruction
import com.example.standard.core.domain.model.Recipe

@Entity(tableName = "recipes")
data class DatabaseRecipe(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "source_name")
    val sourceName: String?,
    @ColumnInfo(name = "source_url")
    val sourceUrl: String?,
    @ColumnInfo(name = "ready_in_minutes")
    val readyInMinutes: Int?,
    val servings: Int?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val summary: String?,
    val score: Float?
)

data class DatabaseRecipeInformation(
    @Embedded val recipe: DatabaseRecipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val instructions: List<RecipeInstructions>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val ingredients: List<RecipeIngredient>
)


fun DatabaseRecipeInformation.asDomainModel(): Recipe {
    return Recipe(
        id = recipe.id,
        title = recipe.title,
        sourceName = recipe.sourceName,
        sourceUrl = recipe.sourceUrl,
        imageUrl = recipe.imageUrl,
        readyInMinutes = recipe.readyInMinutes,
        servings = recipe.servings,
        summary = recipe.summary,
        score = recipe.score,
        ingredients = ingredients.map { it.asDomainModel() },
        instructions = instructions.map { it.asDomainModel() }
    )
}

fun RecipeIngredient.asDomainModel() = Ingredient(id, name, original, amount, unit)

fun RecipeInstructions.asDomainModel() = Instruction(number, step)
