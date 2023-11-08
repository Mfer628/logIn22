package com.example.login2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivit : AppCompatActivity() {
    companion object{
        const val My_Channel_Id = "myChennel"
    }
    private val PERMISSION_REQUEST_CODE = 1
    private lateinit var txtName: EditText
    private lateinit var txtApell: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtpswd: EditText
    private lateinit var btnC: Button
    private lateinit var progressBar : ProgressBar
    private lateinit var dbReference : DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName=findViewById(R.id.txtNombre)
        txtApell=findViewById(R.id.txtapellido)
        txtEdad=findViewById(R.id.txtEdad)
        txtEmail=findViewById(R.id.txtEmail)
        txtpswd = findViewById(R.id.txtpswd)
        btnC = findViewById(R.id.btnAceptar)

        progressBar = ProgressBar(this)
        //Instancia para la base de datos
        database = FirebaseDatabase.getInstance()

        //Instancia para la autenticacion
        auth = FirebaseAuth.getInstance()

        //Referncia leer y escribir en una ubicacion

        //dbReference = database.reference.child("Usuarios")

        //progressBar Se Muestre indete      rminandamente

        progressBar=findViewById(R.id.prgressBar)

    }

    fun Registrar(view: View){
            createNewAccount()
    }

    private fun createNewAccount (){
        val name: String = txtName.text.toString()
        val apellido:String = txtApell.text.toString()
        val edad: String = txtEdad.text.toString()
        val email :String=txtEmail.text.toString()
        val pswd :String=txtpswd.text.toString()
        //Clase para verificar si los campos estan vacios
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(edad) &&
            !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pswd) ) {
            progressBar.visibility = View.VISIBLE
            //Dando de Alta el Usuario y la contraseña
            auth.createUserWithEmailAndPassword(email,pswd)
                .addOnCompleteListener(this){task->
                    //Verificando el registro  Correctamente
                    if (task.isComplete){
                        //El signo de pregunta es por si currentuser pueda ser null y se recibe de manera segura
                        val user = FirebaseAuth.getInstance().currentUser
                        VerificarEmail(user)
                        agregarDatos()
                        action()
                        CrearNotificaciones()


                        //Dando de alta los demas Datos

                    //Una vez Ingresado Correctamente lo mando a la vista LogIn

                    }
                }

        }
    }
    fun llamarFuncionNotyfi(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.VIBRATE), PERMISSION_REQUEST_CODE)
        } else {
            CrearNotificaciones()
        }
    }
    private fun action (){
        startActivity(Intent(this, LogInActivity::class.java))
    }
    //Enviar el Email a usuario para decirle que se verifico correctamente
    private fun VerificarEmail (user: FirebaseUser?){
        user?.sendEmailVerification()
                //Verificamos si se hizo correctamente el envio
            ?.addOnCompleteListener(this){
                task ->
                if (task.isComplete){
                    Toast.makeText(this, "Email Enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "NO  Enviado : Error", Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun agregarDatos() {

        val nombreG = txtName.text.toString();
        val apellidoG = txtApell.text.toString()
        val edadG = txtEdad.text.toString()
        if (nombreG != null && apellidoG != null && edadG != null) {


            val user = hashMapOf(
                "Nombre" to nombreG,
                "Apellido" to apellidoG,
                "Edad" to edadG
            )

            db.collection("usuarios")
                .add(user)
                .addOnSuccessListener { documentRedference ->
                    Log.d("TAG", documentRedference.id)
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error $e")

                }

        }else{
            println("Es nullo")
        }
    }
    @SuppressLint("MissingPermission")
    fun CrearNotificaciones (){
        var builder = NotificationCompat.Builder(this, My_Channel_Id)
            .setSmallIcon(android.R.drawable.btn_minus)
            .setContentTitle("Bienvenido")
            .setContentText("Te haz registrado correctamente")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(1 ,builder.build());
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CrearNotificaciones()
            } else {
                // Manejar el caso cuando el usuario niega el permiso
            }
        }
    }
}