package com.example.myapplication.bitac

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
import com.example.myapplication.MenuIncio
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mostrar_info_persona.*
import kotlinx.android.synthetic.main.activity_set_bitacora.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class setBitacora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_bitacora)


        butBitVolver.setOnClickListener { startActivity(Intent(this, MenuIncio::class.java)) }
        btnBitTerminar.setOnClickListener { startActivity(Intent(this, MenuIncio::class.java)) }

        btnBitGuardar.setOnClickListener {
            validacion_bebidas()
            //almacenarDatos()
           // Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
           // startActivity(Intent(this, MenuIncio::class.java))

        }


        btnBitMostrar.setOnClickListener {
            try {
                if (imprimirDatos()==null) {
                    Toast.makeText(this, "no hay nada guardado", Toast.LENGTH_SHORT).show()
                }
            } catch (e:ArithmeticException){
                Toast.makeText(this,"Error al compilar",Toast.LENGTH_SHORT).show()
            }

        }
        btnBitUpdate.setOnClickListener { updateBitacora()
            Toast.makeText(this, "Datos Actualizados", Toast.LENGTH_SHORT).show()}




    }
        fun tipo_comida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoComidas = arrayOf("")
            var comidastipo: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoComidas)
            comidastipo = findViewById<View>(R.id.spinnerTipodeComida) as Spinner
            var resultipoComida =
                comidastipo.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultipoComida
        }

        fun tipo_bebida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoBebida = arrayOf("")
            var spinnnertipoBebida: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoBebida)
            spinnnertipoBebida = findViewById<View>(R.id.spinner_tipoBebida) as Spinner
            var resultipoBebida =
                spinnnertipoBebida.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultipoBebida
        }

        fun tipo_postre(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoPostre = arrayOf("")
            var spinnertipoPostre: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoPostre)
            spinnertipoPostre = findViewById<View>(R.id.spinner_postre) as Spinner
            var resultPostre =
                spinnertipoPostre.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultPostre
        }

        fun cantidad_apetito(): String {
            //----------declaracion final de datos ----------------------------------
            var cant_hambre = arrayOf("")
            var spinnercan_hambre: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cant_hambre)
            spinnercan_hambre = findViewById<View>(R.id.spinner_cantidad_hambre) as Spinner
            var resulhambre =
                spinnercan_hambre.selectedItem.toString() //recupera el valor nivel de hambre
            return resulhambre

        }

        fun comio_acompanado(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoAcompanado = arrayOf("")
            var spinnercanAcompanado: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoAcompanado)
            spinnercanAcompanado = findViewById<View>(R.id.spinner_acompañado) as Spinner
            var resulcompania =
                spinnercanAcompanado.selectedItem.toString() //recupera el valor si esta acompañado
            return resulcompania
        }

        fun posicionComida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipo_posicion = arrayOf("")
            var spinnerPosicion: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipo_posicion)
            spinnerPosicion = findViewById<View>(R.id.spinner_posicionComida) as Spinner
            var spinnerPos =
                spinnerPosicion.selectedItem.toString() //recupera el valor si esta acompañado

            return spinnerPos

        }

        fun tiempoComida(): String {

            //----------declaracion final de datos ----------------------------------
            var tipo_tiempo = arrayOf("")
            var spinner_tiempo_desalmcen: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipo_tiempo)
            spinner_tiempo_desalmcen = findViewById<View>(R.id.spinner_comida) as Spinner
            var tiempos_deComidas =
                spinner_tiempo_desalmcen.selectedItem.toString() //recupera el valor si esta acompañado

            return tiempos_deComidas

        }

        //formato de fechas----------------------------
        fun fechatimestamp(): String {
            //formato para guardar datos en la bd como referencia
            var timestamp = SimpleDateFormat("yyyy_M_d").format(Date())
            return timestamp
        }

        fun fechaCurrentDate(): String {  //genera fecha para mostrar a usuario
            //formato de fecha para mostrar en la bd al usuario
            val calendar =
                Calendar.getInstance().time //instancia Date para fecha
            //------------------------------------
            val currentDate = DateFormat.getDateInstance(DateFormat.FULL)
                .format(calendar.time) //almacena el dato formato a string
            return currentDate

        }

        fun muestra_horaActual(): String {
            val tiempo = Calendar.getInstance()
            //formato de fecha para mostrar en la bd al usuario
            val hora = tiempo.get(Calendar.HOUR_OF_DAY)
            val minutos = tiempo.get(Calendar.MINUTE)
            val segundos = tiempo.get(Calendar.SECOND)
            //------------------------------------
            var horario: String = ("$hora:$minutos:$segundos")

            //Toast.makeText(this,"la hora es : $hora:$minutos:$segundos",Toast.LENGTH_SHORT).show() //almacena el dato formato a string
            return horario
        }
        //----------------------------------------------
    //funciones para almacenar acutalizar y mostrar bitacora alimenticia
        fun almacenarDatos() {
            var pin = FirebaseAuth.getInstance().uid

            //------------------------------------------------------------------
            var fechaAlmacenado = fechatimestamp() // genera fecha para la carpeta en bd y almacena
            var fechaUsuario = fechaCurrentDate()
            var tiempoComida_desalcen = tiempoComida()
            var hora = muestra_horaActual()

            var detallecomida = editDetalleComida.text.toString()
            var detalleBebida = editTdetalleBebida.text.toString()
            var actividadDia = editactividadDia.text.toString()
            var detalleHumor = editComentario_humor.text.toString()

            var tipoComida = tipo_comida()
            var tipobebida = tipo_bebida()
            var tipoPostre = tipo_postre()
            var cantHambre = cantidad_apetito()
            var enCompania = comio_acompanado()
            var posicion = posicionComida()

            var map = mutableMapOf<String, Any>()

            map ["$fechaAlmacenado tiem_comi"] = tiempoComida_desalcen
            map["$fechaAlmacenado catComida"] = tipoComida
            map["$fechaAlmacenado catbebida"] = tipobebida
            map["$fechaAlmacenado categoria_postre"] = tipoPostre
            map["$fechaAlmacenado nivel_apetito"] = cantHambre
            map["$fechaAlmacenado comio_entorno"] = enCompania
            map["$fechaAlmacenado en_posicion"] = posicion
            map["$fechaAlmacenado detalle_comida"] = detallecomida
            map["$fechaAlmacenado detalle_bebida"] = detalleBebida
            map["$fechaAlmacenado tipo_actividad"] = actividadDia
            map["$fechaAlmacenado estado_animo"] = detalleHumor
            map["$fechaAlmacenado fecha detalle"] = fechaUsuario
            map["$fechaAlmacenado hora"] = hora

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("$tiempoComida_desalcen")
                .document("$fechaAlmacenado")
                .set(map)
        }
    fun updateBitacora(){
        var pin = FirebaseAuth.getInstance().uid

        //------------------------------------------------------------------
        var fechaAlmacenado = fechatimestamp() // genera fecha para la carpeta en bd y almacena
        var fechaUsuario = fechaCurrentDate()
        var tiempoComida_desalcen = tiempoComida()
        var hora = muestra_horaActual()

        var detallecomida = editDetalleComida.text.toString()
        var detalleBebida = editTdetalleBebida.text.toString()
        var actividadDia = editactividadDia.text.toString()
        var detalleHumor = editComentario_humor.text.toString()

        var tipoComida = tipo_comida()
        var tipobebida = tipo_bebida()
        var tipoPostre = tipo_postre()
        var cantHambre = cantidad_apetito()
        var enCompania = comio_acompanado()
        var posicion = posicionComida()

        var map = mutableMapOf<String, Any>()

        map ["$fechaAlmacenado tiem_comi"] = tiempoComida_desalcen
        map["$fechaAlmacenado catComida"] = tipoComida
        map["$fechaAlmacenado catbebida"] = tipobebida
        map["$fechaAlmacenado categoria_postre"] = tipoPostre
        map["$fechaAlmacenado nivel_apetito"] = cantHambre
        map["$fechaAlmacenado comio_entorno"] = enCompania
        map["$fechaAlmacenado en_posicion"] = posicion
        map["$fechaAlmacenado detalle_comida"] = detallecomida
        map["$fechaAlmacenado detalle_bebida"] = detalleBebida
        map["$fechaAlmacenado tipo_actividad"] = actividadDia
        map["$fechaAlmacenado estado_animo"] = detalleHumor
        map["$fechaAlmacenado fecha detalle"] = fechaUsuario
        map["$fechaAlmacenado hora"] = hora

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .update(map)

    }

    fun imprimirDatos(){
        var tiempoComida_desalcen = tiempoComida()
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = fechatimestamp()
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
                      //var tipo_actividad= map["$fecha tipo_actividad"].toString()
                      var estado_animo = map["$fecha estado_animo"].toString()
                      var fechadetalle = map["$fecha fecha detalle"].toString()
                var bebida =map["$fecha catbebida"].toString()

            //------------------------imprimir en un alert dialogo----------------------------
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Bitacora de ${timepo_comida} guardada")
                builder.setMessage(
                        "categoria comida ${categoria_comi} \n" +
                        "postre ${categoria_postre} \n" +
                        "bebida tomada ${bebida} \n" +
                        "nivel de apetito ${nivelapetito} \n" +
                        "lugar o entorno ${entorno} \n " +
                        "como ${timepo_comida} ${posicion} \n" +
                        "su comida fue ${detalle_comida} \n" +
                        "su bebida fue ${detalle_bebida} \n" +
                        "tipo de comida ${timepo_comida} \n" +
                        "su estado de animo es ${estado_animo} \n" +
                        "fecha que se almaceno ${fechadetalle}")

                //-----------------------------------------------------------------------
                builder.setPositiveButton("regresar al inicio",{dialogInterface: DialogInterface, i: Int ->
                    finish()
                })
                builder.setNegativeButton("permanecer aqui",{dialogInterface: DialogInterface, i: Int->})
                builder.show()


            }

        }

    ///-------------------------------------validacion de bebidas

    fun validacion_bebidas():String{


        var detalle_bebida = editDetalleComida.text.toString().split(" ")
       var coquita =("coca cocacola mirinda pepsi seven coquita naranjada sprite fanta powerade").split("")
       // var item:String
        var i :String
        for(item in detalle_bebida){

            for (i in coquita){
                if (item.equals(i,ignoreCase = true)){

                    Toast.makeText(this,"son iguales $i y $item ",Toast.LENGTH_SHORT).show()
                }

            }
            //if (item.equals(coquita, ignoreCase = true)){
               Toast.makeText(this,"BEBIO MUCHA $item ",Toast.LENGTH_SHORT).show()
            }

        }


    }









