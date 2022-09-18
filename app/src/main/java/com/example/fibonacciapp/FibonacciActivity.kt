package com.example.fibonacciapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*


lateinit var numberOne:Number

class FibonacciActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fibonacci)

        val genFib: Button = findViewById(R.id.genFib)
        val genFib2: Button = findViewById(R.id.genFib2)
        val getN: EditText = findViewById(R.id.getN)
        val getN2: EditText = findViewById(R.id.getN2)
        val fibText: TextView = findViewById(R.id.text_view)
        val resultText: TextView = findViewById(R.id.resultOne)
        val resultTextTwo: TextView = findViewById(R.id.resultTwo)

        genFib.setOnClickListener {
            if (getN.text.isNotEmpty()) {
                val N: Int = getN.text.toString().toInt()
                for (i in 1..5) {
                    resultText.text = nFib(N)
                }
            } else {
                resultText.text = "Not a valid value"
            }
        }

        genFib2.setOnClickListener {
            if (getN2.text.isNotEmpty()) {
                val N: Int = getN2.text.toString().toInt()
                resultTextTwo.text = nFib(N)
            } else {
                resultTextTwo.text = "Not a valid value"
            }
        }
      }

    fun nFib(N: Int): String {
        var numberOne = 1
        var numberTwo = 2
        var nextFibo = numberOne + numberTwo

            while (nextFibo <= N) {
                numberOne = numberTwo
                numberTwo = nextFibo
                nextFibo = numberOne + numberTwo
        }
        return nextFibo.toString()
}
}