package pt.ocivr.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiasPagerAdapter(
    private val dias: List<String>,
    private val dados: List<List<String>>
) : RecyclerView.Adapter<DiasPagerAdapter.DiaViewHolder>()
{

    class DiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView =
            view.findViewById(R.id.recyclerViewTrabalhos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_dia, parent, false)
        return DiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaViewHolder, position: Int) {

        // Por agora lista vazia (vamos preencher com Google Sheet depois)
        val listaTrabalhos = dados[position]



        holder.recyclerView.layoutManager =
            LinearLayoutManager(holder.itemView.context)

        holder.recyclerView.adapter =
            TrabalhosAdapter(listaTrabalhos)
    }

    override fun getItemCount(): Int = dias.size
}