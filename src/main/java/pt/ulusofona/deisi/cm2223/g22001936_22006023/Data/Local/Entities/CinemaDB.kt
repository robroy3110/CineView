package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Horario
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Rating

@Entity(tableName = "Cinema")
data class CinemaDB(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "provider") val provider: String,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "postcode") val postcode: String,
    @ColumnInfo(name = "county") val county: String,
    @ColumnInfo(name = "photos") val photos: MutableList<String>,
    @ColumnInfo(name = "ratings") val ratings: List<Rating>,
    @ColumnInfo(name = "hours") val hours: List<Horario>
)
