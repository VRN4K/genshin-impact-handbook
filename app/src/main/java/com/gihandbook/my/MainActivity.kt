package com.gihandbook.my

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.gihandbook.my.ui.screens.charactersscreen.CharacterCard
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreenViewModel
import com.gihandbook.my.ui.screens.charactersscreen.EnemyCard
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.screens.charactersscreen.*
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
                //CharactersScreen()
                CharacterDetailsScreen()
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

    val selectedEnemiesVision = mutableListOf<String>()
    var selectedCharactersWeaponType: String? = null
    var selectedCharactersVision: String? = null

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
            AnimatedVisibility(
                visible = isFilterShown,
                enter = fadeIn(animationSpec = tween(800)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                if (pagerState == TabPagesCharacters.CHARACTERS) {
                    FilterHeroesBlock(
                        selectedCharactersWeaponType,
                        selectedCharactersVision,
                        onChipClick = { filterType, item ->
                            if (filterType == FilterItemsType.WEAPON_TYPE) {
                                selectedCharactersWeaponType = item
                            }
                            if (filterType == FilterItemsType.VISION) {
                                selectedCharactersVision = item
                            }
                            if (filterType == null) {
                                selectedCharactersWeaponType = null
                                selectedCharactersVision = null
                            }
                            viewModel.onCharacterFilterChipClick(
                                FilterDataModel(
                                    selectedTab = pagerState,
                                    weaponType = selectedCharactersWeaponType?.let { WeaponType.valueOf(it.uppercase()) },
                                    element = selectedCharactersVision?.let { Vision.valueOf(it) },
                                    )
                            )
                        })
                } else {
                    FilterEnemiesBlock(
                        selectedEnemiesVision.toList(),
                        onChipClick = { filterType, item ->
                            if (filterType == FilterItemsType.VISION) {
                                if (!selectedEnemiesVision.contains(item)) {
                                    selectedEnemiesVision.add(item!!)
                                    viewModel.onCharacterFilterChipClick(
                                        FilterDataModel(
                                            selectedTab = pagerState,
                                            filteredElementList = selectedEnemiesVision.toMutableList()
                                        )
                                    )
                                } else {
                                    selectedEnemiesVision.remove(item)
                                    viewModel.onCharacterFilterChipClick(
                                        FilterDataModel(
                                            selectedTab = pagerState,
                                            filteredElementList = selectedEnemiesVision.toMutableList()
                                        )
                                    )
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
            AnimatedVisibility(
                visible = isSearchShown,
                enter = fadeIn(animationSpec = tween(800)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                SearchView(
                    onSearchButtonClick = { viewModel.onSearchButtonClick(it, pagerState) },
                    onClearButtonClick = { viewModel.onClearButtonClick(pagerState) })
            }
        }
    }
}

enum class FilterItemsType {
    WEAPON_TYPE, VISION
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
                CharacterCard(character = item as HeroCardModel) {
                    ElementTitle(element = item.element)
                    WeaponTitle(weaponType = item.weaponType)
                }
            } else {
                CharacterCard(character = item as EnemyCardModel) {
                    AnimatedVisibility(
                        visible = item.element.isNotEmpty(),
                        enter = fadeIn(animationSpec = tween(800)),
                        exit = fadeOut(animationSpec = tween(800))
                    ) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                items(items = item.element, itemContent = { element ->
                                    ElementTitle(element = element)
                                })
                            }
                        }
                    }
                }
            }
        })
    }
}



