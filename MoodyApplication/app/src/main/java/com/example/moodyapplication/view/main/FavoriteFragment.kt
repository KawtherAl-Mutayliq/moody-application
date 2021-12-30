package com.example.moodyapplication.view.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.databinding.FragmentFavoriteBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.adapter.FavoriteAdater
import com.example.moodyapplication.view.main.viewmodel.FavoriteViewModel

private const val TAG = "FavoriteFragment"
class FavoriteFragment : Fragment() {


    private lateinit var favoriteAdapter : FavoriteAdater
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    private var musicList = listOf<MusicModel>()

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdater(requireActivity(), favoriteViewModel)
        binding.favoriteRecyclerview.adapter = favoriteAdapter

        observers()
        favoriteViewModel.callFavorite()

    }

    private fun observers(){
        favoriteViewModel.favoriteLiveData.observe(viewLifecycleOwner, {
            binding.favoriteProgressBar.animate().alpha(0f).duration = 1000
            favoriteAdapter.sublist(it)
            Log.d(TAG, favoriteAdapter.sublist(it).toString())
            binding.favoriteRecyclerview.animate().alpha(1f)
        })
    }

}