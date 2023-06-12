package pt.ulusofona.deisi.cm2223.g22001936_22006023

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.ActivityMainBinding
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Horario
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Rating


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var handler: Handler? = null
    private var countdown = 10
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
        var builder = AlertDialog.Builder(this);
        builder.setTitle("Pesquisar por filmes com microfone");

        // Definir o conteúdo do AlertDialog como um contador regressivo
        builder.setMessage(Integer.toString(countdown));

        builder.setCancelable(false); // Impedir que o usuário feche o diálogo

        builder.setPositiveButton("Fechar", DialogInterface.OnClickListener {dialog, id ->
            dialog.dismiss()
        })

        var alertDialog = builder.create();

        alertDialog.setOnShowListener { dialog ->

                startCountdown(alertDialog);

        }

        alertDialog.show()
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

    fun onClick(context : Context) {
        var done = false
        var builder: AlertDialog.Builder? = context.let {
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setPositiveButton("Done",
                    DialogInterface.OnClickListener { dialog, id ->
                        done = true
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        done = true
                    })
            }
        }
        builder?.setMessage("10")?.setTitle("Pesquisar por filme com microfone!")
        var dialog: AlertDialog?  = builder?.create()
        object : CountDownTimer(10500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(!done) {
                    builder?.setMessage((millisUntilFinished / 1000).toString())
                        ?.setTitle("Pesquisar por filme com microfone!")
                    dialog?.dismiss()
                    dialog = builder?.create()
                    dialog?.show()
                }
            }

            override fun onFinish() {
                dialog?.dismiss()
            }
        }.start()
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