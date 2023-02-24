package com.taek.taekstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.taek.taekstagram.R
import com.taek.taekstagram.databinding.ActivityLoginBinding

// MVVM 에서 화면을 통제하는 부분은 ViewModel이 가지고 있는것이 좋다.
class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this   // binding이랑 Activity랑 생명주기를 같이 할 수 있다.
        setObserver()
    }
    fun setObserver() {
        loginViewModel.showInputNumberActivity.observe(this) {
            if(it){
                finish()
                startActivity(Intent(this, InputNumberActivity::class.java))
            }
        }
        loginViewModel.showFindIdActivity.observe(this) {
            if(it){
                startActivity(Intent(this, FindIdActivity::class.java))
            }
        }
    }


    fun findId(){
        println("FindId")
        loginViewModel.showFindIdActivity.value = true
    }

    // 구글 로그인이 성공한 결과값 받는 함수
    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        loginViewModel.firebaseAuthWithGoogle(account.idToken)  // 로그인한 사용자 정보를 암호화한 값
    }
}