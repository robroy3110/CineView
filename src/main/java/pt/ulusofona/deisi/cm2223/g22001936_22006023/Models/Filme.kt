package pt.ulusofona.deisi.cm2223.g22001936_22006023.Models



import java.util.*

class Filme (var nome:String, var cartaz: String, var genero : String, var sinopse: String,var atores :String,
             var dataLancamento: String, var avaliacaoIMDB: Double,var votosIMBD : Int, var linkIMDB: String, val uuid : String = UUID.randomUUID().toString()){

    override fun toString(): String {
        return nome
    }

}