package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.HorarioDB


@Dao
interface HorarioDao {

    @Query("SELECT * FROM Horario WHERE horarioId = :id")
    fun getAllById(id: Int): List<HorarioDB>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(horario: HorarioDB)
}