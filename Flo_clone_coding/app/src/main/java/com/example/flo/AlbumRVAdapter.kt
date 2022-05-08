package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding
import kotlin.coroutines.coroutineContext

class AlbumRVAdapter(private var albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyClickListner {
        fun onItemClick(album: Album)
        fun onPlayClick(btn: Int)
    }

    private lateinit var mClickListner: MyClickListner
    fun setMyClickListner(itemClickListner: MyClickListner){
        mClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {  // position = 인덱스 -> 클릭 이벤트 작성
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener { mClickListner.onItemClick(albumList[position]) }
        holder.binding.itemAlbumPlayImgIv.setOnClickListener { albumList[position].coverImg?.let { it1 ->
            mClickListner.onPlayClick(
                it1
            )
        } }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album : Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

//    inner class activity : AppCompatActivity(){
//        fun toast () {
//            Toast.makeText(this,"play first music", Toast.LENGTH_SHORT)
//        }
//    }

}