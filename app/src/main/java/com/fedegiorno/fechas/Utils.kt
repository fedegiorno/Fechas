package com.fedegiorno.fechas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Utils : AppCompatActivity() {

    companion object {
        val diasdelmes: List<Int> = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val diasacummes: List<Int> = listOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)

        fun cadenafecha(dia: Int, mes: Int, anio: Int): String {
            val sAnio: String = anio.toString()
            var sMes: String = mes.toString()
            var sDia: String = dia.toString()

            if (dia < 10){
                sDia = '0' + dia.toString()
            }
            if (mes < 10){
                sMes = '0' + mes.toString()
            }
            return sDia + '/' + sMes + '/' + sAnio
        }

        fun verificarfecha(fecha: String): Boolean {
            val sAnio: String = fecha.subSequence(6,10).toString()
            val sMes: String = fecha.subSequence(3,5).toString()
            val sDia: String = fecha.subSequence(0,2).toString()

            return (sMes.toInt()>0 && sMes.toInt()<12) && (sDia.toInt()>0 && sDia.toInt()<32) && (sAnio.length < 5)
        }

        fun gregoriano(sFecha: String): Int {
            val sAnio: String
            val sMes: String
            val sDia: String
            var mes: Int

            sAnio = sFecha.subSequence(6,10).toString()
            sMes = sFecha.subSequence(3,5).toString()
            sDia = sFecha.subSequence(0,2).toString()

            mes = diasacummes.get(sMes.toInt()-1)

            if (bisiesto(sAnio)&&(sMes.toInt()>2)){
                mes++
            }
            return mes + sDia.toInt()
        }

        fun bisiesto(sAnio: String): Boolean {
            return (sAnio.toInt() % 4 == 0) && ((sAnio.toInt() % 100 != 0) || (sAnio.toInt() % 400 == 0))
        }
    }
}