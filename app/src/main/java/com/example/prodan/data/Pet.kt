package com.example.prodan.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    @SerializedName("data")
    val `data`: List<Data>,
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("attributes")
    val attributes: Attributes,
    @SerializedName("id")
    val id: Int
) : Parcelable



@Parcelize
data class Attributes(
    @SerializedName("age")
    val age: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("male")
    val male: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String
): Parcelable
