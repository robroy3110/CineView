package pt.ulusofona.deisi.cm2223.g22001936_22006023

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CineViewOkhttp
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.CineViewDBWithRoom
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.CineViewDatabase


class CineViewApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CineRepository.init(
            local = CineViewDBWithRoom(
                CineViewDatabase.getInstance(applicationContext).registoFilmeDao(),
                CineViewDatabase.getInstance(applicationContext).FilmeDao(),
                CineViewDatabase.getInstance(applicationContext).CinemaDao()
            ),
            remote = CineViewOkhttp(client = OkHttpClient()),
            context = this
        )
        Log.i("APP", "Initialized repository")
    }
}