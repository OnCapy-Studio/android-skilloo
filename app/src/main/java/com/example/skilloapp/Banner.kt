package com.example.skilloapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.Button
class Banner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banner)

        val btIniciar = findViewById<Button>(R.id.bt_iniciar)
        btIniciar.setOnClickListener(
            View.OnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        })
    }
}
