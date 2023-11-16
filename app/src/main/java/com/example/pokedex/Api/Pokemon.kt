package com.example.pokedex.Api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Types

data class Pokemon(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("base_experience") val baseExperience: Int,
    @Expose @SerializedName("height") val height: Int)
