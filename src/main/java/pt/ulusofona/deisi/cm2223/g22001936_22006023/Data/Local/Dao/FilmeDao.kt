package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.FilmeDB

@Dao
interface FilmeDao {

    @Insert
    fun insert(Filme: FilmeDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(Filme: List<FilmeDB>)

    @Query("SELECT * FROM Filme WHERE uuid = :id")
    fun getFromId(id: String): FilmeDB


    @Query("DELETE FROM Filme")
    suspend fun deleteAll()

    //@Query("SELECT * FROM Filme ORDER BY data DESC LIMIT 1")
    //fun getLastEntry(): FilmeDB?

}