package pt.ulusofona.deisi.cm2223.g22001936_22006023.data

import android.content.Context
import android.util.Log
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.remote.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.RegistoFilme
import java.lang.IllegalStateException

class CineRepository private constructor(
    private val local: CineView,
    private val remote: CineView,
    private val context: Context
) : CineView() {

    override fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            Log.i("APP", "O aplicativo está online. Obtendo filmes do servidor...")
            local.getFilmesRegistados { result ->
                if (result.isSuccess) {
                    val filmes = result.getOrDefault(mutableListOf())
                    val registosFilme = filmes.map {
                        remote.searchMovie(it.filme.nome){ result ->
                            if(result.isSuccess){
                                var filme = result.getOrNull()
                                if(filme != null) {
                                    local.atualizarFilmeDoRegisto(it.filme.uuid, filme.uuid)
                                }
                            }
                        }
                        RegistoFilme(
                            uuid = it.uuid,
                            filme = Filme(
                                it.filme.nome,
                                it.filme.cartaz,
                                it.filme.genero,
                                it.filme.sinopse,
                                it.filme.atores,
                                it.filme.dataLancamento,
                                it.filme.avaliacaoIMDB,
                                it.filme.votosIMBD,
                                it.filme.linkIMDB
                            ),
                            cinema = Cinema(
                                it.cinema.id,
                                it.cinema.name,
                                it.cinema.provider,
                                it.cinema.logoUrl,
                                it.cinema.latitude,
                                it.cinema.longitude,
                                it.cinema.address,
                                it.cinema.postcode,
                                it.cinema.county,
                                it.cinema.photos,
                                it.cinema.ratings,
                                it.cinema.hours,
                            ),
                            data = it.data,
                            observacoes = it.observacoes,
                            rating = it.rating,
                            photos = it.photos
                        )
                    }
                    Log.i("APP", "Obtidos ${registosFilme.size} filmes do servidor")
                    onFinished(Result.success(registosFilme))
                } else {
                    Log.w("APP", "Erro ao obter filmes do servidor...")
                    onFinished(result)  // propagar a falha remota
                }
            }
        } else {
            Log.i("APP", "O aplicativo está offline. Obtendo filmes do banco de dados...")
            local.getFilmesRegistados(onFinished)
        }
    }

    override fun insertFilmesRegistados(filmes: List<RegistoFilme>, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit) {
        local.insertFilmeRegistado(filme) {
            onFinished()
        }
    }
    override fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun atualizarFilmeDoRegisto(registoFilmeId: String, novoFilmeId: String) {
        local.atualizarFilmeDoRegisto(registoFilmeId, novoFilmeId)
    }

    override fun existsCinema(nome: String, onFinished: (Result<Boolean>) -> Unit) {
        local.existsCinema(nome, onFinished)
    }

    override fun getCinema(nome: String, onFinished: (Result<Cinema>) -> Unit) {
        local.getCinema(nome, onFinished)
    }

    override fun getCinemaIdContainingString(searchString: String, onFinished: (Result<Int>) -> Unit) {
        local.getCinemaIdContainingString(searchString, onFinished)
    }

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        local.getAllCinemas(onFinished)
    }

    override fun getCinemasMaisProximos(context: Context, chars: String, onFinished: (Result<List<Cinema>>) -> Unit) {
        local.getCinemasMaisProximos(context, chars, onFinished)
    }

    override fun hasFilme(nomeFilme: String, onFinished: (Result<Boolean>) -> Unit) {
        local.hasFilme(nomeFilme,onFinished)
    }

    override fun getFilmeIdByName(nomeFilme: String, onFinished: (Result<String>) -> Unit) {
        local.getFilmeIdByName(nomeFilme,onFinished)
    }

    override fun getRegistoIdByFilmeId(filmeId: String, onFinished: (Result<String>) -> Unit) {
        local.getRegistoIdByFilmeId(filmeId,onFinished)
    }

    override fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun insertAllCinemas(cinemas: List<Cinema>) {
        local.insertAllCinemas(cinemas)
    }

    override fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit){
        local.getFilmeRegistadoById(id,onFinished)
    }

    override fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        local.getUltimosRegistos(onFinished)
    }

    override fun getFilmesComMaisVotos(onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmesComMaisVotos(onFinished)
    }

    override fun getAllAtores(onFinished: (Result<String>) -> Unit) {
        local.getAllAtores(onFinished)
    }

    override fun getFilmesComAtor(ator: String, onFinished: (Result<List<Filme>>) -> Unit) {
        local.getFilmesComAtor(ator, onFinished)
    }

    override fun hasFilmesComAtor(ator: String, onFinished: (Result<Boolean>) -> Unit) {
        local.hasFilmesComAtor(ator, onFinished)
    }

    companion object {
        private var instance: CineRepository? = null

        fun init(local: CineView, remote: CineView, context: Context) {
            synchronized(this) {
                if (instance == null) {
                    instance = CineRepository(local, remote, context)
                }
            }
        }

        fun getInstance(): CineRepository {
            if (instance == null) {
                throw IllegalStateException("Singleton não foi inicializado")
            }
            return instance as CineRepository
        }
    }
}