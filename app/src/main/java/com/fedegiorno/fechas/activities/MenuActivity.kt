package com.fedegiorno.fechas.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fedegiorno.fechas.R
import com.fedegiorno.fechas.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var menubinding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menubinding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(menubinding.root)


        menubinding.BTNEntrefechas.setOnClickListener {
            val intent = Intent(this, EntreFechaActivity::class.java)
            startActivity(intent)
        }
        menubinding.BTNLapso.setOnClickListener {
            val intent = Intent(this, LapsoActivity::class.java)
            startActivity(intent)
        }

    }
}
