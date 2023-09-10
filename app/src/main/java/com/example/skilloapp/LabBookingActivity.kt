package com.example.skilloapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.adapter.CardBookingAdapter
import com.example.skilloapp.data.CardBookingItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider

class LabBookingActivity : AppCompatActivity() {
    private lateinit var cardAdapter: CardBookingAdapter
    private lateinit var cardItemList: MutableList<CardBookingItem>

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
        cardItemList.add(CardBookingItem("08:40", "50min", "Laboratório 1", "Matéria 1", "1 DS B"))
        cardItemList.add(CardBookingItem("09:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))
        cardItemList.add(CardBookingItem("23:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))
        cardItemList.add(CardBookingItem("17:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B"))

        // Configure o adaptador
        cardAdapter = CardBookingAdapter(cardItemList)
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
}