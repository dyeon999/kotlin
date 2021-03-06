package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity() : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignInBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }


//    private fun saveJWT(jwt : Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt", jwt)
//        editor.apply()
//    }
}