package com.example.standard.features.categories.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.standard.core.constants.AppConstants.RECIPES_PER_PAGE
import com.example.standard.core.data.network.model.asDomainModel
import com.example.standard.core.data.network.services.RecipeAPI
import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.error.HttpLimitExceededException
import retrofit2.HttpException

class FilteredRecipesPagingSource(
    private val API: RecipeAPI,
    private val options: Map<String, String>
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = RECIPES_PER_PAGE
            val offset = (pageNumber - 1) * pageSize
            val response = API.searchRecipes(
                options = options,
                addRecipeInformation = true,
                number = pageSize,
                offset = offset
            )
            val nextPageNumber = if (response.totalResults - pageSize > offset && offset < 900) {
                pageNumber + 1
            } else {
                null
            }
            return LoadResult.Page(
                data = response.results.map { it.asDomainModel() },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: HttpException) {
            if (e.code() == 402) {
                return LoadResult.Error(HttpLimitExceededException())
            }
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        TODO("Not yet implemented")
    }
}