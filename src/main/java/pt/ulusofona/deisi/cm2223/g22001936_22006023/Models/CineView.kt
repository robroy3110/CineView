package pt.ulusofona.deisi.cm2223.g22001936_22006023.Models

import android.content.Context

abstract class CineView {
    abstract fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit)
    abstract fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit)
    abstract fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit)
    abstract fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit)
    abstract fun insertFilmesRegistados(filmes: List<RegistoFilme>,onFinished: () -> Unit)

    abstract fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit)
    abstract fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit)
}