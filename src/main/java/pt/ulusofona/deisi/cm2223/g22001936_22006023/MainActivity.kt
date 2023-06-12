package pt.ulusofona.deisi.cm2223.g22001936_22006023

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.ActivityMainBinding
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Horario
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Rating
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var handler: Handler? = null
    private var countdown = 10
    private var speechRecognitionLauncher: ActivityResultLauncher<Intent>? = null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 1
    private val REQUEST_LOCATION_PERMISSION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationManager.goToHomeFragment(supportFragmentManager)

        handler = Handler(Looper.getMainLooper());

        // Exibir o AlertDialog quando necessário


        val jsonContent = this.assets.open("cinemas.json").bufferedReader().use {
            it.readText()
        }

        var cinemasList: MutableList<Cinema> = mutableListOf()
        val cinemasJson = JSONObject(jsonContent)
        val cinemas = cinemasJson.getJSONArray("cinemas") as JSONArray
        for (i in 0 until cinemas.length()){
            val cinema = cinemas.getJSONObject(i)
            var horarioList: MutableList<Horario> = mutableListOf()
            var ratingList : MutableList<Rating> = mutableListOf()
            var photoList : MutableList<String> = mutableListOf()
            var photos : JSONArray
            var ratings : JSONArray
            val cinemaid = cinema.getInt("cinema_id")
            val cinemaname = cinema.getString("cinema_name")
            val cinemaprovider = cinema.getString("cinema_provider")
            var logoUrl  = ""
            if(cinema.has("logo_url")){
                logoUrl  = cinema.getString("logo_url")
            }
            val latitude = cinema.getDouble("latitude").toFloat()
            val longitude = cinema.getDouble("longitude").toFloat()
            val address = cinema.getString("address")
            val postcode = cinema.getString("postcode")
            val county = cinema.getString("county")
            if(cinema.has("photos")){
                photos = cinema.getJSONArray("photos") as JSONArray
                for (j in 0 until photos.length()) {
                    photoList.add(photos.get(j).toString())
                }
            }
            if(cinema.has("ratings")){
                ratings = cinema.getJSONArray("ratings") as JSONArray
                for(j in 0 until ratings.length()){
                    ratingList.add(Rating(ratings.getJSONObject(j).getString("category"),ratings.getJSONObject(j).getInt("score")))
                }
            }
            if (cinema.has("opening_hours")) {
                val openingHours = cinema.getJSONObject("opening_hours")
                val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

                for (index in 0 until daysOfWeek.size) {
                    val day = daysOfWeek[index]

                    if (openingHours.has(day)) {
                        val dayObject = openingHours.getJSONObject(day)
                        val openTime = dayObject.getString("open")
                        val closeTime = dayObject.getString("close")
                        horarioList.add(Horario(day, openTime, closeTime))
                    }
                }
            }
            cinemasList.add(Cinema(cinemaid,cinemaname,cinemaprovider, logoUrl,latitude,longitude,address,postcode,county,photoList,ratingList,horarioList))
        }

        CineRepository.getInstance().insertAllCinemas(cinemasList)


        speechRecognitionLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                // Obter os resultados do reconhecimento de voz
                var results = result.getData()!!.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                );

                if (results != null && !results.isEmpty()) {
                    // Obter o texto reconhecido
                    var spokenText = results.get(0);

                    Log.i("APP SPOKE WOKE", "$spokenText")

                    var builder = AlertDialog.Builder(this);
                    builder.setTitle("Este é o filme que procuras?");

                    // Definir o conteúdo do AlertDialog como um contador regressivo
                    builder.setMessage(spokenText);

                    builder.setCancelable(false); // Impedir que o usuário feche o diálogo

                    builder.setNegativeButton("Sim", DialogInterface.OnClickListener {dialog, id ->
                        CineRepository.getInstance().hasFilme(spokenText){result->
                            if(result.isSuccess){
                                if(result.getOrDefault(false)){
                                    CineRepository.getInstance().getFilmeIdByName(spokenText){resultFilmeId->
                                        if(resultFilmeId.isSuccess){
                                            CineRepository.getInstance().getRegistoIdByFilmeId(resultFilmeId.getOrDefault("")){resultRegistoId->
                                                if(resultRegistoId.isSuccess){
                                                    NavigationManager.goToDetalhesFragment(supportFragmentManager,resultRegistoId.getOrDefault(""))
                                                }else{
                                                    Toast.makeText(this, result.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }else{
                                            Toast.makeText(this, result.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }else{
                                    var builder2 = AlertDialog.Builder(this);
                                    builder2.setTitle("Não existem registo com esse filme!");

                                    // Definir o conteúdo do AlertDialog como um contador regressivo
                                    builder2.setMessage("Pretende criar um ou tentar novamente?");

                                    builder2.setCancelable(false); // Impedir que o usuário feche o diálogo

                                    builder2.setNegativeButton("Criar", DialogInterface.OnClickListener {dialog, id ->
                                        NavigationManager.goToRegistarFilmeFragment(supportFragmentManager)
                                    })

                                    builder2.setNeutralButton("Cancel"){dialog, id->
                                        dialog.dismiss()
                                    }

                                    builder2.setPositiveButton("Try Again", DialogInterface.OnClickListener {dialog, id ->
                                        startSpeechRecognition()
                                    })

                                    CoroutineScope(Dispatchers.Main).launch {
                                        var alertDialog = builder2.create();

                                        alertDialog.show()
                                    }

                                }
                            }else{
                                Toast.makeText(this, result.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })

                    builder.setPositiveButton("Try Again", DialogInterface.OnClickListener {dialog, id ->
                        startSpeechRecognition()
                    })
                    builder.setNeutralButton("Cancel"){dialog, id->
                        dialog.dismiss()
                    }

                    var alertDialog = builder.create();


                    alertDialog.show()

                    // Pesquisar o filme pelo nome

                }
            }
        };

        // Exibir o AlertDialog quando necessário
    }


    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "CineView"
        setupDrawerMenu()

        binding.ivMic.setOnClickListener {
            exibirAlertDialog()
        }
    }



    fun exibirAlertDialog() {
        countdown=10
        var builder = AlertDialog.Builder(this);
        builder.setTitle("Pesquisar por filmes com microfone");

        // Definir o conteúdo do AlertDialog como um contador regressivo
        builder.setMessage(Integer.toString(countdown));

        builder.setCancelable(false); // Impedir que o usuário feche o diálogo

        builder.setNegativeButton("Fechar", DialogInterface.OnClickListener {dialog, id ->
            dialog.dismiss()
        })

        builder.setPositiveButton("Microfone", DialogInterface.OnClickListener {dialog, id ->

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // Se a permissão não estiver concedida, solicite-a ao usuário
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
            } else {
                // Se a permissão já estiver concedida, inicie o reconhecimento de voz
                startSpeechRecognition()
            }
        })

        var alertDialog = builder.create();

        alertDialog.setOnShowListener { dialog ->

            startCountdown(alertDialog)


        }

        alertDialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, inicie o reconhecimento de voz
                startSpeechRecognition()
            } else {
                // Permissão negada, informe ao usuário ou tome uma ação apropriada
            }
        }

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, execute a lógica para acessar a localização do usuário
                NavigationManager.goToMapFragment(supportFragmentManager)
            } else {
                // Permissão negada, informe ao usuário ou tome uma ação apropriada
            }
        }

    }

    fun startCountdown(alertDialog :AlertDialog) {
        handler?.postDelayed(Runnable() {
            countdown--

            // Atualizar a mensagem do AlertDialog com o novo valor do contador
            alertDialog.setMessage(Integer.toString(countdown))

            if (countdown > 0) {
                // Continuar o contador regressivo
                startCountdown(alertDialog)
            } else {
                // Contagem regressiva concluída, fechar o AlertDialog
                alertDialog.dismiss()
            }
        },1000)
    }

    private fun startSpeechRecognition() {
        // Criar uma Intent para iniciar o reconhecimento de voz
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale o nome do filme...")

        // Iniciar a atividade de reconhecimento de voz
        speechRecognitionLauncher!!.launch(intent)
    }





    private fun setupDrawerMenu() {

        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener{
            /*for (i in 0 until binding.navDrawer.menu.size()) {
                binding.navDrawer.menu.getItem(i).isChecked = false
            }
            menuInflater.inflate(R.menu.drawer_menu,binding.navDrawer.menu)
            for (i in 0 until binding.navDrawer.menu.size()) {
                val menuItem = binding.navDrawer.menu.getItem(i)
                menuItem.actionView = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
            }
            // Marque a opção selecionada com uma cor de fundo diferente
            it.isChecked = true
            it.actionView.setBackgroundResource(R.color.purple_toolbar)*/
            onClickNavigationItem(it)

        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

    }
    private fun onClickNavigationItem(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_home ->

                NavigationManager.goToHomeFragment(
                    supportFragmentManager
                )
            R.id.nav_registo ->
                NavigationManager.goToRegistarFilmeFragment(
                    supportFragmentManager
                )
            R.id.nav_filmes_vistos ->
                NavigationManager.goToFilmesFragment(
                    supportFragmentManager
                )
            R.id.nav_extra ->
                NavigationManager.goToExtraFragment(
                    supportFragmentManager
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}