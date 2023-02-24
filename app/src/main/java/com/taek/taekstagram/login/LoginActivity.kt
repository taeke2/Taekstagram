package com.taek.taekstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.taek.taekstagram.R
import com.taek.taekstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding : ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this   // binding이랑 Activity랑 생명주기를 같이 할 수 있다.
        auth = FirebaseAuth.getInstance()
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
    fun loginWithSignupEmail() {
        println("Email")
        auth.createUserWithEmailAndPassword(loginViewModel.id.value.toString(), loginViewModel.password.value.toString()).addOnCompleteListener { 
            if(it.isSuccessful){
                loginViewModel.showInputNumberActivity.value = true
            } else {
                // 아이디가 있을 경우
            }
        }
    }

    fun findId(){
        println("FindId")
        loginViewModel.showFindIdActivity.value = true
    }
}