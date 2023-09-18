import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skilloapp.R
import com.example.skilloapp.data.ApiService
import com.example.skilloapp.data.RetrofitConfig
import com.example.skilloapp.data.model.home.HomeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        // Carregar os dados da API
        loadHomeData()
    }

    private fun loadHomeData() {
        val apiService = RetrofitConfig.createService(ApiService::class.java)

        apiService.getHomeData().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                if (response.isSuccessful) {
                    val homeResponse = response.body()

                    updateLayoutWithApiData(homeResponse)

                } else {
                    // Trate erros de resposta aqui
                    showToast("Erro na resposta da API")
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                // Trate erros de requisição aqui
                showToast("Erro na requisição da API")
            }
        })
    }

    private fun updateLayoutWithApiData(homeResponse: HomeResponse?) {
        if (homeResponse != null) {
            findViewById<TextView>(R.id.squareAulaReserva).text = homeResponse.materia.nome
            findViewById<TextView>(R.id.squareTurma).text = homeResponse.turma.nome
//            findViewById<TextView>(R.id.squareSala).text = homeResponse.sala
            findViewById<TextView>(R.id.squareHora).text = homeResponse.horario
            findViewById<TextView>(R.id.squareHoradois).text = homeResponse.horario
//            findViewById<TextView>(R.id.squareReservatempo).text = homeResponse.duracao
//            findViewById<TextView>(R.id.squareSaladeaula).text = homeResponse.nomeSalaAula
//            findViewById<TextView>(R.id.squareAula).text = homeResponse.aula
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
