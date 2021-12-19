package com.example.moodyapplication.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.MusicItemLayoutBinding
import com.example.moodyapplication.model.MusicModel

class SadMoodAdapter(val context: Context) :
    RecyclerView.Adapter<SadMoodAdapter.SadMoodHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicModel>(){
        override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SadMoodHolder {
        val binding = MusicItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SadMoodHolder(binding)
    }

    override fun onBindViewHolder(holder: SadMoodHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun sublist(list: List<MusicModel>){
        differ.submitList(list)
    }

  inner  class SadMoodHolder(val binding: MusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicModel){
                binding.nameTextView.text = item.name
                binding.artsTextView.text = item.description
                Glide.with(context).load(item.photo).into(binding.musicImageview)
                binding.menuImagbutton.setOnClickListener {
                    showPopupMenu(it)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(context,view )
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.share_item_view ->{
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    context.startActivity(intent)
                    true
                }
                R.id.favorite_item_view ->{
                    true
                }
                else -> true
            }
        }
        popupMenu.show()
    }
}