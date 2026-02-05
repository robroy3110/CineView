package pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas

import android.content.Context
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme

object Filmes {

    val _filmes = mutableListOf<Filme>(
    )
    fun filmes2(context: Context) : List<Filme>{

        return _filmes.toList()
    }
    fun add(filme:Filme){
        _filmes.add(filme)
    }
    fun procurarFilme(nome : String) : Boolean{
        for (filme in filmes){
            if(filme.nome == nome){
                return true
            }
        }
        return false
    }

    fun pegarAtores(): List<String>{
        var atores :MutableList<String> = mutableListOf()
        for(filme in filmes){
            var atoresFilme = filme.atores.split(", ")
            for (ator in atoresFilme){
                atores.add(ator)
            }
        }
        return atores
    }

    fun procurarAtor(nome:String):Boolean{
        var atores = pegarAtores()
        for (ator in atores){
            if (nome == ator){
                return true
            }
        }
        return false
    }

    fun pegarFilmesAtor(nome:String) : List<Filme>{
        var filmesAtor :MutableList<Filme> = mutableListOf()
        for(filme in filmes){
            if(filme.atores.contains(nome)){
                filmesAtor.add(filme)
            }
        }
        return filmesAtor
    }

    fun pegarFilme(nome : String) : Filme?{
        for (filme in filmes){
            if(filme.nome == nome){
                return filme
            }
        }
        return null
    }

    fun pegarMaisVistos() : List<Filme>{
        return filmes.sortedByDescending { it.votosIMBD }.take(3)
    }

    val filmes get() = _filmes.toList()

}