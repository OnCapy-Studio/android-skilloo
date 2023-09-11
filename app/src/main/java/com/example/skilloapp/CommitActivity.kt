package com.example.skilloapp

import CommitAdapter
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.skilloapp.data.CommitModel
import java.text.SimpleDateFormat
import java.util.*

class CommitActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommitAdapter
    private lateinit var filterSpinner: Spinner
    private lateinit var createCommitButton: Button
    private val commitList = mutableListOf<CommitModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controle)
        supportActionBar?.hide()

        initializeViews()
        setupRecyclerView()
        populateSpinner()
        setupFilterSpinnerListener()
        setupCreateCommitButtonListener()
        setupSwipeToDelete()
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerView)
        filterSpinner = findViewById(R.id.filterSpinner)
        createCommitButton = findViewById(R.id.buttonCommit)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CommitAdapter(this, commitList)
        recyclerView.adapter = adapter
    }

    private fun populateSpinner() {
        val filterOptions = arrayOf("Mês", "Semana")
        val filterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter
    }

    private fun setupFilterSpinnerListener() {
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = filterSpinner.selectedItem as String
                if (selectedItem == "Semana") {
                    applyWeekFilter()
                } else {
                    // Implemente outros filtros aqui
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Lidar com nenhum item selecionado, se necessário
            }
        }
    }

    private fun setupCreateCommitButtonListener() {
        createCommitButton.setOnClickListener {
            showCreateCommitDialog()
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                showDeleteConfirmationDialog(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun applyWeekFilter() {
        // Implemente a lógica do filtro por semana aqui e atualize o adaptador
    }

    private fun showCreateCommitDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.create_commit)

        val saveButton = dialog.findViewById<Button>(R.id.saveButton)
        val titleEditText = dialog.findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = dialog.findViewById<EditText>(R.id.descriptionEditText)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val currentDate =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
                val newCommit = CommitModel(title, description, currentDate)
                commitList.add(newCommit)
                adapter.notifyDataSetChanged()

                dialog.dismiss()
            } else {
                // Mostrar uma mensagem de erro ao usuário informando que os campos estão vazios
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val confirmDialog = AlertDialog.Builder(this)
            .setTitle("Confirmar exclusão")
            .setMessage("Tem certeza de que deseja excluir este registro?")
            .setPositiveButton("Sim") { _, _ ->
                commitList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancelar") { _, _ ->
                adapter.notifyItemChanged(position)
            }
            .setOnCancelListener {
                adapter.notifyItemChanged(position)
            }
            .create()

        confirmDialog.show()
    }
}
