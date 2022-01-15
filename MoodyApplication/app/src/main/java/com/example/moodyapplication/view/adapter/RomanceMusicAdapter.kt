package com.example.moodyapplication.view.adapter


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
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.main.activities.MusicPlayActivity
import com.example.moodyapplication.view.main.viewmodel.RomanceMusicViewModel

class RomanceMusicAdapter(val context: Context, val viewModel: RomanceMusicViewModel) :
    RecyclerView.Adapter<RomanceMusicAdapter.RomanceMusicHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicModel>(){
        override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RomanceMusicHolder {
        val binding = MusicItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RomanceMusicHolder(binding)
    }

    override fun onBindViewHolder(holder: RomanceMusicHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun sublist(list: List<MusicModel>){
        differ.submitList(list)
    }

 inner class RomanceMusicHolder(val binding: MusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicModel){
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
        val popupMenu = PopupMenu(context,view )
        popupMenu.inflate(R.menu.mainpopmenu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.main_share_item_view ->{

                    val link = item.music
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, link)
                    context.startActivity(Intent.createChooser(intent, "Share Link"))
                    true
                }
                R.id.main_favorite_item_view ->{
                    true
                }
                else -> true
            }
        }
        popupMenu.show()
    }
}