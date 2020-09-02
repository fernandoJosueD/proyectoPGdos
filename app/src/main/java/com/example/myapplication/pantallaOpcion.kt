package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.myapplication.inserts.setPersona
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_mostrar_info_bit.*
import kotlinx.android.synthetic.main.activity_pantalla_opcion.*
import java.lang.reflect.Array
import java.security.acl.Owner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map as Map1


class pantallaOpcion : AppCompatActivity() {
        private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_opcion)
            onclikobtenerfecha()





        textViewSalir.setOnClickListener {}

    }



        fun operarDatos() {
            var pin: String? = FirebaseAuth.getInstance().uid
            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .get().addOnSuccessListener { documentSnapshot ->
                    var map = documentSnapshot.data as Map1<String, Any>
                    var nom: String = map["nombre"].toString()
                    var apell: String = map["apellido"].toString()

                    //traigo valores y los trabajo
                    var dato2:String = editnombre.text.toString()

                    var suma: Float = (nom.toFloat()*dato2.toFloat())

                    var apellidoNuevo:String="soy ferjos"
                    //edito valores y reemplazo

                    var completoapellido=apellidoNuevo+apell
                    // mando a acutalizar
                    var arraynuevo: MutableMap<String, Any> = mutableMapOf<String, Any>()
                    arraynuevo["nombre"]= suma
                    arraynuevo["apellido"]= completoapellido

                    FirebaseFirestore.getInstance()
                        //  FirebaseDatabase.getInstance().reference
                        .collection("usuarios")
                        .document("$pin")
                        .update(arraynuevo)

                }

        }
    fun onclikobtenerfecha():String{
        //------------funcion de botones con date picker
        // CALENDARION por medio de seleccion de boton
        val c = Calendar.getInstance()
        val year =c.get(Calendar.YEAR)
        val Month =c.get(Calendar.MONDAY)
        val day=c.get(Calendar.DAY_OF_MONTH)
        // boton para datepicker de seleccion y traer datos

        btnfechadialogo.setOnClickListener {
            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    textfechamarcada.text = ""+year+"_"+(month+1)+"_"+(0+dayOfMonth)
                },year,Month,day)
            dpd.show()
        }

        return textfechamarcada.getText().toString()
    }

        fun consulta(){
            var pin: String? = FirebaseAuth.getInstance().uid
            var fechamostrar = onclikobtenerfecha()

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("desayuno")
                .document("$fechamostrar")
                .collection("$fechamostrar nivel_apetito")
                .whereEqualTo("ninguna", "ninguna")



            
        }

    fun btnOnclick(view: View) {
            arrayFirebase()
    }

    fun arrayFirebase(){
        var semana = arrayOf("fernando","josue","dondiego")

        semana.forEach {
            textView3.setText(it)
        }

        }

    }






