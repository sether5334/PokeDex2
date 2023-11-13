package com.example.pokedex.UI

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokedex.Api.Pokemon
import com.example.pokedex.Modelo.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class PokemonLoader (private val context: Context) {
//Metodo Ramdon  entre a a 151 la cantidad de poquemos en buscar
    fun loadRandomPokemon(callback: (Pokemon?) -> Unit) {
        val randomPokemonId = Random.nextInt(1, 152)
        loadPokemon(randomPokemonId, callback)
    }

    private fun loadPokemon(pokemonId: Int, callback: (Pokemon?) -> Unit) {
        val call = RetrofitClient.instance.getPokemonById(pokemonId)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    callback.invoke(pokemon)
                } else {
                    callback.invoke(null)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                callback.invoke(null)
            }
        })
    }
    // Método para obtener la URL de la imagen del Pokémon
    fun getPokemonImageUrl(pokemonId: Int): String {
        return "https://pokeres.bastionbot.org/images/pokemon/$pokemonId.png"
    }

    fun loadPokemonImageIntoView(pokemonId: Int, imageView: ImageView) {
        val imageUrl = getPokemonImageUrl(pokemonId)

        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }
}

