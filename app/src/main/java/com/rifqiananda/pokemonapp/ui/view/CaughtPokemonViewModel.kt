package com.rifqiananda.pokemonapp.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rifqiananda.pokemonapp.data.local.PokemonDAO
import com.rifqiananda.pokemonapp.data.local.PokemonDatabase
import com.rifqiananda.pokemonapp.data.local.PokemonUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CaughtPokemonViewModel(application: Application): AndroidViewModel(application) {

    private var pokemonDAO: PokemonDAO?
    private var pokemonDB: PokemonDatabase?

    init {
        pokemonDB = PokemonDatabase.getDatabase(application)
        pokemonDAO = pokemonDB?.pokemonDAO()
    }

    fun getPokemon(): LiveData<List<PokemonUser>>?{
        return pokemonDAO?.getPokemon()
    }

    fun releasePokemon(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            pokemonDAO?.releasePokemon(id)
        }
    }
}