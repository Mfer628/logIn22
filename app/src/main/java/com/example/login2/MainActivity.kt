package com.example.login2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.timepicker.MaterialTimePicker

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //MenuInflater permite convertir un archivo xml de tipo menu en un objeto
    //programable para ser incluido en la actividad
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.camara -> siguientebtn()
            R.id.Reloj -> siguientebtnR()
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun siguientebtn() :Boolean {
        val intent = Intent(this, CamaraActivity :: class.java)
        startActivity(intent);
        return true;
    }
    fun siguientebtnR() :Boolean {
        val intent = Intent(this, ActivityReloj :: class.java)
        startActivity(intent);
        return true;
    }
}