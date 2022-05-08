package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding
    private val albumSongData = ArrayList<AlbumSong>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)
        binding.albumMixOnIv.setOnClickListener {
            setMixStatus(false)
        }
        binding.albumMixOffIv.setOnClickListener {
            setMixStatus(true)
        }

        albumSongData.apply {
            add(AlbumSong("01", "라일락", "아이유 (IU)"))
            add(AlbumSong("02", "Flu", "아이유 (IU)"))
            add(AlbumSong("03", "Coin", "아이유 (IU)"))
            add(AlbumSong("04", "봄 안녕 봄", "아이유 (IU)"))
            add(AlbumSong("05", "Celebrity", "아이유 (IU)"))
            add(AlbumSong("06", "돌림노래 (Feat. DEAN)", "아이유 (IU)"))
            add(AlbumSong("07", "빈 컵 (Empty Cup)", "아이유 (IU)"))
            add(AlbumSong("08", "아이와 나의 바다", "아이유 (IU)"))
            add(AlbumSong("09", "어푸 (Ah puh)", "아이유 (IU)"))
            add(AlbumSong("10", "에필로그", "아이유 (IU)"))
        }

        val songRVAdapter = SongRVAdapter(albumSongData)
        binding.albumSongRv.adapter = songRVAdapter
        binding.albumSongRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

//        binding.albumMyStyleMixTv.setOnClickListener {
//            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.songFluLayout.setOnClickListener {
//            Toast.makeText(activity, "Flu", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.songCoinLayout.setOnClickListener {
//            Toast.makeText(activity, "Coin", Toast.LENGTH_SHORT).show()
//        }
        
        return binding.root
    }

    fun setMixStatus(isMixed : Boolean){
        if (isMixed) {
            binding.albumMixOffIv.visibility = View.GONE
            binding.albumMixOnIv.visibility = View.VISIBLE
        }
        else {
            binding.albumMixOffIv.visibility = View.VISIBLE
            binding.albumMixOnIv.visibility = View.GONE
        }
    }
}