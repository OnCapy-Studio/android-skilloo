package com.example.skilloapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.adapter.SupportRequestAdapter
import com.example.skilloapp.data.SupportRequest
import com.google.gson.Gson

class SupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suporte)

        val json = """[
            {
                "titulo": "Teclado quebrado",
                "lab": "lab2",
                "descricao": "teclado quebrado no lab 2",
                "status": "EM ANDAMENTO"
            },
            {
                "titulo": "Problema na impressora",
                "lab": "Laboratório 1",
                "descricao": "impressora não está funcionando",
                "status": "PENDENTE"
            }
        ]"""

        val gson = Gson()
        val supportRequests = gson.fromJson(json, Array<SupportRequest>::class.java).toList()

        val layoutManagerPending = LinearLayoutManager(this)
        val layoutManagerInProgress = LinearLayoutManager(this)

        val recyclerViewPending = findViewById<RecyclerView>(R.id.recyclerViewPendingOrders)
        recyclerViewPending.layoutManager = layoutManagerPending

        val recyclerViewInProgress = findViewById<RecyclerView>(R.id.recyclerViewInProgressOrders)
        recyclerViewInProgress.layoutManager = layoutManagerInProgress

        val pendingRequests = supportRequests.filter { it.status == "PENDENTE" }
        val inProgressRequests = supportRequests.filter { it.status == "EM ANDAMENTO" }

        val pendingAdapter = SupportRequestAdapter(pendingRequests)
        val inProgressAdapter = SupportRequestAdapter(inProgressRequests)

        recyclerViewPending.adapter = pendingAdapter
        recyclerViewInProgress.adapter = inProgressAdapter
    }
}