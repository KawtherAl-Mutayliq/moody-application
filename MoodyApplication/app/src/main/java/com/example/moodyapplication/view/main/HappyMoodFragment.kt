package com.example.moodyapplication.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.databinding.FragmentHappyMoodBinding
import com.example.moodyapplication.view.adapter.HappyMusicAdapter
import com.example.moodyapplication.view.main.viewmodel.HappyMusicViewModel


class HappyMoodFragment : Fragment() {

    private lateinit var happyMoodAdapter : HappyMusicAdapter

    val happyMoodViewModel : HappyMusicViewModel by activityViewModels()

    private lateinit var binding: FragmentHappyMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHappyMoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        happyMoodAdapter = HappyMusicAdapter(requireActivity())
        binding.happyRecyclerview.adapter = happyMoodAdapter

        observers()
        happyMoodViewModel.callMusic()
    }

    private fun observers(){
        happyMoodViewModel.happyMusicLiveData.observe(viewLifecycleOwner, {
            binding.happyProgressBar.animate().alpha(0f).duration = 1000
            happyMoodAdapter.sublist(it)
            binding.happyRecyclerview.animate().alpha(1f)
        })
    }
}