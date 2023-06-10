package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Horario")
@TypeConverters(BitmapListConverter::class)
data class HorarioDB(
    @PrimaryKey val horarioId: Int,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "openHour") val openHour: String,
    @ColumnInfo(name = "closeHour") val closeHour: String
)