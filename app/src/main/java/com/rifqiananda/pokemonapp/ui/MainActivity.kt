package com.rifqiananda.pokemonapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rifqiananda.pokemonapp.R
import com.rifqiananda.pokemonapp.databinding.ActivityMainBinding
import com.rifqiananda.pokemonapp.ui.fragments.FragmentDetailPokemon
import com.rifqiananda.pokemonapp.util.Communicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pokemon.*

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            bottomNavigation.setupWithNavController(fragment!!.findNavController())

            bottomNavigation.itemIconTintList = null

        }
    }

    override fun passDataCom(pokemonName: String, pokemonImage:String) {
        val bundle = Bundle()
        bundle.putString("name", pokemonName)
        bundle.putString("image", pokemonImage)

        val transaction = this.supportFragmentManager.beginTransaction()
        val detailFrag = FragmentDetailPokemon()
        detailFrag.arguments = bundle

        transaction.replace(R.id.fragment_container, detailFrag)

        transaction.addToBackStack(null)
        transaction.commit()
    }

}