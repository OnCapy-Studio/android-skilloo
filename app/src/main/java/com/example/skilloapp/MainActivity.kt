import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.skilloapp.R
import com.example.skilloapp.HomeActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var passwordToggleBtn: Button
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        passwordToggleBtn = findViewById(R.id.password_toggle_button)

        passwordToggleBtn.setOnClickListener {
            togglePasswordVisibility()
        }

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidCredentials(username, password)) {
                showToast("Login successful!")

                val intent = Intent(this, CommitActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid credentials!")
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        val validUsername = "admin"
        val validPassword = "password"
        return username == validUsername && password == validPassword
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            // Mostrar a senha
            editTextPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordToggleBtn.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_eye_open,
                0,
                0,
                0
            )
        } else {
            // Ocultar a senha
            editTextPassword.inputType =
                android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordToggleBtn.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_eye_closed,
                0,
                0,
                0
            )
        }

        // Mover o cursor para o final do texto
        editTextPassword.setSelection(editTextPassword.text.length)
    }
}
