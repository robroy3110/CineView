package pt.ulusofona.deisi.cm2223.g22001936_22006023.Fragments


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
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters.FilmesAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.NavigationManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.RegistoFilmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentFilmesBinding

class FilmesFragment : Fragment() {
    private lateinit var binding: FragmentFilmesBinding
    private val adapter = FilmesAdapter(::onMovieClick, RegistoFilmes.registo_filmes)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFilmesBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Filmes Vistos"
        
        binding.fab.setOnClickListener { view ->
            NavigationManager.goToMapFragment(parentFragmentManager)
        }

        if(screenRotated(savedInstanceState)) {
            NavigationManager.goToFilmesFragment(parentFragmentManager)
        }

        return binding.root
    }

   private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    private fun onMovieClick(uuid: String) {
        NavigationManager.goToDetalhesFragment(parentFragmentManager, uuid)
    }

    override fun onStart() {
        super.onStart()

        binding.rvFilmes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilmes.adapter = adapter
        CineRepository.getInstance().getFilmesRegistados { result ->
            if (result.isSuccess) {
                CoroutineScope(Dispatchers.Main).launch{
                    adapter.updateItems(result.getOrDefault(mutableListOf()))
                }
            }
        }
        
    }

}