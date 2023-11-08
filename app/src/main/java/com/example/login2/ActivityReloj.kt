package com.example.login2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
//import androidx.fragment.app.DialogFragment
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Button
import java.util.Calendar

class ActivityReloj : AppCompatActivity() {
   private lateinit var txtTime: EditText
   private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reloj)
        txtTime = findViewById(R.id.txtselectedTime)
        btn = findViewById(R.id.btnselectTime)

        txtTime.setOnClickListener { showTimePickerDialog() }
        btn.setOnClickListener{
            val intent = Intent(this, AlarmReviviver::class.java)
            sendBroadcast(intent)
            intent.action = "com.example.login2.ALARM_TRIGGERED"
        }
    }

    private fun showTimePickerDialog() {
        val timePicker = HoraDialog {onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "Time")
    }
    private fun onTimeSelected(time:String){
        val txtTime1: EditText = findViewById(R.id.txtselectedTime)
        txtTime1.setText("Has seleccionado $time")

        // Parsear la hora seleccionada para configurar la alarma
        val parts = time.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()

        // Configurar la alarma
        setAlarm(hour, minute)

    }
    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        // Configurar la hora de la alarma
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Si la hora programada ya pasó, agrega un día
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Configurar la alarma
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}