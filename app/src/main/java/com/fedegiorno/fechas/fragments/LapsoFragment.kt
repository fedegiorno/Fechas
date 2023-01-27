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
import com.fedegiorno.fechas.databinding.FragmentLapsoBinding

class LapsoFragment : Fragment() {
    private var _lapsobinding: FragmentLapsoBinding? = null
    private val lapsobinding get() = _lapsobinding!!

    private var lapsoResultado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lapsoResultado = it.getString(ARG_PARAM2)
            Log.d("KIRCHOFFF", lapsoResultado.orEmpty())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _lapsobinding = FragmentLapsoBinding.inflate(inflater, container, false)
        lapsobinding.TXTResultadoLapso.setText(lapsoResultado)

        return lapsobinding.root
    }

    companion object {
        const val ARG_PARAM2 = "resultado_lapso"

        @JvmStatic
        fun newInstance(lapsoResultado: String) =
            LapsoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM2, lapsoResultado)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _lapsobinding = null
    }
}