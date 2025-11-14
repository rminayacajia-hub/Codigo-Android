package com.example.agenda26

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class contacto : AppCompatActivity() {
    //Declaracion de variables de todos los elementos qu evamos a utilizar, fuera para trabajar con metodos
    lateinit var txtnombre: EditText
    lateinit var txtapellido: EditText
    lateinit var txttelefono: EditText
    lateinit var txtcorreo: EditText
    lateinit var txtcodigo: EditText
    lateinit var txtcodcontacto: EditText
    lateinit var btnnuevo: Button
    lateinit var btnguardar: Button
    lateinit var btncancelar: Button
    lateinit var btneliminar: Button
    lateinit var btnactualizar: Button
    lateinit var lvllistar: ListView
    var codigos= ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacto)

        // llamar a la funcion mapeo
        mapeo()
        txtcodigo.setText(intent.getStringExtra("cod_persona"))
        // llamar a la funcion primero limpiar cajas lcajas()
        lcajas()
        // llamar a la funcion segundo bloquear cajas bcajas()
        bcajas()
        // llamar a la funcion tercero botones bbotones()
        bbotones()
        // pero el botono nuevo btnnuevo debe estar acti
        btnnuevo.isEnabled=true
        // llamar a la funcion consultar
        consultar()
        // crear el evento del boton nuevo btnnuevo
        btnnuevo.setOnClickListener {
            // llamar a la funcion bbotones
            bbotones()
            // activar los botones cancelar y guardar
            btncancelar.isEnabled=true
            btnguardar.isEnabled=true
            // llamar a la funcion lcajas y acajas
            lcajas()
            acajas()
        }
        // crear el evento del boton guardar btnguardar

        btnguardar.setOnClickListener{

            //validacion de campos vacios
            if(txtnombre.text.toString().equals("") || txtapellido.text.toString().equals("")
                || txttelefono.text.toString().equals("") || txtcorreo.text.toString().equals(""))

            //crear un toast para mostrar mensaje
            {
                Toast.makeText(applicationContext, "Debe llenar todos los campos", Toast.LENGTH_LONG).show()
            }
            // si los campos no estan vacios procedemos a insertar los datos
            else {
                insertar()
            }

        }

        // crear el evento del boton cancelar btncancelar
        btncancelar.setOnClickListener {
            // llamar a la funcion bbotones
            bbotones()
            // llamar a la funcion lcajas y bcajas
            lcajas()
            bcajas()
            // activar el boton nuevo
            btnnuevo.isEnabled=true

        }
        // crear el evento del boton actualizar btnactualizar
        btnactualizar.setOnClickListener{
            //validacion de campos vacios
            if(txtnombre.text.toString().equals("") || txtapellido.text.toString().equals("") ||
                txttelefono.text.toString().equals("") || txtcorreo.text.toString().equals(""))
            //crear un toast para mostrar mensaje
            {
                Toast.makeText(applicationContext, "Debe llenar todos los campos", Toast.LENGTH_LONG).show()
            }
            // si los campos no estan vacios procedemos a actualizar los datos
            else {
                actualizar()
            }
        }

        // crear el evento del boton eliminar btneliminar
        btneliminar.setOnClickListener {
           eliminar()
        }

        // crear la lista lvllistar
        lvllistar.setOnItemClickListener { adapterView, view, i, l ->
            // llamar a la funcion bbotones ->
            bbotones()
            btncancelar.isEnabled=true
            btneliminar.isEnabled=true
            btnactualizar.isEnabled=true
            txtcodcontacto.setText(codigos[i])
            consultarDato(codigos[i])
            btnactualizar.isEnabled=true
            acajas()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    //crear funcion para el mapeo de las variables declaradas mas cajas de texto y botones
    fun mapeo(){
        txtcodigo=findViewById<EditText>(R.id.txt_codigo)
        txtnombre=findViewById<EditText>(R.id.txt_nombre)
        txtapellido=findViewById<EditText>(R.id.txt_apellido)
        txttelefono=findViewById<EditText>(R.id.txt_telefono)
        txtcorreo=findViewById<EditText>(R.id.txt_correo)
        txtcodcontacto=findViewById<EditText>(R.id.txt_cod_contacto)
        lvllistar=findViewById<ListView>(R.id.lvl_lista)
        btnnuevo=findViewById<Button>(R.id.btn_nuevo)
        btnguardar=findViewById<Button>(R.id.btn_guardar)
        btncancelar=findViewById<Button>(R.id.btn_cancelar)
        btneliminar=findViewById<Button>(R.id.btn_eliminar)
        btnactualizar=findViewById<Button>(R.id.btn_actualizar)
    }
    // crear la funcion para bloquear los botones con el nombre bbotones
    fun bbotones(){
        btnnuevo.isEnabled=false
        btnguardar.isEnabled=false
        btnactualizar.isEnabled=false
        btncancelar.isEnabled=false
        btneliminar.isEnabled=false
    }
    // crear la funcion para limpiar las cajas con el nombre lcajas
    fun lcajas(){
        txtnombre.setText("")
        txtapellido.setText("")
        txttelefono.setText("")
        txtcorreo.setText("")
        txtcodcontacto.setText("")
    }
    // crear la funcion para bloquear las cajas con el nombre bcajas
    fun bcajas(){
        txtnombre.isEnabled=false
        txtapellido.isEnabled=false
        txttelefono.isEnabled=false
        txtcorreo.isEnabled=false
    }
    // crear la funcion para activar las cajas con el nombre acajas
    fun acajas(){
        txtnombre.isEnabled=true
        txtapellido.isEnabled=true
        txttelefono.isEnabled=true
        txtcorreo.isEnabled=true
        txtnombre.requestFocus()
    }
    // crear la funcion consultar para listar los contactos
    fun consultar(){
        val al= ArrayList<String>()
        codigos.clear() // Limpiar la lista de códigos antes de una nueva consulta
        var url="http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
        val datos= JSONObject()
        datos.put("accion","consultar")
        datos.put("cod_persona", txtcodigo.text.toString())
        val rq= Volley.newRequestQueue(this)
        val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
            {s->
                try {

                    val obj=(s)
                    if(obj.getBoolean("estado")){
                        val array=obj.getJSONArray("personas")
                        for(i in 0 .. array.length()-1){
                            val fila=array.getJSONObject(i)
                            al.add(fila.getString("nombre")+" "+fila.getString("apellido"))
                            codigos.add(fila.getString("codigo"))
                        }
                        val ad= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al)
                        lvllistar.adapter=ad
                        ad.notifyDataSetChanged()
                    }
                    else
                    {
                       // Opcional: Mostrar un toast si no hay contactos
                       // Toast.makeText(applicationContext, "No tienes contactos", Toast.LENGTH_SHORT).show()
                        lvllistar.adapter = null // Limpiar la lista si no hay datos
                    }
                }
                catch (e: JSONException)
                {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }
            }, { volleyError ->
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
            })
        rq.add(jsor)
    }
    // crear la funcion insertar o guardar del button guardar los contactos
    fun insertar(){
        var url="http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
        val datos= JSONObject()
        datos.put("accion","insertar")
        datos.put("cod_persona", txtcodigo.text.toString())
        datos.put("nombre", txtnombre.text.toString())
        datos.put("apellido", txtapellido.text.toString())
        datos.put("telefono", txttelefono.text.toString())
        datos.put("email", txtcorreo.text.toString())
        val rq= Volley.newRequestQueue(this)
        val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
            {s->
                try {
                    val obj=(s)
                    Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                    if(obj.getBoolean("estado")){
                        lcajas()
                        bcajas()
                        bbotones()
                        btnnuevo.isEnabled=true
                        consultar()
                    }
                }
                catch (e: JSONException)
                {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }
            }, { volleyError ->
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
            })
        rq.add(jsor)
    }

    // crear la funcion consultarDato para listar los contactos
    fun consultarDato(codigo: String){
        var url="http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
        val datos= JSONObject()
        datos.put("accion","consultarDato")
        datos.put("cod_contacto", codigo)
        val rq= Volley.newRequestQueue(this)
        val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
            {s->
                try {
                    val obj=(s)
                    if(obj.getBoolean("estado"))
                    {
                        val dato=obj.getJSONObject("contacto")
                        txtnombre.setText(dato.getString("nom_contacto"))
                        txtapellido.setText(dato.getString("ape_contacto"))
                        txttelefono.setText(dato.getString("telefono_contacto"))
                        txtcorreo.setText(dato.getString("email_contacto"))
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
            }, { volleyError ->
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
            })
        rq.add(jsor)
    }

    // crear la funcion para actualizar los datos
    fun actualizar(){
        var url="http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
        val datos= JSONObject()
        datos.put("accion","actualizar")
        datos.put("cod_contacto", txtcodcontacto.text.toString())
        datos.put("nombre", txtnombre.text.toString())
        datos.put("apellido", txtapellido.text.toString())
        datos.put("telefono", txttelefono.text.toString())
        datos.put("email", txtcorreo.text.toString())
        val rq= Volley.newRequestQueue(this)
        val jsor= JsonObjectRequest(Request.Method.POST,url,datos,
            {s->
                try {
                    val obj=(s)
                    Toast.makeText(applicationContext, obj.getString("mensaje"), Toast.LENGTH_LONG).show()
                    if(obj.getBoolean("estado")){
                        lcajas()
                        bcajas()
                        bbotones()
                        btnnuevo.isEnabled=true
                        consultar()
                    }
                }
                catch (e: JSONException)
                {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                }
            }, { volleyError ->
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
            })
        rq.add(jsor)
    }

    // crear la funcion para eliminar el contacto
    fun eliminar(){
        val mseliminar = AlertDialog.Builder(this)
        mseliminar.setTitle("Confirmar eliminación")
        mseliminar.setMessage("¿Estás seguro de que deseas eliminar este contacto?")

        mseliminar.setPositiveButton("Sí") { dialog, which ->
            val url = "http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
            val datos = JSONObject()
            datos.put("accion", "eliminar")
            datos.put("cod_contacto", txtcodcontacto.text.toString())

            val rq = Volley.newRequestQueue(this)
            val jsor = JsonObjectRequest(Request.Method.POST, url, datos,
                { response ->
                    try {
                        Toast.makeText(applicationContext, response.getString("mensaje"), Toast.LENGTH_LONG).show()
                        if (response.getBoolean("estado")) {
                            // Actualizar UI después de eliminar
                            lcajas()
                            bcajas()
                            bbotones()
                            btnnuevo.isEnabled = true
                            consultar()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                    }
                },
                { volleyError ->
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            )
            rq.add(jsor)
        }

        mseliminar.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = mseliminar.create()
        dialog.show()
    }
}
