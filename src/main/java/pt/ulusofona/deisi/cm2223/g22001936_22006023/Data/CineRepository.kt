package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data

import android.content.Context
import android.util.Log
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Remote.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.RegistoFilme
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
                    val filmes = result.getOrNull()!!
                    //filmes.map{
                    //    remote.searchMovie(it.filme.nome){ result ->
                    //        if(result.isSuccess){
                    //            it.filme = result.getOrNull()!!
                    //            local.clearFilmeRegistadoById(it.filme.uuid) {
                    //                Log.i("APP", "Banco de dados limpo")
                    //                local.insertFilmeRegistado(it) {}
                    //            }
                    //        }
                    //    }
//
                    //}
                    Log.i("APP", "Obtidos ${filmes.size} filmes do servidor")
                    onFinished(Result.success(filmes))
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
            Log.i("Aoo","Inseri o pau na panela ${filme.filme.uuid}")
            onFinished()
        }
    }

    override fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Operação não permitida")
    }
    override fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit){
        local.getFilmeRegistadoById(id,onFinished)
    }

    override fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        local.getUltimosRegistos(onFinished)
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