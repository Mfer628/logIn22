package com.example.login2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import kotlinx.android.synthetic.main.activity_main.*

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
        private lateinit var txtUser : EditText
        private lateinit var txtPassword : EditText
        private lateinit var btnGoogle: ImageButton
        private lateinit var progressBar : ProgressBar
        private lateinit var auth : FirebaseAuth
        private lateinit var googleSignInClient: GoogleSignInClient

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        txtUser = findViewById(R.id.txtUser1)
        txtPassword = findViewById(R.id.txtpswd1)
        progressBar = findViewById(R.id.prgressBar1)
        btnGoogle = findViewById(R.id.ImageGoogle)
        //Creando la instancia de auth
        auth = FirebaseAuth.getInstance()

            //Programacion para logearte con google
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Web client ID from Firebase Console
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            auth = FirebaseAuth.getInstance()

            btnGoogle.setOnClickListener{
                signInWithGoogle()
            }
    }
    fun OlvidoContraseña ( view: View){
        startActivity(Intent(this , forgotPassword :: class.java))
    }
    fun register (view: View){
        startActivity(Intent(this , RegisterActivit :: class.java))
    }
    fun logIn (view: View){
        loginUser()
    }
    private fun loginUser (){
        val User:String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()
        if (!TextUtils.isEmpty(User) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(User, password)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun action (){
        startActivity(Intent(this , MainActivity :: class.java))
    }



    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (task.isSuccess) {
                val account = task.signInAccount
                firebaseAuthWithGoogle(account)
            }
        }
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                } else {
                    // Fallo en el inicio de sesión
                }
            }
    }
    companion object {
        private const val RC_SIGN_IN = 9001
    }
}