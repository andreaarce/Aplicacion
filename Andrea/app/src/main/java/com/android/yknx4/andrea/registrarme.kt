package com.android.yknx4.andrea

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registrarme.*

class registrarme : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarme)

       irinicio.setOnClickListener {
           val mainActivityIntent = Intent(this, tabbed::class.java)
           mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           startActivity(mainActivityIntent)
       }

    }

}
