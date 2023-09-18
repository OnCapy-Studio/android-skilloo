package com.example.skilloapp.ui

import CommitAdapter
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import com.example.skilloapp.R
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
        setupListeners()
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
                    // Implement other filters here if needed
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if necessary
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
        // Implement the week filter logic here and update the adapter
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
                // Show an error message to the user indicating that the fields are empty
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val confirmDialog = AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Yes") { _, _ ->
                commitList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel") { _, _ ->
                adapter.notifyItemChanged(position)
            }
            .setOnCancelListener {
                adapter.notifyItemChanged(position)
            }
            .create()

        confirmDialog.show()
    }
}
