package com.sampath.directwhatsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codecat.directwhatsapp.databinding.ActivityPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.loadUrl("file:///android_asset/privacy_policy.html")
    }

}