package com.example.standard.ui.categories

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.standard.R
import com.example.standard.core.domain.model.Recipe
import com.example.standard.core.error.toFailure
import com.example.standard.core.presentation.adapters.PagingLoadStateAdapter
import com.example.standard.core.presentation.adapters.RecipePagingAdapter
import com.example.standard.core.presentation.dispatchers.RecipeEventDispatcher
import com.example.standard.core.utils.applyTopWindowInsets
import com.example.standard.core.utils.bindNetworkImage
import com.example.standard.core.utils.configureStatusBar
import com.example.standard.core.utils.navigateToRecipeDetail
import com.example.standard.databinding.FragmentCategoryDetailBinding
import com.example.standard.features.categories.presentation.viewmodel.CategoryDetailViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryDetailFragment : Fragment(), RecipeEventDispatcher{
    private val viewModel:CategoryDetailViewModel by activityViewModels()
    private val args:CategoryDetailFragmentArgs by navArgs()
    private lateinit var adapter: RecipePagingAdapter

    private var _binding: FragmentCategoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RecipePagingAdapter(this)
        viewModel.requestRecipesForCategory(args.category)
        configureTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar(false)
        _binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.transitionName = args.category.name

        binding.btnBack.applyTopWindowInsets()

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = adapter.withLoadStateFooter(PagingLoadStateAdapter())

        binding.tvTitle.text = args.category.name
        bindNetworkImage(binding.ivCategoryItem,args.category.imageUrl)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.errorLayout.btnRetry.setOnClickListener {
            adapter.refresh()
        }
        setupObservers()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecipePressed(recipe: Recipe, view: View) {
        navigateToRecipeDetail(recipe, view)
    }

    private fun setupObservers() {
        adapter.addLoadStateListener { loadStates ->
            val loadState = loadStates.source.refresh
            binding.progressLayout.spinView.isVisible = loadState is LoadState.Loading
            binding.rvRecipes.isVisible = loadState is LoadState.NotLoading && adapter.itemCount > 0
            binding.errorLayout.errorView.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                val failure = loadState.error.toFailure()
                binding.errorLayout.tvErrorSubtitle.text = failure.translate(requireContext())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun configureTransitions() {
        val duration = resources.getInteger(R.integer.page_transition_duration)
        val color = requireContext().getColor(R.color.colorBackground)
        val transition = MaterialContainerTransform().apply {
            this.duration = duration.toLong()
            containerColor = color
            drawingViewId = R.id.nav_host_fragment_activity_main
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}