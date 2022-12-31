package com.rifqiananda.pokemonapp.api

import com.rifqiananda.pokemonapp.data.model.Pokemon
import com.rifqiananda.pokemonapp.data.model.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {

    @GET("pokemon")
    fun getPokemonList() : Call<PokemonList>

    @GET("pokemon/{name}")
    fun getPokemonInfo(
        @Path("name") name: String
    ) : Call<Pokemon>
}