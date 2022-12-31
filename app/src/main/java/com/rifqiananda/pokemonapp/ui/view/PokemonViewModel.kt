package com.rifqiananda.pokemonapp.ui.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqiananda.pokemonapp.api.PokeAPI
import com.rifqiananda.pokemonapp.api.RetrofitClient
import com.rifqiananda.pokemonapp.data.local.PokemonDAO
import com.rifqiananda.pokemonapp.data.local.PokemonDatabase
import com.rifqiananda.pokemonapp.data.local.PokemonUser
import com.rifqiananda.pokemonapp.data.model.PokemonList
import com.rifqiananda.pokemonapp.data.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel() : ViewModel() {

    private val api by lazy { RetrofitClient().endpoint }

    val pokemon = MutableLiveData<List<Result>>()

    fun setPokemonList() {
        api.getPokemonList().enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                pokemon.postValue(response.body()!!.results)

            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                Log.e("Failure", t.message!!)
            }
        })
    }

    fun getPokemonList(): LiveData<List<com.rifqiananda.pokemonapp.data.model.Result>>{
        return pokemon
    }

}