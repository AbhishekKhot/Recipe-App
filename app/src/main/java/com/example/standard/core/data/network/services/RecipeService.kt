package com.example.standard.core.data.network.services

import com.example.standard.core.constants.K
import com.example.standard.core.data.network.model.NetworkRecipe
import com.example.standard.core.data.network.model.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RecipeService {
    @GET(K.COMPLEX_SEARCH_PATH)
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("number") number:  Int,
        @Query("offset") offset: Int
    ): RecipeSearchResponse

    @GET(K.COMPLEX_SEARCH_PATH)
    suspend fun searchRecipes(
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("number") number:  Int,
        @Query("offset") offset: Int,
        @QueryMap options: Map<String, String>
    ): RecipeSearchResponse

    @GET(K.RECIPE_INFO_PATH)
    suspend fun requestRecipeInformation(
        @Path("id") id: Int
    ): NetworkRecipe
}