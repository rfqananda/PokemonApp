package com.rifqiananda.pokemonapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon")
data class PokemonUser(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String = "",
    var image: String =""
) : Serializable
