package com.example.fastcampusbasicproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class SecretDiaryPageActivity : AppCompatActivity() {
    private val diaryEditText: EditText by lazy {
        findViewById<EditText>(R.id.diaryEditText)
    }

    private val handler = Handler(Looper.getMainLooper())  // 메인 스레드와 통신할 핸들러

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_diary_page)

        // 저장된 다이어리 가져오기
        val diaryPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        diaryEditText.setText(diaryPreferences.getString("memo", ""))

        // 백그라운드에서 처리할 다이어리 저장 스레드
        val runnable = Runnable {
            diaryPreferences.edit {
                putString("memo", diaryEditText.text.toString())
            }
        }

        // 다이어리 작성할 때마다 500ms 간격으로 백그라운드 작업 처리하기
        diaryEditText.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}