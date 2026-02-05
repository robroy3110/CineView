package pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters.FilmesAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters.HomeAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.NavigationManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas.Filmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas.RegistoFilmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val TAG = HomeFragment::class.java.simpleName
    private val adapterFilmes = HomeAdapter(Filmes.filmes)
    private val adapterRegistos = FilmesAdapter(::onMovieClick, RegistoFilmes.registo_filmes)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"
        return binding.root
    }

    private fun onMovieClick(uuid: String) {
        Toast.makeText(context, uuid, Toast.LENGTH_LONG).show()
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

    override fun onStart() {
        super.onStart()

        CineRepository.getInstance().getUltimosRegistos { result ->
            CoroutineScope(Dispatchers.Main).launch {
                if (result.isSuccess) {
                    adapterRegistos.updateItems(result.getOrDefault(mutableListOf()))
                }
            }
        }

        binding.rvUltimosRegistos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUltimosRegistos.adapter = adapterRegistos

        CineRepository.getInstance().getFilmesComMaisVotos { result ->
            CoroutineScope(Dispatchers.Main).launch {
                if (result.isSuccess) {
                    adapterFilmes.updateItems(result.getOrDefault(mutableListOf()))
                }
            }
        }

        binding.rvFilmesMaisVistos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilmesMaisVistos.adapter = adapterFilmes

    }



}