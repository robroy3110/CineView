package pt.ulusofona.deisi.cm2223.g22001936_22006023

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.remote.CineViewOkhttp
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.CineViewDBWithRoom
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.CineViewDatabase


class CineViewApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CineRepository.init(
            local = CineViewDBWithRoom(
                CineViewDatabase.getInstance(applicationContext).registoFilmeDao(),
                CineViewDatabase.getInstance(applicationContext).FilmeDao(),
                CineViewDatabase.getInstance(applicationContext).CinemaDao(),
                CineViewDatabase.getInstance(applicationContext).HorarioDao(),
                CineViewDatabase.getInstance(applicationContext).RatingDao()
            ),
            remote = CineViewOkhttp(client = OkHttpClient()),
            context = this
        )
        Log.i("APP", "Initialized repository")
    }

    private fun initCineViewRoom(): CineViewDBWithRoom {
        return CineViewDBWithRoom(
            CineViewDatabase.getInstance(applicationContext).registoFilmeDao(),
            CineViewDatabase.getInstance(applicationContext).FilmeDao(),
            CineViewDatabase.getInstance(applicationContext).CinemaDao(),
            CineViewDatabase.getInstance(applicationContext).HorarioDao(),
            CineViewDatabase.getInstance(applicationContext).RatingDao(),
        )
    }



}