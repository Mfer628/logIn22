package com.example.login2

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class HoraDialog (val listener:(String) -> Unit) : DialogFragment(),TimePickerDialog.OnTimeSetListener  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
      val calendar: Calendar = Calendar.getInstance();
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY);
        val minute:Int = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context, this, hour, minute, false)
    return dialog;
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
       listener("$hourOfDay:$minute")
    }

}