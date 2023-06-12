package pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.adapters.PosterAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentExtraBinding


class ExtraFragment : Fragment() {

    private lateinit var binding: FragmentExtraBinding
    private val TAG = ExtraFragment::class.java.simpleName
    private val adapter = PosterAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentExtraBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Pesquisa Por Atores"

        var adapterAtores : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.select_dialog_item, mutableListOf())
        val actvAtores = binding.autoCompleteTextViewAtores

        actvAtores.threshold = 1

        CineRepository.getInstance().getAllAtores { result ->
            if (result.isSuccess) {
                val atores = result.getOrDefault("").split(", ")

                CoroutineScope(Dispatchers.Main).launch {
                    adapterAtores = ArrayAdapter<String>(requireContext(), R.layout.select_dialog_item, atores)

                    actvAtores.setAdapter(adapterAtores)
                }
            }
        }


        binding.atorLayout.setEndIconOnClickListener {
            if(binding.autoCompleteTextViewAtores.text.toString() != "" && binding.atorLayout.error == null){

                var filmes : List<Filme>
                CineRepository.getInstance().getFilmesComAtor(binding.autoCompleteTextViewAtores.text.toString()) { result ->
                    if (result.isSuccess) {
                        CoroutineScope(Dispatchers.Main).launch {
                            filmes = result.getOrDefault(mutableListOf())

                            binding.rvPoster.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                            binding.rvPoster.adapter = adapter

                            adapter.updateItems(filmes)
                        }
                    }
                }

            }
        }

        binding.autoCompleteTextViewAtores.doOnTextChanged { text, start, before, count ->

            CineRepository.getInstance().hasFilmesComAtor(text.toString()) { result ->
                if (result.isSuccess) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if(result.getOrDefault(false)){
                            binding.atorLayout.error = null
                        } else {
                            binding.atorLayout.error = "Ator inexistente, por favor escolha um da lista"
                        }
                    }
                }
            }
        }


        return binding.root
    }

}