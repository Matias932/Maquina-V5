package com.maquinav5.cloudgaming

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.content.Context

class MainActivity : Activity() {

    private lateinit var status: TextView
    private lateinit var serverAddress: EditText

    private val prefs by lazy {
        getSharedPreferences("maquina_settings", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 40, 32, 40)
            setBackgroundColor(Color.rgb(15, 18, 28))
        }

        val title = TextView(this).apply {
            text = "MAQUINA CLOUD"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        status = TextView(this).apply {
            text = "Estado: Sin conectar"
            textSize = 16f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        serverAddress = EditText(this).apply {
            hint = "IP o dirección del servidor"
            setSingleLine(true)
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setText(prefs.getString("server_address", "") ?: "")
        }

        val saveButton = makeButton("GUARDAR SERVIDOR")
        val connectButton = makeButton("CONECTAR")
        val playButton = makeButton("JUGAR")
        val settingsButton = makeButton("CONFIGURACIÓN")

        saveButton.setOnClickListener {
            val address = serverAddress.text.toString().trim()

            if (address.isBlank()) {
                status.text = "Introduce la dirección del servidor."
            } else {
                prefs.edit().putString("server_address", address).apply()
                status.text = "Dirección guardada."
            }
        }

        connectButton.setOnClickListener {
            val address = serverAddress.text.toString().trim()

            if (address.isBlank()) {
                status.text = "Primero introduce la dirección del servidor."
            } else {
                prefs.edit().putString("server_address", address).apply()
                status.text = "Dirección guardada. Falta configurar el servidor."
            }
        }

        playButton.setOnClickListener {
            status.text = "El streaming estará disponible al conectar un servidor compatible."
        }

        settingsButton.setOnClickListener {
            status.text = "Introduce la dirección del servidor y pulsa GUARDAR."
        }

        layout.addView(title, params(320, 90))
        layout.addView(status, params(320, 60))
        layout.addView(serverAddress, params(320, 60))
        layout.addView(saveButton, params(320, 58))
        layout.addView(connectButton, params(320, 58))
        layout.addView(playButton, params(320, 58))
        layout.addView(settingsButton, params(320, 58))

        setContentView(layout)
    }

    private fun makeButton(label: String): Button {
        return Button(this).apply {
            text = label
            textSize = 14f
        }
    }

    private fun params(width: Int, height: Int): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            width,
            height
        ).apply {
            gravity = Gravity.CENTER
            setMargins(0, 8, 0, 8)
        }
    }
}
