package pt.ulusofona.deisi.cm2223.g22001936_22006023.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters.FilmesAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters.HomeAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.CineViewApplication
import pt.ulusofona.deisi.cm2223.g22001936_22006023.NavigationManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.Filmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.RegistoFilmes
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

        val ultimosRegistos = RegistoFilmes.getLasts()
        adapterRegistos.updateItems(ultimosRegistos)

        binding.rvUltimosRegistos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUltimosRegistos.adapter = adapterRegistos

        val filmesMaisVistos = Filmes.pegarMaisVistos()
        adapterFilmes.updateItems(filmesMaisVistos)

        binding.rvFilmesMaisVistos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilmesMaisVistos.adapter = adapterFilmes

    }



}