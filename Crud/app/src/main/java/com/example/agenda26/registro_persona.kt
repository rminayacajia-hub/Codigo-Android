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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class registro_persona : AppCompatActivity() {

    // Declaración de variables para los campos de texto y botones
     lateinit var txtCedula: EditText
     lateinit var txtNombre: EditText
     lateinit var txtApellido: EditText
     lateinit var txtClave: EditText
     lateinit var txtCorreo: EditText
     lateinit var btnGuardar: Button
     lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_persona)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mapeo de las variables con los IDs del layout
        txtCedula = findViewById<EditText>(R.id.txt_cedula)
        txtNombre = findViewById<EditText>(R.id.txt_nombre)
        txtApellido = findViewById<EditText>(R.id.txt_apellido)
        txtClave = findViewById<EditText>(R.id.txt_clave)
        txtCorreo = findViewById<EditText>(R.id.txt_correo)
        btnGuardar = findViewById<Button>(R.id.btn_guardar)
        btnCancelar = findViewById<Button>(R.id.btn_cancelar)

        // Asignación de listeners a los botones
        btnGuardar.setOnClickListener {
            // Verificación de campos vacíoscon equals
            if (txtCedula.text.toString().equals("") ||
                txtNombre.text.toString().equals("") ||
                txtApellido.text.toString().equals("") ||
                txtClave.text.toString().equals("") ||
                txtCorreo.text.toString().equals("")
            ) {
                Toast.makeText(applicationContext, "Debe llenar todos los campos", Toast.LENGTH_LONG).show()
            } else {
                // Llamada a la función para registrar la persona
                registrarPersona()
                limparcajas()
            }

        }

        btnCancelar.setOnClickListener {
            finish() // Cierra la actividad actual
        }

    }
    // Función para limpiar las cajas de texto
    fun limparcajas(){
        txtCedula.setText("")
        txtNombre.setText("")
        txtApellido.setText("")
        txtClave.setText("")
        txtCorreo.setText("")
        txtCedula.requestFocus()

    }

    fun registrarPersona() {
        // URL del script PHP para el registro
        val url = "http://10.0.2.2:8080/WSAGENDA26/datos/registro_persona.php"
        // Crear un objeto JSONObject con los datos a enviar
        val datos= JSONObject()
        // Asignar los valores de los campos de texto al objeto JSONObject
        datos.put("accion","insertar")
        datos.put("ci_persona",txtCedula.text.toString())
        datos.put("nom_persona",txtNombre.text.toString())
        datos.put("ape_persona",txtApellido.text.toString())
        datos.put("clave_persona",txtClave.text.toString())
        datos.put("correo_persona",txtCorreo.text.toString())

        // Crear una nueva cola de solicitudes de Volley
        val rq = Volley.newRequestQueue(this)

        // Crear una nueva solicitud de tipo POST con los datos a enviar y los listeners correspondientes

        val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
            {s->
                try {
                    val obj=(s)
                    if(obj.getBoolean("estado")){
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                    }
                }
                catch (e: JSONException)
                {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }
            }, {
                    volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()

            })

        // Agregar la solicitud a la cola de solicitudes
        rq.add(jsor)
    }
}
