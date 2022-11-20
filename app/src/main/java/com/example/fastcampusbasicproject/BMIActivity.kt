package com.example.fastcampusbasicproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        // view 불러오기
        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText: EditText = findViewById(R.id.weightEditText)
        val resultButton: Button = findViewById(R.id.resultButton)

        // BMI 계산 결과 버튼 클릭 이벤트
        resultButton.setOnClickListener {
            // 빈값이 있는 경우
            if(heightEditText.text.isEmpty() || weightEditText.text.isEmpty()) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 신장과 체중을 정수타입으로 바꾸기
            val height = heightEditText.text.toString().toInt()
            val weight = weightEditText.text.toString().toInt()

            // 화면 전환
            val intent: Intent = Intent(this@BMIActivity, BMIResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}