package com.example.ws54_compose_speedrun2.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ws54_compose_speedrun2.MainActivity
import com.example.ws54_compose_speedrun2.service.SimpleNavController
import com.example.ws54_compose_speedrun2.service.getTranslated

object ChoosePage {
    @Composable
    fun build(navController: SimpleNavController,allCityData:Map<String,Any>){
        val lazyListState = rememberLazyListState()
        val allCityDataList = allCityData.values.toList()
        LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(15.dp)){
            items(allCityDataList.size){
                val cityData = allCityDataList[it] as Map<String,Any>
                val cityCurrentData = cityData.get("current")as Map<String,Any>
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(35.dp))
                        .background(
                            Color.LightGray
                        )
                        .clickable {
                            navController.push(
                                MainActivity.Screen.Home,
                                allCityDataList[it]
                            )
                        }
                ) {
                    Text(text = getTranslated(value = cityCurrentData.get("name").toString()))

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        }
}