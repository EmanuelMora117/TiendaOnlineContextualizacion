package com.example.tiendaonline

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.database.dbUsersOperations
import com.example.tiendaonline.models.User
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userOperations = dbUsersOperations(this)

        val inputUser = findViewById<EditText>(R.id.inputUser)
        val inputEmail = findViewById<EditText>(R.id.inputEmail)
        val inptPassword = findViewById<EditText>(R.id.inptPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val linkReturn = findViewById<TextView>(R.id.linkVolverLogin)


        btnRegistrar.setOnClickListener {
            val user = inputUser.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val password = inptPassword.text.toString().trim()

            if (user.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val newUser = User(nameUser = user, email = email, password = password)
                val id = userOperations.insertUser(newUser)

                if (id) {
                    Toast.makeText(this, "Registro exitoso de $user", Toast.LENGTH_LONG).show()
                    backLogin()
                } else {
                    Toast.makeText(this, "El usuario ya está registrado con ese correo", Toast.LENGTH_SHORT).show()
                }

            }
        }

        linkReturn.setOnClickListener {
            this.backLogin()
        }

    }

    private fun backLogin (){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}


@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    TiendaOnlineTheme {
        Greeting5("Android")
    }
}