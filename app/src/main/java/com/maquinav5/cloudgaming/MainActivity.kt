package com.maquinav5.cloudgaming

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var status: TextView
    private lateinit var serverAddress: EditText

    private val prefs by lazy {
        getSharedPreferences("maquina_settings", Context.MODE_PRIVATE)
    }

    private val bg = Color.rgb(10, 10, 18)
    private val panel = Color.rgb(23, 21, 37)
    private val purple = Color.rgb(153, 76, 255)
    private val white = Color.WHITE
    private val muted = Color.rgb(175, 170, 195)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = Color.rgb(5, 5, 10)

        val scroll = ScrollView(this)
        scroll.isFillViewport = true
        scroll.setBackgroundColor(bg)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(28))
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val badge = label("●  CLOUD GAMING PLATFORM", 12f, purple, true)
        badge.gravity = Gravity.CENTER

        val title = label("MAQUINA", 34f, white, true).apply {
            gravity = Gravity.CENTER
        }

        val subtitle = label("C L O U D", 20f, purple, true).apply {
            gravity = Gravity.CENTER
        }

        status = label(
            "●  SERVIDOR DESCONECTADO", 13f, muted, true
        ).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(16), 0, dp(16))
        }

        val serverTitle = label("TU SERVIDOR", 14f, white, true)

        serverAddress = EditText(this).apply {
            hint = "IP o dirección del servidor"
            setSingleLine(true)
            textSize = 14f
            setTextColor(white)
            setHintTextColor(muted)
            setPadding(dp(14), dp(10), dp(14), dp(10))
            setBackground(roundRect(panel, dp(12), purple))
            setText(prefs.getString("server_address", "") ?: "")
        }

        val save = actionButton("GUARDAR DIRECCIÓN", true)
        val connect = actionButton("CONECTAR SERVIDOR", false)

        save.setOnClickListener {
            val address = serverAddress.text.toString().trim()

            if (address.isBlank()) {
                showStatus("●  INTRODUCE UNA DIRECCIÓN", Color.rgb(255, 100, 120))
            } else {
                prefs.edit().putString("server_address", address).apply()
                showStatus("●  DIRECCIÓN GUARDADA", Color.rgb(90, 240, 160))
            }
        }

        connect.setOnClickListener {
            val address = serverAddress.text.toString().trim()

            if (address.isBlank()) {
                showStatus("●  INTRODUCE UNA DIRECCIÓN", Color.rgb(255, 100, 120))
            } else {
                prefs.edit().putString("server_address", address).apply()
                showStatus(
                    "●  DIRECCIÓN GUARDADA; FALTA CONFIGURAR EL SERVIDOR",
                    Color.rgb(255, 205, 100)
                )
            }
        }

        root.addView(badge, matchWrap())
        root.addView(space(18))
        root.addView(title, matchWrap())
        root.addView(subtitle, matchWrap())
        root.addView(space(8))
        root.addView(status, matchWrap())

        val serverCard = sectionCard()

        serverCard.addView(serverTitle, matchWrap())
        serverCard.addView(space(8))

        serverCard.addView(
            serverAddress,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(54)
            )
        )

        serverCard.addView(space(10))

        serverCard.addView(
            save,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(52)
            )
        )

        serverCard.addView(space(8))

        serverCard.addView(
            connect,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(52)
            )
        )

        root.addView(serverCard, matchWrap())
        root.addView(space(22))
        root.addView(label("TU BIBLIOTECA", 18f, white, true), matchWrap())
        root.addView(space(10))
        root.addView(gameCard("GTA V", "Acción · Mundo abierto"))
        root.addView(space(10))
        root.addView(gameCard("RED DEAD REDEMPTION 2", "Aventura · Mundo abierto"))
        root.addView(space(10))
        root.addView(gameCard("CYBERPUNK 2077", "RPG · Ciencia ficción"))
        root.addView(space(18))

        root.addView(
            label(
                "Los juegos necesitan un servidor remoto compatible para ejecutarse.",
                12f,
                muted,
                false
            ),
            matchWrap()
        )

        scroll.addView(
            root,
            ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        setContentView(scroll)
    }

    private fun showStatus(message: String, color: Int) {
        status.text = message
        status.setTextColor(color)
    }

    private fun label(
        textValue: String,
        size: Float,
        color: Int,
        bold: Boolean
    ): TextView {
        return TextView(this).apply {
            text = textValue
            textSize = size
            setTextColor(color)

            if (bold) {
                setTypeface(null, Typeface.BOLD)
            }
        }
    }

    private fun actionButton(
        textValue: String,
        primary: Boolean
    ): Button {
        return Button(this).apply {
            text = textValue
            textSize = 12f
            isAllCaps = false
            setTextColor(white)

            background = roundRect(
                if (primary) purple else panel,
                dp(12),
                if (primary) purple else Color.rgb(85, 65, 125)
            )
        }
    }

    private fun gameCard(
        name: String,
        description: String
    ): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))

            background = roundRect(
                panel,
                dp(14),
                Color.rgb(60, 48, 85)
            )

            addView(label(name, 16f, white, true), matchWrap())
            addView(space(5))
            addView(label(description, 12f, muted, false), matchWrap())
            addView(space(8))

            addView(
                label(
                    "SERVIDOR DE JUEGO REQUERIDO",
                    10f,
                    purple,
                    true
                ),
                matchWrap()
            )
        }
    }

    private fun sectionCard(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))

            background = roundRect(
                panel,
                dp(16),
                Color.rgb(70, 52, 105)
            )
        }
    }

    private fun roundRect(
        color: Int,
        radius: Int,
        stroke: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = radius.toFloat()
            setStroke(dp(1), stroke)
        }
    }

    private fun space(height: Int): TextView {
        return TextView(this).apply {
            this.height = dp(height)
        }
    }

    private fun matchWrap(): LinearLayout.LayoutParams {
        return LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
