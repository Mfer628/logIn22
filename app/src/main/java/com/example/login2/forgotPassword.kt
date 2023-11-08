package com.example.login2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class forgotPassword : AppCompatActivity() {
    private lateinit var txtUser : EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        txtUser= findViewById(R.id.txtUser_Recu)
        progressBar = findViewById(R.id.prgressBarRecu)
        // Creamos Instancia
        auth = FirebaseAuth.getInstance()
    }
    fun send (view: View){
        val email = txtUser.text.toString()
        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                    task->
                    if (task.isSuccessful){
                        progressBar.visibility = View.VISIBLE
                        startActivity(Intent(this, LogInActivity:: class.java))
                    }else
                    {
                        Toast.makeText(this, "Error al enviar el Email", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}