package com.example.fastcampusbasicproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.edit

class SecretDiaryActivity : AppCompatActivity() {
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false  // 비밀번호 변경 모드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_diary)

        // view 그리기
        numberPicker1
        numberPicker2
        numberPicker3

        // 다이어리 열람 버튼 클릭
        openButton.setOnClickListener {
            if(changePasswordMode) {
                // 비밀번호 변경 모드일 경우
                showToast("비밀번호 변경중입니다.")
                return@setOnClickListener
            }

            // 비밀번호 확인하기
            val diaryPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(diaryPreferences.getString("password", "000").equals(passwordFromUser)) {
                // 비밀번호 일치
                startActivity(Intent(this@SecretDiaryActivity, SecretDiaryPageActivity::class.java))
            } else {
                // 비밀번호 불일치
                showAlertDialog()
            }
        }

        // 다이어리 비밀번호 변경 버튼 클릭
        changePasswordButton.setOnClickListener {
            val diaryPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changePasswordMode) {
                // 비밀번호 변경하기 : 비밀번호 변경 및 버튼색 변경
                diaryPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                showToast("비밀번호가 변경되었습니다.")
            } else {
                // 비밀번호가 맞는지 확인하기
                if(diaryPreferences.getString("password", "000").equals(passwordFromUser)) {
                    // 비밀번호 일치 : 비밀번호 변경 모드 전환 및 버튼색 변경
                    changePasswordMode = true
                    changePasswordButton.setBackgroundColor(ContextCompat.getColor(this, R.color.diary_red))
                    showToast("변경할 비밀번호를 입력해주세요.")
                } else {
                    // 비밀번호 불일치 : 알림창 띄우기
                    showAlertDialog()
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 일치하지 않습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }

}