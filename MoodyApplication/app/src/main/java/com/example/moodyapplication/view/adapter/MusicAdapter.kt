package com.example.moodyapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.MusicItemLayoutBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import android.app.DownloadManager
import android.content.SharedPreferences
import android.widget.Toast
import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.view.identity.USERID
import com.example.moodyapplication.view.identity.sharedPref
import com.example.moodyapplication.view.identity.sharedPrefEditor
import com.example.moodyapplication.view.main.activities.MusicPlayActivity
import com.example.moodyapplication.view.main.viewmodel.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth


private const val TAG = "MusicAdapter"

class MusicAdapter(val context: Context , val viewModel: PlaylistViewModel) :
    RecyclerView.Adapter<MusicAdapter.MusicHolder>() {


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicModel>() {
        override fun areItemsTheSame(oldItem: MusicModel , newItem: MusicModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MusicModel , newItem: MusicModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this , DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MusicHolder {
        val binding =
            MusicItemLayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MusicHolder(binding)
    }


    override fun onBindViewHolder(holder: MusicHolder , position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun sublist(list: List<MusicModel>) {
        differ.submitList(list)
    }

    inner class MusicHolder(val binding: MusicItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(item: MusicModel) {
            binding.nameTextView.text = item.name
            binding.artsTextView.text = item.description

            Log.d(TAG , " bind ${item.music}")

            Glide.with(context).load(item.photo).into(binding.musicImageview)

            binding.menuImagbutton.setOnClickListener {
                showPopupMenu(it , item)
            }

            val position = adapterPosition

            // open itemView in musicPlayActivity and play music with its photo, title and description
            itemView.setOnClickListener {

                viewModel.musicArrayList.postValue(differ.currentList)

                val intent = Intent(context , MusicPlayActivity::class.java)

                intent.putExtra("name" , item.name)
                intent.putExtra("description" , item.description)
                intent.putExtra("photo" , item.photo)
                intent.putExtra("music" , item.music)
                Log.d(TAG , " item view ${item.music}")

                intent.putParcelableArrayListExtra(
                    "list" ,
                    (ArrayList<Parcelable>(differ.currentList))
                )
                intent.putExtra("position" , position)

                context.startActivity(intent)

            }
        }

        // showing popupMenu in itemView
        private fun showPopupMenu(view: View , item: MusicModel) {
            val popupMenu = PopupMenu(context , view)
            popupMenu.inflate(R.menu.popupmenu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {

                    // rename title and description of item
//                    R.id.rename_item -> {
//                        val manager: FragmentManager =
//                            (context as AppCompatActivity).supportFragmentManager
//                        val fragmentDialog = UpdateDialogFragment(
//                            item.id ,
//                            item.name ,
//                            item.description ,
//                            item.music ,
//                            item.photo ,
//                            item.type
//                        )
//                        fragmentDialog.show(manager , "dialog fragment")
//                        true
//                    }

                    // download music using DownloadManager
                    R.id.download_item -> {

                        val url = item.music
                        val request = DownloadManager.Request(Uri.parse(url))
                            .setTitle(item.name)
                            .setDescription(item.description)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setAllowedOverMetered(true)

                        val manager =
                            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                        manager!!.enqueue(request)

                        true
                    }

                    // sharing music file
                    R.id.share_item_view -> {
                        val link = item.music
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT , link)
                        context.startActivity(Intent.createChooser(intent , "Share Link"))
                        true
                    }

                    // add item in favorite
                    R.id.favorite_item_view -> {
                        viewModel.addFavorite(item)
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
        }
    }
}