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



        binding.nameEditText.setText(name)
        binding.descriptionEditText.setText(description)


        observers()
        updateViewModel.callMusic()
        binding.okButton.setOnClickListener {

            val name = binding.nameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val musicModel = MusicModel(description,musicId, music,name, photo, type)

            updateViewModel.update(musicModel)
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

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