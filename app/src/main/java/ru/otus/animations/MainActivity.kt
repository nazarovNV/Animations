package ru.otus.animations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.otus.animations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.pinkCircle.setOnClickListener {
            binding.pinkCircle.animatePinkCircle()
            binding.purpleCircle.animatePurpleCircle()
        }
        binding.purpleCircle.setOnClickListener {
            binding.pinkCircle.animatePinkCircle()
            binding.purpleCircle.animatePurpleCircle()
        }

        binding.tealCirclesView.setOnClickListener {
            binding.tealCirclesView.animateTealCircle()
        }
        setContentView(binding.root)
    }
}