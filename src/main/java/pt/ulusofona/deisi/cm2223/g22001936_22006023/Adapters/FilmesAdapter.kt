package pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.RegistoFilme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.CardViewBinding
import java.io.File


class FilmesAdapter(private val onOperationClick: (String) -> Unit, private var items: List<RegistoFilme> = listOf()) : RecyclerView.Adapter<FilmesAdapter.FilmeViewHolder>() {

    class FilmeViewHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {

        return FilmeViewHolder(
            CardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) { //this mf

        holder.itemView.setOnClickListener{ onOperationClick(items[position].uuid) }
        holder.binding.filmeName?.text = items[position].filme.nome
        holder.binding.filmeCinema?.text = "Cinema: ${items[position].cinema.name}"
        holder.binding.ratingNumber?.text = items[position].rating.toString()
        holder.binding.data?.text = "Data de visualização: ${items[position].data}"


        holder.binding.filmeNameLand?.text = items[position].filme.nome
        holder.binding.filmeCinemaLand?.text = "Cinema: ${items[position].cinema.name}"
        holder.binding.generoFilme?.text = "Género: ${items[position].filme.genero}"
        holder.binding.filmeDataLancamento?.text = "Data de visualização: ${items[position].data}"
        holder.binding.ratingStarsLand?.removeAllViews()
        for (i in 1..items[position].rating) {
            val starImageView = ImageView(holder.binding.root.context)
            starImageView.setPadding(-12, 0,-12,0)
            starImageView.setImageResource(R.drawable.ic_star)
            holder.binding.ratingStarsLand?.addView(starImageView)
        }
        val imgFile = File(items[position].filme.cartaz)
        if (imgFile.exists()) {
            holder.binding.cartaz?.setImageURI(Uri.fromFile(imgFile))
        }
        //val bmImg = BitmapFactory.decodeFile(items[position].filme.cartaz)
        //holder.binding.cartaz?.setImageBitmap(bmImg)

        //holder.binding.cartaz?.setImageResource(items[position].filme.cartaz)
    }

    override fun getItemCount() = items.size

    fun updateItems(items: List<RegistoFilme>) {
        this.items = items
        notifyDataSetChanged()
    }


    // Implementação do HistoryAdapter

}