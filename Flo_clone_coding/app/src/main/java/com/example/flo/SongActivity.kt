package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    var repeat: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB :SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) // 바인딩 초기화
        setContentView(binding.root) // root: activity_song.xml의 최상단 view -> 여기있는거 우리가 맘대로 쓸거임~

        initPlayList()
        initSong()
        initClickListner()
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime) / 100) / 1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) // 내부 저장소에 데이터 저장 -> 앱 껐다 켜도 데이터 저장
        val editor = sharedPreferences.edit() // 에디터
//        editor.putString("title", song.title)
//        editor.putString("singer", song.singer)
//        editor.putInt("second", song.second)
//        editor.putInt("playTime", song.playTime)
//        editor.putBoolean("isPlaying", song.isPlaying)
//        editor.putString("music", song.music)

//        val songJson = gson.toJson(songs[nowPos]) // gson이 song 데이터를 json으로 변환
//        editor.putString("songData", songJson)
        editor.putInt("songId", songs[nowPos].id)

        editor.apply() // 이거 해야지 작업 완료

        Log.d("Song", songs[nowPos].title + songs[nowPos].singer + songs[nowPos].second)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어 플레이어가 갖고있던 리소스 해제
        mediaPlayer = null
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())

        Log.d("Songss ", songs.toString())
    }

    private fun initClickListner(){
        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
            setPlayerStatus(false)
        }

        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(0)
        }
        binding.songRepeatOnIv.setOnClickListener {
            setRepeatStatus(1)
        }
        binding.songRepeatOn1Iv.setOnClickListener {
            setRepeatStatus(2)
        }
        binding.songRandomIv.setOnClickListener {
            setRandomStatus(true)
        }
        binding.songRandomOnIv.setOnClickListener {
            setRandomStatus(false)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d(" now Song Id ", songs[nowPos].id.toString())

        startTimer()

        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }

    fun moveSong(direct: Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)  //찾으려는 리소스의 이름, 폴더, 패키지 네임
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isplaying: Boolean) {
        songs[nowPos].isPlaying = isplaying
        timer.isPlaying = isplaying

        if (isplaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    fun setRepeatStatus(isrepeat: Int) {
        if (isrepeat == 0) {
            binding.songRepeatOn1Iv.visibility = View.GONE
            binding.songRepeatOnIv.visibility = View.VISIBLE
            binding.songRepeatIv.visibility = View.GONE
        } else if (isrepeat == 1) {
            binding.songRepeatOn1Iv.visibility = View.VISIBLE
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatIv.visibility = View.GONE
        } else {
            binding.songRepeatOn1Iv.visibility = View.GONE
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatIv.visibility = View.VISIBLE
        }
        repeat = isrepeat
    }

    fun setRandomStatus(israndom: Boolean) {
        if (israndom) {
            binding.songRandomIv.visibility = View.GONE
            binding.songRandomOnIv.visibility = View.VISIBLE
        } else {
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomIv.visibility = View.VISIBLE
        }
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()

            try {
                while (true) {
                    if (second >= playTime)
                        break

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()


                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }


        }
    }
}



//

// binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()

//if (mills % 1000 == 0f) {
//                                runOnUiThread {
//                                    binding.songStartTimeTv.text = String.format(
//                                        "%02d:%02d",
//                                        second / 60,
//                                        second % 60
//                                    )
//                                }
//                                second++
//                            }