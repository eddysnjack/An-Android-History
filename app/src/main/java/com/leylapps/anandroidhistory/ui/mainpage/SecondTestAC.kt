package com.leylapps.anandroidhistory.ui.mainpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.leylapps.anandroidhistory.R

class SecondTestAC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_test)
        findViewById<TextView>(R.id.tvHelloWorld).text
    }
}