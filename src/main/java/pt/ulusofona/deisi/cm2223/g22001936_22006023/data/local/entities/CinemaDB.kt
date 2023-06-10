package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Cinema")
@TypeConverters(BitmapListConverter::class)
data class CinemaDB(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "provider") val provider: String,
    @ColumnInfo(name = "LogoUrl") val logoUrl: String,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "postcode") val postcode: String,
    @ColumnInfo(name = "county") val county: String,
    @ColumnInfo(name = "photos") val photos: List<String>,
    @ColumnInfo(name = "ratingsId") val ratingsId: Int,
    @ColumnInfo(name = "horarioId") val horarioId: Int
)
