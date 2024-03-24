package com.example.ws54_compose_speedrun2.widget

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import com.example.ws54_compose_speedrun2.service.getTranslated
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object HomeAppBar{
    @Composable
    fun build(scope:CoroutineScope, drawerState: DrawerState, cityName:String){
        TopAppBar(title = { Text(text = getTranslated(value = cityName))}, navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        })
    }
}