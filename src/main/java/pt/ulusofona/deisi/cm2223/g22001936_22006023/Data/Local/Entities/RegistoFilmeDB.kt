package pt.ulusofona.deisi.cm2223.g22001936_22006023.Data.Local.Entities
import android.graphics.Bitmap
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


@Entity(tableName = "registoFilme")
@TypeConverters(BitmapListConverter::class)
data class RegistoFilmeDB(
    @PrimaryKey val registoFilmeId: String,
    @ColumnInfo(name = "filmeId") val filmeId: String,
    @ColumnInfo(name = "cinemaId") val cinemaId: Int,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "photos") val photos : List<Bitmap>,
    @ColumnInfo(name = "observacoes") val observacoes : String)


class BitmapListConverter {
    /* @TypeConverter
    fun fromStringToBitmap(bitstring: String): Bitmap?{
        return if(bitstring == ""){
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }else {
            try {
                val encodeByte: ByteArray = Base64.decode(bitstring, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                bitmap
            } catch (e: Exception) {
                e.message
                null
            }
        }

    }
    @TypeConverter
    fun fromBitMapToString(bitmap: Bitmap?): String{
        if(bitmap == null){
            return ""
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val temp = Base64.encodeToString(b, Base64.DEFAULT)

        return if(temp==null) {
            ""
        } else {
            temp
        }
    }*/

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): String {
        val gson = Gson()
        return gson.toJson(bitmap)
    }

    @TypeConverter
    fun toBitmap(bitmapString: String):Bitmap? {
        val gson = Gson()
        val type = object : TypeToken<Bitmap?>() {}.type
        return gson.fromJson(bitmapString, type)
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, type)
    }
    @TypeConverter
    fun fromBitmapList(bitmapList: List<Bitmap>): String {
        val gson = Gson()
        return gson.toJson(bitmapList)
    }

    @TypeConverter
    fun toBitmapList(bitmapListString: String): List<Bitmap> {
        val gson = Gson()
        val type = object : TypeToken<List<Bitmap>>() {}.type
        return gson.fromJson(bitmapListString, type)
    }
   /* @TypeConverter
    fun fromBitmapToList(bitmapList: List<Bitmap>): List<String> {
        val bitmapString = mutableListOf<String>()
        bitmapList.map{bitmap ->
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            val temp = Base64.encodeToString(b, Base64.DEFAULT)

            if(temp==null)
            {
                bitmapString.add("")
            }
            else {
                bitmapString.add(temp)
            }

        }
        return bitmapString
    }
    @TypeConverter
    fun fromListToBitmap(bitmapListString: List<String>): List<Bitmap> {
        val bitmapList = mutableListOf<Bitmap>()
        bitmapListString.map{ bitstring->
            if(bitstring == ""){
                bitmapList.add(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
            }else {
                try {
                    val encodeByte: ByteArray = Base64.decode(bitstring, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                    bitmapList.add(bitmap)
                } catch (e: Exception) {
                    e.message
                    null
                }
            }
        }
        return bitmapList
    }*/
}