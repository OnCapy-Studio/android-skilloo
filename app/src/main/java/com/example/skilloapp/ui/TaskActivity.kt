package com.example.skilloapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.adapter.TaskAdapter
import com.example.skilloapp.data.model.TaskItem

class TaskActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var todoList: MutableList<TaskItem>
    private lateinit var inProgressList: MutableList<TaskItem>
    private lateinit var doneList: MutableList<TaskItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        supportActionBar?.hide()

        initializeViews()
        setupRecyclerViews()
        addSampleTasks()
    }

    private fun initializeViews() {
        todoList = mutableListOf()
        inProgressList = mutableListOf()
        doneList = mutableListOf()

        taskAdapter = TaskAdapter(todoList) // Inicialmente, carrega tarefas pendentes
    }

    private fun setupRecyclerViews() {
        val recyclerViewTodo: RecyclerView = findViewById(R.id.recyclerViewTodo)
        val recyclerViewInProgress: RecyclerView = findViewById(R.id.recyclerViewInProgress)
        val recyclerViewDone: RecyclerView = findViewById(R.id.recyclerViewDone)

        recyclerViewTodo.layoutManager = LinearLayoutManager(this)
        recyclerViewInProgress.layoutManager = LinearLayoutManager(this)
        recyclerViewDone.layoutManager = LinearLayoutManager(this)

        recyclerViewTodo.adapter = taskAdapter // Define o adaptador para tarefas pendentes
        recyclerViewInProgress.adapter = TaskAdapter(inProgressList)
        recyclerViewDone.adapter = TaskAdapter(doneList)
    }

    private fun addSampleTasks() {
        // Adicione suas tarefas de exemplo às listas apropriadas (pendente, em progresso, concluído)
        todoList.add(TaskItem("Corrigir provas", "Correção de provas de geografia analítica", "DONE"))
        todoList.add(TaskItem("Orientar turma", "Ajudar a turma do 3 DS B", "TO_DO"))
        todoList.add(TaskItem("Chamar diretor", "Conversar sobre o sarau", "DONE"))
    }
}
