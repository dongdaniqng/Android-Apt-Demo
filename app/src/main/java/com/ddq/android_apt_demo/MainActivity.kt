package com.ddq.android_apt_demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ddq.apt_annotation.Bind
import com.ddq.apt_sdk._Api

class MainActivity : AppCompatActivity() {

    @Bind(R.id.tv)
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _Api.bind(this)

        textView.text = "666"
    }
}
