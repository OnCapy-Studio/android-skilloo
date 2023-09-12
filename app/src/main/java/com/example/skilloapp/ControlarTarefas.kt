package com.example.skilloapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.widget.ToggleButton

class ControlarTarefas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controlar_tarefas)

        fun onToggleClicked(view: View) {
            if (view is ToggleButton) {
                val isChecked = view.isChecked
                if(isChecked){

                } else {

                }
            }
        }
    }
}