package com.taek.taekstagram.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.taek.taekstagram.R
import com.taek.taekstagram.databinding.ActivityInputNumberBinding

class InputNumberActivity : AppCompatActivity() {
    lateinit var binding: ActivityInputNumberBinding
    val inputNumberViewModel: InputNumberViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_number)
        binding.viewModel = inputNumberViewModel
        binding.lifecycleOwner = this
        setObserver()
    }

    fun setObserver() {
        inputNumberViewModel.nextPage.observe(this) {
            if (it) {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}