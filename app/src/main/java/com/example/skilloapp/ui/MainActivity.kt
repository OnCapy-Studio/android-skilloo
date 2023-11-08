package com.example.skilloapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skilloapp.R
import com.example.skilloapp.data.ApiService
import com.example.skilloapp.data.RetrofitConfig
import com.example.skilloapp.data.model.login.LoginRequest
import com.example.skilloapp.data.model.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        editTextEmail = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidCredentials(email, password)) {
                performLogin(email, password)
            } else {
                showToast("Invalid credentials!")
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {
        // Implement your validation logic here
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun performLogin(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        val apiService = RetrofitConfig.createService(ApiService::class.java)
        val call = apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        if (loginResponse.token != null) {
                            showToast("Login successful!")

                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            showToast("Invalid credentials!")
                        }
                    }
                } else {
                    showToast("Error occurred while logging in.")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showToast("Error occurred while logging in.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
