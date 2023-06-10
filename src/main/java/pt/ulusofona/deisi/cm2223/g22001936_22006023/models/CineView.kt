package pt.ulusofona.deisi.cm2223.g22001936_22006023.models

abstract class CineView {
    abstract fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit)
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
}