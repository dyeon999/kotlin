package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentLockerMusicfileBinding

class LockerMusicfileFragment : Fragment() {
    lateinit var binding: FragmentLockerMusicfileBinding
    private val musicData = ArrayList<MusicData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerMusicfileBinding.inflate(inflater, container, false)

        musicData.apply {
            add(MusicData("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(MusicData("라일락", "아이유 (IU)", R.drawable.img_album_exp2))
            add(MusicData("Next Level", "aespa", R.drawable.img_album_exp3))
            add(MusicData("작은 것들을 위한 시 (Boy with Love)", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(MusicData("뿜뿜", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(MusicData("Weekend", "태연", R.drawable.img_album_exp6))
            add(MusicData("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(MusicData("라일락", "아이유 (IU)", R.drawable.img_album_exp2))
            add(MusicData("Next Level", "aespa", R.drawable.img_album_exp3))
            add(MusicData("작은 것들을 위한 시 (Boy with Love)", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(MusicData("뿜뿜", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(MusicData("Weekend", "태연", R.drawable.img_album_exp6))
        }

        val musicfileRVAdapter = MusicfileRVAdapter(musicData)
        binding.musicfileSongListRv.adapter = musicfileRVAdapter
        binding.musicfileSongListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        musicfileRVAdapter.setMyItemClickListner(object : MusicfileRVAdapter.myItemClickListner{
            override fun onRemoveItem(position: Int) {
                musicfileRVAdapter.removeItem(position)
            }

        })

        return binding.root
    }
}