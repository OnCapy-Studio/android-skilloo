import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skilloapp.R
import com.example.skilloapp.data.model.commit.Commit
import java.text.SimpleDateFormat
import java.util.*

class CommitAdapter(private val context: Context, private val commits: List<Commit>) :
    RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    inner class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.annotationTitleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.annotationDateTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val commit = commits[position]
                    showCommitInfoPopup(commit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.anotacao_item, parent, false)
        return CommitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val commit = commits[position]

        holder.titleTextView.text = commit.titulo

        val dataFormatada = formatarData(commit.data, "dd/MM/yyyy HH:mm")
        holder.dateTextView.text = dataFormatada
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    private fun formatarData(data: String, formatoDestino: String): String {
        val formatoOrigem = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dataOrigem = formatoOrigem.parse(data)

        val formatoSaida = SimpleDateFormat(formatoDestino, Locale.getDefault())
        return formatoSaida.format(dataOrigem!!)
    }

    private fun showCommitInfoPopup(commit: Commit) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_commit_info)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val titleTextView = dialog.findViewById<TextView>(R.id.popupTitleTextView)
        val descriptionTextView = dialog.findViewById<TextView>(R.id.popupDescriptionTextView)
        val dateTextView = dialog.findViewById<TextView>(R.id.popupDateTextView)
        val closeButton = dialog.findViewById<Button>(R.id.closePopupButton)

        titleTextView.text = commit.titulo
        descriptionTextView.text = commit.descricao
        dateTextView.text = formatarData(commit.data, "dd/MM/yyyy HH:mm")

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
