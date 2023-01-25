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

    private var resultado: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultado = it.getString(ARG_PARAM1)
            Log.d("KIRCHOFFF", resultado.orEmpty())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _entreFechaBinding = FragmentEntreFechasBinding.inflate(inflater, container, false)
        entreFechaBinding.TXTResultadoFecha.setText(resultado)
        return entreFechaBinding.root

    }

    companion object {
        const val ARG_PARAM1 = "resultado_parametro"

        @JvmStatic
        fun newInstance(resultado: String) =
            EntreFechasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, resultado)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _entreFechaBinding = null
    }
}