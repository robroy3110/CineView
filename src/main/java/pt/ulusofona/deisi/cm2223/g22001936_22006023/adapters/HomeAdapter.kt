package pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.HomeRecyclerBinding


class HomeAdapter(private var items: List<Filme> = listOf()) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: HomeRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        return HomeViewHolder(
            HomeRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) { //this mf

        holder.binding.filmeName.text = items[position].nome
        holder.binding.dataLancamento.text = "Lançamento: ${items[position].dataLancamento}"
        holder.binding.ratingNumber.text = items[position].avaliacaoIMDB.toString()
        holder.binding.filmeVotos.text = "${items[position].votosIMBD} votos"
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<Filme>) {
        this.items = items
        notifyDataSetChanged()
    }


    // Implementa��o do HistoryAdapter

}