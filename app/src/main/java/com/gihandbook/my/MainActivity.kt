package com.gihandbook.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gihandbook.my.ui.screens.navigation.NavGraph
import com.gihandbook.my.ui.theme.GIHandbookTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            GIHandbookTheme {
                systemUiController.setSystemBarsColor(MaterialTheme.colors.onPrimary)
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Scaffold { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                NavGraph(navHostController)
            }
        }
    }
}



