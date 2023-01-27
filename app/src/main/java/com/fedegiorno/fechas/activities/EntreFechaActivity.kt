package com.fedegiorno.fechas.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.fedegiorno.fechas.R
import com.fedegiorno.fechas.Utils
import com.fedegiorno.fechas.databinding.ActivityEntreFechaBinding
import com.fedegiorno.fechas.fragments.EntreFechasFragment
import com.fedegiorno.fechas.fragments.EntreFechasFragment.Companion.ARG_PARAM1


class EntreFechaActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    private lateinit var entreFechaBinding: ActivityEntreFechaBinding

    private var sFecha: String? = ""
    private var opcion: Int = 1

    @SuppressLint("ResourceType", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        entreFechaBinding = ActivityEntreFechaBinding.inflate(layoutInflater)
        setContentView(entreFechaBinding.root)

        entreFechaBinding.TXTfechainicial.requestFocus()

        entreFechaBinding.calendarioFecha.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            var dia: Int = dayOfMonth
            var mes = month + 1
            var anio: Int = year

            sFecha = Utils.cadenafecha(dia, mes, anio)
            entreFechaBinding.BTNfechas.isEnabled = true

            if (entreFechaBinding.TXTfechainicial.hasFocus()){
                entreFechaBinding.TXTfechainicial.setText(sFecha.toString())
            }

            if (entreFechaBinding.TXTfechafinal.hasFocus()){
                entreFechaBinding.TXTfechafinal.setText(sFecha.toString())
            }
        }

        entreFechaBinding.BTNfechas.setOnClickListener {
            var totaldias: Int = 0
            var totalanios: Int = 0
            var totalmeses: Int = 0
            var totalsemanas: Int = 0
            var restomeses: Int = 0
            var restosemanas: Int = 0
            var restodiassemana: Int = 0
            var restodias: Int = 0

            when (entreFechaBinding.BTNfechas.text){
                "OK Fecha Inicial" -> {
                    entreFechaBinding.BTNfechas.isEnabled = false
                    entreFechaBinding.BTNfechas.text = "OK Fecha Final"
                    entreFechaBinding.TXTfechafinal.requestFocus()
                }
                "OK Fecha Final" -> {
                    entreFechaBinding.BTNfechas.isEnabled = false
                    entreFechaBinding.BTNfechas.text = "Calcular"
                    entreFechaBinding.BTNfechas.isEnabled=true
                    entreFechaBinding.BTNfechas.requestFocus()
                }
                "Calcular" -> {
                    var miResultado: String? = null
                    //Verificar fechas
                    if ((Utils.verificarfecha(entreFechaBinding.TXTfechainicial.text.toString())) && Utils.verificarfecha(
                            entreFechaBinding.TXTfechafinal.text.toString()
                        )
                    ){
                        if (!fechasordenadas(entreFechaBinding.TXTfechainicial.text.toString(),entreFechaBinding.TXTfechafinal.text.toString())){
                            sFecha = entreFechaBinding.TXTfechainicial.text.toString()
                            entreFechaBinding.TXTfechainicial.setText(entreFechaBinding.TXTfechafinal.text.toString())
                            entreFechaBinding.TXTfechafinal.setText(sFecha)
                        }
                        totalanios = calcularanios(entreFechaBinding.TXTfechainicial.text.toString(),entreFechaBinding.TXTfechafinal.text.toString())
                        restomeses = calcularrestomeses(entreFechaBinding.TXTfechainicial.text.toString(),entreFechaBinding.TXTfechafinal.text.toString(), totalanios)
                        totaldias = calcularlapso(entreFechaBinding.TXTfechafinal.text.toString(), entreFechaBinding.TXTfechainicial.text.toString())
                        restodias = calcularRestodias(entreFechaBinding.TXTfechainicial.text.toString(),entreFechaBinding.TXTfechafinal.text.toString())
                        restosemanas = restodias / 7
                        restodiassemana = restodias % 7
                        when (opcion){
                            1 -> {
                                miResultado = totalanios.toString() + " anios\r\n" + restomeses + " meses\r\n" + restosemanas + " semanas\r\n" + restodiassemana + " dias"
                            }
                            2 -> {
                                totalmeses = totalanios * 12 + restomeses
                                miResultado = totalmeses.toString() + " meses\r\n"+ restosemanas + " semanas\r\n" + restodiassemana + " dias"
                            }
                            3 -> {
                                totalsemanas = totaldias / 7
                                miResultado = totalsemanas.toString() + " semanas\r\n" + restodiassemana + " dias"
                            }
                            4 -> {
                                miResultado = totaldias.toString() + " dias"
                            }
                        }
                        val bundle = bundleOf(ARG_PARAM1 to miResultado)
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add<EntreFechasFragment>(R.id.FragmentoContenedor, args = bundle)
                        }
                    }
                }
            }
        }

        entreFechaBinding.BTNHoy.setOnClickListener {
            var fechaHoy: Long = System.currentTimeMillis()
            entreFechaBinding.calendarioFecha.setDate(fechaHoy,false,false)
        }

        entreFechaBinding.TXTfechainicial.setOnClickListener {
            entreFechaBinding.BTNfechas.text = "OK Fecha Inicial"
            entreFechaBinding.BTNfechas.isEnabled = true
        }

        entreFechaBinding.TXTfechafinal.setOnClickListener {
            entreFechaBinding.BTNfechas.text = "OK Fecha Final"
            entreFechaBinding.BTNfechas.isEnabled = true
        }

        entreFechaBinding.RGXSelectorSalida.setOnCheckedChangeListener { group, checkedId ->
            when {
                checkedId == R.id.RBX_Anios -> {
                    opcion = 1
                    }
                checkedId == R.id.RBX_Meses -> {
                    opcion = 2
                }
                checkedId == R.id.RBX_semanas -> {
                    opcion = 3
                }
                checkedId == R.id.RBX_dias -> {
                    opcion = 4
                }

            }
        }
    }
    private fun calcularRestodias(fIni: String, fFin: String): Int {
        var sDia1: String = fIni.subSequence(0,2).toString()
        var sDia2: String = fFin.subSequence(0,2).toString()
        var sMes2: String = fFin.subSequence(3,5).toString()
        var resto: Int = 0
        var indice: Int = 0

        if ((sMes2.toInt()-2) > 0){
            indice = (sMes2.toInt()-2)
        } else {
            indice = 12 - (sMes2.toInt()-2)
        }

        if ((sDia2.toInt() - sDia1.toInt()) < 0) {
            resto = Utils.diasdelmes.get(indice)+(sDia2.toInt() - sDia1.toInt())
        } else {
            resto = (sDia2.toInt() - sDia1.toInt())
        }
        Log.d("KIRCHOFFF", "Indice: " + indice)
        Log.d("KIRCHOFFF", "Resto: " + resto)
        return resto
    }

    private fun fechasordenadas(fIni: String, fFin: String): Boolean {
        var sAnio1: String = fIni.subSequence(6,10).toString()
        var sMes1: String = fIni.subSequence(3,5).toString()
        var sDia1: String = fIni.subSequence(0,2).toString()
        var sAnio2: String = fFin.subSequence(6,10).toString()
        var sMes2: String = fFin.subSequence(3,5).toString()
        var sDia2: String = fFin.subSequence(0,2).toString()
        var fecha1: String = ""
        var fecha2: String = ""

        fecha1 = sAnio1 + sMes1 + sDia1
        fecha2 = sAnio2 + sMes2 + sDia2

        return fecha1.toLong() <= fecha2.toLong()
    }

    private fun calcularrestomeses(fInicio: String, fFinal: String, totalanios: Int): Int {
        var sMesIni: String = fInicio.subSequence(3,5).toString()
        var sMesFin: String = fFinal.subSequence(3,5).toString()
        var sDiaIni: String = fInicio.subSequence(0,2).toString()
        var sDiaFin: String = fFinal.subSequence(0,2).toString()

        var totalMeses: Int = 0

        if ((sMesIni.toInt() - sMesFin.toInt()) <= 0) {
            totalMeses = sMesFin.toInt() - sMesIni.toInt()
        }else{
            totalMeses =  12 + (sMesFin.toInt() - sMesIni.toInt())
        }

        if ((sDiaFin.toInt() < sDiaIni.toInt())){
            totalMeses--
        }

        return totalMeses
    }

    private fun calcularanios(fInicio: String, fFinal: String): Int {
        var sAnioIni: String = fInicio.subSequence(6,10).toString()
        var sAnioFin: String = fFinal.subSequence(6,10).toString()
        var greFechaIni: Int = Utils.gregoriano(fInicio)
        var greFechaFin: Int = Utils.gregoriano(fFinal)

        var cantidadanios: Int = sAnioFin.toInt()-sAnioIni.toInt()

        if (greFechaIni <= greFechaFin){
            //cumplio anios
            Log.i("KIRCHOFFF", (greFechaFin-greFechaIni).toString())
        } else {
            //no cumplio anios
            Log.i("KIRCHOFFF", (greFechaFin-greFechaIni).toString())
            cantidadanios--
        }
        return cantidadanios
    }

    private fun calcularlapso(fFinal: String, fInicial: String): Int {
        var sAnio: String = ""
        var anioIni: Int = 0
        var anioFin: Int = 0
        var gregIni: Int = 0
        var gregFin: Int = 0

        gregIni = Utils.gregoriano(fInicial)
        gregFin = Utils.gregoriano(fFinal)

        sAnio = fFinal.subSequence(6,10).toString()

        anioFin = sAnio.toInt()

        sAnio = fInicial.subSequence(6,10).toString()

        anioIni = sAnio.toInt()

        when (anioFin - anioIni){
            0 -> return calcularlapso1(gregFin, gregIni)
            1 -> return calcularlapso2(gregFin, gregIni, anioIni)
            else -> return calcularlapso3(gregFin, gregIni, anioIni, anioFin)
        }
    }

    private fun calcularlapso1(gregFin: Int, gregIni: Int): Int {
        return (gregFin - gregIni)
    }

    private fun calcularlapso2(gregFin: Int, gregIni: Int, anioIni: Int): Int {
        var hastafin: Int = 0

        if (Utils.bisiesto(anioIni.toString())){
            hastafin = 366 - gregIni
        }else{
            hastafin = 365 - gregIni
        }
        return hastafin + gregFin
    }

    private fun calcularlapso3(gregFin: Int, gregIni: Int, anioIni: Int, anioFin: Int): Int {
        var cantdias: Int = 0

        for (i in anioIni+1..anioFin-1){
            if (Utils.bisiesto(i.toString())){
                cantdias = cantdias + 366
            }else{
                cantdias = cantdias + 365
            }
        }
        return cantdias + calcularlapso2(gregFin, gregIni, anioIni)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        TODO("Not yet implemented")
    }

}

