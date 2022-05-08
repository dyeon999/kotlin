package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val info = arrayListOf("", "")
    private val albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodayMusicLl.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
//        }

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "aespa", R.drawable.img_album_exp3))
            add(Album("Boy with Love", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연", R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyClickListner(object:AlbumRVAdapter.MyClickListner{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayClick(position: Int) {
                Toast.makeText(activity, "SONG PLAY", Toast.LENGTH_SHORT).show()
            }

            private fun changeAlbumFragment(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    })
                    .commitAllowingStateLoss()
            }

//            private fun changeMiniplayer(){
//                MainActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frm, MainActivity().)
//            }

        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(bannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(bannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL // viewpager 좌우 스크롤

        val homeAdapter = HomeVPAdapter(this)
        binding.homePannelVp.adapter = homeAdapter
        TabLayoutMediator(binding.homePannelIndicatorTl, binding.homePannelVp){
            tab, position ->
            tab.text = info[position]
        }.attach()

        return binding.root
    }
}