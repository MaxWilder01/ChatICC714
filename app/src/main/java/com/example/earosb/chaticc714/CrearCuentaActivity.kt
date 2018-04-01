package com.example.earosb.chaticc714

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.crear_cuenta_layout.*

class CrearCuentaActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "CrearCuentaActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta_layout)
        btnCrear.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val i = view!!.id

        when (i) {
            R.id.btnCrear -> return
        }
    }

}