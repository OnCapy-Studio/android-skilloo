import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.adapter.CardBookingAdapter
import com.example.skilloapp.data.ApiService
import com.example.skilloapp.data.CardBookingItem
import com.example.skilloapp.data.RetrofitConfig
import com.example.skilloapp.data.model.reserva.DiaDaSemana
import com.example.skilloapp.data.model.reserva.SalvarReservaResponse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabBookingActivity : AppCompatActivity() {
    private lateinit var cardAdapter: CardBookingAdapter
    private lateinit var cardItemList: MutableList<CardBookingItem>
    private lateinit var apiService: ApiService // Adicione a instância do ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horarios)
        supportActionBar?.hide()

        apiService = RetrofitConfig.createService(ApiService::class.java) // Inicialize o ApiService

        initializeViews()
        setupRecyclerView()
        setupChips()
        setupRangeSlider()
    }

    private fun initializeViews() {
        cardItemList = mutableListOf()
        cardAdapter = CardBookingAdapter(cardItemList)

        cardAdapter.setOnReserveClickListener(object : CardBookingAdapter.OnReserveClickListener {
            override fun onReserveClick(position: Int) {
                onReserveClick(position)
            }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Adicione dados de exemplo
        addSampleCardItems()

        // Configure o adaptador
        recyclerView.adapter = cardAdapter
    }

    private fun addSampleCardItems() {
        cardItemList.add(CardBookingItem("08:40", "50min", "Laboratório 1", "Matéria 1", "1 DS B", 1, DiaDaSemana.THURSDAY))
        cardItemList.add(CardBookingItem("09:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B", 2, DiaDaSemana.WEDNESDAY))
        cardItemList.add(CardBookingItem("23:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B", 3, DiaDaSemana.TUESDAY))
        cardItemList.add(CardBookingItem("17:30", "45min", "Laboratório 2", "Matéria 2", "3 DS B", 4, DiaDaSemana.MONDAY))
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

    private fun onReserveClick(position: Int) {
        val item = cardItemList[position]

        apiService.salvarReserva(item.diaDaSemana, item.id).enqueue(object : Callback<SalvarReservaResponse> {
            override fun onResponse(call: Call<SalvarReservaResponse>, response: Response<SalvarReservaResponse>) {
                if (response.isSuccessful) {
                    showToast("Reserva realizada com sucesso!")
                } else {
                    showToast("Erro ao salvar a reserva")
                }
            }

            override fun onFailure(call: Call<SalvarReservaResponse>, t: Throwable) {
                showToast("Erro na requisição da API")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
