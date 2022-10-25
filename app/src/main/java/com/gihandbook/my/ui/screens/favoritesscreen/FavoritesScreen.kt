package com.gihandbook.my.ui.screens.favoritesscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.extensions.forwardingPainter
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.ui.screens.charactersscreen.*
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.screens.navigation.NavigationItems
import com.gihandbook.my.ui.screens.weaponsscreen.ShowWeaponsScreenContent
import com.gihandbook.my.ui.screens.weaponsscreen.WeaponDetails
import com.gihandbook.my.ui.screens.weaponsscreen.WeaponErrorIcons
import com.gihandbook.my.ui.snippets.*
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalPagerApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel(),
    actions: NavigationActions
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetBackgroundColor = MaterialTheme.colors.onPrimary,
        sheetState = viewModel.bottomState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.weapon_description_title),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.Center)
                )
                FavoriteCheckBox(
                    initialCheckBoxStatus = viewModel.clickedWeapon.value?.isFavorite ?: false,
                    onClick = {
                        viewModel.onFavoriteIconClick(
                            viewModel.clickedWeapon,
                            FavoriteListType.WEAPON
                        )
                    })
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))
            viewModel.clickedWeapon.value?.let {
                WeaponDetails(it.weaponDetails)
            }
        }
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

                    ShowContent(stateLiveData = viewModel.weapons, onContent = {
                        ExpandableCardsListWithCountIndicator(
                            stringResource(id = R.string.favorite_weapons_list_title),
                            it,
                            bodyContent = { weapons ->
                                ShowWeaponsScreenContent(
                                    weapons as List<WeaponCardModel>,
                                    onCardClicked = { weapons ->
                                        viewModel.onWeaponCardClicked(
                                            weapons
                                        )
                                    },
                                    bottomState = viewModel.bottomState
                                )
                            }
                        )
                    })
                }
            }
        }
    }
}

@Composable
fun WeaponFavoriteCard(weapon: WeaponCardModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                    text = weapon.name, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                    text = weapon.type.title, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier.padding(bottom = 2.dp, start = 4.dp),
                    text = weapon.subStat, color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h4
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                    text = stringResource(
                        id = R.string.weapon_base_atk,
                        weapon.baseAttack.toString()
                    ),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h4
                )
                RarityIcons(count = weapon.rarity, title = null, starsSize = 20.dp)
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = weapon.imageUrl,
                contentDescription = null,
                error = forwardingPainter(
                    painterResource(id = WeaponErrorIcons.valueOf(weapon.type.title).imageId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(126.dp)
            )
        }
    }
}

@Composable
fun ShowFavoriteWeapons(weapons: List<WeaponCardModel>) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = weapons, itemContent = { item ->
            WeaponFavoriteCard(item)
        })
    }
}

