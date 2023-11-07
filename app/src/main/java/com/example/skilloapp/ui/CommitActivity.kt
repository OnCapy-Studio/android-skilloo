package com.example.skilloapp.ui

import CommitAdapter
import android.app.Dialog
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
import com.example.skilloapp.data.model.commit.CommitRequest
import com.example.skilloapp.data.model.common.Materia
import com.example.skilloapp.data.model.turmas.TurmaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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

        // Inicializar o ApiService
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
        val filterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
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

                val filterOptions = arrayOf("Mês", "Semana")

                val selectedItem = filterOptions[position]
                if (selectedItem == "Semana") {
                    applyWeekFilter()
                } else {
                    // Implemente a lógica para outro filtro, se necessário
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implemente conforme necessário
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
//                    showDeleteConfirmationDialog(position, commitId)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun applyWeekFilter() {
        // Implemente a lógica para o filtro por semana e atualize o adaptador
    }

    private fun showCreateCommitDialog() {
        // Implemente a lógica para exibir o diálogo de criação de commit
        // ...

        // Verifique se os IDs da turma e matéria foram selecionados
        if (selectedTurmaId != -1 && selectedMateriaId != -1) {
            val commitRequest = CommitRequest("Título", "Descrição", "Data")
            apiService.criarCommit(selectedTurmaId, selectedMateriaId, commitRequest)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Commit criado com sucesso, você pode tratar conforme necessário
                            // Atualize a lista de commits ou faça qualquer outra ação necessária
                        } else {
                            // Não foi possível criar o commit, trate conforme necessário
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Erro de rede ou outro erro, trate conforme necessário
                    }
                })
        } else {
            // IDs da turma e matéria não selecionados, exiba uma mensagem de erro ao usuário
            Toast.makeText(this, "Selecione uma turma e matéria", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun showDeleteConfirmationDialog(position: Int, commitId: Int) {
//        // Implementar a lógica para o diálogo de confirmação de exclusão
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_confirmation)
//        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
//        val confirmButton = dialog.findViewById<Button>(R.id.confirmButton)
//
//        cancelButton.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        confirmButton.setOnClickListener {
//            dialog.dismiss()
//            deleteCommit(position, commitId)
//        }
//
//        dialog.show()
//    }

    private fun deleteCommit(position: Int, commitId: Int) {
        // Verificar se os IDs da turma e matéria foram selecionados
        if (selectedTurmaId != -1 && selectedMateriaId != -1) {
            apiService.excluirCommit(selectedTurmaId, selectedMateriaId, commitId)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Commit excluído com sucesso, atualize sua lista de commits
                            commitList.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        } else {
                            // Não foi possível excluir o commit, trate conforme necessário
                            Toast.makeText(this@CommitActivity, "Erro ao excluir commit", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Erro de rede ou outro erro, trate conforme necessário
                        Toast.makeText(this@CommitActivity, "Erro de rede ao excluir commit", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            // IDs da turma e matéria não selecionados, exiba uma mensagem de erro ao usuário
            Toast.makeText(this, "Selecione uma turma e matéria", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
