package com.example.standard.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.standard.core.utils.configureStatusBar
import com.example.standard.databinding.FragmentCategoriesBinding
import com.example.standard.features.categories.domain.model.CategoryItem
import com.example.standard.features.categories.presentation.adapters.CategoriesAdapter
import com.example.standard.features.categories.presentation.dispatchers.CategoryEventDispatcher
import com.example.standard.features.categories.presentation.viewmodel.CategoriesViewModel
import com.google.android.material.transition.Hold

class CategoriesFragment : Fragment(), CategoryEventDispatcher {

    private val viewModel:CategoriesViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold()
        adapter = CategoriesAdapter(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            binding.rvCategories.scheduleLayoutAnimation()
            adapter.submitList(categories)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvCategories.adapter = null
        _binding = null
    }

    override fun onCategoryPressed(category: CategoryItem, view: View) {
        val extras = FragmentNavigatorExtras(view to category.name)
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment(category)
        findNavController().navigate(action,extras)
    }
}