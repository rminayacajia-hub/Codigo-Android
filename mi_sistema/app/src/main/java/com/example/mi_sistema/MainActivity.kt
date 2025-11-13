package com.example.mi_sistema

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //Mapeo de los elementos de la vista y sus button con sus respectivos ID en el archivo XML
        var txtusuario=findViewById<EditText>(R.id.txt_usuario)
        var txtclave=findViewById<EditText>(R.id.txt_clave)
        var btningresar=findViewById<Button>(R.id.bnt_ingresar)
        var btnregistrarse=findViewById<Button>(R.id.btn_registrarse)

        // creacion del metodo onclick para el boton de ingresar
        btningresar.setOnClickListener {
            // crear una variable con la url de conexion
            var url="http://10.0.2.2:8080/mi_sistema/datos/usuarios.php"

            // crear una variable tipo josonObject para enviar los datos
            var datos = JSONObject()

            // agregar los datos a la variable datos con sus respectivos nombres
            datos.put("accion","ingresar") // estas variables viennen desde php
            datos.put("usuario",txtusuario.text.toString())
            datos.put("clave",txtclave.text.toString())

            // crear una variable tipo volley para enviar los datos
            var vrq = Volley.newRequestQueue(this)

            // crear la variable para enviar por post a la url
            var srq = JsonObjectRequest(Request.Method.POST,url,datos,
                { s ->

                    try {
                        val obj=(s)
                        if(obj.getBoolean("estado"))
                        {
                            val codigo=obj.getString("codigo")
                            Toast.makeText(applicationContext,"Ingreso Correcto",Toast.LENGTH_LONG).show()
                            // crear variables y codigo para llamar al segundo formulario
                        }
                        else{
                            Toast.makeText(applicationContext,"Usuario o Calve Incorrecta",Toast.LENGTH_LONG).show()
                        }
                    }
                    catch (e:Exception)
                    {
                        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
                    }

                },
                { volleyError ->
                    Toast.makeText(this,volleyError.message,Toast.LENGTH_LONG).show()
                })
            vrq.add(srq)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}