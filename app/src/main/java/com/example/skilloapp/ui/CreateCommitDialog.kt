package com.example.skilloapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.example.skilloapp.R
import com.example.skilloapp.data.ApiService
import com.example.skilloapp.data.RetrofitConfig
import com.example.skilloapp.data.model.commit.CommitRequest
import com.example.skilloapp.data.model.turmas.TurmaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CreateCommitDialog(context: Context, private val onSaveSuccessListener: () -> Unit) : Dialog(context) {

    private lateinit var subjectSpinner: Spinner
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var classSpinner: Spinner
    private lateinit var saveButton: Button
    private val apiService: ApiService = RetrofitConfig.createService(ApiService::class.java)
    private var selectedTurmaId: Int = -1
    private var selectedMateriaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.create_commit)

        val layoutParams = window?.attributes
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = layoutParams

        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        classSpinner = findViewById(R.id.classSpinner)
        subjectSpinner = findViewById(R.id.subjectSpinner)
        saveButton = findViewById(R.id.saveButton)

        // Popular os spinners de turmas e matérias
        populateSpinners(classSpinner, subjectSpinner)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (selectedTurmaId != -1 && selectedMateriaId != -1) {
                val commitRequest = CommitRequest(title, description, getCurrentDate())
                apiService.criarCommit(selectedTurmaId, selectedMateriaId, commitRequest)
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                onSaveSuccessListener()
                            } else {
                                Toast.makeText(context, "Erro ao criar commit", Toast.LENGTH_SHORT).show()
                            }
                            dismiss()
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Erro de rede ao criar commit", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                    })
            } else {
                Toast.makeText(context, "Selecione uma turma e matéria", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

        private fun populateSpinners(classSpinner: Spinner, subjectSpinner: Spinner) {
            apiService.getMinhasTurmas().enqueue(object : Callback<List<TurmaResponse>> {
                override fun onResponse(call: Call<List<TurmaResponse>>, response: Response<List<TurmaResponse>>) {
                    if (response.isSuccessful) {
                        val turmas = response.body()
                        if (turmas != null) {
                            val turmaNames = mutableListOf<String>()
                            val materiaNames = mutableListOf<String>()

                            for (turma in turmas) {
                                turmaNames.add(turma.nomeTurma)
                                for (materia in turma.materias) {
                                    val nomeMateria = materia?.nome
                                    if (nomeMateria != null) {
                                        materiaNames.add(nomeMateria)
                                    }
                                }
                            }

                            val turmaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, turmaNames)
                            turmaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            classSpinner.adapter = turmaAdapter

                            val materiaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, materiaNames)
                            materiaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            subjectSpinner.adapter = materiaAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<List<TurmaResponse>>, t: Throwable) {
                    // Handle network errors here
                    Toast.makeText(context, "Erro de rede ao obter turmas", Toast.LENGTH_SHORT).show()
                    dismiss()
                }

        })
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}
