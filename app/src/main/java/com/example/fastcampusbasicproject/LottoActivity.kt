package com.example.fastcampusbasicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class LottoActivity : AppCompatActivity() {
    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val clearButton: Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val runButton: Button by lazy {
        findViewById(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private val textViewList: List<TextView> by lazy {
        listOf(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }

    private val pickNumberSet = hashSetOf<Int>()  // 사용자가 뽑은 로또 번호들

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)

        // numberPicker 숫자 범위 정하기
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        // 버튼 초기화
        initAddButton()
        initClearButton()
        initRunButton()
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 선택한 번호 view 에 출력하기
            val textView = textViewList[pickNumberSet.size]
            setTextView(numberPicker.value, textView)

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            textViewList.forEach {
                it.isVisible = false
            }
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumberList()  // 랜덤 번호 6개

            list.forEachIndexed { index, number ->
                val textView = textViewList[index]

                setTextView(number, textView)
            }
        }
    }

    private fun getRandomNumberList(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for(i in 1..45) {
                    if(pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted()
    }

    private fun setTextView(number: Int, textView: TextView) {
        textView.isVisible = true
        textView.text = number.toString()
        textView.background = when(number) {
            in 1..10 -> ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }
}
