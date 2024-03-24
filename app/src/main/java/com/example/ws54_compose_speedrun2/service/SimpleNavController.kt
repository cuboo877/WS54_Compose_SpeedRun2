package com.example.ws54_compose_speedrun2.service

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.ws54_compose_speedrun2.MainActivity

class SimpleNavController(private val startDestination:MainActivity.Screen){
    private val navigationStack = mutableStateListOf<Pair<MainActivity.Screen, Any?>>(startDestination to null) //把預設的startDestination放進Stack裡面

    val currentScreen: State<Pair<MainActivity.Screen, Any?>> get() = navigationStack.last().let { mutableStateOf(it) } //這就是要顯示的螢幕，拿最後堆疊的page，也就是呼叫push後剛剛疊上去的

    fun push(destination:MainActivity.Screen, argument:Any? = null){
        navigationStack += destination to argument
    }
}