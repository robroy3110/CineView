package pt.ulusofona.deisi.cm2223.g22001936_22006023.models


class Cinema(var id: Int, var name:String, var provider: String,var logoUrl:String = "", var latitude: Float, var longitude: Float, var address:String, var postcode:String
             , var county: String, var photos: List<String> = mutableListOf(), var ratings:List<Rating>, var hours:List<Horario>) {

    override fun toString(): String {
        return "$name - $provider"
    }

}

