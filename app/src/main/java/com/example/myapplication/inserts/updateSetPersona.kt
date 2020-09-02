package com.example.myapplication.inserts

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.MenuIncio
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pantalla_opcion.*
import kotlinx.android.synthetic.main.activity_set_persona.*
import kotlinx.android.synthetic.main.activity_update_set_persona.*

class updateSetPersona : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_set_persona)

        mostrarDatos()

        btnMostrarDatos3.setOnClickListener {
        actualizaDatosFinales()
            mostrarDatos() }

        btnupdateDatosPersonales.setOnClickListener { actualizar()
            limpiar()
            actualizaDatosFinales()
            mostrarDatos()}

        btnreInicio.setOnClickListener {  startActivity(Intent(this,MenuIncio::class.java)) }

        btnReturnMostrarDatos.setOnClickListener { startActivity(Intent(this,setPersona::class.java)) }

    }//---------fin oncreate
    fun llave():String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()
        }
        var contraseña = (currentUser?.uid.toString())
        return contraseña
    }

    @SuppressLint("SetTextI18n")
    fun mostrarDatos(){
        ///// validacion por cualquier correo

        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()


            //----------------------------------------------------------
//------recoge el id y correo de usuario para validar el ingreso a datos personales

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("Datos")
                .document("usuario")
                .get().addOnSuccessListener { documentSnapshot ->


                    var map = documentSnapshot.data as Map<String, Any>
                    textleerNombre.text = map["nombre"].toString()
                    textleerNombredos.text = map["segundo nombre"].toString()
                    textleerIMCpersona.text = map["estado imc"].toString()
                    textleerApellido.text = map["apellido"].toString()
                    var valorAltura = map["altura metros"].toString()
                    var miEdad = map["edad"].toString()
                    var miPeso = map["peso en libras"].toString()
                    textleerFechaIngresoDato.text = map["fecha"].toString()
                    textogenero.text = map["genero"].toString()
                    textleerModificado.text = map["fecha modificado"].toString()
                    textViewemailUsario.text = map["correo_Usuario"].toString()
                   // textleerModificado.text = map["actualizacion"].toString()
                    textleeroficio.text = map ["oficio"].toString()
                    textleercentroestudio.text= map ["centro"].toString()
                    textleerfacultad.text = map["facultad"].toString()
                    textogenero.text= map["genero"].toString()


                    textleerAltura.setText("mi altura es $valorAltura m")
                    textleerEdad.setText("mi edad es $miEdad años")
                    textleerPeso.setText("mi peso es $miPeso.Lbs")

                }
            //////---------------------------------------------------------


        }
    }

    fun actualizar ():Boolean{
        var valoresLlenos = true
        var nom: String = editactualizarNombre.getText().toString()
        var segnom:String= editactualizarNombredos.getText().toString()
        var ape: String = editactualizarApellido.getText().toString()
        var eda: String = editactualizarEdad.getText().toString()
        var pes: String = editactualizarPesoLibras.getText().toString()
        var alt: String = editactualizarAltura.getText().toString()
        var ofi:String  = editactualizaroficio.getText().toString()
        var cent:String = editactualizarcentroestudio.getText().toString()
        var fac: String = editactualizarfacultad.getText().toString()

        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()

            //----------verifica si hay espacions vacios

            if (nom.isEmpty()) {
                valoresLlenos = false
            } else {
                var updateNombre = editactualizarNombre.text.toString()

                var map = mutableMapOf<String, Any>()
                map["nombre"] = updateNombre

                FirebaseFirestore.getInstance()
                    //FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "nombre actualizado", Toast.LENGTH_SHORT)
                    .show()

            }

            if (segnom.isEmpty()) {
                valoresLlenos = false
            } else {
                var updateNombredos = editactualizarNombredos.text.toString()

                var map = mutableMapOf<String, Any>()
                map["segundo nombre"] = updateNombredos

                FirebaseFirestore.getInstance()
                    //FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "nombre actualizado", Toast.LENGTH_SHORT)
                    .show()

            }

            if (ape.isEmpty()) {
                valoresLlenos = false
            } else {
                var updateApellido = editactualizarApellido.text.toString()

                var map = mutableMapOf<String, Any>()
                map["apellido"] = updateApellido
                FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "apellido actualizdo", Toast.LENGTH_SHORT)
                    .show()

            }

            if (eda.isEmpty()) {
                valoresLlenos = false
            } else {
                var updateEdad = editactualizarEdad.text.toString()
                var map = mutableMapOf<String, Any>()
                map["edad"] = updateEdad
                FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "edad actualizada", Toast.LENGTH_SHORT).show()

            }

            if(pes.isEmpty()){
                valoresLlenos=false
            } else{
                var updatepes = editactualizarPesoLibras.text.toString()
                var map = mutableMapOf<String, Any>()
                map["peso en libras"] = updatepes
                FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "peso actualizado", Toast.LENGTH_SHORT).show()
            }

            if(alt.isEmpty()){
                valoresLlenos=false
            } else{
                var updatealt = editactualizarAltura.text.toString()
                var map = mutableMapOf<String, Any>()
                map["altura metros"] = updatealt
                FirebaseFirestore.getInstance()
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "peso actualizado", Toast.LENGTH_SHORT).show()
            }

            if (ofi.isEmpty()) {
                valoresLlenos = false
            } else {
                var updateoficio = editactualizaroficio.text.toString()

                var map = mutableMapOf<String, Any>()
                map["oficio"] = updateoficio

                FirebaseFirestore.getInstance()
                    //FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "oficio actualizado", Toast.LENGTH_SHORT)
                    .show()

            }

            if (cent.isEmpty()) {
                valoresLlenos = false
            } else {
                var updatecentro = editactualizarcentroestudio.text.toString()

                var map = mutableMapOf<String, Any>()
                map["centro"] = updatecentro

                FirebaseFirestore.getInstance()
                    //FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "oficio actualizado", Toast.LENGTH_SHORT)
                    .show()
            }

            if (fac.isEmpty()) {
                valoresLlenos = false
            } else {
                var updatefacultad = editactualizarfacultad.text.toString()

                var map = mutableMapOf<String, Any>()
                map["facultad"] = updatefacultad

                FirebaseFirestore.getInstance()
                    //FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(map)
                Toast.makeText(this, "oficio actualizado", Toast.LENGTH_SHORT)
                    .show()
            }

        }
            return valoresLlenos
    }

    fun limpiar(){
     editactualizarNombre.setText("")
     editactualizarNombredos.setText("")
     editactualizarApellido.setText("")
     editactualizarEdad.setText("")
     editactualizarPesoLibras.setText("")
     editactualizarAltura.setText("")
        editactualizaroficio.setText("")
        editactualizarcentroestudio.setText("")
        editactualizarfacultad.setText("")


 }

    fun actualizaDatosFinales(){

        var pin: String? = FirebaseAuth.getInstance().uid
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("usuario")
            .get().addOnSuccessListener { documentSnapshot ->
                var map = documentSnapshot.data as Map<String, Any>

                var altura: String = map["altura metros"].toString()
                var peso: String = map["peso en libras"].toString()

                //traigo valores y los trabajo------------------------------------------------
                var unKilogramo: Double = 2.2046
                var pesoKilogramos: Double
                var pesoIMC: Double
               // var miAltura: Double = editTextAltura.text.toString().toDouble()
                //var miPeso: Double = editTextPeso.text.toString().toDouble()
                var alcuadrado: Double


                pesoKilogramos = (peso.toDouble() / unKilogramo)
                alcuadrado = altura.toDouble()*altura.toDouble()
                pesoIMC = pesoKilogramos / alcuadrado

                var estadoImc:String=""
                if (pesoIMC <= 18.5) {
                    estadoImc="IMC rango de peso insuficiente " + "%.2f".format(pesoIMC)
                } else if (pesoIMC <= 25) {
                    estadoImc="IMC rango de peso normal " + "%.2f".format(pesoIMC)
                } else if (pesoIMC <= 30) {
                    estadoImc="IMC rango de sobrepeso " + "%.2f".format(pesoIMC)
                } else if (pesoIMC <= 40) {
                    estadoImc="IMC rango de obesidad " + "%.2f".format(pesoIMC)
                } else if (pesoIMC >= 40) {
                    estadoImc="IMC rango de Obesidad Morvida " + "%.2f".format(pesoIMC)
                }


                var imcDato: String
                imcDato = " %.2f".format(pesoIMC)

                //--------------------------------------------------------

                // mando a acutalizar---------------------------------------------------
                var arraynuevo: MutableMap<String, Any> = mutableMapOf<String, Any>()

                arraynuevo["estado imc"]=estadoImc
                arraynuevo["imc"]= imcDato


                FirebaseFirestore.getInstance()
                    //  FirebaseDatabase.getInstance().reference
                    .collection("usuarios")
                    .document("$pin")
                    .collection("Datos")
                    .document("usuario")
                    .update(arraynuevo)

            }
    }

}