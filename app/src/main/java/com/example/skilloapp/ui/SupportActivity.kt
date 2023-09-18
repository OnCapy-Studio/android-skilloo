package com.example.skilloapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.adapter.SupportRequestAdapter
import com.example.skilloapp.data.SupportRequest
import com.google.gson.Gson

class SupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suporte)
        supportActionBar?.hide()

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

        val recyclerViewPending = findViewById<RecyclerView>(R.id.recyclerViewPendingOrders)
        val recyclerViewInProgress = findViewById<RecyclerView>(R.id.recyclerViewInProgressOrders)

        setupRecyclerView(recyclerViewPending, supportRequests, "PENDENTE")
        setupRecyclerView(recyclerViewInProgress, supportRequests, "EM ANDAMENTO")
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        supportRequests: List<SupportRequest>,
        status: String
    ) {
        recyclerView.layoutManager = LinearLayoutManager(this)

        val filteredRequests = supportRequests.filter { it.status == status }

        val adapter = SupportRequestAdapter(filteredRequests)
        recyclerView.adapter = adapter
    }
}
