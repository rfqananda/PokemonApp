package com.rifqiananda.pokemonapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonUser::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    companion object {
        var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase? {
            if (INSTANCE == null) {
                synchronized(PokemonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDatabase::class.java,
                        "pokemon_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun pokemonDAO(): PokemonDAO
}