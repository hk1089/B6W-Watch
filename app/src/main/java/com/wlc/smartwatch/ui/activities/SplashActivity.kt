package com.wlc.smartwatch.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.wlc.smartwatch.databinding.ActivitySplashBinding
import com.wlc.wearable.ui.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTask()
    }

    private fun initTask() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, DevicesActivity::class.java))
            finish()
        }, 2000)
    }
}