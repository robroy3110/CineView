package pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas

import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R

object Filmes {

    private val _filmes = mutableListOf<Filme>(
        Filme(nome = "Spider-Man", cartaz = R.drawable.spider_man,genero ="Action, Adventure, Sci-Fi", sinopse = "After being bitten by a genetically-modified spider, a shy teenager gains spider-like abilities that he uses to fight injustice as a masked superhero and face a vengeful enemy", atores = "Tobey Maguire, Kirsten Dunst, Willem Dafoe",dataLancamento = "2002/05/03", avaliacaoIMDB = 7.4, votosIMBD = 831569,linkIMDB = "https://www.imdb.com/title/tt0145487/" ),
        Filme(nome = "2012",R.drawable.what,"Action, Adventure, Sci-Fi","A frustrated writer struggles to keep his family alive when a series of global catastrophes threatens to annihilate mankind.","John Cusack, Thandiwe Newton, Chiwetel Ejiofor","2009/11/13",5.8,384211,"https://www.imdb.com/title/tt1190080/"),
        Filme("Cars",R.drawable.cars,"Animation, Adventure, Comedy","On the way to the biggest race of his life, a hotshot rookie race car gets stranded in a rundown town, and learns that winning isn't everything in life.","Owen Wilson, Bonnie Hunt, Paul Newman","2009/06/09",7.2,434062,"https://www.imdb.com/title/tt0317219/"),
        Filme("The Maze Runner",R.drawable.maze_runner,"Action, Mystery, Sci-Fi","Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow \\\"runners\\\" for a shot at escape.","Dylan O'Brien, Kaya Scodelario, Will Poulter","2014/07/19",6.8,479483,"https://www.imdb.com/title/tt1790864/"),
        Filme("Elysium",
            R.drawable.elysium,"Action, Drama, Sci-Fi","In the year 2154, the very wealthy live on a man-made space station while the rest of the population resides on a ruined Earth. A man takes on a mission that could bring equality to the polarized worlds.","Matt Damon, Jodie Foster, Sharlto Copley","2013/08/09",6.6,455061,"https://www.imdb.com/title/tt1535108/"),
        Filme("Interstellar",R.drawable.interstellar,"Adventure, Drama, Sci-Fi","A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.","Matthew McConaughey, Anne Hathaway, Matt Damon","2014/11/07",8.6,1882826,"https://www.imdb.com/title/tt0816692/")
    )
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