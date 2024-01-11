package com.example.star_wars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.star_wars.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for the Application.
 * @author by Harshil Gupta
 *
 * This is the entry point of the application as well as only activity in the project.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}