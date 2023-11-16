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
    // ejemplo de URL pra obtener imagen https://assets.pokemon.com/assets/cms2/img/pokedex/full/063.png
    fun getPokemonImageUrl(pokemonId: Int): String {
        return when{
            pokemonId>=100-> "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$pokemonId.png"
            pokemonId>=10-> "https://assets.pokemon.com/assets/cms2/img/pokedex/full/0$pokemonId.png"
            else->"https://assets.pokemon.com/assets/cms2/img/pokedex/full/00$pokemonId.png"
        }
        /*
        se obta por when en lugar de if para eliminar el else if
        if(pokemonId>+100){return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$pokemonId.png"}
             else if(pokemonId>=10){return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/0$pokemonId.png"
             } else{return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/00$pokemonId.png"}*/
    }

    fun loadPokemonImageIntoView(pokemonId: Int, imageView: ImageView) {
        val imageUrl = getPokemonImageUrl(pokemonId)
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }
}

