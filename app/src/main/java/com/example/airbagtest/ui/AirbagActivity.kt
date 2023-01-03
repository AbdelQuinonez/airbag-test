package com.example.airbagtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.airbagtest.databinding.AirbagActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirbagActivity : AppCompatActivity() {

    private lateinit var binding: AirbagActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AirbagActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}