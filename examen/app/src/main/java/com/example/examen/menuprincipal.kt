package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menuprincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menuprincipal)
        // declaracion de variables
        val btnperfil = findViewById<Button>(R.id.btn_perfilusuario)
        val bntcerrarcesion=findViewById<Button>(R.id.bnt_cerracesion)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // logica de programacion de cada boton con su funcion
        btnperfil.setOnClickListener {
            val intent = Intent(this, perfilusuario::class.java)
            startActivity(intent)
        }
        bntcerrarcesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}