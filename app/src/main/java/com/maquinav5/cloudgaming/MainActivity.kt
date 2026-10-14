package com.maquinav5.cloudgaming

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 40, 32, 40)
            setBackgroundColor(Color.rgb(15, 17, 25))
        }

        val title = TextView(this).apply {
            text = "MAQUINA CLOUD"
            textSize = 28f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "TU PLATAFORMA DE CLOUD GAMING"
            textSize = 12f
            setTextColor(Color.rgb(120, 180, 255))
            gravity = Gravity.CENTER
        }

        statusText = TextView(this).apply {
            text = "● Servidor desconectado"
            textSize = 16f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(0, 36, 0, 36)
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(statusText)

        addButton(root, "ENCENDER PC") {
            statusText.text = "Servidor no configurado"
        }

        addButton(root, "JUGAR") {
            statusText.text = "Primero debemos configurar el streaming"
        }

        addButton(root, "APAGAR PC") {
            statusText.text = "No hay ninguna máquina conectada"
        }

        addButton(root, "CONFIGURACIÓN") {
            statusText.text = "Configuración remota pendiente"
        }

        setContentView(root)
    }

    private fun addButton(
        parent: LinearLayout,
        label: String,
        action: () -> Unit
    ) {
        val button = Button(this).apply {
            text = label
            textSize = 14f
            setOnClickListener { action() }
        }

        parent.addView(
            button,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 10, 0, 10)
            }
        )
    }
}
