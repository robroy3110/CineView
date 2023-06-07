package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CinemaDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.FilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.BitmapListConverter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.CinemaDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.FilmeDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.RegistoFilmeDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.RegistoFilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.RegistoFilme

class CineViewDBWithRoom(private val registoFilmeDao: RegistoFilmeDao, private val filmeDao: FilmeDao, private val cinemaDao: CinemaDao) : CineView() {

    override fun insertFilmesRegistados(filmes: List<RegistoFilme>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            filmes.forEach {
                filmeDao.insert(FilmeDB(
                    uuid = it.filme.uuid,
                    nome = it.filme.nome,
                    cartaz = it.filme.cartaz,
                    genero = it.filme.genero,
                    sinopse = it.filme.sinopse,
                    atores = it.filme.atores,
                    dataLancamento = it.filme.dataLancamento,
                    avaliacaoIMDB = it.filme.avaliacaoIMDB,
                    votosIMDB = it.filme.votosIMBD,
                    linkIMDB = it.filme.linkIMDB,
                ))
                cinemaDao.insert(CinemaDB(
                    id = it.cinema.id,
                    name = it.cinema.name,
                    provider = it.cinema.provider,
                    latitude = it.cinema.latitude,
                    longitude = it.cinema.longitude,
                    address = it.cinema.address,
                    postcode = it.cinema.postcode,
                    county = it.cinema.county,
                    photos = it.cinema.photos,
                    ratings = "uns bons 20",
                    hours = "20:00 Wednesday",
                ))
            }
            filmes.map {
                RegistoFilmeDB(
                    registoFilmeId = it.uuid,
                    filmeId = it.filme.uuid,
                    cinemaId = it.cinema.id,
                    data = it.data,
                    observacoes = it.observacoes,
                    rating = it.rating,
                    photos = it.photos
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

    override fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val filmes = registoFilmeDao.getAll().map {
                var filme = filmeDao.getFromId(it.filmeId)
                var cinema = cinemaDao.getFromId(it.cinemaId)
                    RegistoFilme(
                        uuid = it.registoFilmeId,
                        filme = Filme(
                            filme.nome,
                            filme.cartaz,
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
                        photos = it.photos
                    )
                }

            onFinished(Result.success(filmes))
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
            var registoFilme = registoFilmeDao.getFromId(id)
            var filme = filmeDao.getFromId(registoFilme.filmeId)
            var cinema = cinemaDao.getFromId(registoFilme.cinemaId)
            var registo = RegistoFilme(
                uuid = registoFilme.registoFilmeId,
                filme = Filme(
                    filme.nome,
                    filme.cartaz,
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
                photos = registoFilme.photos
            )
            Log.i("APP", "Inserido ${registoFilme.filmeId} no banco de dados")
            onFinished(Result.success(registo))
        }
    }
    override fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            filmeDao.deleteAll()
            registoFilmeDao.deleteAll()
            cinemaDao.deleteAll()
            var registofilme = RegistoFilmeDB(
                                    registoFilmeId = filme.uuid,
                                    filmeId = filme.filme.uuid,
                                    cinemaId = filme.cinema.id,
                                    data = filme.data,
                                    observacoes = filme.observacoes,
                                    rating = filme.rating,
                                    photos = filme.photos
                                )
            registoFilmeDao.insert(registofilme)
            var filmeinserir = FilmeDB(
                uuid = filme.filme.uuid,
                nome = filme.filme.nome,
                cartaz = filme.filme.cartaz,
                genero = filme.filme.genero,
                sinopse = filme.filme.sinopse,
                atores = filme.filme.atores,
                dataLancamento = filme.filme.dataLancamento,
                avaliacaoIMDB = filme.filme.avaliacaoIMDB,
                votosIMDB = filme.filme.votosIMBD,
                linkIMDB = filme.filme.linkIMDB,
            )
            filmeDao.insert(filmeinserir)
            cinemaDao.insert(CinemaDB(
                id = filme.cinema.id,
                name = filme.cinema.name,
                provider = filme.cinema.provider,
                latitude = filme.cinema.latitude,
                longitude = filme.cinema.longitude,
                address = filme.cinema.address,
                postcode = filme.cinema.postcode,
                county = filme.cinema.county,
                photos = filme.cinema.photos,
                ratings = "uns bons 20",
                hours = "20:00 Wednesday",
            ))
            Log.i("APP", "Inserido ${registofilme.filmeId} no banco de dados")
            Log.i("Aoo","Vamos ver ${filmeDao.getAll()}")
            Log.i("aoo", "vavo UMA CENA EM CAPS LOCK ISSO DEPOIS NAO SE REPARA ${registoFilmeDao.getAll()}")
            onFinished()
        }
    }
}