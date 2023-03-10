package com.fedegiorno.fechas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.fedegiorno.fechas.R
import com.fedegiorno.fechas.Utils
import com.fedegiorno.fechas.databinding.FragmentEntreFechasBinding

class EntreFechasFragment : Fragment() {
    private var _entreFechaBinding: FragmentEntreFechasBinding? = null
    private val entreFechaBinding get() = _entreFechaBinding!!

    private var fechaResultado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fechaResultado = it.getString(ARG_PARAM1)
            Log.d("KIRCHOFFF", fechaResultado.orEmpty())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _entreFechaBinding = FragmentEntreFechasBinding.inflate(inflater, container, false)
        entreFechaBinding.TXTResultadoFecha.setText(fechaResultado)
        return entreFechaBinding.root

    }

    companion object {
        const val ARG_PARAM1 = "resultado_fecha"

        @JvmStatic
        fun newInstance(fechaResultado: String) =
            EntreFechasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, fechaResultado)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _entreFechaBinding = null
    }
}