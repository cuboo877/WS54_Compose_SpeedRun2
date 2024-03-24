package com.example.ws54_compose_speedrun2.service

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ws54_compose_speedrun2.MainActivity
import com.example.ws54_compose_speedrun2.page.ChoosePage
import com.example.ws54_compose_speedrun2.page.HomePage
import com.example.ws54_compose_speedrun2.page.RegionPage

@Composable
fun SimpleNavHost(navController: SimpleNavController, modifier: Modifier = Modifier,data:Map<String,Any>){
    Box(modifier = modifier){
        when(navController.currentScreen.value.first){
            MainActivity.Screen.Choose -> ChoosePage.build(navController,data)
        MainActivity.Screen.Home -> HomePage.build(navController, navController.currentScreen.value.second as Map<String, Any>)
        MainActivity.Screen.Region -> RegionPage.build(navController,navController.currentScreen.value.second as Map<String, Any>, data)
        }
    }
}