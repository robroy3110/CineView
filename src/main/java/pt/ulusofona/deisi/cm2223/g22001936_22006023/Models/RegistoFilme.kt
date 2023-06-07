package pt.ulusofona.deisi.cm2223.g22001936_22006023.Models

import java.util.*
import android.graphics.Bitmap


class RegistoFilme(var filme: Filme, var cinema : Cinema, var rating:Int, var data: String, var photos:List<Bitmap> , var observacoes :String, val uuid : String = UUID.randomUUID().toString()) {

}