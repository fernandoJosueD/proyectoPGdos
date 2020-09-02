package com.example.myapplication.Mostrar

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mostrar_info_bit.*
import java.text.SimpleDateFormat
import java.util.*

class mostrarInfoBit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_info_bit)

            onclickbtnfecha()


        btnBuscar.setOnClickListener {
            //mostrarBitPorfecha()
              validardatosdeldia()

        }




    }// fin del oncreate


        fun mostrarInfocom(): String{
 //----------declaracion final de datos ----------------------------------
            var tiempocomdiabuscar = arrayOf("")
            var spinnertiempoBuscar: Spinner
            // imprimir dato spinner
        var adaptador: ArrayAdapter<String> = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tiempocomdiabuscar)
        spinnertiempoBuscar = findViewById<View>(R.id.spinnerBuscar) as Spinner
        //recupera el valor si esta acompañado
            var timcomida = spinnertiempoBuscar.selectedItem.toString()
            return timcomida
    }

        fun onclickbtnfecha():String{
            //------------funcion de botones con date picker
            // CALENDARION por medio de seleccion de boton
            val c = Calendar.getInstance()
            val year =c.get(Calendar.YEAR)
            val Month =c.get(Calendar.MONDAY)
            val day=c.get(Calendar.DAY_OF_MONTH)
            // boton para datepicker de seleccion y traer datos

            btnFechaInicial.setOnClickListener {
                val dpd = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        textFechaA.text = ""+year+"_"+(month+1)+"_"+(0+dayOfMonth)
                    },year,Month,day)
                dpd.show()
            }

            return textFechaA.getText().toString()
        }

        fun mostrarBitPorfecha(){
        var fechadeHoy:String = onclickbtnfecha()

        var tiempoComida_desalcen = mostrarInfocom()
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = fechadeHoy
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                var map = documentSnapshot.data as Map<String, Any>

                //------------guardo en una variable los datos de comida del dia

                var timepo_comida =  map ["$fecha tiem_comi"].toString()
                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_postre=  map["$fecha categoria_postre"].toString()
                var nivelapetito =  map["$fecha nivel_apetito"].toString()
                var entorno = map["$fecha comio_entorno"].toString()
                var posicion = map["$fecha en_posicion"].toString()
                var detalle_comida = map["$fecha detalle_comida"].toString()
                var detalle_bebida = map["$fecha detalle_bebida"].toString()
                var tipo_actividad= map["$fecha tipo_actividad"].toString()
                var estado_animo = map["$fecha estado_animo"].toString()
                var fechadetalle = map["$fecha fecha detalle"].toString()
                var bebida =map["$fecha catbebida"].toString()

                //------------------------imprimir en un alert dialogo----------------------------

                texmostrarConsulta.setText(
                    "su $timepo_comida esta en categoria  $categoria_comi   \n" +
                            "su comida fue $detalle_comida con postre $categoria_postre \n" +
                            "bebida tomada $bebida  bebida fue $detalle_bebida \n" +
                            "el nivel de apetito $nivelapetito en un entorno $entorno \n " +
                            "degusto de $timepo_comida en $posicion  y su su estado de animo fue $estado_animo \n" +
                            "fecha que se almaceno $fechadetalle")


            }

    }

    //-------------se ejecuta si no se ha elegido una fecha y trae los datos del dia
    fun fechaDelDia(): String {
        //formato para guardar datos en la bd como referencia
        var timestamp = SimpleDateFormat("yyyy_M_d").format(Date())
        return timestamp
    }

    fun validardatosdeldia(){


            var revisafecha:String="fecha"
           var datofecha:String = textFechaA.getText().toString()

        if (revisafecha.equals(datofecha)){ // si la cadena de texto fecha no tiene fecha establecida
            //que se ejecute el codigo de ver datos el dia de hoy


        } else{
            MBdesayuno()
            MBalmuerzo()
            MBcena()
        }


    } // fin validar datos del dia



    fun MBdesayuno(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = onclickbtnfecha()

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("desayuno")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                var map = documentSnapshot.data as Map<String, Any>

                //------------guardo en una variable los datos de comida del dia

                var timepo_comida =  map ["$fecha tiem_comi"].toString()
                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_postre=  map["$fecha categoria_postre"].toString()
                var nivelapetito =  map["$fecha nivel_apetito"].toString()
                var entorno = map["$fecha comio_entorno"].toString()
                var posicion = map["$fecha en_posicion"].toString()
                var detalle_comida = map["$fecha detalle_comida"].toString()
                var detalle_bebida = map["$fecha detalle_bebida"].toString()
                var tipo_actividad= map["$fecha tipo_actividad"].toString()
                var estado_animo = map["$fecha estado_animo"].toString()
                var fechadetalle = map["$fecha fecha detalle"].toString()
                var bebida =map["$fecha catbebida"].toString()

                //------------------------imprimir en un texto----------------------------
                texmostrarConsulta.setText(
                    "su $timepo_comida esta en categoria  $categoria_comi   \n" +
                            "su comida fue $detalle_comida con postre $categoria_postre \n" +
                            "bebida tomada $bebida  bebida fue $detalle_bebida \n" +
                            "el nivel de apetito $nivelapetito en un entorno $entorno \n " +
                            "degusto de $timepo_comida en $posicion  y su su estado de animo fue $estado_animo \n" +
                            "fecha que se almaceno $fechadetalle")

            }
        }

    fun MBalmuerzo(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = onclickbtnfecha()

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("almuerzo")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                var map = documentSnapshot.data as Map<String, Any>

                //------------guardo en una variable los datos de comida del dia

                var timepo_comida =  map ["$fecha tiem_comi"].toString()
                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_postre=  map["$fecha categoria_postre"].toString()
                var nivelapetito =  map["$fecha nivel_apetito"].toString()
                var entorno = map["$fecha comio_entorno"].toString()
                var posicion = map["$fecha en_posicion"].toString()
                var detalle_comida = map["$fecha detalle_comida"].toString()
                var detalle_bebida = map["$fecha detalle_bebida"].toString()
                var tipo_actividad= map["$fecha tipo_actividad"].toString()
                var estado_animo = map["$fecha estado_animo"].toString()
                var fechadetalle = map["$fecha fecha detalle"].toString()
                var bebida =map["$fecha catbebida"].toString()

                //------------------------imprimir en un texto----------------------------
                texmostrarConsultab.setText(
                    "su $timepo_comida esta en categoria  $categoria_comi   \n" +
                            "su comida fue $detalle_comida con postre $categoria_postre \n" +
                            "bebida tomada $bebida  bebida fue $detalle_bebida \n" +
                            "el nivel de apetito $nivelapetito en un entorno $entorno \n " +
                            "degusto de $timepo_comida en $posicion  y su su estado de animo fue $estado_animo \n" +
                            "fecha que se almaceno $fechadetalle")

            }
    }

    fun MBcena(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = onclickbtnfecha()

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("cena")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                var map = documentSnapshot.data as Map<String, Any>

                //------------guardo en una variable los datos de comida del dia

                var timepo_comida =  map ["$fecha tiem_comi"].toString()
                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_postre=  map["$fecha categoria_postre"].toString()
                var nivelapetito =  map["$fecha nivel_apetito"].toString()
                var entorno = map["$fecha comio_entorno"].toString()
                var posicion = map["$fecha en_posicion"].toString()
                var detalle_comida = map["$fecha detalle_comida"].toString()
                var detalle_bebida = map["$fecha detalle_bebida"].toString()
                var tipo_actividad= map["$fecha tipo_actividad"].toString()
                var estado_animo = map["$fecha estado_animo"].toString()
                var fechadetalle = map["$fecha fecha detalle"].toString()
                var bebida =map["$fecha catbebida"].toString()

                //------------------------imprimir en un texto----------------------------
                texmostrarConsultac.setText(
                    "su $timepo_comida esta en categoria  $categoria_comi   \n" +
                            "su comida fue $detalle_comida con postre $categoria_postre \n" +
                            "bebida tomada $bebida  bebida fue $detalle_bebida \n" +
                            "el nivel de apetito $nivelapetito en un entorno $entorno \n " +
                            "degusto de $timepo_comida en $posicion  y su su estado de animo fue $estado_animo \n" +
                            "fecha que se almaceno $fechadetalle")

            }
    }



}