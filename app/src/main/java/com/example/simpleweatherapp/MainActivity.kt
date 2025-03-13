package com.example.simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleweatherapp.ui.theme.SimpleWeatherAppTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleWeatherAppTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    val config = LocalConfiguration.current
    val isRtl = config.layoutDirection == android.util.LayoutDirection.RTL

    Scaffold(
        topBar = { WeatherTopBar() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = if (isRtl) Alignment.End else Alignment.CenterHorizontally
            ) {
                WeatherCard()
                ForecastSection()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.weather_title),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun WeatherCard() {
    val config = LocalConfiguration.current
    val isRtl = config.layoutDirection == android.util.LayoutDirection.RTL

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = if (isRtl) Alignment.End else Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "31°C",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
            Text(
                text = stringResource(R.string.sunny),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
            Text(
                text = stringResource(id = R.string.today),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
        }
    }
}

@Composable
fun ForecastSection() {
    val forecastList = listOf(
        ForecastData(stringResource(R.string.monday), "28°C", stringResource(R.string.cloudy), R.drawable.cloudy),
        ForecastData(stringResource(R.string.tuesday), "30°C", stringResource(R.string.sunny), R.drawable.sunny),
        ForecastData(stringResource(R.string.wednesday), "25°C", stringResource(R.string.rainy), R.drawable.rainy)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecastList) { forecast ->
            ForecastCard(forecast)
        }
    }
}

@Composable
fun ForecastCard(forecast: ForecastData) {
    val config = LocalConfiguration.current
    val isRtl = config.layoutDirection == android.util.LayoutDirection.RTL

    Card(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = if (isRtl) Alignment.End else Alignment.CenterHorizontally
        ) {
            Text(
                text = forecast.day,
                fontSize = 16.sp,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
            Image(
                painter = painterResource(id = forecast.icon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = forecast.temperature,
                fontSize = 14.sp,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
            Text(
                text = forecast.condition,
                fontSize = 14.sp,
                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left
            )
        }
    }
}

data class ForecastData(val day: String, val temperature: String, val condition: String, val icon: Int)

@Preview(showBackground = true)
@Composable
fun WeatherAppPreview() {
    SimpleWeatherAppTheme {
        WeatherApp()
    }
}
