package pt.ulusofona.deisi.cm2223.g22001936_22006023.Models


class Cinema(var id: Int, var name:String, var provider: String, var latitude: Float, var longitude: Float, var address:String, var postcode:String
             , var county: String, var photos: List<String>, var ratings:List<Rating>, var hours:List<Horario>) {

    override fun toString(): String {
        return "$name - $provider"
    }

}