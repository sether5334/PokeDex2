package com.example.pokedex.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}/")
    fun getPokemonById(@Path("id") id: Int): Call<Pokemon>
}