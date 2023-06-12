package pt.ulusofona.deisi.cm2223.g22001936_22006023.models

import android.content.Context

abstract class CineView {
    abstract fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit)
    abstract fun insertAllCinemas(cinemas: List<Cinema>)
    abstract fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit)
    abstract fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit)
    abstract fun getAllAtores(onFinished: (Result<String>) -> Unit)
    abstract fun getFilmesComAtor(ator: String, onFinished: (Result<List<Filme>>) -> Unit)
    abstract fun hasFilmesComAtor(ator: String, onFinished: (Result<Boolean>) -> Unit)
    abstract fun getFilmesComMaisVotos(onFinished: (Result<List<Filme>>) -> Unit)
    abstract fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit)
    abstract fun insertFilmesRegistados(filmes: List<RegistoFilme>,onFinished: () -> Unit)
    abstract fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit)
    abstract fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit)
    abstract fun atualizarFilmeDoRegisto(registoFilmeId: String, novoFilmeId: String)
    abstract fun existsCinema(nome: String, onFinished: (Result<Boolean>) -> Unit)
    abstract fun getCinema(nome: String, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit)
    abstract fun getCinemasMaisProximos(context: Context, chars: String, onFinished: (Result<List<Cinema>>) -> Unit)


}