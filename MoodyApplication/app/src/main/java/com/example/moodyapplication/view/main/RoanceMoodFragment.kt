package com.example.moodyapplication.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.FragmentRoanceMoodBinding
import com.example.moodyapplication.view.adapter.HappyMusicAdapter
import com.example.moodyapplication.view.adapter.RomanceMusicAdapter
import com.example.moodyapplication.view.main.viewmodel.HappyMusicViewModel
import com.example.moodyapplication.view.main.viewmodel.RomanceMusicViewModel

class RoanceMoodFragment : Fragment() {

    private lateinit var romanceMoodAdapter : RomanceMusicAdapter

    val romanceMoodViewModel : RomanceMusicViewModel by activityViewModels()

    private lateinit var binding: FragmentRoanceMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoanceMoodBinding.inflate(layoutInflater , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        romanceMoodAdapter = RomanceMusicAdapter(requireActivity())
        binding.romanceRecyclerview.adapter = romanceMoodAdapter

        observers()
        romanceMoodViewModel.callMusic()
    }


    private fun observers(){
        romanceMoodViewModel.romanceMusicLiveData.observe(viewLifecycleOwner, {
            binding.romanceProgressBar.animate().alpha(0f).duration = 1000
            romanceMoodAdapter.sublist(it)
            binding.romanceRecyclerview.animate().alpha(1f)
        })
    }
}