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

class MainActivity : AppCompatActivity() {
    // variable para la conexion a mysql con php var url="http://10.0.2.2:8080/WSAGENDA26/datos/persona.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //de claracion de variable de las cajas y botones con sus respectivos id del activity_main_xml
        val btningresar = findViewById<Button>(R.id.btn_ingreso)
        val txtusu = findViewById<EditText>(R.id.txt_usu)
        val txtcla = findViewById<EditText>(R.id.txt_clave)
        val btnmenuprincipal = findViewById<Button>(R.id.btn_menuprincipal)
        val btnperfilusuario = findViewById<Button>(R.id.btn_perfilusuari)



        // generar la accion boton de perfilusuario con setOnclickListen

        btnperfilusuario.setOnClickListener{
            val FormularioRegistro= Intent(this, perfilusuario::class.java)
            startActivity(FormularioRegistro)
        }

        btnmenuprincipal.setOnClickListener{
            val FormularioRegistro= Intent(this, menuprincipal::class.java)
            startActivity(FormularioRegistro)
        }


        btningresar.setOnClickListener{
            var url="http://10.0.2.2:8080/WSAGENDA26/datos/persona.php"
            val datos= JSONObject()
            datos.put("accion","loggin")
            datos.put("usuario", txtusu.text.toString())
            datos.put("clave",txtcla.text.toString())
            val rq= Volley.newRequestQueue(this)

            val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
                {s->
                    try {
                        //Para llamar al segundo formulario
                        val obj=(s)
                        if(obj.getBoolean("estado")){
                            val codigo=obj.getString("codigo")

                            val f= Intent(this, validacion_campos::class.java)
                            f.putExtra("cod_persona",codigo)
                            startActivity(f)
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "Usuario o clave incorrecta", Toast.LENGTH_LONG).show()
                        }
                    }
                    catch (e: JSONException)
                    {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }, {
                        volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()

                })
            rq.add(jsor)

        }







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}