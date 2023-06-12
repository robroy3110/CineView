package pt.ulusofona.deisi.cm2223.g22001936_22006023.data.remote
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.CineView
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.RegistoFilme
import java.io.IOException

class CineViewOkhttp(
    private val apiKey: String = "f7580d7a",
    private val client: OkHttpClient
) : CineView() {

    override fun searchMovie(title: String, onFinished: (Result<Filme>) -> Unit) {
        val url = "http://www.omdbapi.com/?apikey=$apiKey&t=$title"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFinished(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onFinished(Result.failure(IOException("Error: $response")))
                } else {
                    val body = response.body?.string()
                    if (body != null) {
                        val jsonResponse = JSONObject(body)
                        print(jsonResponse.toString())
                        if (jsonResponse.has("Error")) {
                            val errorMessage = jsonResponse.getString("Error")
                            onFinished(Result.failure(IOException(errorMessage)))
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                            val movie = parseMovieFromJson(jsonResponse)
                            onFinished(Result.success(movie))
                            }
                        }
                    }
                }
            }
        })
    }


    override fun insertAllCinemas(cinemas: List<Cinema>) {
        throw Exception("Operação não permitida")
    }

    override fun insertFilmesRegistados(filmes: List<RegistoFilme>, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }
    override fun getFilmeRegistadoById(id:String,onFinished: (Result<RegistoFilme>) -> Unit){
        throw Exception("Operação não permitida")
    }
    override fun insertFilmeRegistado(filme: RegistoFilme, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun clearFilmeRegistadoById(id: String, onFinished: () -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun atualizarFilmeDoRegisto(registoFilmeId: String, novoFilmeId: String) {
        throw Exception("Operação não permitida")
    }

    override fun existsCinema(nome: String, onFinished: (Result<Boolean>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getCinema(nome: String, onFinished: (Result<Cinema>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getCinemasMaisProximos(context: Context, chars: String, onFinished: (Result<List<Cinema>>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getFilmesRegistados(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getUltimosRegistos(onFinished: (Result<List<RegistoFilme>>) -> Unit) {
        throw Exception("Operação não permitida")
    }
    override fun getFilmesComMaisVotos(onFinished: (Result<List<Filme>>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getAllAtores(onFinished: (Result<String>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun getFilmesComAtor(ator: String, onFinished: (Result<List<Filme>>) -> Unit) {
        throw Exception("Operação não permitida")
    }

    override fun hasFilmesComAtor(ator: String, onFinished: (Result<Boolean>) -> Unit) {
        throw Exception("Operação não permitida")
    }
    private fun downloadImage(imageUrl: String, onImageDownloaded: (Bitmap?) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            Picasso.get()
                .load(imageUrl)
                .into(object : com.squareup.picasso.Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        onImageDownloaded(bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        onImageDownloaded(null)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                })
        }
    }

    private suspend fun parseMovieFromJson(json: JSONObject): Filme {
        val nome = json.getString("Title")
        val cartazUrl = json.getString("Poster")
        val genero = json.getString("Genre")
        val sinopse = json.getString("Plot")
        val atores = json.getString("Actors")
        val dataLancamento = json.getString("Released")
        val avaliacaoIMDB: Double = if(json.getString("imdbRating") != "N/A") {
            json.getString("imdbRating").toDouble()
        } else {
            0.0
        }
        val votosIMDB: Int = if(json.getString("imdbVotes") != "N/A"){
            json.getString("imdbVotes").replace(",", "").toInt()
        } else {
            0
        }
        val linkIMDB: String = if(json.getString("imdbID") != "N/A"){
            "https://www.imdb.com/title/" + json.getString("imdbID")
        } else {
            "Este filme não existe no IMDB"
        }

        val imageUrl = cartazUrl
        var filme = Filme("",Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),"","","","",0.0,0,"")
        var finished = false
        downloadImage(imageUrl) { bitmap ->
            filme = if (bitmap != null) {
                Filme(nome, bitmap, genero, sinopse, atores, dataLancamento, avaliacaoIMDB, votosIMDB, linkIMDB)
            } else {
                Filme(nome, null, genero, sinopse, atores, dataLancamento, avaliacaoIMDB, votosIMDB, linkIMDB)
            }
            finished = true
        }

        while (!finished){
            delay(100)
        }


        return filme
    }






}

