package pt.ulusofona.deisi.cm2223.g22001936_22006023.Fragments


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters.PhotoAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.RegistoFilme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.RegistoFilmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentDetalhesBinding
import java.io.File


private const val ARG_FILME_UUID = "ARG_FILME_UUID"


class DetalhesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentDetalhesBinding
    private var filmeUuid: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmeUuid = it.getString(ARG_FILME_UUID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detalhes, container, false)
        binding = FragmentDetalhesBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        filmeUuid?.let { uuid ->
          val filme = RegistoFilmes.getFilmeById(uuid)
          filme.let { placeData(it) }
        }
    }


    private fun placeData(ui: RegistoFilme) {
        val photoList: MutableList<Bitmap> = ui.photos
        val adapter = PhotoAdapter(photoList)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ui.filme.nome

        //val imgFile = File(ui.filme.cartaz)
        //if (imgFile.exists()) {
        //    binding.ivCartaz.setImageURI(Uri.fromFile(imgFile))
        //}

        Picasso.get().load(File(ui.filme.cartaz)).into(binding.ivCartaz)


        //val bmImg = BitmapFactory.decodeFile(ui.filme.cartaz)
        //binding.ivCartaz.setImageBitmap(bmImg)

        //binding.ivCartaz.setImageResource(ui.filme.cartaz)

        binding.tvGenero.text = ui.filme.genero
        binding.tvSinopse.text = ui.filme.sinopse
        binding.tvDataLancamento.text = ui.filme.dataLancamento
        binding.tvRatingIMDB.text = ui.filme.avaliacaoIMDB.toString()
        binding.tvLink.text = ui.filme.linkIMDB

        binding.tvDetalhes.text = "Visto em ${ui.cinema.name} no dia ${ui.data}"
        binding.tvRating.text = ui.rating.toString()
        binding.tvVotos.text = ui.filme.votosIMBD.toString()


        if (ui.observacoes.isNotBlank()){
            binding.tvObservacoes.text = ui.observacoes
        }

        if (ui.photos.isNotEmpty()){
            binding.titlePhotos.text = "@string/photos"
            binding.rvPhotos.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            binding.rvPhotos.adapter = adapter
            adapter?.notifyDataSetChanged()
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment DetalhesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(uuid: String) =
            DetalhesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILME_UUID, uuid)
                }
            }
    }
}