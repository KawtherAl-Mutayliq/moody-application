package com.example.moodyapplication.view.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        savedInstanceState: Bundle?): View {

       binding = FragmentHappyMoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        happyMoodAdapter = HappyMusicAdapter(requireActivity(), happyMoodViewModel)
        binding.happyRecyclerview.adapter = happyMoodAdapter

        // calling observe function
        observers()

        // calling function from api
        happyMoodViewModel.callMusic()
    }

    private fun observers(){
        happyMoodViewModel.happyMusicLiveData.observe(viewLifecycleOwner, {
            binding.happyProgressBar.animate().alpha(0f).duration = 1000
            happyMoodAdapter.sublist(it)
            binding.happyRecyclerview.animate().alpha(1f)
        })
        happyMoodViewModel.happyMusicErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity() , it , Toast.LENGTH_SHORT).show()
            }
        })
    }
}