package com.example.skilloapp.ui

import CommitAdapter
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.data.ApiService
import com.example.skilloapp.data.RetrofitConfig
import com.example.skilloapp.data.model.commit.Commit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommitActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommitAdapter
    private lateinit var filterSpinner: Spinner
    private lateinit var createCommitButton: Button
    private val commitList = mutableListOf<Commit>()
    private lateinit var apiService: ApiService

    private var selectedTurmaId: Int = -1
    private var selectedMateriaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controle)
        supportActionBar?.hide()

        // Initialize ApiService
        apiService = RetrofitConfig.createService(ApiService::class.java)

        initializeViews()
        setupRecyclerView()
        populateSpinner()
        setupListeners()
        setupSwipeToDelete()

        val arrowControle = findViewById<ImageView>(R.id.arrowControle)
        arrowControle.setOnClickListener {
            goToHomeActivity()
        }
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
        val filterAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = filterAdapter
    }

    private fun setupListeners() {
        setupFilterSpinnerListener()
        setupCreateCommitButtonListener()
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
                    // Implement logic for the "Mês" filter if necessary
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implement as needed
            }
        }
    }

    private fun setupCreateCommitButtonListener() {
        createCommitButton.setOnClickListener {
            showCreateCommitDialog()
        }
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val commitId = commitList[position].id
                    deleteCommit(position, commitId)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun applyWeekFilter() {
        // Implement logic for filtering by week and update the adapter
        // ...
    }

    private fun showCreateCommitDialog() {
        val createCommitDialog = CreateCommitDialog(this) {
            // Lógica a ser executada quando o commit for salvo com sucesso
        }

        createCommitDialog.show()
    }

    private fun deleteCommit(position: Int, commitId: Int) {
        // Check if turma and materia IDs are selected
        if (selectedTurmaId != -1 && selectedMateriaId != -1) {
            apiService.excluirCommit(selectedTurmaId, selectedMateriaId, commitId)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Commit deleted successfully, update your commit list
                            commitList.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        } else {
                            // Failed to delete commit, handle as needed
                            Toast.makeText(
                                this@CommitActivity,
                                "Erro ao excluir commit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Network error or other error, handle as needed
                        Toast.makeText(
                            this@CommitActivity,
                            "Erro de rede ao excluir commit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            // Turma and materia IDs not selected, display an error message to the user
            Toast.makeText(this, "Selecione uma turma e matéria", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
