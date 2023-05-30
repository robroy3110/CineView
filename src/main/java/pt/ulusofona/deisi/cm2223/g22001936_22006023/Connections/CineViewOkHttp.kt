package pt.ulusofona.deisi.cm2223.g22001936_22006023.Connections
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CineViewOkhttp(
    private val apiKey: String,
    private val client: OkHttpClient
) {

    fun searchMovie(title: String, context: Context, onFinished: (Result<Filme>) -> Unit) {
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
                            val movie = parseMovieFromJson(jsonResponse, context)
                            onFinished(Result.success(movie))
                        }
                    }
                }
            }
        })
    }

    private fun saveImageToDrawable(context: Context, drawable: Drawable, imageName: String) {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val directory = "app/src/main/res/drawable/app/src/main/res/drawable-v24/"

        val file = File(directory, "$imageName.jpg")
        val fileOutputStream: FileOutputStream? = FileOutputStream(file)

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream?.flush()
        } catch (e: Exception) {
            Log.e("DownloadImage", "Error saving image: ${e.message}")
        } finally {
            fileOutputStream?.close()
        }
    }


    private fun downloadImage(context: Context, imageUrl: String, imageName: String) {
        Picasso.get()
            .load(imageUrl)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    if (bitmap != null) {
                        val drawable = BitmapDrawable(context.resources, bitmap)
                        saveImageToDrawable(context, drawable, imageName)
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }

    private fun parseMovieFromJson(json: JSONObject, context: Context): Filme {
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

        val imageName = "cartaz_${nome.replace(" ","_")}"
        val imageUrl = cartazUrl

        downloadImage(context, imageUrl, imageName)

        return Filme(nome, imageName, genero, sinopse, atores, dataLancamento, avaliacaoIMDB, votosIMDB, linkIMDB)
    }






}

