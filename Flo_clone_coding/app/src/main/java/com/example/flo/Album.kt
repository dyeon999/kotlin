package com.example.flo

import android.icu.text.CaseMap

data class Album(
    var title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
    var songs : ArrayList<Song>? = null // 앨범 수록곡들
)
