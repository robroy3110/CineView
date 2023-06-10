package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.local
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                for(rating in it.cinema.ratings){
                    ratingDao.insert(
                        RatingDB(
                            ratingId = it.cinema.id,
                            category = rating.category,
                            score = rating.score
                        )
                    )
                }
                for(horario in it.cinema.hours){
                    horarioDao.insert(
                        HorarioDB(
                            horarioId = it.cinema.id,
                            day = horario.dia,
                            openHour = horario.openHour,
                            closeHour = horario.closeHour
                        )
                    )
                }
                cinemaDao.insert(CinemaDB(
                    id = it.cinema.id,
                    name = it.cinema.name,
                    provider = it.cinema.provider,
                    logoUrl = it.cinema.logoUrl,
                    latitude = it.cinema.latitude,
                    longitude = it.cinema.longitude,
                    address = it.cinema.address,
                    postcode = it.cinema.postcode,
                    county = it.cinema.county,
                    photos = it.cinema.photos,
                    ratingsId = it.cinema.id,
                    horarioId = it.cinema.id,
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
        TODO("Not yet implemented")
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
            for(rating in filme.cinema.ratings){
                ratingDao.insert(
                    RatingDB(
                        ratingId = filme.cinema.id,
                        category = rating.category,
                        score = rating.score
                    )
                )
            }
            for(horario in filme.cinema.hours){
                horarioDao.insert(
                    HorarioDB(
                        horarioId = filme.cinema.id,
                        day = horario.dia,
                        openHour = horario.openHour,
                        closeHour = horario.closeHour
                    )
                )
            }
            cinemaDao.insert(CinemaDB(
                id = filme.cinema.id,
                name = filme.cinema.name,
                provider = filme.cinema.provider,
                logoUrl = filme.cinema.logoUrl,
                latitude = filme.cinema.latitude,
                longitude = filme.cinema.longitude,
                address = filme.cinema.address,
                postcode = filme.cinema.postcode,
                county = filme.cinema.county,
                photos = filme.cinema.photos,
                ratingsId = filme.cinema.id,
                horarioId = filme.cinema.id
            ))
            Log.i("APP", "Inserido ${registofilme.filmeId} no banco de dados")
            Log.i("Aoo","Vamos ver ${filmeDao.getAll()}")
            Log.i("aoo", "vavo UMA CENA EM CAPS LOCK ISSO DEPOIS NAO SE REPARA ${registoFilmeDao.getAll()}")
            onFinished()
        }
    }
}