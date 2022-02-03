package com.example.standard.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.standard.core.constants.AppConstants.RECIPES_PER_PAGE
import com.example.standard.core.data.network.services.RecipeAPI
import com.example.standard.core.domain.model.Recipe
import com.example.standard.features.search.repository.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val API: RecipeAPI
) : ViewModel() {

    val recipesFlow: Flow<PagingData<Recipe>>
    var query = ""

    init {
        recipesFlow = Pager(
            config = PagingConfig(RECIPES_PER_PAGE),
            pagingSourceFactory = ::createPagingSource
        ).flow
            .cachedIn(viewModelScope)
    }

    private fun createPagingSource(): SearchPagingSource {
        return SearchPagingSource(API, query)
    }
}