package com.skku.cmdapp.couplecalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skku.cmdapp.couplecalendar.databinding.ActivityMainBinding
import com.skku.cmdapp.couplecalendar.models.LoginActivity
import com.skku.cmdapp.couplecalendar.models.ModelUser
import com.skku.cmdapp.couplecalendar.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    var fbAuth: FirebaseAuth? = null //firebase 로그인
    var fbFirestore: FirebaseFirestore? = null //firebase firestore 저장소
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()
        var userInfo = ModelUser()
        userInfo.uid = fbAuth?.uid
        userInfo.userEmail = fbAuth?.currentUser?.email
        fbFirestore?.collection("users")?.document(userInfo.uid.toString())?.set(userInfo)

        //맨 처음에 홈 페이지가 기본으로 나오도록 설정
        changeFragment(CalendarViewFragment())


        //네비게이션바 터치시 해당되는 프레그먼트 나오도록
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navi_home -> changeFragment(CalendarViewFragment())
                //R.id.navi_diary -> changeFragment(DiaryWriting())
                R.id.navi_statistic -> changeFragment(StatisticFragment())
                R.id.navi_mypage -> changeFragment(SettingFragment())
            }
            true

        }

        setContentView(binding.root)


    }


    /*fun diarybuttonclicked(view: View) {

        val diarypopup = popupdialog(this)

        diarypopup.show()
    }*/

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrameLayout,fragment)
            .commit()
    }

}