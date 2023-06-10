package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.CinemaDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.FilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.HorarioDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.RatingDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.*
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.RegistoFilmeDao

@Database(entities = [RegistoFilmeDB::class, FilmeDB::class, CinemaDB::class, HorarioDB::class,RatingDB::class], version = 4)
abstract class CineViewDatabase : RoomDatabase() {

    abstract fun registoFilmeDao(): RegistoFilmeDao
    abstract fun FilmeDao(): FilmeDao
    abstract fun CinemaDao(): CinemaDao
    abstract fun RatingDao(): RatingDao
    abstract fun HorarioDao(): HorarioDao
    companion object {
        private var instance: CineViewDatabase? = null

        fun getInstance(context: Context): CineViewDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CineViewDatabase::class.java,
                        "movies_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance as CineViewDatabase
            }
        }
    }
}