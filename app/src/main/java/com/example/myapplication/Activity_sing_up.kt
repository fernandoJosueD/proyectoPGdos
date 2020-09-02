package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sing_up.*

class Activity_sing_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        auth = FirebaseAuth.getInstance()
        texregresar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnSignUp.setOnClickListener {
             SigUnpUser()
            Toast.makeText(this,"confirmar registro en su correo",Toast.LENGTH_SHORT).show()
        }
    }
        private fun SigUnpUser(){

            if (textUsuario.text.toString().isEmpty()){
                textUsuario.error = "favor ingresar correo"
                textUsuario.requestFocus()
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(textUsuario.text.toString()).matches()){
                textUsuario.error = "porfavor ingrese un correo "
                textUsuario.requestFocus()
                return
            }

            if (textcontraseña.text.toString().isEmpty()){
                textcontraseña.error = "favor ingresar contraseña"
                textcontraseña.requestFocus()
                return
            }
            auth.createUserWithEmailAndPassword(textUsuario.text.toString(), textcontraseña.text.toString())
                .addOnCompleteListener(this) { task  ->
                    if (task.isSuccessful) {
                        // valida si el usuari tiene cuenta activa para proseguir validando correo
                        val user :FirebaseUser? = auth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    startActivity(Intent(this,
                                        MainActivity::class.java))
                                    finish()
                                }
                            }

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, "Authentication fallida.",
                            Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
        }


}