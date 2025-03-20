package com.univalle.unimatch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lorentzos.swipecards.SwipeFlingAdapterView

class MainActivity : AppCompatActivity() {
    private lateinit var flingContainer: SwipeFlingAdapterView
    private lateinit var al: ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa tu SwipeFlingAdapterView (asumiendo que lo tienes en tu layout)
        flingContainer = findViewById(R.id.frame)

        // Inicializa el ArrayList con datos
        al = ArrayList()
        al.add("php")
        al.add("c")
        al.add("python")
        al.add("java")
        al.add("html")
        al.add("c++")
        al.add("css")
        al.add("javascript")

        // Configura el adaptador
        arrayAdapter = ArrayAdapter(this, R.layout.item, R.id.helloText, al)
        flingContainer.adapter = arrayAdapter

        // Configura el listener para los eventos de swipe
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // Esta es la forma más simple de eliminar un objeto del adaptador
                Log.d("LIST", "removed object!")
                al.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                // Hacer algo con el swipe hacia la izquierda
                makeToast(this@MainActivity, "Left!")
            }

            override fun onRightCardExit(dataObject: Any) {
                makeToast(this@MainActivity, "Right!")
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Solicitar más datos aquí
                al.add("XML ".plus(i.toString()))
                arrayAdapter.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++
            }

            override fun onScroll(scrollProgressPercent: Float) {
                val view = flingContainer.selectedView
                view.findViewById<View>(R.id.item_swipe_right_indicator).alpha =
                    if (scrollProgressPercent < 0) -scrollProgressPercent else 0f
                view.findViewById<View>(R.id.item_swipe_left_indicator).alpha =
                    if (scrollProgressPercent > 0) scrollProgressPercent else 0f
            }
        })

        // Opcionalmente añadir un OnItemClickListener
        flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            makeToast(this@MainActivity, "Clicked!")
        }
    }

    companion object {
        fun makeToast(ctx: Context, s: String) {
            Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show()
        }
    }

    // Si estás usando alguna librería de inyección como ButterKnife en Kotlin,
    // deberías reemplazar estos métodos con la alternativa adecuada
    fun right() {
        // Trigger the right event manually
        flingContainer.topCardListener.selectRight()
    }

    fun left() {
        flingContainer.topCardListener.selectLeft()
    }
}