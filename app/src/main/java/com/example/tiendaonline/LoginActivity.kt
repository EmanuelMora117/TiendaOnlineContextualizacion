package com.example.tiendaonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiendaonline.ui.theme.TiendaOnlineTheme
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputUser = findViewById<EditText>(R.id.inputUser)
        val inptPassword = findViewById<EditText>(R.id.inptPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val linkRegister = findViewById<TextView>(R.id.linkRegistro)

        linkRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val user = inputUser.text.toString().trim()
            val password = inptPassword.text.toString().trim()

            val userFound = Users.users.find { it.nameUser == user && it.password == password }

            if(userFound != null){
                Toast.makeText(this, "Bienvenido, ${userFound.nameUser}!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ListaProductosActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello aaaa $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TiendaOnlineTheme {
        Greeting2("Android")
    }
}