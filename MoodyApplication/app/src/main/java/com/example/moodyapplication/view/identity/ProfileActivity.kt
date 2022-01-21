package com.example.moodyapplication.view.identity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moodyapplication.databinding.ActivityProfileBinding
import com.example.moodyapplication.model.UserModel
import com.example.moodyapplication.view.main.viewmodel.ProfileViewModel


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel : ProfileViewModel by viewModels()
    private var userModel = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.saveButton.setOnClickListener {

            if (binding.saveButton.isChecked) {

                binding.profileFirstNameEdittext.isEnabled = true
                binding.profileLastNameEdittext.isEnabled = true
                binding.profileEmailEdittext.isEnabled = true

            } else {
                binding.profileFirstNameEdittext.isEnabled = false
                binding.profileLastNameEdittext.isEnabled = false
                binding.profileEmailEdittext.isEnabled = false
                saveUserChange()
                Toast.makeText(this , "changes saved" , Toast.LENGTH_SHORT).show()
            }
        }


        observer()
        viewModel.addUser()
    }

    private fun saveUserChange(){
        userModel.apply {
            firstName = binding.profileFirstNameEdittext.text.toString()
            lastName = binding.profileLastNameEdittext.text.toString()
            email = binding.profileEmailEdittext.text.toString()
            viewModel.updateUserProfile(this)
        }
    }

    private fun observer(){
        viewModel.getUserLiveData.observe(this, {
            binding.profileFirstNameEdittext.setText(it.firstName)
            binding.profileLastNameEdittext.setText(it.lastName)
            binding.profileEmailEdittext.setText(it.email)

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}