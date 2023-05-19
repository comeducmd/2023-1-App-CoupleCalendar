package com.skku.cmdapp.couplecalendar.view

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.skku.cmdapp.couplecalendar.R


class DeleteAccountActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_account)

        //1. Shared Preferenced에서 사용자 식별자(이메일) 불러와 userId라는 변수에 저장

        //shared preferenced에 접근할 수 있는 객체 생성
        //우리 앱 preferenced 이름은 tinyDB임. tinyDB라는 이름의 sharedPreference를 불러온다
        //SP 릴레이션 따로 생성하는 코드 없이 아래 코드로 name에 해당하는 릴레이션 없으면 자동생성
        val tinyDB:SharedPreferences = getSharedPreferences("tinyDB", MODE_PRIVATE)
        //shared Preferenced 에디터를 초기화 -> 데이터 입력, 삭제할 때 필요. editor에 입력, 삭제 작업 저장하고 마지막에 editor를 commit() or apply() 해 변경내용 적용 해야함
        val editor: SharedPreferences.Editor = tinyDB.edit()

        //cf) 테스트값 SP에 임시로 넣어두자
        editor.putString("email", "cripton02@naver.com")
        editor.commit()

        //tinyDB에 있는 데이터들중 email 키에 해당하는 value를 가져오고 해당 키에 맞는 데이터가 없으면 기본값으로 user_email
        //email키에 해당하는 value값이 String이므로 getString()함수 사용
        //getString의 반환값이 String?인 널러블인데 결과가 없으면 기본값이 들어가므로 널일 수 없음...? -> !!로 널 아님 확신시키자
        val userId =tinyDB.getString("email","user_email")!!
        Log.d("userId","${userId}")

        //2. 위에서 얻은 사용자식별자(userId)를 이용해 파이어스토어의 users컬렉션 중 해당 사용자식별자를 문서 식별자로 사용하는 문서 불러와 삭제

        //firestore의 연결링크 db에 저장
        val db = Firebase.firestore

        db.collection("users")
            .document(userId)
            .delete()

        //3. shared Preferenced도 삭제
        editor.clear()
        editor.commit()

//        val user1= hashMapOf(
//            "first_name" to "minsun",
//            "last_name" to "Kwon",
//            "born" to 2002
//        )
//
//        //파이어스토어(db)의 콜렉션 중 "users"라는 이름의 콜렉션에 접근해 user1 문서를 add한다
//        db.collection("users")
//            .add(user1)//add함수는 문서의 작업객체인 Task<DocumentRefernece>를 반환하고 이 객체는 addOnSuccessListener 등의 콜백 함수를 이용할 수 있음
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

    }

}