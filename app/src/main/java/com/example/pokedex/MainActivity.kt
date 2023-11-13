package com.example.pokedex

import LocationClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pokedex.UI.PokemonLoader

class MainActivity : AppCompatActivity() {
    private var locationClient : LocationClient? = null
    private lateinit var textViewPokemonInfo: TextView
    private lateinit var imageViewPokemon: ImageView
    private lateinit var buttonLoadPokemon: Button
    private val pokemonLoader = PokemonLoader(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //bloque Ubicacion usuario
        locationClient = LocationClient(this) { location ->
            val latitud = location.latitude
            val longitud = location.longitude
            showToast("Latitud: $latitud, Longitud: $longitud")
        }

        //bloque de  cargar poquemon
        imageViewPokemon = findViewById(R.id.imageViewPokemon)
        textViewPokemonInfo = findViewById(R.id.textViewPokemonInfo)
        buttonLoadPokemon = findViewById(R.id.buttonLoadPokemon)

        buttonLoadPokemon.setOnClickListener {
            pokemonLoader.loadRandomPokemon { pokemon ->
                if (pokemon != null) {
                    val infoText = "Nombre: ${pokemon.name}\nAltura: ${pokemon.height}\nPeso: ${pokemon.weight}"
                    textViewPokemonInfo.text = infoText

                    // Utiliza Glide para cargar la imagen
                    pokemonLoader.loadPokemonImageIntoView(pokemon.id, imageViewPokemon)
                } else {
                    // Manejar el caso cuando no se puede cargar el Pokémon
                    textViewPokemonInfo.text = "Error al cargar el Pokémon"
                    imageViewPokemon.setImageResource(android.R.color.black)
                }
            }
        }
    }


// metodo para localizacion del usuario
    override fun onResume() {
        super.onResume()
        locationClient?.startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        locationClient?.stopLocationUpdates()
    }

    private fun showToast(message: String) {
        Toast.makeText(this,"tu ubicacion: $message",Toast.LENGTH_SHORT).show()
    }
}