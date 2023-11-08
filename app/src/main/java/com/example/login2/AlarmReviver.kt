package com.example.login2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReviviver : BroadcastReceiver() {
    private companion object {
        const val My_Channel_Id = "myChannel"
    }

    private val PERMISSION_REQUEST_CODE = 1

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.login2.ALARM_TRIGGERED") {
            // Aquí puedes realizar las acciones que desees cuando se active la alarma
            // Por ejemplo, mostrar una notificación
            if (context != null) {
                crearNotificacion(context)
            }
        }
    }

    fun llamarFuncionNotyfi(context: Context) {
        if (context != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.VIBRATE), PERMISSION_REQUEST_CODE)
            } else {
                crearNotificacion(context)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun crearNotificacion(context: Context) {
        // Crear un canal de notificación (solo necesita hacerse una vez)
        val notificationManager = NotificationManagerCompat.from(context)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(My_Channel_Id, "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, My_Channel_Id)
            .setSmallIcon(android.R.drawable.btn_minus)
            .setContentTitle("Bienvenido")
            .setContentText("Te has registrado correctamente")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(notificationManager) {
            notify(1, builder.build())
        }
    }


}
