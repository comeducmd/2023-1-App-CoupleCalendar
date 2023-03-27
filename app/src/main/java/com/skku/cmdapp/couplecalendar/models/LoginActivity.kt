package com.skku.cmdapp.couplecalendar.models

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.RegisterBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var startGoogleLoginForResult: ActivityResultLauncher<Intent>

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = Firebase.auth
        googleInit()

        binding = DataBindingUtil.setContentView(this, R.layout.register)

        binding.loginGoogleBtn.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startGoogleLoginForResult.launch(signInIntent)
        }
    }

    private fun googleInit(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        startGoogleLoginForResult = registerForActivityResult(ActivityResultContracts
            .StartActivityForResult()){result: ActivityResult ->
            if(result.resultCode == RESULT_OK) {
                result.data?.let {data ->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        val account = task.getResult(ApiException::class.java)!!
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        Log.w("구글 회원가입 실패", e)
                    }
                }
            } else {
                Log.w("구글 결과 오류 ${result}", null)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateUI(user)
            } else {
                Log.w("signInWithCredential:failure", task.exception)
                updateUI(null)
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        // FirebaseUser 데이터에 따른 UI 작업
    }
}