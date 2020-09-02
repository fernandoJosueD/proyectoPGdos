package com.example.myapplication.inserts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplication.R

import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.MenuIncio
import com.example.myapplication.pantallaOpcion
import  com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pantalla_opcion.*
import kotlinx.android.synthetic.main.activity_set_persona.*
import kotlinx.android.synthetic.main.activity_update_set_persona.*
import java.text.DateFormat

import java.util.*

class setPersona : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    private var googleApiClient: GoogleApiClient? = null

    private var imagenusuariogmail: ImageView? = null
    private var textnombre_de_usuario: TextView? = null
    private var textcorreoUsario: TextView? = null
    private var id_usuario: TextView? = null
    var googleSignInClient: GoogleSignInClient?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_persona)


     //   /-----------------actividad para validar correo con google---------------------------------
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        //------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------

        imagenusuariogmail = findViewById<View>(R.id.imagenusuariogmail) as ImageView
        textnombre_de_usuario = findViewById<View>(R.id.textnombre_de_usuario) as TextView
        textcorreoUsario = findViewById<View>(R.id.textcorreoUsario) as TextView

//---------------------------------------------------------------------------------------------

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()
//-------------------------------------------------------------------------------------------------*/
        insertPin()


        btnREGRESAR.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnMostrarDatos2.setOnClickListener {
            validarVentana()
        }


        btnGuardarDatos.setOnClickListener {
            if (validar()) {
                guardarInfoUsuario()
            }
        }



        //------------------------------------------------------------------

    }
    fun salirDeLaAplicacion(){
        FirebaseAuth.getInstance().signOut()
        //salir de google--------------
        googleSignInClient?.signOut()
        finish()
        finish()
    }



    override fun onStart() {
        super.onStart()
        val opr =
            Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }
