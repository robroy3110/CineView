package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Filme")
@TypeConverters(BitmapListConverter::class)
data class FilmeDB(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "cartaz") val cartaz: Bitmap?,
    @ColumnInfo(name = "genero") val genero: String,
    @ColumnInfo(name = "sinopse") val sinopse: String,
    @ColumnInfo(name = "atores") val atores: String,
    @ColumnInfo(name = "data_lancamento") val dataLancamento: String,
    @ColumnInfo(name = "avaliacao_imdb") val avaliacaoIMDB: Double,
    @ColumnInfo(name = "votos_imdb") val votosIMDB: Int,
    @ColumnInfo(name = "link_imdb") val linkIMDB: String
)
