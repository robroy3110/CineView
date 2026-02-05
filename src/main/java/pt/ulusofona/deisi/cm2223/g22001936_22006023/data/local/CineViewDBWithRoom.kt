package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlin.math.*
import androidx.room.PrimaryKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.CinemaDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.FilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.HorarioDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.RatingDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.entities.*
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local.dao.RegistoFilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.*
import java.io.ByteArrayOutputStream

class CineViewDBWithRoom(private val registoFilmeDao: RegistoFilmeDao, private val filmeDao: FilmeDao, private val cinemaDao: CinemaDao, private val horarioDao: HorarioDao, private val ratingDao:RatingDao) : CineView() {

    override fun insertFilmesRegistados(filmes: List<RegistoFilme>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            filmes.forEach {
                filmeDao.insert(FilmeDB(
                    uuid = it.filme.uuid,
                    nome = it.filme.nome,
                    cartaz = bitmapToByteArray(it.filme.cartaz),
                    genero = it.filme.genero,
                    sinopse = it.filme.sinopse,
                    atores = it.filme.atores,
                    dataLancamento = it.filme.dataLancamento,
                    avaliacaoIMDB = it.filme.avaliacaoIMDB,
                    votosIMDB = it.filme.votosIMBD,
                    linkIMDB = it.filme.linkIMDB,
                ))
            }
            filmes.map {
                val photos = it.photos.map{
                        bitmap ->  bitmapToByteArray(bitmap)
                }
                RegistoFilmeDB(
                    registoFilmeId = it.uuid,
                    filmeId = it.filme.uuid,
                    cinemaId = it.cinema.id,
                    data = it.data,
                    observacoes = it.observacoes,
                    rating = it.rating,
                    photos = photos
                )
            }.forEach {
                registoFilmeDao.insert(it)
                Log.i("APP", "Inserido ${it.filmeId} no banco de dados")
            }
            onFinished()
        }
    }

    override fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun atualizarFilmeDoRegisto(registoFilmeId: String, novoFilmeId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            registoFilmeDao.updateFilme(registoFilmeId, novoFilmeId)
        }
    }

    override fun existsCinema(nome: String, onFinished: (Result<Boolean>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val bool = cinemaDao.existsCinema(nome)
            onFinished(Result.success(bool))
        }
    }

    override fun getCinema(nome: String, onFinished: (Result<Cinema>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaDB = cinemaDao.getFromName(nome)
            val ratingDB = ratingDao.getAllById(cinemaDB.id)

            val rating = ratingDB.map { ratingDB ->
                Rating(
                    category = ratingDB.category,
                    score = ratingDB.score
                )
            }
            val horarioDB = horarioDao.getAllById(cinemaDB.id)

            val horario = horarioDB.map{ horarioDB ->
                Horario(
                    dia = horarioDB.day,
                    openHour = horarioDB.openHour,
                    closeHour = horarioDB.closeHour
                )
            }
            val cinema = Cinema(
                cinemaDB.id,
                cinemaDB.name,
                cinemaDB.provider,
                cinemaDB.logoUrl,
                cinemaDB.latitude,
                cinemaDB.longitude,
                cinemaDB.address,
                cinemaDB.postcode,
                cinemaDB.county,
                cinemaDB.photos,
                rating,
                horario
            )
            onFinished(Result.success(cinema))
        }
    }


    override fun getCinemaIdContainingString(searchString: String, onFinished: (Result<Int>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaId = cinemaDao.getCinemaIdContainingString(searchString)
            onFinished(Result.success(cinemaId))
        }
    }

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemasDB = cinemaDao.getAllCinemas()
            val cinemas = cinemasDB.map { cinema ->
                val ratingDB = ratingDao.getAllById(cinema.id)

                val rating = ratingDB.map { ratingDB ->
                    Rating(
                        category = ratingDB.category,
                        score = ratingDB.score
                    )
                }
                val horarioDB = horarioDao.getAllById(cinema.id)

                val horario = horarioDB.map{ horarioDB ->
                    Horario(
                        dia = horarioDB.day,
                        openHour = horarioDB.openHour,
                        closeHour = horarioDB.closeHour
                    )
                }
                Cinema(
                    cinema.id,
                    cinema.name,
                    cinema.provider,
                    cinema.logoUrl,
                    cinema.latitude,
                    cinema.longitude,
                    cinema.address,
                    cinema.postcode,
                    cinema.county,
                    cinema.photos,
                    rating,
                    horario
                )
            }
            onFinished(Result.success(cinemas))
        }
    }

    override fun hasFilme(nomeFilme: String, onFinished: (Result<Boolean>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            onFinished(Result.success(filmeDao.hasFilmeComNome(nomeFilme)))
        }
    }

    override fun getFilmeIdByName(nomeFilme: String, onFinished: (Result<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            onFinished(Result.success(filmeDao.getFilmeIdPorNome(nomeFilme)))
        }
    }

    override fun getRegistoIdByFilmeId(filmeId: String, onFinished: (Result<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            onFinished(Result.success(registoFilmeDao.getRegistoFilmeIdByFilmeId(filmeId)))
        }
    }

    override fun getCinemasMaisProximos(context: Context, chars: String, onFinished: (Result<List<Cinema>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemasDB = cinemaDao.getCinemasContainingString(chars)

            val minhaLocalizacao = getLocalizacao(context)
            val minhaLatitude = minhaLocalizacao?.latitude ?: 0.0
            val minhaLongitude = minhaLocalizacao?.longitude ?: 0.0

            val cinemasOrdenados = cinemasDB.sortedBy { calcularDistancia(it.latitude, it.longitude, minhaLatitude, minhaLongitude) }
            val cinemas = cinemasOrdenados.map { cinema ->
                val ratingDB = ratingDao.getAllById(cinema.id)

                val rating = ratingDB.map { ratingDB ->
                    Rating(
                        category = ratingDB.category,
                        score = ratingDB.score
                    )
                }
                val horarioDB = horarioDao.getAllById(cinema.id)

                val horario = horarioDB.map{ horarioDB ->
                    Horario(
                        dia = horarioDB.day,
                        openHour = horarioDB.openHour,
                        closeHour = horarioDB.closeHour
                    )
                }
                Cinema(
                    cinema.id,
                    cinema.name,
                    cinema.provider,
                    cinema.logoUrl,
                    cinema.latitude,
                    cinema.longitude,
                    cinema.address,
                    cinema.postcode,
                    cinema.county,
                    cinema.photos,
                    rating,
                    horario
                )
            }
            onFinished(Result.success(cinemas))
        }
    }

    fun getLocalizacao(context: Context): Location? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = locationManager.getProviders(true)
        var bestLocation: Location? = null

        // Verifique se as permissões estão concedidas
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            for (provider in providers) {
                val location = locationManager.getLastKnownLocation(provider) ?: continue

                if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                    bestLocation = location
                }
            }
        }

        return bestLocation
    }

    fun calcularDistancia(latitude: Float, longitude: Float, minhaLatitude: Double, minhaLongitude: Double): Double {
        val raioTerra = 6371 // Raio médio da Terra em quilômetros

        val lat1Rad = Math.toRadians(latitude.toDouble())
        val lon1Rad = Math.toRadians(longitude.toDouble())
        val lat2Rad = Math.toRadians(minhaLatitude)
        val lon2Rad = Math.toRadians(minhaLongitude)

        val difLat = lat2Rad - lat1Rad
        val difLon = lon2Rad - lon1Rad

        val a = sin(difLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(difLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distancia = raioTerra * c // Distância em quilômetros

        return distancia
    }

    override fun insertAllCinemas(cinemas: List<Cinema>) {
        CoroutineScope(Dispatchers.IO).launch {
            cinemas.forEach {
                for(rating in it.ratings){
                    ratingDao.insert(
                        RatingDB(
                            ratingId = it.id,
                            category = rating.category,
                            score = rating.score
                        )
                    )
                }
                for(horario in it.hours){
                    horarioDao.insert(
                        HorarioDB(
                            horarioId = it.id,
                            day = horario.dia,
                            openHour = horario.openHour,
                            closeHour = horario.closeHour
                        )
                    )
                }
                cinemaDao.insert(CinemaDB(
                    id = it.id,
                    name = it.name,
                    provider = it.provider,
                    logoUrl = it.logoUrl,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    address = it.address,
                    postcode = it.postcode,
                    county = it.county,
                    photos = it.photos,
                ))
            }
        }
    }

    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
        if(byteArray!= null){
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    override fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val filmesRegistados : MutableList<RegistoFilme> = mutableListOf()
            val filmes = registoFilmeDao.getAll()
            filmes.forEach {
                val photos = it.photos.map{
                        bitmap ->  byteArrayToBitmap(bitmap)
                }
                val filme = filmeDao.getFromId(it.filmeId)
                val cinema = cinemaDao.getFromId(it.cinemaId)
                filmesRegistados.add(RegistoFilme(
                    uuid = it.registoFilmeId,
                    filme = Filme(
                        filme.nome,
                        byteArrayToBitmap(filme.cartaz),
                        filme.genero,
                        filme.sinopse,
                        filme.atores,
                        filme.dataLancamento,
                        filme.avaliacaoIMDB,
                        filme.votosIMDB,
                        filme.linkIMDB
                    ),
                    cinema = Cinema(
                        cinema.id,
                        cinema.name,
                        cinema.provider,
                        cinema.logoUrl,
                        cinema.latitude,
                        cinema.longitude,
                        cinema.address,
                        cinema.postcode,
                        cinema.county,
                        cinema.photos,
                        mutableListOf(),
                        mutableListOf(),
                    ),
                    data = it.data,
                    observacoes = it.observacoes,
                    rating = it.rating,
                    photos = photos
                ))
            }

            onFinished(Result.success(filmesRegistados.toList()))
        }
    }

    override fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            registoFilmeDao.delete(id)
            onFinished()
        }
    }

    override fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit){
        CoroutineScope(Dispatchers.IO).launch{
            val registoFilme = registoFilmeDao.getFromId(id)
            val filme = filmeDao.getFromId(registoFilme.filmeId)
            val cinema = cinemaDao.getFromId(registoFilme.cinemaId)
            Log.i("App","Vou pegar agora filme cartaz")
            val photos = registoFilme.photos.map{
                    bitmap ->  byteArrayToBitmap(bitmap)
            }
            val registo = RegistoFilme(
                uuid = registoFilme.registoFilmeId,
                filme = Filme(
                    filme.nome,
                    byteArrayToBitmap(filme.cartaz),
                    filme.genero,
                    filme.sinopse,
                    filme.atores,
                    filme.dataLancamento,
                    filme.avaliacaoIMDB,
                    filme.votosIMDB,
                    filme.linkIMDB
                ),
                cinema = Cinema(
                    cinema.id,
                    cinema.name,
                    cinema.provider,
                    cinema.logoUrl,
                    cinema.latitude,
                    cinema.longitude,
                    cinema.address,
                    cinema.postcode,
                    cinema.county,
                    cinema.photos,
                    mutableListOf(),
                    mutableListOf(),
                ),
                data = registoFilme.data,
                observacoes = registoFilme.observacoes,
                rating = registoFilme.rating,
                photos = photos
            )
            Log.i("APPPPPPPP", "${registo.filme.cartaz}")
            Log.i("APP", "Inserido ${registoFilme.filmeId} no banco de dados")
            onFinished(Result.success(registo))
        }
    }

    override fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val registoFilme = registoFilmeDao.getUltimosRegistos()
            val registosFilme = registoFilme.map { 
                val filme = filmeDao.getFromId(it.filmeId)
                val cinema = cinemaDao.getFromId(it.cinemaId)
                val ratingDB = ratingDao.getAllById(it.cinemaId)

                val rating = ratingDB.map { ratingDB ->
                    Rating(
                        category = ratingDB.category,
                        score = ratingDB.score
                    )
                }
                val horarioDB = horarioDao.getAllById(it.cinemaId)
                
                val horario = horarioDB.map{horarioDB ->  
                    Horario(
                        dia = horarioDB.day,
                        openHour = horarioDB.openHour,
                        closeHour = horarioDB.closeHour
                    )
                }
                
                val photos = it.photos.map{
                        bitmap ->  byteArrayToBitmap(bitmap)
                }
                RegistoFilme(
                    uuid = it.registoFilmeId,
                    filme = Filme(
                        filme.nome,
                        byteArrayToBitmap(filme.cartaz),
                        filme.genero,
                        filme.sinopse,
                        filme.atores,
                        filme.dataLancamento,
                        filme.avaliacaoIMDB,
                        filme.votosIMDB,
                        filme.linkIMDB
                    ),
                    cinema = Cinema(
                        cinema.id,
                        cinema.name,
                        cinema.provider,
                        cinema.logoUrl,
                        cinema.latitude,
                        cinema.longitude,
                        cinema.address,
                        cinema.postcode,
                        cinema.county,
                        cinema.photos,
                        rating,
                        horario,
                    ),
                    data = it.data,
                    observacoes = it.observacoes,
                    rating = it.rating,
                    photos = photos
                )
            }

            onFinished(Result.success(registosFilme))
        }
    }

    override fun getAllAtores(onFinished: (Result<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val atores = filmeDao.getAllAtores()
            onFinished(Result.success(atores))
        }
    }

    override fun getFilmesComAtor(ator: String, onFinished: (Result<List<Filme>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val filme = filmeDao.getFilmesComAtor(ator)
            val filmes = filme.map {
                Filme(
                    it.nome,
                    byteArrayToBitmap(it.cartaz),
                    it.genero,
                    it.sinopse,
                    it.atores,
                    it.dataLancamento,
                    it.avaliacaoIMDB,
                    it.votosIMDB,
                    it.linkIMDB
                )
            }
            onFinished(Result.success(filmes))
        }
    }

    override fun hasFilmesComAtor(ator: String, onFinished: (Result<Boolean>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val bool = filmeDao.hasFilmesComAtor(ator)
            onFinished(Result.success(bool))
        }
    }

    override fun getFilmesComMaisVotos(onFinished: (Result<List<Filme>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            val filme = filmeDao.getFilmesComMaisVotos()
            val filmes = filme.map {
                Filme(
                    it.nome,
                    byteArrayToBitmap(it.cartaz),
                    it.genero,
                    it.sinopse,
                    it.atores,
                    it.dataLancamento,
                    it.avaliacaoIMDB,
                    it.votosIMDB,
                    it.linkIMDB
                )
            }
            onFinished(Result.success(filmes))
        }
    }
    override fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val photos = filme.photos.map{
                    bitmap ->  bitmapToByteArray(bitmap)
            }
            val registofilme = RegistoFilmeDB(
                                    registoFilmeId = filme.uuid,
                                    filmeId = filme.filme.uuid,
                                    cinemaId = filme.cinema.id,
                                    data = filme.data,
                                    observacoes = filme.observacoes,
                                    rating = filme.rating,
                                    photos = photos
                                )
            registoFilmeDao.insert(registofilme)
            val filmeinserir = FilmeDB(
                uuid = filme.filme.uuid,
                nome = filme.filme.nome,
                cartaz = bitmapToByteArray(filme.filme.cartaz),
                genero = filme.filme.genero,
                sinopse = filme.filme.sinopse,
                atores = filme.filme.atores,
                dataLancamento = filme.filme.dataLancamento,
                avaliacaoIMDB = filme.filme.avaliacaoIMDB,
                votosIMDB = filme.filme.votosIMBD,
                linkIMDB = filme.filme.linkIMDB,
            )
            filmeDao.insert(filmeinserir)
            Log.i("APP", "Inserido ${registofilme.filmeId} no banco de dados")
            Log.i("Aoo","Vamos ver ${filmeDao.getAll()}")
            Log.i("aoo", "vavo UMA CENA EM CAPS LOCK ISSO DEPOIS NAO SE REPARA ${registoFilmeDao.getAll()}")
            onFinished()
        }
    }
}