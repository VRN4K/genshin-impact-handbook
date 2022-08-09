package com.gihandbook.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gihandbook.my.ui.screens.charactersscreen.CharacterCard
import com.gihandbook.my.ui.snippets.AppBarWithPager
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreenViewModel
import com.gihandbook.my.ui.screens.charactersscreen.EnemyCard
import com.gihandbook.my.ui.snippets.TabPagesCharacters
import com.gihandbook.my.ui.theme.GIHandbookTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GIHandbookTheme {
                CharactersScreen()
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun CharactersScreen(viewModel: CharactersScreenViewModel = hiltViewModel()) {
    var pagerState by remember { mutableStateOf(TabPagesCharacters.CHARACTERS) }

    val listHeroes by viewModel.characters.observeAsState()
    val listEnemies by viewModel.enemies.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithPager(
                title = R.string.screen_title_characters,
                pagerState.ordinal
            ) { pagerState = it }
        }
    ) { insets ->
        Column(modifier = Modifier.padding(insets)) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (pagerState.ordinal == 0) {
                    items(items = listHeroes ?: emptyList(), itemContent = { item ->
                        CharacterCard(character = item)
                    })
                } else {
                    items(items = listEnemies ?: emptyList(), itemContent = { item ->
                        EnemyCard(enemy = item)
                    })
                }
            }
        }
    }
}


