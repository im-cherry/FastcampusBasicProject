package com.example.fastcampusbasicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlin.math.pow

class BMIResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiresult)
        
        // 신장과 체중 데이터 받기
        val intent = intent
        val height: Int = intent.getIntExtra("height", 0)
        val weight: Int = intent.getIntExtra("weight", 0)

        // BMI 계산하기
        val bmi = weight / (height / 100.0).pow(2)
        val bmiResult = when {
            (bmi >= 35.0) -> "고도비만"
            (bmi >= 30.0) -> "중정도비만"
            (bmi >= 25.0) -> "경도비만"
            (bmi >= 23.0) -> "과체중"
            (bmi >= 18.5) -> "정상체중"
            else -> "저체중"
        }

        // 화면에 BMI 출력하기
        val bmiTextView: TextView = findViewById(R.id.bmiTextView)
        val bmiResultTextView: TextView = findViewById(R.id.bmiResultTextView)

        bmiTextView.text = bmi.toString()
        bmiResultTextView.text = bmiResult
    }
}