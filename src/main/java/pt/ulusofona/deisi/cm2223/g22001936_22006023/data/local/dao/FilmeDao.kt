package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.FilmeDB

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

    @Query("SELECT GROUP_CONCAT(atores, ', ') AS allAtores FROM Filme")
    fun getAllAtores(): String

    @Query("SELECT * FROM Filme WHERE atores LIKE '%' || :ator || '%'")
    fun getFilmesComAtor(ator: String): List<FilmeDB>

    @Query("SELECT EXISTS(SELECT 1 FROM Filme WHERE atores LIKE '%' || :ator || '%')")
    fun hasFilmesComAtor(ator: String): Boolean




    //@Query("SELECT * FROM Filme ORDER BY data DESC LIMIT 1")
    //fun getLastEntry(): FilmeDB?

}