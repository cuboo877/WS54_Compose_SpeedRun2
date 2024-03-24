package com.example.ws54_compose_speedrun2.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun readJsonFromAssets(context: Context, file:String):String{
    return context.assets.open(file).bufferedReader().use { it.readText() }
}

fun jsonToMap(jsonString: String):Map<String,Any>{
    val gson = Gson()
    val mapType = object : TypeToken<Map<String,Any>>(){}.type
    return gson.fromJson(jsonString, mapType)
}