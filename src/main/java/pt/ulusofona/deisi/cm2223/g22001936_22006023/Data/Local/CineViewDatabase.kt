package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CinemaDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.FilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.BitmapListConverter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.CinemaDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.FilmeDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.RegistoFilmeDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.RegistoFilmeDao

@Database(entities = [RegistoFilmeDB::class, FilmeDB::class, CinemaDB::class], version = 1)
abstract class CineViewDatabase : RoomDatabase() {

    abstract fun registoFilmeDao(): RegistoFilmeDao
    abstract fun FilmeDao(): FilmeDao
    abstract fun CinemaDao(): CinemaDao

    companion object {
        private var instance: CineViewDatabase? = null

        fun getInstance(context: Context): CineViewDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CineViewDatabase::class.java,
                        "movies_db"
                    )
                        .build()
                }
                return instance as CineViewDatabase
            }
        }
    }
}