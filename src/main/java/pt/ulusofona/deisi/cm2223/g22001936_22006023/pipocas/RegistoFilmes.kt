package pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas

import android.graphics.Bitmap
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.RegistoFilme

object RegistoFilmes {

    private val _registo_filmes= mutableListOf<RegistoFilme>(

    )

    val registo_filmes get() = _registo_filmes.toList()

    fun submit(filme: Filme, cinema: Cinema, rating: Int, data: String, photoList:MutableList<Bitmap> , observacoes: String) {
       _registo_filmes.add(RegistoFilme(filme, cinema, rating, data,photoList, observacoes))
    }

    fun getFilmeById(uuid: String): RegistoFilme {
        return _registo_filmes.find { it.uuid == uuid }!!
    }

    fun getLasts(): List<RegistoFilme> {
        var lasts : MutableList<RegistoFilme> = mutableListOf()
        if(_registo_filmes.isNotEmpty()){
            lasts.add(_registo_filmes[_registo_filmes.size-1])
            if(_registo_filmes.size>=2){
                lasts.add(_registo_filmes[_registo_filmes.size-2])
            }
        }
        return lasts
    }
}