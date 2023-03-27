package com.skku.cmdapp.couplecalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skku.cmdapp.couplecalendar.databinding.ActivityMainBinding
import com.skku.cmdapp.couplecalendar.models.LoginActivity
import com.skku.cmdapp.couplecalendar.models.ModelUser
import com.skku.cmdapp.couplecalendar.view.CalendarViewActivity

class MainActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null //firebase 로그인
    var fbFirestore: FirebaseFirestore? = null //firebase firestore 저장소
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()
        var userInfo = ModelUser()
        userInfo.uid = fbAuth?.uid
        userInfo.userEmail = fbAuth?.currentUser?.email
        fbFirestore?.collection("users")?.document(userInfo.uid.toString())?.set(userInfo)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick(binding)
    }

    private fun handleClick(binding: ActivityMainBinding){
        binding.openCalendarBtn.setOnClickListener{
            startActivity(Intent(this, CalendarViewActivity::class.java))
        }
        binding.openSignUpBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}