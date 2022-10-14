package com.gihandbook.my.ui.screens.charactersscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.screens.navigation.NavigationItems
import com.gihandbook.my.ui.snippets.*
import com.gihandbook.my.ui.theme.ImagesBackgroundColorLight
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun CharactersScreen(
    viewModel: CharactersScreenViewModel = hiltViewModel(),
    actions: NavigationActions
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithPager(
                title = stringResource(id = R.string.screen_title_characters),
                viewModel.selectedTab.value.ordinal,
                onSelectedTab = { viewModel.onTabClick(it) },
                onFilterClick = { viewModel.onFilterClick() },
                onSearchClick = { viewModel.onSearchButtonClick() }
            )
        },
        bottomBar = { BottomNavigation(NavigationItems.values().asList(), actions) }
    ) { insets ->
        Box(
            modifier = Modifier
                .padding(insets)
                .fillMaxSize()
        ) {
            ShowScreenContent(viewModel, actions)
            ShowFilterBlock(viewModel)
            ShowSearchView(
                initialSearchValue = viewModel.searchQuery.value,
                isShow = viewModel.isSearchShown.value,
                onSearchButtonClick = { viewModel.onSystemSearchButtonClick(it) },
                onClearButtonClick = { viewModel.onClearButtonClick() }
            )
        }
    }
}

@Composable
fun ShowFilterBlock(viewModel: CharactersScreenViewModel) {
    AnimatedVisibility(
        visible = viewModel.isFilterShown.value,
        enter = fadeIn(animationSpec = tween(800)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        if (viewModel.selectedTab.value == TabPagesCharacters.CHARACTERS) {
            FilterHeroesBlock(
                viewModel.selectedCharactersWeaponType.value,
                viewModel.selectedCharactersVision.value,
                onChipClick = { filterType, item ->
                    viewModel.onCharacterFilterChipClick(filterType, item)
                })
        } else {
            FilterEnemiesBlock(
                viewModel.selectedEnemiesVision.toList(),
                onChipClick = { filterType, item ->
                    viewModel.onEnemyFilterChipClick(filterType, item)
                }
            )
        }
    }
}

@Composable
fun ShowSearchView(
    isShow: Boolean,
    initialSearchValue: String,
    onSearchButtonClick: (searchText: String) -> Unit,
    onClearButtonClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isShow,
        enter = fadeIn(animationSpec = tween(800)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        SearchView(
            initialSearchValue = initialSearchValue,
            onSearchButtonClick = { onSearchButtonClick(it) },
            onClearButtonClick = { onClearButtonClick() })
    }
}


@Composable
fun ShowScreenContent(
    viewModel: CharactersScreenViewModel,
    actions: NavigationActions
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (viewModel.selectedTab.value == TabPagesCharacters.CHARACTERS) {
            ShowContent(
                stateLiveData = viewModel.characterState,
                onContent = { ShowCharacters(it, viewModel.selectedTab.value, actions) })
        } else {
            ShowContent(
                stateLiveData = viewModel.enemiesState,
                onContent = { ShowCharacters(it, viewModel.selectedTab.value, actions) })
        }
    }
}

@Composable
fun ShowCharacters(
    characters: List<CharacterCardModel>,
    tabPagesCharacters: TabPagesCharacters,
    actions: NavigationActions
) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = characters, itemContent = { item ->
            if (tabPagesCharacters == TabPagesCharacters.CHARACTERS) {
                CharacterCard(character = item as HeroCardModel, onCardClick = {
                    actions.navigateTo(Screens.Character.setName(item.id))
                }) {
                    ElementTitle(element = item.element)
                    WeaponTitle(weaponType = item.weaponType)
                }
            } else {
                CharacterCard(character = item as EnemyCardModel,
                    onCardClick = { actions.navigateTo(Screens.Enemy.setName(item.id)) }) {
                    if (item.element.isNotEmpty()) {
                        Row(modifier = Modifier.padding(4.dp)) {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
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

@Composable
fun CharacterCard(
    character: CharacterCardModel,
    onCardClick: () -> Unit,
    rowContent: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 104.dp)
            .clickable { onCardClick.invoke() },
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = null,
                error = rememberAsyncImagePainter(
                    model = character.imageUrlOnError,
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(104.dp)
                    .background(ImagesBackgroundColorLight)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                    text = character.name, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                    text = character.region, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1
                )
                Row(modifier = Modifier.padding(4.dp)) {
                    rowContent.invoke()
                }
            }
        }
    }
}
