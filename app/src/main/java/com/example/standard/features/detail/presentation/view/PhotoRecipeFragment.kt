package com.example.standard.features.detail.presentation.view

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.standard.core.utils.applyTopWindowInsets
import com.example.standard.core.utils.bindNetworkImage
import com.example.standard.core.utils.configureStatusBar
import com.example.standard.databinding.FragmentPhotoRecipeBinding
import kotlinx.android.synthetic.main.fragment_photo_recipe.*
import kotlinx.android.synthetic.main.fragment_web_detail.btnBack


class PhotoRecipeFragment : Fragment() {

    private val args:PhotoRecipeFragmentArgs by navArgs()
    private var _binding: FragmentPhotoRecipeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val SHARED_IMAGE = "recipeImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        _binding = FragmentPhotoRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindNetworkImage(ivRecipe,args.imageUrl)
        btnBack.applyTopWindowInsets()
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}