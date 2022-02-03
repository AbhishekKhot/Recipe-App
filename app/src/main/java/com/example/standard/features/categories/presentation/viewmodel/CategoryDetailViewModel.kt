package com.example.standard.features.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.standard.core.constants.K
import com.example.standard.core.data.network.services.RecipeService
import com.example.standard.core.domain.model.Recipe
import com.example.standard.features.categories.domain.model.CategoryItem
import com.example.standard.features.categories.domain.repository.FilteredRecipesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val service: RecipeService
) :ViewModel() {

    var recipesFlow: Flow<PagingData<Recipe>>? = null

    fun requestRecipesForCategory(categoryItem: CategoryItem) {
        recipesFlow = Pager(PagingConfig(K.RECIPES_PER_PAGE)) {
            val options = mapOf(categoryItem.type to categoryItem.name)
            FilteredRecipesPagingSource(service, options)
        }.flow
            .cachedIn(viewModelScope)
    }
}