package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities
import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme


@Entity(tableName = "registoFilme")
data class RegistoFilmeDB(
    @PrimaryKey val registoFilmeId: String,
    @ColumnInfo(name = "filmeId") val filmeId: String,
    @ColumnInfo(name = "cinemaId") val cinemaId: Int,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "photos") val photos : MutableList<Bitmap>,
    @ColumnInfo(name = "observacoes") val observacoes : String)