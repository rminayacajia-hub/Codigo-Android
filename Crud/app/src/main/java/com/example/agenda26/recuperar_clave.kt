package com.example.agenda26

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import android.widget.TextView

class recuperar_clave : AppCompatActivity() {
    lateinit var txtci: EditText
    lateinit var btnrecuperarclave: Button
    lateinit var btnsalir: Button
    lateinit var txtmostrarclave: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_clave)

        mapeo()

        btnsalir.setOnClickListener {
            finish()
        }
        btnrecuperarclave.setOnClickListener {
            recuperarClave()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun mapeo() {
        // Nota: Se usa 'edt_cedula' que es el ID en el archivo XML del layout.
        txtci = findViewById<EditText>(R.id.txt_ci)
        btnrecuperarclave = findViewById<Button>(R.id.btn_recuperar_clave)
        txtmostrarclave = findViewById<TextView>(R.id.txt_mostrar_clave)
        btnsalir = findViewById<Button>(R.id.btn_salir)
    }

    // creacion de la funcion recuperar clave con volley para que la clave se muestre en el txtmostrarclave
    fun recuperarClave() {
        // url del servicio web
       val url = "http://10.0.2.2:8080/WSAGENDA26/datos/recuperarclave.php"
        // crear la variable con JsonObject con los datos a enviar
        val datos = JSONObject()
        // agregar los datos a la variable y la accion de php
        datos.put("accion", "recuperarclave")
        datos.put("ci", txtci.text.toString())

        val rq = Volley.newRequestQueue(this)

        val jsor = JsonObjectRequest(
            Request.Method.POST, url, datos,
            Response.Listener { s ->
                try {
                    // se crea getBoolean con estado desde el php
                    if (s.getBoolean("estado")) {
                       // mostrar la contraseña en el txtmostrarclave con getString si el codigo en php existe
                        val claveRecuperada = s.getString("codigo")
                        // crear mensaje con su clave es toas
                        Toast.makeText(applicationContext, "Su clave es: $claveRecuperada", Toast.LENGTH_LONG).show()
                        txtmostrarclave.text = claveRecuperada
                    } else {
                        // Mensaje si el usuario no existe en el php
                        val mensaje = s.getString("mensaje")
                        Toast.makeText(applicationContext, mensaje, Toast.LENGTH_LONG).show()
                        txtmostrarclave.text = ""
                    }
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }
            },
            { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }
        )
        rq.add(jsor)
    }
}