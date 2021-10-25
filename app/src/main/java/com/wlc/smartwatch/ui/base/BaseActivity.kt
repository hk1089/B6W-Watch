package com.wlc.wearable.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wlc.smartwatch.databinding.ActivityBaseBinding

open class BaseActivity: AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}