package com.example.ws54_compose_speedrun2.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.ws54_compose_speedrun2.MainActivity
import com.example.ws54_compose_speedrun2.service.SimpleNavController
import com.example.ws54_compose_speedrun2.R

object BackAppBar{
    @Composable
    fun build(isCreating: MutableState<Boolean>, navController: SimpleNavController, previousData:Map<String,Any>){
        TopAppBar(title = { Text(text = stringResource(id = R.string.NavDrawerContent_region)) }, navigationIcon = {
            IconButton(onClick = { navController.push(MainActivity.Screen.Home, previousData)
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }
}