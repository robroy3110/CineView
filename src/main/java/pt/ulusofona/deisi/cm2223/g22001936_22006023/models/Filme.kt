package pt.ulusofona.deisi.cm2223.g22001936_22006023.models



import android.graphics.Bitmap
import java.util.*

class Filme (var nome:String, var cartaz: Bitmap?, var genero : String, var sinopse: String, var atores :String,
             var dataLancamento: String, var avaliacaoIMDB: Double, var votosIMBD : Int, var linkIMDB: String, var uuid : String = UUID.randomUUID().toString()){

    override fun toString(): String {
        return nome
    }

}