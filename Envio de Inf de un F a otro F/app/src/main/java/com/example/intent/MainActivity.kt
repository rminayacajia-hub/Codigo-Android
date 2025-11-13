package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtnombre = findViewById<EditText>(R.id.txt_nombre)
        val txtapellido = findViewById<EditText>(R.id.txt_apellido)
        val btnenviar = findViewById<Button>(R.id.btn_enviar)

        //creacion del evento click del boton enviar
        btnenviar.setOnClickListener {
            // declaracion de la variable para enviar los datos
            val enviar = Intent(this, Formulario::class.java)
            // envio de datos
            enviar.putExtra("nombre", txtnombre.text.toString())
            enviar.putExtra("apellido", txtapellido.text.toString())
            // inicio de la actividad
            startActivity(enviar)

        }
    }
}