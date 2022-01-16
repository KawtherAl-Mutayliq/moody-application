package com.example.moodyapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.MusicItemLayoutBinding
import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.view.main.activities.MusicPlayActivity
import com.example.moodyapplication.view.main.viewmodel.FavoriteViewModel

class FavoriteAdater(val context: Context, val viewModel: FavoriteViewModel) :
    RecyclerView.Adapter<FavoriteAdater.FavoriteHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteMusic>() {
        override fun areItemsTheSame(oldItem: FavoriteMusic , newItem: FavoriteMusic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteMusic , newItem: FavoriteMusic): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this , DIFF_CALLBACK)


    override fun onCreateViewHolder(
        parent: ViewGroup ,
        viewType: Int
    ): FavoriteHolder {
        val binding =
            MusicItemLayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder , position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun sublist(list: List<FavoriteMusic>) {
        differ.submitList(list)
    }

   inner class FavoriteHolder(val binding: MusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(item: FavoriteMusic) {
            binding.nameTextView.text = item.name
            binding.artsTextView.text = item.description
            Glide.with(context).load(item.photo).into(binding.musicImageview)

            binding.menuImagbutton.setOnClickListener {
                showPopupMenu(it, item)
            }

            val position = adapterPosition

            // intent for play music and take its info (title, description, photo)
            itemView.setOnClickListener {

                viewModel.musicArrayList.postValue(differ.currentList)

                val intent= Intent(context , MusicPlayActivity::class.java)

                intent.putExtra("name", item.name)
                intent.putExtra("description", item.description)
                intent.putExtra("photo", item.photo)
                intent.putExtra("music", item.music)
                intent.putParcelableArrayListExtra("list",(ArrayList<Parcelable>(differ.currentList)))
                intent.putExtra("position" , position)

                context.startActivity(intent)
            }
        }

       @SuppressLint("NotifyDataSetChanged")
       private fun showPopupMenu(view: View , item: FavoriteMusic) {

           val popupMenu = PopupMenu(context , view)
           popupMenu.inflate(R.menu.favoritepopup)

           popupMenu.setOnMenuItemClickListener {
               when (it.itemId) {
                   // to share music url
                   R.id.share_favorite_item -> {

                       val link = item.music
                       val intent = Intent(Intent.ACTION_SEND)
                       intent.type = "text/plain"
                       intent.putExtra(Intent.EXTRA_TEXT, link)
                       context.startActivity(Intent.createChooser(intent, "Share Link"))

                       true
                   }

                   //delete item from favorite model
                   R.id.delete_favorite_item -> {

                       val favoriteMusic = mutableListOf<FavoriteMusic>()
                       favoriteMusic.addAll(differ.currentList)
                       favoriteMusic.removeAt(position)
                       viewModel.deleteFavorite(item)
                       differ.submitList(favoriteMusic)
                       true
                   }
                   else -> true
               }
           }
           popupMenu.show()
       }
    }
}