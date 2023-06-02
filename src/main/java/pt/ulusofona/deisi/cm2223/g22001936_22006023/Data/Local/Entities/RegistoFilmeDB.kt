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

        @ColumnInfo(name="registoFilme_id")
        @PrimaryKey val registoFilmeId: String,
        val filmeId: String,
        val cinemaId: String,
        val rating: Int,
        val data: String,
        val photos : MutableList<Bitmap>,
        val observacoes : String)