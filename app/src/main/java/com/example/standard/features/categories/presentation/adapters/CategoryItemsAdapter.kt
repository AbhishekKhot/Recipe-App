package com.example.standard.features.categories.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.standard.core.utils.loadImage
import com.example.standard.databinding.ViewHomeCategoryBinding
import com.example.standard.features.categories.domain.model.CategoryItem
import com.example.standard.features.categories.presentation.dispatchers.CategoryEventDispatcher

class CategoryItemsAdapter(
    private val items: List<CategoryItem>,
    private val eventDispatcher: CategoryEventDispatcher
): RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHomeCategoryBinding.inflate(inflater, parent, false)
        return CategoryItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CategoryItemsViewHolder(
        private val binding: ViewHomeCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryItem) {
            binding.cardView.transitionName = item.name
            binding.tvCategoryName.text = item.name
            loadImage(binding.ivCategoryItem, item.imageUrl)
            binding.tvCategoryName.setOnClickListener {
                eventDispatcher.onCategoryPressed(item, binding.cardView)
            }
        }
    }
}