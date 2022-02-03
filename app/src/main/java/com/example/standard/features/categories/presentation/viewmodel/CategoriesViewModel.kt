package com.example.standard.features.categories.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standard.features.categories.constants.categoriesList
import com.example.standard.features.categories.domain.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesViewModel: ViewModel() {
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()

    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        viewModelScope.launch {
            delay(320)
            _categories.value = categoriesList.toList()
        }
    }
}