package com.example.standard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.standard.R

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        supportActionBar?.hide()
    }
}