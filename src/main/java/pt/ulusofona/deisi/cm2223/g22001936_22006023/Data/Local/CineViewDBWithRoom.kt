package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.CinemaDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.FilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities.RegistoFilmeDB
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.RegistoFilmeDao
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.RegistoFilme

class CineViewDBWithRoom(private val registoFilmeDao: RegistoFilmeDao, private val filmeDao: FilmeDao, private val cinemaDao: CinemaDao) : CineView() {

    override fun insertFilmesRegistados(filmes: List<RegistoFilme>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
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
                                cinema.ratings,
                                cinema.hours,
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

    override fun clearAllFilmesRegistados(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            registoFilmeDao.deleteAll()
            onFinished()
        }
    }

    override fun searchMovie(title: String, context: Context, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Operação não permitida")
    }
}