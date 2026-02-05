package pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters.PhotoAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.RegistoFilme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentDetalhesBinding


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
          CineRepository.getInstance().getFilmeRegistadoById(uuid){result ->
              if(result.isSuccess){
                  Log.i("APP", "peguei o filme vou agora por")
                  CoroutineScope(Dispatchers.Main).launch {
                      Log.i("APP", "vou agora por")
                      placeData(result.getOrDefault(RegistoFilme(Filme("", Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888), "", "", "", "", 0.0, 0, "", ""),
                          Cinema(
                              0,
                              "cinema.name",
                              "cinema.provider",
                              "",
                              1.0f,
                              1f,
                              "cinema.address,",
                              "cinema.postcode",
                              "cinema.county",
                              mutableListOf(),
                              mutableListOf(),
                              mutableListOf(),
                          ),0,"", mutableListOf(),"")))
                  }
              }

          }
        }
    }
    private fun placeData(ui: RegistoFilme) {
        val photoList: List<Bitmap?> = ui.photos
        Log.i("APP", "Meti o photoList")
        val adapter = PhotoAdapter(photoList)
        Log.i("APP", "Meti o photoAdapter")
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ui.filme.nome

        //val imgFile = File(ui.filme.cartaz)
        //if (imgFile.exists()) {
        //    binding.ivCartaz.setImageURI(Uri.fromFile(imgFile))
        //}
        //var file = File(ui.filme.cartaz)

       // Picasso.get().load(Uri.fromFile(file)).into(binding.ivCartaz)

        Log.i("APP ATOR", ui.filme.atores)

        binding.ivCartaz.setImageBitmap(ui.filme.cartaz)


        //binding.ivCartaz.setImageResource(ui.filme.cartaz)

        binding.tvGenero.text = ui.filme.genero
        binding.tvSinopse.text = ui.filme.sinopse
        binding.tvDataLancamento.text = ui.filme.dataLancamento
        binding.tvRatingIMDB.text = ui.filme.avaliacaoIMDB.toString()

        binding.btnLink.apply {
            setOnClickListener {
                val link = ui.filme.linkIMDB
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }


        binding.tvDetalhes.text = "Visto em ${ui.cinema.name} no dia ${ui.data}"
        binding.tvRating.text = ui.rating.toString()
        binding.tvVotos.text = ui.filme.votosIMBD.toString()


        if (ui.observacoes.isNotBlank()){
            binding.tvObservacoes.text = ui.observacoes
        }

        if (ui.photos.isNotEmpty()){
            binding.titlePhotos.text = getString(R.string.fotos)
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