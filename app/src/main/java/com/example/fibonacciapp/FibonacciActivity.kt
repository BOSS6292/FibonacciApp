package com.example.fibonacciapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*

class FibonacciActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fibonacci)

        val genFib: Button = findViewById(R.id.genFib)
        val genFib2: Button = findViewById(R.id.genFib2)
        val getN: EditText = findViewById(R.id.getN)
        val getN2: EditText = findViewById(R.id.getN2)
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
                resultTextTwo.text = nFib2(N)
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

    private fun nFib2(N: Int): String {
        return if (N > 0) {
            val result = LongArray(N)
            result[1] = 1
            result[0] = result[1]
            for (i in 2 until N) {
                result[i] = result[i - 1] + result[i - 2]
            }

            for (res in result) {
                Log.d(TAG, res.toString())
            }
            result.contentToString()
        } else {
            "Not a valid value of N"
        }
    }
}