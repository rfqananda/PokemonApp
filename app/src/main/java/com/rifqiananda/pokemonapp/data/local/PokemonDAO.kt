package com.rifqiananda.pokemonapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDAO {

    @Insert
    suspend fun catchPokemon(pokemonUser: PokemonUser)

    @Query("SELECT * FROM pokemon")
    fun getPokemon(): LiveData<List<PokemonUser>>

    @Query("SELECT pokemon.name FROM pokemon where pokemon.name = :name")
    suspend fun checkPokemon(name: String): String

    @Query("DELETE FROM pokemon WHERE pokemon.id = :id")
    suspend fun releasePokemon(id: Int) : Int
}