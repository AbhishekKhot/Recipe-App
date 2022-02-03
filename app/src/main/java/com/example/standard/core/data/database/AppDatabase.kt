package com.example.standard.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.standard.core.constants.K
import com.example.standard.core.data.database.dao.RecipeDao
import com.example.standard.core.data.database.entities.DatabaseIngredient
import com.example.standard.core.data.database.entities.DatabaseInstruction
import com.example.standard.core.data.database.entities.DatabaseRecipe

@Database(
    version = 1,
    exportSchema = false,
    entities = [DatabaseRecipe::class, DatabaseIngredient::class, DatabaseInstruction::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            K.DATABASE_NAME
        ).build()
    }
}