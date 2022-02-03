package com.example.standard.features.detail.presentation.viewmodel

import androidx.lifecycle.*
import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _state = MutableLiveData<RecipeDetailState>()
    val state: LiveData<RecipeDetailState>
        get() = _state

    private var isFavorite = false

    var motionProgress = 0F

    fun requestRecipeInfo(recipe: Recipe) {
        viewModelScope.launch {
            _state.value = RecipeDetailState.Loading(recipe)
            val result = repository.requestRecipeInformation(recipe.id)
            val newState = result.fold(
                { failure ->
                    RecipeDetailState.Error(recipe, failure)
                },
                { recipe ->
                    RecipeDetailState.Success(recipe)
                }
            )
            _state.value = newState
        }
    }

    fun requestRecipeInfo(id: Int) {
        viewModelScope.launch {
            _state.value = RecipeDetailState.Loading(null)
            val result = repository.requestRecipeInformation(id)
            val newState = result.fold(
                { failure ->
                    RecipeDetailState.Error(null, failure)
                },
                { recipe ->
                    RecipeDetailState.Success(recipe)
                }
            )
            _state.value = newState
        }
    }

    fun presentRecipeInfo(recipe: Recipe) {
        _state.value = RecipeDetailState.Success(recipe)
    }

    fun retryRecipeRequest(id: String?) {
        if (id != null) {
            requestRecipeInfo(id.toInt())
        } else {
            _state.value?.recipe?.let {
                requestRecipeInfo(it)
            }
        }
    }

    fun saveOrDeleteRecipe() {
        if (isFavorite) {
            deleteFavoriteRecipe()
        } else {
            saveFavoriteRecipe()
        }

    }

    fun isFavorite(id: Int): LiveData<Boolean> {
        return repository.requestFavoriteRecipeById(id).map {
            isFavorite = it != null
            isFavorite
        }.asLiveData()
    }

    private fun saveFavoriteRecipe() {
        viewModelScope.launch {
            _state.value?.let { state ->
                if (state is RecipeDetailState.Success) {
                    repository.saveFavoriteRecipe(state.recipe!!)
                }
            }
        }
    }

    private fun deleteFavoriteRecipe() {
        viewModelScope.launch {
            _state.value?.recipe?.let { repository.deleteFavoriteRecipe(it) }
        }
    }
}