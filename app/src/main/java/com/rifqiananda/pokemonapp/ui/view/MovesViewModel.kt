package com.rifqiananda.pokemonapp.ui.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqiananda.pokemonapp.api.RetrofitClient
import com.rifqiananda.pokemonapp.data.local.PokemonDAO
import com.rifqiananda.pokemonapp.data.local.PokemonDatabase
import com.rifqiananda.pokemonapp.data.local.PokemonUser
import com.rifqiananda.pokemonapp.data.model.Move
import com.rifqiananda.pokemonapp.data.model.Pokemon
import com.rifqiananda.pokemonapp.data.model.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovesViewModel(application: Application): AndroidViewModel(application) {

    private val api by lazy { RetrofitClient().endpoint }

    val pokemonMoves = MutableLiveData<List<Move>>()
    val pokemonType = MutableLiveData<List<Type>>()

    private var pokemonDAO: PokemonDAO?
    private var pokemonDB: PokemonDatabase?

    init {
        pokemonDB = PokemonDatabase.getDatabase(application)
        pokemonDAO = pokemonDB?.pokemonDAO()
    }

    fun setMovesPokemon(name: String) {
        api.getPokemonInfo(name).enqueue(object : Callback<Pokemon>{
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful){
                    pokemonMoves.postValue(response.body()!!.moves)
                    pokemonType.postValue(response.body()!!.types)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }
        })
    }

    fun getMovesPokemon(): LiveData<List<Move>> {
        return pokemonMoves
    }

    fun getTypePokemon(): LiveData<List<Type>> {
        return pokemonType
    }

    fun catchPokemon(id: Int, name: String, image: String){
        CoroutineScope(Dispatchers.IO).launch {
            var pokemon = PokemonUser(id, name, image)
            pokemonDAO?.catchPokemon(pokemon)
        }
    }


    suspend fun checkPokemon(name: String) = pokemonDAO?.checkPokemon(name)

}