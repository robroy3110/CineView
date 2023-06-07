package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.FilmeDB

@Dao
interface FilmeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(filme: FilmeDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(filme: List<FilmeDB>)

    @Query("SELECT * FROM Filme WHERE uuid = :id")
    fun getFromId(id: String): FilmeDB

    @Query("DELETE FROM Filme")
    fun deleteAll()

    @Query("DELETE FROM Filme WHERE uuid = :id")
    fun delete(id: String) : Int

    @Query("SELECT * FROM Filme")
    fun getAll(): List<FilmeDB>

    @Query("SELECT * FROM Filme ORDER BY votos_imdb DESC LIMIT 4")
    fun getFilmesComMaisVotos(): List<FilmeDB>

    //@Query("SELECT * FROM Filme ORDER BY data DESC LIMIT 1")
    //fun getLastEntry(): FilmeDB?

}