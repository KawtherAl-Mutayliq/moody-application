package com.example.moodyapplication.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.FragmentWorkoutMoodBinding
import com.example.moodyapplication.view.adapter.RomanceMusicAdapter
import com.example.moodyapplication.view.adapter.WorkoutMusicAdapter
import com.example.moodyapplication.view.main.viewmodel.RomanceMusicViewModel
import com.example.moodyapplication.view.main.viewmodel.WorkoutMusicViewModel

class WorkoutMoodFragment : Fragment() {

    private lateinit var workoutMoodAdapter : WorkoutMusicAdapter

    val workoutMoodViewModel : WorkoutMusicViewModel by activityViewModels()

    private lateinit var binding: FragmentWorkoutMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutMoodBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutMoodAdapter = WorkoutMusicAdapter(requireActivity())
        binding.workoutRecyclerview.adapter = workoutMoodAdapter

        observers()
        workoutMoodViewModel.callMusic()
    }

    private fun observers(){
        workoutMoodViewModel.workoutMusicLiveData.observe(viewLifecycleOwner, {
            binding.workoutProgressBar.animate().alpha(0f).duration = 1000
            workoutMoodAdapter.sublist(it)
            binding.workoutRecyclerview.animate().alpha(1f)
        })
    }
}