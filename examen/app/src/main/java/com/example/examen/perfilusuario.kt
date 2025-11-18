package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class perfilusuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfilusuario)

        val txtCi = findViewById<EditText>(R.id.txt_ci_persona)
        val txtNom = findViewById<EditText>(R.id.txt_nom_persona)
        val txtApe = findViewById<EditText>(R.id.txt_ape_persona)
        val txtCorreo = findViewById<EditText>(R.id.txt_correo_persona)
        val txtClave = findViewById<EditText>(R.id.txt_clave_persona)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar)
        val btnRegresar = findViewById<Button>(R.id.btn_regresar)

        btnGuardar.setOnClickListener {
            val url = "http://10.0.2.2:8080/WSAGENDA26/datos/persona.php"
            val datos = JSONObject()
            datos.put("accion", "insertar")
            datos.put("ci_persona", txtCi.text.toString())
            datos.put("nom_persona", txtNom.text.toString())
            datos.put("ape_persona", txtApe.text.toString())
            datos.put("correo_persona", txtCorreo.text.toString())
            datos.put("clave_persona", txtClave.text.toString())

            val rq = Volley.newRequestQueue(this)
            val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                { response ->
                    try {
                        if (response.getBoolean("estado")) {
                            Toast.makeText(applicationContext, "Guardado correctamente", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(applicationContext, "Error al guardar", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                    }
                },
                { volleyError ->
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                })
            rq.add(jsor)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, menuprincipal::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
