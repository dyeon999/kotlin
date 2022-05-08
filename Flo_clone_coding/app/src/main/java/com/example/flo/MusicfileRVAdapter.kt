package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemMusiclistBinding

class MusicfileRVAdapter(private var musicData: ArrayList<MusicData>) : RecyclerView.Adapter<MusicfileRVAdapter.ViewHolder>() {
    interface myItemClickListner{
        fun onRemoveItem(position : Int)
    }

    private lateinit var mItemClickListner : myItemClickListner
    fun setMyItemClickListner(itemClickListner : myItemClickListner){
        mItemClickListner = itemClickListner
    }

    fun removeItem(position: Int){
        musicData.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMusiclistBinding = ItemMusiclistBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(musicData[position])
        holder.binding.itemMusicMore.setOnClickListener { mItemClickListner.onRemoveItem(position) }
    }

    override fun getItemCount(): Int = musicData.size


    inner class ViewHolder(val binding: ItemMusiclistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(musiclist : MusicData){
            binding.itemMusicTitleTv.text = musiclist.title
            binding.itemMusicSingerTv.text = musiclist.singer
            binding.itemMusicAlbumIv.setImageResource(musiclist.coverImg!!)
        }
    }
}