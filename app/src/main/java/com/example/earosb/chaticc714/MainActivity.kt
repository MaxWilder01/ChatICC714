package com.example.earosb.chaticc714

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.mensaje_layout.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mDatabase: DatabaseReference? = null
    private var mMessageReferencia: DatabaseReference? = null
    private var rv = null
    private var mensajes: ArrayList<Mensaje>? = null

    private val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReferencia = FirebaseDatabase.getInstance().getReference("mensajes")

        msjBody?.setOnClickListener(this)
        btnEnviar.setOnClickListener(this)

        escucharMensajes()

        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mensajes = ArrayList<Mensaje>()

        var adapter = MensajeAdapter(mensajes!!)
        recyclerView1.adapter = adapter
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        when (i) {
            R.id.btnEnviar -> {
                enviarMensaje(txtMensaje.text.toString(), getHora())
                Toast.makeText(this, "Enviado", Toast.LENGTH_LONG).show()
                txtMensaje.setText("")
            }
            R.id.msjBody -> Toast.makeText(this, "Funciona", Toast.LENGTH_LONG).show()
        }
    }

    private fun getHora() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString() + ":" + Calendar.getInstance().get(Calendar.MINUTE).toString()


    /**
     * Envía mensaje a base de datos firebase
     */
    private fun enviarMensaje(mensaje: String, hora: String) {
        val msj = Mensaje(mensaje, hora);
        mMessageReferencia!!.push().setValue(msj)
    }

    private fun escucharMensajes() {
        val escuchadorMensajes = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    val msjData = data.getValue<Mensaje>(Mensaje::class.java)
                    val msj = msjData?.let { it } ?: continue

                    mensajes!!.add(msj)
                    Log.e(TAG, "onDataChange: Message data is updated: " + msj!!.toString())
                }
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                Log.i(TAG, "Error al escuchar mensajes")
            }
        }

        mMessageReferencia!!.addValueEventListener(escuchadorMensajes)
    }
}
