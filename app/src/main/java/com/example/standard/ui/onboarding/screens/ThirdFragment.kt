package com.example.standard.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.standard.R
import kotlinx.android.synthetic.main.fragment_third.view.*

class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_mainActivity)
            onBoardingFinish()
        }
        return view
    }

    private fun onBoardingFinish() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}