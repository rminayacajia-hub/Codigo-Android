package com.example.examen

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Toast

class validacion_campos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_validacion_campos)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // mapear los campos
        val txtcedula = findViewById<EditText>(R.id.txt_cedula)
        val txtcorreo= findViewById<EditText>(R.id.txt_correo)
        val txtclave= findViewById<EditText>(R.id.txt_clave)
        val btnvalidar= findViewById<Button>(R.id.btn_Validar)

        // validacion de campos al momento de ejecutar la fun validacion l avalicion de la cedula debe ser igual a 10 digitos el correo debe ser validacion que sea tipo mail y la clave debe ser validada con un minimo de 8 caracteres
        // necesito un mensaje por cada campo que este ingresado de manera incorrecta

        btnvalidar.setOnClickListener {
            // validacion de campos con if anidados donde se debe validar que los campos no esten vacios
            // si elcedula igual a 10 digitos, correo que sea tipo mail y clave con minimo de 8 caracteres
            if (txtcedula.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese su cedula", Toast.LENGTH_SHORT).show()
            } else if (txtcedula.text.toString().length != 10) {
                Toast.makeText(this, "La cedula debe tener 10 digitos", Toast.LENGTH_SHORT).show()
            } else if (txtcorreo.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtcorreo.text.toString()).matches()) {
                Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show()
            } else if (txtclave.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese su clave", Toast.LENGTH_SHORT).show()
            } else if (txtclave.text.toString().length < 8) {
                Toast.makeText(this, "La clave debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Validacion exitosa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}