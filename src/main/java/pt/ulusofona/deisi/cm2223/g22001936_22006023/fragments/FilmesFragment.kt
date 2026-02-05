package pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters.FilmesAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.NavigationManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas.RegistoFilmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentFilmesBinding

class FilmesFragment : Fragment() {
    private lateinit var binding: FragmentFilmesBinding
    private val adapter = FilmesAdapter(::onMovieClick, RegistoFilmes.registo_filmes)
    private val REQUEST_LOCATION_PERMISSION = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFilmesBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Filmes Vistos"

        binding.fab.setOnClickListener { view ->



            // Verificar se a permissão ACCESS_FINE_LOCATION já está concedida
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Se a permissão não estiver concedida, solicite-a ao usuário
                ActivityCompat.requestPermissions((requireActivity() as AppCompatActivity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            } else {
                // Se a permissão já estiver concedida, execute a lógica para acessar a localização do usuário
                NavigationManager.goToMapFragment(parentFragmentManager)
            }


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