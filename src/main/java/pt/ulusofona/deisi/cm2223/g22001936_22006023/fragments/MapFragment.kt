package pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.g22001936_22006023.data.CineRepository
import pt.ulusofona.deisi.cm2223.g22001936_22006023.location.CineViewLocation
import pt.ulusofona.deisi.cm2223.g22001936_22006023.location.OnLocationChangedListener
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.RegistoFilme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.NavigationManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.R
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentMapBinding
import java.util.*

class MapFragment : Fragment(), OnLocationChangedListener {

    private lateinit var binding: FragmentMapBinding
    // Este objeto será utilizado para obter o nome da localidade
    private lateinit var geocoder: Geocoder
    // Esta variável irá guardar uma referência para o mapa
    private var map: GoogleMap? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        binding = FragmentMapBinding.bind(view)
        geocoder = Geocoder(context, Locale.getDefault())
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { map ->
            this.map = map
            map.isMyLocationEnabled = true
            CineViewLocation.registerListener(this)


            val targetLocation = LatLng(38.7223, -9.1793)
            val zoomLevel = 11f

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLocation, zoomLevel))

            CineRepository.getInstance().getFilmesRegistados { result ->

                if (result.isSuccess) {
                    var registos = result.getOrDefault(mutableListOf())
                    registos.forEach {
                        val icon = getMarkerIconByRating(it.rating)
                        val markerIcon = context?.let { getBitmapFromVector(it, icon) }
                        val markerBitmapDescriptor = markerIcon?.let {
                            BitmapDescriptorFactory.fromBitmap(
                                it
                            )
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            val markerOptions = MarkerOptions()
                                .position(LatLng(it.cinema.latitude.toDouble(), it.cinema.longitude.toDouble()))
                                .title(it.filme.nome + "  " + it.rating + "★")
                                .snippet("Cinema: " + it.cinema.name)
                                .icon(markerBitmapDescriptor)

                            var marker = map.addMarker(markerOptions)
                            marker?.tag = it
                            // Define o listener de clique no marcador
                            map.setOnInfoWindowClickListener { clickedMarker ->
                                val clickedRegisto = clickedMarker.tag as? RegistoFilme
                                if (clickedRegisto != null) {
                                    Log.i("APP", "u here?")
                                    NavigationManager.goToDetalhesFragment(parentFragmentManager, clickedRegisto.uuid)
                                }
                                // Retorna false para permitir que o comportamento padrão (info window) seja executado
                                false
                            }
                        }

                    }
                }
            }

        }

        binding.fab.setOnClickListener { view ->
            NavigationManager.goToFilmesFragment(parentFragmentManager)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    // Este método será invocado sempre que a posição alterar
    override fun onLocationChanged(latitude: Double, longitude: Double) {
        placeCamera(latitude, longitude)
    }

    // Atualiza e faz zoom no mapa de acordo com a localização
    private fun placeCamera(latitude: Double, longitude: Double) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(12f)
            .build()

        map?.animateCamera(
            CameraUpdateFactory.newCameraPosition(cameraPosition))
    }


    override fun onDestroy() {
        super.onDestroy()
        CineViewLocation.unregisterListener()
    }

    private fun getMarkerIconByRating(rating: Int): Int {
        return when (rating) {
            in 1..2 -> R.drawable.marker_muitofraco
            in 3..4 -> R.drawable.marker_fraco
            in 5..6 -> R.drawable.marker_medio
            in 7..8 -> R.drawable.marker_bom
            in 9..10 -> R.drawable.marker_excelente
            else -> R.drawable.marker_good
        }
    }
    private fun getBitmapFromVector(context: Context, vectorResId: Int): Bitmap? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth * 2,
            vectorDrawable.intrinsicHeight * 2,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

}