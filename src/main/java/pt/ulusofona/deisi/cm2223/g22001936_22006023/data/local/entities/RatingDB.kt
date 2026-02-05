package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rating")
data class RatingDB(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "ratingId") val ratingId: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "score") val score: Int,
)
