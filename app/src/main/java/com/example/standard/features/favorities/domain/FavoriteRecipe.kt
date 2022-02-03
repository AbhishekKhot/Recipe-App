package com.example.standard.features.favorities.domain

import com.example.standard.core.domain.model.Recipe

data class FavoriteRecipe(
    val data: Recipe,
    val isSelected: Boolean
){
    val id: Int get() = data.id
}
