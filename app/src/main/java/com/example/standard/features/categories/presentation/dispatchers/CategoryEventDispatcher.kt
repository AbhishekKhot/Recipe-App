package com.example.standard.features.categories.presentation.dispatchers

import android.view.View
import com.example.standard.features.categories.domain.model.CategoryItem

interface CategoryEventDispatcher {
    fun onCategoryPressed(category: CategoryItem, view: View)
}