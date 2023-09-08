package com.example.skilloapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.adapter.CardAdapter
import com.example.skilloapp.data.CardItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider

class LabBookingActivity : AppCompatActivity() {
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardItemList: MutableList<CardItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios)

        setupRecyclerView()
        setupChips()
        setupRangeSlider()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dados - CardItem
        cardItemList = mutableListOf()
        cardItemList.add(CardItem("08:40", "50min", "Laboratório 1", "Matéria 1", "1 DS B"))
        cardItemList.add(CardItem("09:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))
        cardItemList.add(CardItem("23:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))
        cardItemList.add(CardItem("17:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))

        // Configure o adaptador
        cardAdapter = CardAdapter(cardItemList)
        recyclerView.adapter = cardAdapter
    }

    private fun setupChips() {
        val chipGroup = findViewById<ChipGroup>(R.id.chip_group)

        val daysOfWeek = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")

        for (day in daysOfWeek) {
            val chip = Chip(this)
            chip.text = day
            chip.isCheckable = true
            chip.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
            chipGroup.addView(chip)
        }
    }

    private fun setupRangeSlider() {
        val rangeSlider = findViewById<RangeSlider>(R.id.rangeSlider)

        val minValue = 7
        val maxValue = 23

        rangeSlider.valueFrom = minValue.toFloat()
        rangeSlider.valueTo = maxValue.toFloat()

        rangeSlider.values = mutableListOf(minValue.toFloat(), maxValue.toFloat())

        rangeSlider.setLabelFormatter { value ->
            val hour = value.toInt()
            String.format("%02d:00", hour)
        }

        rangeSlider.addOnChangeListener { slider, _, _ ->
            val selectedMinValue = slider.values[0].toInt()
            val selectedMaxValue = slider.values[1].toInt()

            val filteredReservations = cardItemList.filter { cardItem ->
                val cardHour = cardItem.time.split(":")[0].toInt()
                cardHour in selectedMinValue..selectedMaxValue
            }

            cardAdapter.cardItemList = filteredReservations
            cardAdapter.notifyDataSetChanged()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}