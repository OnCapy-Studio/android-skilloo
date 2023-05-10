package com.example.skilloapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidCredentials(username, password)) {
                showToast("Login successful!")
            } else {
                showToast("Invalid credentials!")
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Aqui você pode adicionar a lógica para verificar as credenciais
        // Por exemplo, você pode comparar com um nome de usuário e senha pré-definidos
        val validUsername = "admin"
        val validPassword = "password"
        return username == validUsername && password == validPassword
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
