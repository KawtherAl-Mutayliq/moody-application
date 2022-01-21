package com.example.moodyapplication.view.main.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.FragmentPlaylistBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.adapter.MusicAdapter
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moodyapplication.view.identity.*


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

        // set option menu for main fragment
        setHasOptionsMenu(true)

        observers()
        musicViewModel.callMusic()

    }

    private fun observers(){
        musicViewModel.musicLiveData.observe(viewLifecycleOwner, {
            binding.playlistProgressBar.animate().alpha(0f).duration = 1000
            musicAdapter.sublist(it)
            musicList = it
            binding.playlistRecyclerView.animate().alpha(1f)
        })

        musicViewModel.musicErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity() , it , Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("CommitPrefEdits")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        // logout item
        if (id == R.id.logout_item){

            // build dialog to confirm user if will logout or not
            val buildDialog = AlertDialog.Builder(requireActivity())

            // set message dialog
            buildDialog.setMessage("Are you sure to logout ?")

            // set positive button for dialog
            buildDialog.setPositiveButton("Yes"){ _ , _ ->
            FirebaseAuth.getInstance().signOut()
                sharedPref = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
                sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.clear()
                sharedPrefEditor.commit()

                val intent = Intent(requireActivity() ,LoginActivity ::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

            // set negative button for dialog
            buildDialog.setNegativeButton("No"){ _ , _ ->
            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = buildDialog.create()

            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

            return true
        } else if (id == R.id.profile_item){

            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // function to show search view and use filter to search by title and description
    override fun onCreateOptionsMenu(menu: Menu , inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.actionbar, menu)

        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                musicAdapter.sublist(
                    musicList.filter {
                        it.name.lowercase().contains(query!!.lowercase()) or
                                it.description.lowercase().contains(query.lowercase())
                    }
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                musicAdapter.sublist(
                    musicList.filter {
                        it.name.lowercase().contains(newText!!.lowercase()) or
                                it.description.lowercase().contains(newText.lowercase())
                    }
                )
                return true
            }

        })

        searchItem.setOnActionExpandListener(object :MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                musicAdapter.sublist(musicList)
                return true
            }

        })
    }
}

