package com.example.ws54_compose_speedrun2.page

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ws54_compose_speedrun2.service.SimpleNavController
import com.example.ws54_compose_speedrun2.service.getTranslated
import com.example.ws54_compose_speedrun2.widget.BackAppBar
import  com.example.ws54_compose_speedrun2.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

object RegionPage{
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun build(navController: SimpleNavController, previousData: Map<String, Any>,allCityData: Map<String, Any>){
        val isCreating = remember {
            mutableStateOf(false)
        }
        val cityDataList = allCityData.values.toList()
        val unselectCityDataList = remember {
            mutableStateOf(cityDataList)
        }
        val selectCityDataList = remember{
            mutableStateOf(listOf<Map<String,Any>>())
        }
        val openDialog = remember {
            mutableStateOf(false)
        }
        val lazyListState = rememberLazyListState()
        Scaffold(modifier = Modifier
            .fillMaxSize(),topBar = { BackAppBar.build(
            isCreating = isCreating,
            navController = navController, previousData = previousData) },
            floatingActionButton = {
                FloatingActionButton(onClick = { openDialog.value = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    if(openDialog.value){
                        AlertDialog(onDismissRequest = { openDialog.value = false },
                            confirmButton = {},
                            title = { Text(text = stringResource(id = R.string.add_a_region))},
                            text = {
                                LazyColumn(state = lazyListState, contentPadding = PaddingValues(5.dp)){
                                    items(unselectCityDataList.value.size){
                                        val cityData = unselectCityDataList.value[it] as Map<String,Any>
                                        val current = cityData.get("current")as Map<String,Any>
                                        Text(text = getTranslated(value = current.get("name")), modifier = Modifier.clickable {
                                            unselectCityDataList.value = unselectCityDataList.value.minus( cityData)
                                            selectCityDataList.value = selectCityDataList.value.plus(cityData)
                                            openDialog.value = false
                                        })
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) {
            val userAgent = "test"
            Configuration.getInstance().userAgentValue = userAgent
            val regions = remember{
                mutableStateListOf<RegionData>()
            }
            selectCityDataList.value.forEach{
                val current = it.get("current") as Map<String,Any>
                regions.add(
                    RegionData(
                        title = getTranslated(value = current.get("name")),
                        temp = (current.get("temp_c") as Double).toInt(),
                        rain = (current.get("daily_chance_of_rain") as Double).toInt(),
                        lat = current.get("lat") as Double,
                        lon = current.get("lon") as Double,
                        description = getTranslated(value = current.get("description"))
                    )
                )
            }
            var geoPoint = remember {
                mutableStateOf(GeoPoint(23.5,121.5))
            }
            AndroidView(
                factory = {
                    context ->
                    MapView(context).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(9.5)
                        controller.setCenter(geoPoint.value)
                    }
                },
                update = {
                    view ->

                    for(data in regions){
                        val marker = Marker(view)
                        marker.position = GeoPoint(data.lat, data.lon)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                        marker.title = data.title
                        marker.snippet = "${data.temp}C / ${data.rain}% / ${data.description}"
                        view.overlays.add(marker)
                        marker.setOnMarkerClickListener {
                                it , view ->
                            marker.showInfoWindow()
                            true
                        }
                    }
                }
            )
        }
    }
}

data class RegionData(
    val title:String,
    val temp:Int,
    val rain:Int,
    val lat:Double,
    val lon:Double,
    val description: String
)