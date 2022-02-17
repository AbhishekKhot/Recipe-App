package com.example.standard.core.data.network.services

import com.example.standard.core.data.network.model.NetworkRecipe
import com.example.standard.core.data.network.model.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeAPI {
    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("number") number:  Int,
        @Query("offset") offset: Int
    ): RecipeSearchResponse

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("number") number:  Int,
        @Query("offset") offset: Int,
        @QueryMap options: Map<String, String>
    ): RecipeSearchResponse

    @GET("/recipes/{id}/information?includeNutrition=false")
    suspend fun requestRecipeInformation(
        @Path("id") id: Int
    ): NetworkRecipe
}