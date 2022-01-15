package com.example.moodyapplication.view.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.databinding.FragmentSadMoodBinding
import com.example.moodyapplication.view.adapter.SadMoodAdapter
import com.example.moodyapplication.view.main.viewmodel.SadMoodViewModel

class SadMoodFragment : Fragment() {

    private lateinit var sadMoodAdapter : SadMoodAdapter

    val sadMoodViewModel : SadMoodViewModel by activityViewModels()

    private lateinit var binding: FragmentSadMoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSadMoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sadMoodAdapter = SadMoodAdapter(requireActivity(), sadMoodViewModel)
        binding.sadmoodRecyclerview.adapter = sadMoodAdapter

        observers()
        sadMoodViewModel.callMusic()
    }

    private fun observers(){
        sadMoodViewModel.musicLiveData.observe(viewLifecycleOwner, {
            binding.sadmoodProgressBar.animate().alpha(0f).duration = 1000
            sadMoodAdapter.sublist(it)
            binding.sadmoodRecyclerview.animate().alpha(1f)
        })
    }
}