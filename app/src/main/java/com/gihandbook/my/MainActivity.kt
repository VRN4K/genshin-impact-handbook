package com.gihandbook.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.screens.charactersscreen.CharacterCard
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreenViewModel
import com.gihandbook.my.ui.screens.charactersscreen.EnemyCard
import com.gihandbook.my.ui.snippets.*
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
    var isFilterShown by remember { mutableStateOf(false) }
    var isSearchShown by remember { mutableStateOf(false) }
    var filterResetState by remember { mutableStateOf(false) }

    var selectedWeaponType by remember { mutableStateOf<String?>(null) }
    var selectedVision by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithPager(
                title = R.string.screen_title_characters,
                pagerState.ordinal,
                onSelectedTab = { pagerState = it },
                onFilterClick = { isFilterShown = !isFilterShown },
                onSearchClick = { isSearchShown = !isSearchShown }
            )
        }
    ) { insets ->
        Box(
            modifier = Modifier
                .padding(insets)
                .fillMaxSize()
        ) {
            ShowCharacter(pagerState, viewModel)
            if (isFilterShown) {
                isSearchShown = false
                FilterBlock(onChipClick = { filterType, item ->
                    if (filterType == FilterItemsType.weaponType) {
                        selectedWeaponType = item
                        viewModel.onWeaponFilterClick(
                            weaponType = WeaponType.values()
                                .find { it.title == selectedWeaponType })
                    }
                    if (filterType == FilterItemsType.vision) {
                        selectedVision = item
                        viewModel.onWeaponFilterClick(element = selectedVision)
                    }
                    if (filterType == null) {
                        selectedWeaponType = null
                        selectedVision = null
                        viewModel.onWeaponFilterClick()
                    }
                })
            }
            if (isSearchShown) {
                isFilterShown = false
                SearchView(
                    onSearchButtonClick = { viewModel.onSearchButtonClick(it) },
                    onClearButtonClick = { viewModel.onClearButtonClick() })
            }
        }
    }
}

enum class FilterItemsType {
    weaponType, vision
}

@Composable
fun ShowCharacter(currentTab: TabPagesCharacters, viewModel: CharactersScreenViewModel) {
    var characters by remember { mutableStateOf(listOf<CharacterCardModel>()) }
    var enemies by remember { mutableStateOf(listOf<EnemyCardModel>()) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        showContent(stateLiveData = viewModel.characterState, onContent = { characters = it })
        showContent(stateLiveData = viewModel.enemiesState, onContent = { enemies = it })
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (currentTab.ordinal == 0) {
                showCharacters(characters = characters)
            } else {
                showEnemies(characters = enemies)
            }
        }
    }
}

fun LazyListScope.showCharacters(characters: List<CharacterCardModel>) {
    items(items = characters, itemContent = { item -> CharacterCard(character = item) })
}

fun LazyListScope.showEnemies(characters: List<EnemyCardModel>) {
    items(items = characters, itemContent = { item -> EnemyCard(enemy = item) })
}



