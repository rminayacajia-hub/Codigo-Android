package com.example.agenda26

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
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
import java.util.ArrayList

class contacto : AppCompatActivity() {
        //Declaracion de variables de todos los elementos qu evamos a utilizar, fuera para trabajar con metodos
    lateinit var txtnombre: EditText
    lateinit var txtapellido: EditText
    lateinit var txttelefono: EditText
    lateinit var txtcorreo: EditText
    lateinit var txtcodigo: EditText
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
                    lcajas()
                    bcajas()
                    bbotones()
                    btnnuevo.isEnabled=true
                    consultar()
                }

            }

            // crear el evento del boton cancelar btncancelar
            btncancelar.setOnClickListener {
                // llamar a la funcion bbotones
                bbotones()
                // llamar a la funcion lcajas y acajas
                lcajas()
                bcajas()
                btnnuevo.isEnabled=true

            }
            // crear la lista lvllistar
            lvllistar.setOnItemClickListener { adapterView, view, i, l ->
                // llamar a la funcion bbotones ->
                bbotones()
                btncancelar.isEnabled=true
                btneliminar.isEnabled=true
                btnactualizar.isEnabled=true
                consultarDato(codigos[i])

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
        lvllistar=findViewById<ListView>(R.id.lvl_lista)
        btnnuevo=findViewById<Button>(R.id.btn_nuevo)
        btnguardar=findViewById<Button>(R.id.btn_guardar)
        btncancelar=findViewById<Button>(R.id.btn_cancelar)
        btneliminar=findViewById<Button>(R.id.btn_eliminar)
        btnactualizar=findViewById<Button>(R.id.btn_actualizar)
        btneliminar=findViewById<Button>(R.id.btn_eliminar)

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
    }
    // crear la funcion consultar para listar los contactos
    fun consultar(){
        val al= ArrayList<String>()
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
    // crear la funcion insertar o guardar del button guardar los contactos
    fun insertar(){

        var url="http://10.0.2.2:8080/WSAGENDA26/datos/contacto.php"
        val datos= JSONObject()

        datos.put("accion","insertar")
        datos.put("cod_persona", txtcodigo.text.toString())
        datos.put("nombre", txtnombre.text.toString())
        datos.put("apellido", txtapellido.text.toString())
        datos.put("telefono", txttelefono.text.toString())
        datos.put("correo", txtcorreo.text.toString())


        val rq= Volley.newRequestQueue(this)
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
            }, {
                    volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()

            })
        rq.add(jsor)
    }

}