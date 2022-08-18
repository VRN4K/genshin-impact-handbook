package com.gihandbook.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

    val selectedEnemiesVision = remember { mutableStateListOf<String>() }
    var selectedCharactersWeaponType by remember { mutableStateOf<String?>(null) }
    var selectedCharactersVision by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithPager(
                title = R.string.screen_title_characters,
                pagerState.ordinal,
                onSelectedTab = {
                    pagerState = it
                    isFilterShown = false
                    isSearchShown = false
                },
                onFilterClick = {
                    isFilterShown = !isFilterShown
                    if (isFilterShown) isSearchShown = false
                },
                onSearchClick = {
                    isSearchShown = !isSearchShown
                    if (isSearchShown) isFilterShown = false
                }
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
                if (pagerState == TabPagesCharacters.CHARACTERS) {
                    FilterHeroesBlock(onChipClick = { filterType, item ->
                        if (filterType == FilterItemsType.weaponType) {
                            selectedCharactersWeaponType = item
                        }
                        if (filterType == FilterItemsType.vision) {
                            selectedCharactersVision = item
                        }
                        if (filterType == null) {
                            selectedCharactersWeaponType = null
                            selectedCharactersVision = null
                        }
                        viewModel.onCharacterFilterChipClick(
                            weaponType = WeaponType.values()
                                .find { it.title == selectedCharactersWeaponType },
                            selectedCharactersVision
                        )
                    })
                } else {
                    FilterEnemiesBlock(
                        onChipClick = { filterType, item ->
                            if (filterType == FilterItemsType.vision) {
                                if (!selectedEnemiesVision.contains(item)) {
                                    selectedEnemiesVision.add(item!!)
                                    viewModel.onEnemyFilterChipClick(selectedEnemiesVision.toMutableList())
                                } else {
                                    selectedEnemiesVision.remove(item)
                                    viewModel.onEnemyFilterChipClick(selectedEnemiesVision.toMutableList())
                                }
                            }
                            if (filterType == null) {
                                selectedEnemiesVision.clear()
                                viewModel.onClearButtonClick(pagerState)
                            }
                        }
                    )
                }
            }
            if (isSearchShown) {
                SearchView(
                    onSearchButtonClick = { viewModel.onSearchButtonClick(it, pagerState) },
                    onClearButtonClick = { viewModel.onClearButtonClick(pagerState) })
            }
        }
    }
}

enum class FilterItemsType {
    weaponType, vision
}

@Composable
fun ShowCharacter(currentTab: TabPagesCharacters, viewModel: CharactersScreenViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentTab == TabPagesCharacters.CHARACTERS) {
            showContent(
                stateLiveData = viewModel.characterState,
                onContent = { ShowCharacters(it, currentTab) })
        } else {
            showContent(
                stateLiveData = viewModel.enemiesState,
                onContent = { ShowCharacters(it, currentTab) })
        }
    }
}


@Composable
fun ShowCharacters(characters: List<*>, tabPagesCharacters: TabPagesCharacters) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = characters, itemContent = { item ->
            if (tabPagesCharacters == TabPagesCharacters.CHARACTERS) {
                CharacterCard(character = item as CharacterCardModel)
            } else {
                EnemyCard(enemy = item as EnemyCardModel)
            }
        })
    }
}



