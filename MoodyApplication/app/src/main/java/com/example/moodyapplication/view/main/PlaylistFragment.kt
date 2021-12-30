package com.example.moodyapplication.view.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.FragmentPlaylistBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.adapter.MusicAdapter
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel

private const val TAG = "PlaylistFragment"

class PlaylistFragment : Fragment() {

    private lateinit var musicAdapter : MusicAdapter
    private val musicViewModel: PlaylistViewModel by activityViewModels()

    private var musicList = listOf<MusicModel>()

    private lateinit var binding: FragmentPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        musicAdapter = MusicAdapter(requireActivity(), musicViewModel)
        binding.playlistRecyclerView.adapter = musicAdapter

        observers()
        musicViewModel.callMusic()

        binding
    }

    private fun observers(){
        musicViewModel.musicLiveData.observe(viewLifecycleOwner, {
            binding.playlistProgressBar.animate().alpha(0f).duration = 1000
            musicAdapter.sublist(it)
            musicList = it
            binding.playlistRecyclerView.animate().alpha(1f)
        })
    }
}

