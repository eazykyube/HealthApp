package com.example.healthapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthapp.databinding.FragmentCreateHabitBinding

class CreateHabitFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCreateHabitBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.createButton.setOnClickListener {

        }

        return binding.root

    }

}