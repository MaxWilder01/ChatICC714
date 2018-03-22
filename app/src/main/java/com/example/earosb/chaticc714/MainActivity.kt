package com.example.earosb.chaticc714

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mDatabase: DatabaseReference? = null
    private var mMessageReferencia: DatabaseReference? = null

    private val TAG = "ChatActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReferencia = FirebaseDatabase.getInstance().getReference("mensajes")

        btnEnviar.setOnClickListener(this)

        escucharMensajes()
    }

    /**
     *
     */
    override fun onClick(view: View?) {
        val i = view!!.id

        when (i) {
            R.id.btnEnviar -> {
                enviarMensaje(txtMensaje.text.toString())
                txtMensaje.setText("")
            }
        }
    }

    /**
     * Envía mensaje a base de datos firebase
     */
    private fun enviarMensaje(mensaje: String) {
        mMessageReferencia!!.push().setValue(mensaje)
    }

    private fun escucharMensajes() {
        val escuchadorMensajes = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    val mensajeData = data.getValue<String>(String::class.java)
                    val mensaje = mensajeData?.let { it } ?: continue
                    Log.i(TAG, mensaje)
                }
            }

            override fun onCancelled(dataSnapshot: DatabaseError?) {
                Log.i(TAG, "Error al escuchar mensajes")
            }
        }

        mMessageReferencia!!.addValueEventListener(escuchadorMensajes)
    }
}