///agregar persistencia o escucha habilitado por siempre

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {

            val account = result.signInAccount
            textnombre_de_usuario?.setText(account!!.displayName)
            textcorreoUsario?.setText(account?.email)
            id_usuario?.setText(account?.id)
            imagenusuariogmail?.let { Glide.with(this).load(account?.photoUrl).into(it) }
        } else {

        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    fun guardarInfoUsuario() {
        // recupera mi uid de firebase y correo si asi lo deseo
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // startActivity(Intent(this,activity_segunda_actividad::class.java))
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()
            println("mi emailAcural es :" + emailcorreo + " pin" + pin)
            //   textViewGenero.setText(emailcorreo + " mi pin es $pin")
            //-------------------------------------------------------
            var unKilogramo: Double = 2.2046
            var pesoKilogramos: Double
            var pesoIMC: Double
            var miAltura: Double = editTextAltura.text.toString().toDouble()
            var miPeso: Double = editTextPeso.text.toString().toDouble()
            var alcuadrado: Double
            pesoKilogramos = (miPeso / unKilogramo)
            alcuadrado = miAltura * miAltura
            pesoIMC = pesoKilogramos / alcuadrado


                if (pesoIMC <= 18.5) {
                textViewGenero.setText("IMC rango de peso insuficiente " + "%.2f".format(pesoIMC))
            } else if (pesoIMC <= 25) {
                textViewGenero.setText("IMC rango de peso normal " + "%.2f".format(pesoIMC)
                )
            } else if (pesoIMC <= 30) {
                textViewGenero.setText("IMC rango de sobrepeso " + "%.2f".format(pesoIMC))
            } else if (pesoIMC <= 40) {
                textViewGenero.setText("IMC rango de obesidad " + "%.2f".format(pesoIMC))
            } else if (pesoIMC >= 40) {
                textViewGenero.setText("IMC rango de Obesidad Morvida " + "%.2f".format(pesoIMC))
            }

            var estadoImc:String= textViewGenero.text.toString()
            var imcDato: String
            imcDato = " %.2f".format(pesoIMC)

//------------declaro los valores y el array para acceder a el array genero ----------------------
            var spinnerGenero: Spinner? = null
            val items: Array<String>
            var isFirstime = true
//------------------------------------------------------------------------------------------------

            spinnerGenero = findViewById<View>(R.id.spinnerGenero) as Spinner
            items = resources.getStringArray(R.array.Genero)
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                baseContext,
                android.R.layout.simple_spinner_item,
                items
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGenero.setAdapter(adapter)
            //-----------recoger el dato seleccionado del array Genero----------------------------------
            spinnerGenero.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (isFirstime) {
                        var datoGenero = items[position]  //posicion del dato en el spinner

                        //-------------------------------------------------------------------------------
                        //almacenar la fecha
                        val calendar =
                            Calendar.getInstance().time //instancia Date para fecha
                        //------------------------------------
                        val currentDate = DateFormat.getDateInstance(DateFormat.FULL)
                            .format(calendar.time) //almacena el dato formato a string
                        //-------------------------------------
                        val textViewData =
                            findViewById<TextView>(R.id.textViewGenero) //asigna el textview para imprimir en el mismo
                        //-------------------------------------

                        var seteditTextNombre = editTextNombre.text.toString()
                        var seteditTextSegundoNombre = editTextSegundoNombre.text.toString()
                        var seteditTextApellido = editTextApellido.text.toString()
                        var setEditTextAltura = editTextAltura.text.toString()
                        var setEditTextString = editTextEdad.text.toString()
                        var setEditTextPeso = editTextPeso.text.toString()
                        var setoficio= editTexOficio.text.toString()
                        var centroEstudio=editTextEstudio.text.toString()
                        var setfacultad = editTextfacultad.text.toString()

                        var map = mutableMapOf<String, Any>()

                        map["correo_Usuario"] = emailcorreo
                        map["id_usuario"] = pin
                        map["nombre"] = seteditTextNombre
                        map["segundo nombre"] = seteditTextSegundoNombre
                        map["apellido"] = seteditTextApellido
                        map["altura metros"] = setEditTextAltura
                        map["edad"] = setEditTextString
                        map["peso en libras"] = setEditTextPeso
                        map["fecha"] = currentDate
                        map["genero"] = datoGenero
                        map["imc"] = imcDato
                        map["estado imc"]= estadoImc
                        map["oficio"]= setoficio
                        map["centro"]= centroEstudio
                        map["facultad"]= setfacultad


                        FirebaseFirestore.getInstance()
                        //FirebaseDatabase.getInstance().reference
                            .collection("usuarios")
                            .document("$pin")
                            .collection("Datos")
                            .document("usuario")
                            .set(map)

                        limpiar()
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            })

            //fin del la función

        }
    }

    fun validar(): Boolean {
        var retorno = true
        val nom: String = editTextNombre.getText().toString()
        val segnom: String=editTextSegundoNombre.getText().toString()
        val ape: String = editTextApellido.getText().toString()
        val est: String = editTextAltura.getText().toString()
        val eda: String = editTextEdad.getText().toString()
        val pes: String = editTextPeso.getText().toString()



        if (nom.isEmpty()) {
            editTextSegundoNombre.setError("ingrese un nombre")
            retorno = false
        }
        if (segnom.isEmpty()) {
            editTextNombre.setError("ingrese segundo nombre")
            retorno = false
        }
        if (ape.isEmpty()) {
            editTextApellido.setError("ingrese un apellido")
            retorno = false
        }
        if (est.isEmpty()) {
            editTextAltura.setError("ingrese su altura")
            retorno = false
        }
        if (eda.isEmpty()) {
            editTextEdad.setError("ingrese una edad")
            retorno = false
        }
        if (pes.isEmpty()) {
            editTextPeso.setError("ingrese su peso")
            retorno = false
        }
        return retorno
    }

    fun validarVentana(){
        var pin: String? = FirebaseAuth.getInstance().uid
        var correo: String? = FirebaseAuth.getInstance().currentUser?.email
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("usuario")
            .get().addOnSuccessListener { documentSnapshot ->

                var map = documentSnapshot.data as Map<String, Any>

               var correoEvaluar = map["correo_Usuario"].toString()
                var pinEvaluar =map["id_usuario"].toString()

                if (correoEvaluar.equals(correo)){
                    Toast.makeText(this,"datos actualizados",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,updateSetPersona::class.java))
                } else {
                    Toast.makeText(this,"no existen datos almacenados ",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,updateSetPersona::class.java))
                }
            }
    }

    fun insertPin(){
        var pin: String? = FirebaseAuth.getInstance().uid
        var updatePin:String = pin.toString()
        var map = mutableMapOf<String, Any>()
             map ["id_usuario"] = updatePin

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("usuario")
            .update(map)
                }

    fun limpiar(){
        editTextNombre.setText("")
        editTextApellido.setText("")
        editTextAltura.setText("")
        editTextEdad.setText("")
        editTexOficio.setText("")
        editTextEstudio.setText("")
        editTextfacultad.setText("")
        editTextSegundoNombre.setText("")
    }

    fun informaciondatoPeso():String{
        var unKilogramo: Double = 2.2046
        var pesoKilogramos: Double
        var pesoIMC: Double
        var miAltura: Double = editTextAltura.text.toString().toDouble()
        var miPeso: Double = editTextPeso.text.toString().toDouble()
        var alcuadrado: Double
        pesoKilogramos = (miPeso / unKilogramo)
        alcuadrado = miAltura * miAltura
        pesoIMC = pesoKilogramos / alcuadrado

        var resultado:String =""

        if (pesoIMC <= 18.5) {
            resultado =("Una medida de la obesidad se determina mediante el índice de masa corporal (IMC), que se calcula dividiendo los kilogramos de peso por el cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]). Según el Instituto Nacional del Corazón, los Pulmones y la Sangre de los Estados Unidos (NHLBI), el sobrepeso se define como un IMC de más de 25. Se considera que una persona es obesa si su IMC es superior a 30" +
                    " su  indice de masa corporal IMC esta en rango de peso insuficiente " + "%.2f".format(pesoIMC))
        } else if (pesoIMC <= 25) {
            resultado =("Una medida de la obesidad se determina mediante el índice de masa corporal (IMC), que se calcula dividiendo los kilogramos de peso por el cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]). Según el Instituto Nacional del Corazón, los Pulmones y la Sangre de los Estados Unidos (NHLBI), el sobrepeso se define como un IMC de más de 25. Se considera que una persona es obesa si su IMC es superior a 30 " +
                    "su  indice de masa corporal IMC esta en rango de peso normal " + "%.2f".format(pesoIMC)
            )
        } else if (pesoIMC <= 30) {
            resultado = ("Una medida de la obesidad se determina mediante el índice de masa corporal (IMC), que se calcula dividiendo los kilogramos de peso por el cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]). Según el Instituto Nacional del Corazón, los Pulmones y la Sangre de los Estados Unidos (NHLBI), el sobrepeso se define como un IMC de más de 25. Se considera que una persona es obesa si su IMC es superior a 30" +
                    "su  indice de masa corporal IMC esta rango de sobrepeso " + "%.2f".format(pesoIMC))
        } else if (pesoIMC <= 40) {
            resultado = ("Una medida de la obesidad se determina mediante el índice de masa corporal (IMC), que se calcula dividiendo los kilogramos de peso por el cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]). Según el Instituto Nacional del Corazón, los Pulmones y la Sangre de los Estados Unidos (NHLBI), el sobrepeso se define como un IMC de más de 25. Se considera que una persona es obesa si su IMC es superior a 30" +
                    "su  indice de masa corporal IMC esta en rango de obesidad " + "%.2f".format(pesoIMC))
        } else if (pesoIMC >= 40) {
            resultado = ("Una medida de la obesidad se determina mediante el índice de masa corporal (IMC), que se calcula dividiendo los kilogramos de peso por el cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]). Según el Instituto Nacional del Corazón, los Pulmones y la Sangre de los Estados Unidos (NHLBI), el sobrepeso se define como un IMC de más de 25. Se considera que una persona es obesa si su IMC es superior a 30" +
                    "su  indice de masa corporal IMC esta en rango de Obesidad Morvida " + "%.2f".format(pesoIMC))
        }

        var estadoImc:String= textViewGenero.text.toString()
        var imcDato: String
        imcDato = " %.2f".format(pesoIMC)
        return resultado
    }

}