package com.example.moodyapplication.view.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.databinding.FragmentFavoriteBinding
import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.view.adapter.FavoriteAdater
import com.example.moodyapplication.view.identity.USERID
import com.example.moodyapplication.view.identity.sharedPref
import com.example.moodyapplication.view.main.viewmodel.FavoriteViewModel

private const val TAG = "FavoriteFragment"
class FavoriteFragment : Fragment() {


    private lateinit var favoriteAdapter : FavoriteAdater
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    private lateinit var binding: FragmentFavoriteBinding
    private var list = listOf<FavoriteMusic>()
    private var userId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdater(requireActivity(), favoriteViewModel)
        binding.favoriteRecyclerview.adapter = favoriteAdapter

        // call observe function
        observers()

        // calling callFavorite function from api
        favoriteViewModel.callFavorite()

    }

    private fun observers(){
        favoriteViewModel.favoriteLiveData.observe(viewLifecycleOwner, { it ->

            userId = sharedPref.getString(USERID, "")!!

            it?.let {
                val favoriteList = it.filter {
                    userId == it.userId
                }
                favoriteAdapter.sublist(favoriteList)
                binding.favoriteProgressBar.animate().alpha(0f).duration = 100
                binding.favoriteRecyclerview.animate().alpha(1f)
            }

          //  favoriteViewModel.favoriteLiveData.postValue(null)
        })

        favoriteViewModel.favoriteErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity() , it , Toast.LENGTH_SHORT).show()
            }
        })
    }
}