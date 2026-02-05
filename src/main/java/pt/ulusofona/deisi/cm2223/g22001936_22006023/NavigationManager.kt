package pt.ulusofona.deisi.cm2223.g22001936_22006023

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pt.ulusofona.deisi.cm2223.g22001936_22006023.fragments.*

object NavigationManager {

    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToRegistarFilmeFragment(fm: FragmentManager) {
        placeFragment(fm, RegistarFilmeFragment())
    }

    fun goToFilmesFragment(fm: FragmentManager) {
        placeFragment(fm, FilmesFragment())
    }


    fun goToDetalhesFragment(fm: FragmentManager, uuid: String) {
        placeFragment(fm, DetalhesFragment.newInstance(uuid))
    }

    fun goToHomeFragment(fm: FragmentManager) {
        placeFragment(fm, HomeFragment())
    }

    fun goToExtraFragment(fm: FragmentManager) {
        placeFragment(fm, ExtraFragment())
    }

    fun goToMapFragment(fm:FragmentManager){
        placeFragment(fm, MapFragment())
    }
}