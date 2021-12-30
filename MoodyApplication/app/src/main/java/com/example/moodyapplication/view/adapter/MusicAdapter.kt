package com.example.moodyapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.moodyapplication.view.main.MusicPlayActivity
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel

private const val TAG = "MusicAdapter"

class MusicAdapter(val context: Context , val viewModel: PlaylistViewModel) :
    RecyclerView.Adapter<MusicAdapter.MusicHolder>() {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicModel>() {
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
            Glide.with(context).load(item.photo).into(binding.musicImageview)
            binding.menuImagbutton.setOnClickListener {
                showPopupMenu(it, item)
            }

            val position = adapterPosition

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
    }

    private fun showPopupMenu(view: View, item: MusicModel) {
        val popupMenu = PopupMenu(context , view)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share_item_view -> {
                    val musicUrl = item.music
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "audio/*"
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(Intent.createChooser(intent , "share audio"))
                    true
                }
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