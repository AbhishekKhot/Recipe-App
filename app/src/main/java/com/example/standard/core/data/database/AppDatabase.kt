package com.example.standard.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.standard.core.constants.AppConstants.DATABASE_NAME
import com.example.standard.core.data.database.dao.RecipeDao
import com.example.standard.core.data.database.entities.RecipeIngredient
import com.example.standard.core.data.database.entities.RecipeInstructions
import com.example.standard.core.data.database.entities.DatabaseRecipe

@Database(
    version = 1,
    exportSchema = false,
    entities = [DatabaseRecipe::class, RecipeIngredient::class, RecipeInstructions::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}