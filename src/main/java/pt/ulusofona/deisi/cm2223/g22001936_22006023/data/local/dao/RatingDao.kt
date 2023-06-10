package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.RatingDB

@Dao
interface RatingDao {

    @Query("SELECT * FROM Rating WHERE ratingId = :id")
    fun getAllById(id: Int): List<RatingDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rating: RatingDB)

}