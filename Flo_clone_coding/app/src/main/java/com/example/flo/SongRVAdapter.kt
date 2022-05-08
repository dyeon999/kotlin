package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.ItemSongBinding

class SongRVAdapter(private var albumSongData : ArrayList<AlbumSong>) : RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemSongBinding = ItemSongBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumSongData[position])
    }

    override fun getItemCount(): Int = albumSongData.size

    inner class ViewHolder(val binding : ItemSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(albumSong : AlbumSong){
            binding.itemSongListNo.text = albumSong.no
            binding.itemSongTitleTv.text = albumSong.title
            binding.itemSongSingerTv.text = albumSong.singer
        }
    }

}