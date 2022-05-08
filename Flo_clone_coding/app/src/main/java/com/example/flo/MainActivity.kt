package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var song : Song = Song()
    private var gson : Gson = Gson()

//    lateinit var fhBinding : FragmentHomeBinding
//    lateinit var homeAdapter : HomeVPAdapter

//    var currentPosition = 0
//    var handler = Handler(Looper.getMainLooper()) {
//        setPage()
//        true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        fhBinding = FragmentHomeBinding.inflate(layoutInflater)
//        val handler = Handler(Looper.getMainLooper())
//        val vpList = arrayListOf<ViewPager2>()


//        Thread {
//            for (image in vpList){
//                handler.post() {
//                    fhBinding.homePannelVp.
//                }
//                Thread.sleep(2000)
//            }
//        }.start()

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
            //val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("second", song.second)
//            intent.putExtra("playTime", song.playTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("music", song.music)
//            startActivity(intent)

            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)

        }


        inputDummySongs()
        initBottomNavigation()



        Log.d("Song",song.title + song.singer)
        Log.d("Song",song.title + song.singer + song.second)
    }

//    private fun setPage() {
//        if (currentPosition == 5) currentPosition = 0
//        pager.set
//    }


    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerBarSb.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()

//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if (songJson == null){
//            Song("라일락", "아이유 (IU)", 0, 60, false, "music_iu_lilac")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.songDao().getSong(1)
        } else{
            songDB.songDao().getSong(songId)
        }

        Log.d(" song ID ", song.id.toString())

        setMiniPlayer(song)
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                250,
                false,
                "music_bts_butter",
                R.drawable.img_album_exp,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "라일락",
                "아이유 (IU)",
                0,
                214,
                false,
                "music_iu_lilac",
                R.drawable.img_album_exp2,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "aespa",
                0,
                250,
                false,
                "music_aespa_next",
                R.drawable.img_album_exp3,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "작은 것들을 위한 시",
                "방탄소년단 (BTS)",
                0,
                240,
                false,
                "music_bts_butter",
                R.drawable.img_album_exp4,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "뿜뿜",
                "모모랜드",
                0,
                230,
                false,
                "music_iu_lilac",
                R.drawable.img_album_exp5,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "Weekend",
                "태연",
                0,
                230,
                false,
                "music_ty_weekend",
                R.drawable.img_album_exp6,
                false
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data ", _songs.toString())
    }
}