package com.taek.taekstagram.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FindIdViewModel : ViewModel() {
    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var id = ""
    var phoneNumber = ""
    var toastMessage = MutableLiveData("")

    fun findMyId() {
        firestore.collection("findIds").whereEqualTo("phoneNumber", phoneNumber).get().addOnCompleteListener {
                if (it.isSuccessful && it.result.documents.size > 0) {
                    var findIdModel = it.result.documents.first().toObject(FindIdModel::class.java)   // 값이 넘어올때 결과가 하나라도 배열로 넘어오기 때문에 first()를 사용해서 배열의 첫번째 값을 가져온다.
                    toastMessage.value = "당신의 아이디는 " + findIdModel?.id
                } else {
                    toastMessage.value = "정보가 정확하지 않습니다."
                }
            }
    }

    fun findMyPassword() {
        auth.sendPasswordResetEmail(id).addOnCompleteListener {
            if(it.isSuccessful){
                toastMessage.value = "비밀번호를 초기화 했습니다."
            } else {
                toastMessage.value = "정보가 정확하지 않습니다."
            }
        }
    }
}