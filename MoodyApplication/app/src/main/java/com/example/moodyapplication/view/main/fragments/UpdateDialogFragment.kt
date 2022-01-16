package com.example.moodyapplication.view.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.moodyapplication.databinding.FragmentUpdateDialogBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel

class UpdateDialogFragment(private val musicId:String ,
                           val name: String ,
                           val description: String,
                           val music: String,
                           val photo: String,
                           val type: String
                           ) : DialogFragment() {

    private lateinit var binding: FragmentUpdateDialogBinding
    private val updateViewModel : PlaylistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpdateDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)



        // set text of music name and description in edit text
        binding.nameEditText.setText(name)
        binding.descriptionEditText.setText(description)

        // calling observe function
        observers()

        // calling music model
        updateViewModel.callMusic()

        // button to save update data
        binding.okButton.setOnClickListener {

            // set text of update name and description in edit text
            val name = binding.nameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val musicModel = MusicModel(description,musicId, music,name, photo, type)

            // update function from api
            updateViewModel.update(musicModel)
        }

        // button to close dialog fragment
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    // function for observe data
    private fun observers(){

        updateViewModel.updateLiveData.observe(viewLifecycleOwner, {
            it?.let {
                updateViewModel.updateLiveData.postValue(null)
                Toast.makeText( requireActivity(), "rename success" , Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })

        updateViewModel.updateErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity() , it , Toast.LENGTH_SHORT).show()
            }
        })
    }
}