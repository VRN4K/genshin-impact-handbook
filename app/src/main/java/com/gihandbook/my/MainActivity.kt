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
import androidx.navigation.compose.rememberNavController
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.screens.charactersscreen.CharacterCard
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreenViewModel
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.gihandbook.my.ui.screens.charactersscreen.*
import com.gihandbook.my.ui.screens.enemydetailsscreen.EnemyDetailsScreen
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
            val navController = rememberNavController()
            GIHandbookTheme {
                // NavGraph(navController)
                //CharacterDetailsScreen()
                EnemyDetailsScreen()
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
                            when (filterType) {
                                FilterItemsType.WEAPON_TYPE -> selectedCharactersWeaponType = item
                                FilterItemsType.VISION -> selectedCharactersVision = item
                                else -> selectedCharactersWeaponType = null.also {
                                    selectedCharactersVision = it
                                }
                            }

                            viewModel.onCharacterFilterChipClick(
                                FilterDataModel(
                                    selectedTab = pagerState,
                                    weaponType = selectedCharactersWeaponType?.let {
                                        WeaponType.valueOf(it.uppercase())
                                    },
                                    element = selectedCharactersVision?.let { Vision.valueOf(it) },
                                )
                            )
                        })
                } else {
                    ShowEnemiesFilterBlock(
                        pagerState,
                        viewModel,
                        selectedEnemiesVision
                    )
                }
            }
            ShowSearchView(
                isShow = isSearchShown,
                onSearchButtonClick = { viewModel.onSearchButtonClick(it, pagerState) },
                onClearButtonClick = { viewModel.onClearButtonClick(pagerState) }
            )
        }
    }
}

@Composable
fun ShowEnemiesFilterBlock(
    tabPagesCharacters: TabPagesCharacters,
    viewModel: CharactersScreenViewModel,
    selectedEnemiesVision: MutableList<String>
) {
    FilterEnemiesBlock(
        selectedEnemiesVision.toList(),
        onChipClick = { filterType, item ->
            if (filterType == FilterItemsType.VISION) {
                if (!selectedEnemiesVision.contains(item)) {
                    selectedEnemiesVision.add(item!!)
                    viewModel.onCharacterFilterChipClick(
                        FilterDataModel(
                            selectedTab = tabPagesCharacters,
                            filteredElementList = selectedEnemiesVision.toMutableList()
                        )
                    )
                } else {
                    selectedEnemiesVision.remove(item)
                    viewModel.onCharacterFilterChipClick(
                        FilterDataModel(
                            selectedTab = tabPagesCharacters,
                            filteredElementList = selectedEnemiesVision.toMutableList()
                        )
                    )
                }
            }
            if (filterType == null) {
                selectedEnemiesVision.clear()
                viewModel.onClearButtonClick(tabPagesCharacters)
            }
        }
    )
}

@Composable
fun ShowSearchView(
    isShow: Boolean,
    onSearchButtonClick: (searchText: String) -> Unit,
    onClearButtonClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isShow,
        enter = fadeIn(animationSpec = tween(800)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        SearchView(
            onSearchButtonClick = { onSearchButtonClick(it) },
            onClearButtonClick = { onClearButtonClick() })
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



