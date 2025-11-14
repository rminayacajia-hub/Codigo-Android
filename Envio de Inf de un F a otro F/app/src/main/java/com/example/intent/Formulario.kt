package com.example.intent

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Formulario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //creacion de las variables del formulario que envia la informacion con intent
        val nombre= intent.getStringExtra("nombre")
        val apellido= intent.getStringExtra("apellido")

        // creacion de la variable del formulario 2
        val lblpersona = findViewById<TextView>(R.id.lbl_perosna)
        val btnregresar = findViewById<Button>(R.id.btn_regresar)

        // llenar el formulario 2 con la informacion del formulario 1
        lblpersona.text = nombre + " " + apellido

        //regresar al formulario 1
        btnregresar.setOnClickListener {
            finish()
        }


    }
}