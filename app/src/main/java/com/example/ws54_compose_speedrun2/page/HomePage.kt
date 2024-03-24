package com.example.ws54_compose_speedrun2.page

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ws54_compose_speedrun2.service.SimpleNavController
import com.example.ws54_compose_speedrun2.service.getIconIdByDescription
import com.example.ws54_compose_speedrun2.service.getTranslated
import com.example.ws54_compose_speedrun2.widget.HomeAppBar
import com.example.ws54_compose_speedrun2.widget.NavDrawerContent

object HomePage {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun build(navController: SimpleNavController, cityData:Map<String,Any>){
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val cityCurrentData = cityData.get("current") as Map<String,Any>
        val cityForecastData = cityData.get("forecast") as Map<String,Any>
        val dailyData = cityForecastData.get("day") as List<Map<String,Any>>
        val hourlyData = cityForecastData.get("hourly") as List<Map<String,Any>>
        ModalDrawer(drawerState = drawerState ,drawerContent = {NavDrawerContent.build(
            navController = navController,
            scope = scope,
            drawerState = drawerState,
            previousData = cityData
        )}) {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                HomeAppBar.build(
                scope = scope,
                drawerState = drawerState,
                cityName = cityCurrentData.get("name").toString()
            )}) {
                val lazyListState = rememberLazyListState()
                LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(15.dp)){
                    item {
                        currentTemp(cityCurrentData)
                        Spacer(modifier = Modifier.height(20.dp))
                        hourlyDataDisplay(hourlyData)
                        Spacer(modifier = Modifier.height(20.dp))
                        dailyDataColumn(dailyData)
                        Spacer(modifier = Modifier.height(20.dp))
                        pm25Display(cityCurrentData)
                    }
                }

            }
        }


    }
    @Composable
    fun currentTemp(current:Map<String,Any>){
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${current.get("temp_c")}C", fontSize = 40.sp)
            Text(text = "${current.get("maxTemp_c")}C / ${current.get("minTemp_c")}", fontSize = 40.sp)
            Text(text = getTranslated(value = current.get("description")), fontSize = 30.sp)
        }
    }

    @Composable
    fun hourlyDataDisplay(hourlyData:List<Map<String,Any>>){
        val lazyListState = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(35.dp))
                .background(
                    Color.LightGray
                ),
            state = lazyListState,
            )
        {
            items(hourlyData.size){
                _hourlyDataColumn(hourlyData[it])
            }
        }
    }

    @Composable
    fun _hourlyDataColumn(hour:Map<String,Any>){
        Column(
            Modifier
                .fillMaxHeight()
                .padding(15.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = hour.get("time").toString())
            Image(painter = painterResource(id = getIconIdByDescription(value = hour.get("description"))), contentDescription = null, Modifier.size(45.dp))
            Text(text = "${hour.get("temp_c")}C")
            Text(text = "${hour.get("daily_chance_of_rain")}%")
        }
    }

    @Composable
    fun dailyDataColumn(dailyData:List<Map<String,Any>>){
        Column(Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(35.dp))
            .background(
                Color.LightGray
            ),
        ) {
            dailyData.forEach{
                _dailyDataRow(it)
            }
        }
    }

    @Composable
    fun _dailyDataRow(data:Map<String,Any>){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = data.get("date").toString())
            Image(painter = painterResource(id = getIconIdByDescription(value = data.get("description"))), contentDescription = null, Modifier.size(45.dp))
            Text(text = "${ data.get("daily_chance_of_rain")}%")
            Text(text ="${ data.get("maxTemp_c")}C / ${ data.get("minTemp_c")}C ")
        }
    }


    @Composable
    fun pm25Display(data:Map<String,Any>){
        Column(
            Modifier
                .fillMaxWidth()
                .height(125.dp)
                .clip(RoundedCornerShape(35.dp))
                .background(
                    Color.LightGray
                ), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "PM25")
            Divider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.9f))
            Text(text = data.get("pm25").toString(), fontSize = 30.sp)
        }
    }
}
