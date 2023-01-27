package com.fedegiorno.fechas.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.fedegiorno.fechas.R
import com.fedegiorno.fechas.Utils
import com.fedegiorno.fechas.databinding.ActivityLapsoBinding
import com.fedegiorno.fechas.fragments.EntreFechasFragment
import com.fedegiorno.fechas.fragments.LapsoFragment
import com.fedegiorno.fechas.fragments.LapsoFragment.Companion.ARG_PARAM2

class LapsoActivity : AppCompatActivity() {
    private lateinit var lapsobinding: ActivityLapsoBinding
    private lateinit var sFecha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lapsobinding = ActivityLapsoBinding.inflate(layoutInflater)
        setContentView(lapsobinding.root)
        lapsobinding.TXTfechaOrigen.requestFocus()

        lapsobinding.calendariolapso.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            val dia: Int = dayOfMonth
            val mes = month + 1
            val anio: Int = year


            sFecha = Utils.cadenafecha(dia, mes, anio)
            lapsobinding.BTNCalcularlapso.isEnabled = true

            if (lapsobinding.TXTfechaOrigen.hasFocus()) {
                lapsobinding.TXTfechaOrigen.setText(sFecha)
            }
        }

        lapsobinding.BTNCalcularlapso.setOnClickListener {
            if (Utils.verificarfecha(sFecha)) {
                if (!lapsobinding.TXTlapso.text.isNullOrEmpty()) {
                    val nuevaFecha: String = CalcularFecha(
                        lapsobinding.TXTfechaOrigen.text.toString(),
                        lapsobinding.TXTlapso.text.toString()
                    )
                    Log.d("KIRCHOFFF", nuevaFecha)
                    val bundle = bundleOf(ARG_PARAM2 to nuevaFecha)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<LapsoFragment>(R.id.FragmentoContenedor, args = bundle)
                    }
                }
            }
        }

        lapsobinding.BTNHoy.setOnClickListener {
            var fechaHoy: Long = System.currentTimeMillis()
            lapsobinding.calendariolapso.setDate(fechaHoy,false,false)
        }
    }
    private fun CalcularFecha(fecha: String, lapso: String): String {
        var sAnio: String = fecha.subSequence(6, 10).toString()
        var sMes: String = fecha.subSequence(3, 5).toString()
        var sDia: String = fecha.subSequence(0, 2).toString()
        val diasdelanio: Int
        val diasrestantes: Int
        val restodias: Int

        if (Utils.bisiesto(sAnio)) {
            diasdelanio = 366
        } else {
            diasdelanio = 365
        }

        diasrestantes = diasdelanio - Utils.gregoriano(fecha)

        if (lapso.toInt() < diasrestantes) {
            //el lapso esta dentro del anio
            //calculo si esta dentro del mes
            if ((lapso.toInt() + sDia.toInt()) <= Utils.diasdelmes.get(sMes.toInt() - 1)) {
                return (sDia.toInt() + lapso.toInt()).toString() + '/' + sMes + '/' + sAnio
            } else {
                //dentro del anio y fuera del mes
                var i: Int = Utils.diasdelmes.get(sMes.toInt() - 1) - sDia.toInt()
                var j: Int = sMes.toInt() - 1
                while (i < lapso.toInt()) {
                    j++
                    i = i + Utils.diasdelmes.get(j)
                }
                restodias = (lapso.toInt() - (i - Utils.diasdelmes.get(j)))
                sMes = (j + 1).toString()
                sDia = restodias.toString()
                return (sDia + '/' + sMes + '/' + sAnio)
            }
        } else {
            //el lapso esta fuera del anio
            var i: Int = diasdelanio - Utils.gregoriano(fecha)
            var j: Int = sAnio.toInt()
            while (i < lapso.toInt()) {
                j++
                if (Utils.bisiesto(j.toString())) {
                    i = i + 366
                } else {
                    i = i + 365
                }
            }
            if (Utils.bisiesto(j.toString())) {
                restodias = (lapso.toInt() - (i - 366))
            } else {
                restodias = (lapso.toInt() - (i - 365))
            }
            sAnio = j.toString()
            // con el resto de los dias calcular los meses
            i = 0
            j = 0
            while (i < restodias) {
                i = i + Utils.diasdelmes.get(j)
                j++
            }
            sMes = j.toString()
            i = i - Utils.diasdelmes.get(j - 1)

            sDia = (restodias - i).toString()
            return (sDia + '/' + sMes + '/' + sAnio)
        }
    }
}


