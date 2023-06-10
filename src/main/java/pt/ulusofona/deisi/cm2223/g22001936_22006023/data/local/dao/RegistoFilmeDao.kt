package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.RegistoFilmeDB

@Dao
interface RegistoFilmeDao {

    @Insert
    fun insert(registoFilme: RegistoFilmeDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(registoFilme: List<RegistoFilmeDB>)

    @Query("SELECT * FROM registoFilme")
    fun getAll(): List<RegistoFilmeDB>

   @Query("DELETE FROM registoFilme WHERE filmeId = :id")
   fun delete(id: String): Int

    @Query("DELETE FROM registoFilme")
    fun deleteAll()
    @Query("SELECT * FROM registoFilme WHERE registoFilmeId = :id")
    fun getFromId(id: String): RegistoFilmeDB

    @Query("SELECT * FROM RegistoFilme LIMIT 4")
    fun getUltimosRegistos(): List<RegistoFilmeDB>

}