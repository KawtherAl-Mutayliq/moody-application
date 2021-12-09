package com.example.moodyapplication.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.FragmentSadMoodBinding

class SadMoodFragment : Fragment() {

  private lateinit var binding: FragmentSadMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSadMoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}