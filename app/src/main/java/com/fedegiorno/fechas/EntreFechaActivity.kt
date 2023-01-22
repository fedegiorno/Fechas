package com.fedegiorno.fechas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fedegiorno.fechas.databinding.ActivityEntreFechaBinding

class EntreFechaActivity : AppCompatActivity() {
    private lateinit var entrefechabinding: ActivityEntreFechaBinding
    private lateinit var sFecha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        entrefechabinding = ActivityEntreFechaBinding.inflate(layoutInflater)
        setContentView(entrefechabinding.root)
        entrefechabinding.TXTfechaOrigen.requestFocus()

        entrefechabinding.calendariolapso.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            var dia: Int = dayOfMonth
            var mes = month + 1
            var anio: Int = year


            //sFecha = cadenafecha(dia, mes, anio)
            Log.d("KIRCHOFFF", sFecha.toString())
            entrefechabinding.BTNCalcularlapso.isEnabled=true

            if (entrefechabinding.TXTfechaOrigen.hasFocus()){
                entrefechabinding.TXTfechaOrigen.setText(sFecha.toString())
            }
        }
    }
}