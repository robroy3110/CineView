package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.CinemaDB

@Dao
interface CinemaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Cinema: CinemaDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(Cinema: List<CinemaDB>)

    @Query("SELECT * FROM Cinema WHERE id = :id")
    fun getFromId(id: Int): CinemaDB

    @Query("SELECT * FROM Cinema WHERE name = :name")
    fun getFromName(name: String): CinemaDB

    //@Query("SELECT * FROM Cinema ORDER BY data ASC")
    //fun getAll(): List<CinemaDB>

    @Query("DELETE FROM Cinema")
    fun deleteAll()

    //@Query("SELECT * FROM Cinema ORDER BY data DESC LIMIT 1")
    //fun getLastEntry(): CinemaDB?

}