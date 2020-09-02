@file:Suppress("Annotator", "Annotator")


package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sing_up.*

class MainActivity : AppCompatActivity() {

    var googleSignInClient:GoogleSignInClient?=null
    var RC_SIGN_IN =1000
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        //---------google para login
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        btnsignInGoogle.setOnClickListener {
                var signInClient = googleSignInClient?.signInIntent
                startActivityForResult(signInClient,RC_SIGN_IN)

        }




        //-------------------fin de google login
        textView4.setOnClickListener { startActivity(Intent(this, Activity_sing_up::class.java)) }

        btnLogin.setOnClickListener {
            doLogin()
        }





    }// fin del oncreate

        fun firebaseAuthWithGoogle(acct : GoogleSignInAccount?){
            try {

                var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){

                        startActivity(Intent(this, MenuIncio::class.java))
                    }
                }
            }catch (e : NullPointerException){
                Toast.makeText(this,"no se ha registrado correctamente ",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==RC_SIGN_IN){
            var Task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = Task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
    }

    private fun doLogin() {
        try {
            if (textEmail.text.toString().isEmpty()){
                textEmail.error = "favor ingresar correo"
                textUsuario.requestFocus()
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(textEmail.text.toString()).matches()){
                textEmail.error = "porfavor ingrese su correo"
                textEmail.requestFocus()
                return
            }

            if (textPassword.text.toString().isEmpty()){
                textPassword.error = "favor ingresar contraseña"
                textPassword.requestFocus()
                return
            }

            auth.signInWithEmailAndPassword(textEmail.text.toString(), textPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        Toast.makeText(baseContext, "Authentication faillida.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }
        }catch (e:NullPointerException){
            Toast.makeText(this,"no se ha registrado correctamente ",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

}

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

   private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser!=null){
            if (currentUser.isEmailVerified){
            startActivity(Intent(this, MenuIncio::class.java))
            }else{
                Toast.makeText(baseContext, "verificiar su correo para autentificar.",
                    Toast.LENGTH_SHORT).show()

            }
        }else{

            Toast.makeText(baseContext, "Authentication faillida.",
                Toast.LENGTH_SHORT).show()
        }

    }

    }




