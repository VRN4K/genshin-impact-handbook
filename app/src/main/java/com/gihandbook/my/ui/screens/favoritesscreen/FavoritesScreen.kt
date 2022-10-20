package com.gihandbook.my.ui.screens.favoritesscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import com.gihandbook.my.ui.screens.charactersscreen.*
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.screens.navigation.NavigationItems
import com.gihandbook.my.ui.snippets.*
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
    actions: NavigationActions
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarWithSidesButton(
                title = stringResource(id = R.string.favorite_screen_title)
            )
        },
        bottomBar = { BottomNavigationWithFAB(NavigationItems.values().asList(), actions) },
        isFloatingActionButtonDocked = true,
        floatingActionButton = { FloatingFavoriteActionButton(actions = actions) },
        floatingActionButtonPosition = FabPosition.Center,
    ) { insets ->
        Box(
            modifier = Modifier
                .padding(insets)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Column {
                ShowContent(stateLiveData = viewModel.heroes, onContent = {
                    ExpandableCardsListWithCountIndicator(
                        stringResource(id = R.string.favorite_heroes_list_title),
                        it,
                        bodyContent = { characters ->
                            ShowCharacters(
                                characters as List<HeroCardModel>,
                                TabPagesCharacters.CHARACTERS,
                                onFavoriteClick = { character, _ ->
                                    viewModel.onFavoriteIconClick(
                                        character,
                                        FavoriteListType.PLAYABLE_CHARACTER
                                    )
                                },
                                actions
                            )
                        }
                    )
                })

                ShowContent(stateLiveData = viewModel.enemies, onContent = {
                    ExpandableCardsListWithCountIndicator(
                        stringResource(id = R.string.favorite_enemies_list_title),
                        it,
                        bodyContent = { enemies ->
                            ShowCharacters(
                                enemies as List<EnemyCardModel>,
                                TabPagesCharacters.ENEMIES,
                                onFavoriteClick = { character, _ ->
                                    viewModel.onFavoriteIconClick(
                                        character,
                                        FavoriteListType.ENEMY_CHARACTER
                                    )
                                },
                                actions
                            )
                        }
                    )
                })
            }
        }
    }
}

