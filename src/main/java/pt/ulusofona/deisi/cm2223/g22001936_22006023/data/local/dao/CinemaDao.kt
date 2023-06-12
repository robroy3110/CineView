package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import android.content.Context
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

    @Query("SELECT * FROM Cinema")
    fun getAllCinemas(): List<CinemaDB>

    @Query("SELECT EXISTS(SELECT 1 FROM Cinema WHERE name LIKE '%' || :cinemaName || '%' LIMIT 1)")
    fun existsCinema(cinemaName: String): Boolean

    @Query("SELECT * FROM Cinema WHERE name LIKE '%' || :searchString || '%'")
    fun getCinemasContainingString(searchString: String): List<CinemaDB>

    @Query("SELECT id FROM Cinema WHERE name LIKE '%' || :searchString || '%' LIMIT 1")
    fun getCinemaIdContainingString(searchString: String): Int

    @Query("DELETE FROM Cinema")
    fun deleteAll()

    //@Query("SELECT * FROM Cinema ORDER BY data DESC LIMIT 1")
    //fun getLastEntry(): CinemaDB?

}