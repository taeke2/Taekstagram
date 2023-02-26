package com.taek.taekstagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class FindIdModel(var id: String? = null, var phoneNumber: String? = null)

class InputNumberViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var nextPage = MutableLiveData(false)
    var inputNumber = ""

    fun savePhoneNumber() {
        var findIdModel = FindIdModel(auth.currentUser?.email, inputNumber)
        firestore.collection("findIds").document().set(findIdModel).addOnCompleteListener { // DB에 넣는 방식
            if(it.isSuccessful){
                nextPage.value = true
                auth.currentUser?.sendEmailVerification()    // 가입된 유저한테 인증메일 보냄
            }
        }
    }
}