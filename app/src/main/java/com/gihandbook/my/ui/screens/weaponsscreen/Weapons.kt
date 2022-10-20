package com.gihandbook.my.ui.screens.weaponsscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.WeaponDetails
import com.gihandbook.my.domain.model.WeaponUIModel
import com.gihandbook.my.ui.screens.charactersscreen.FloatingFavoriteActionButton
import com.gihandbook.my.ui.screens.charactersscreen.ShowSearchView
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.screens.navigation.NavigationItems
import com.gihandbook.my.ui.snippets.*
import launchIO

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeaponsScreen(viewModel: WeaponsScreenViewModel = hiltViewModel(), actions: NavigationActions) {
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
                    text = "Weapon description",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.Center)
                )
                FavoriteCheckBox(onClick = {})
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))
            viewModel.clickedWeapon.value?.let {
                WeaponDetails(it.weaponDetails!!)
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBarWithSidesButton(title = stringResource(id = R.string.weapon_screen_title),
                    leftButton = {
                        IconButton(
                            onClick = { viewModel.onFilterClick() }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_filter_svgrepo_com),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                contentDescription = null
                            )
                        }
                    },
                    rightButton = {
                        IconButton(onClick = { viewModel.onSearchButtonClick() }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_search_svgrepo_com__4_),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                contentDescription = null
                            )
                        }
                    }
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
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    ShowContent(
                        stateLiveData = viewModel.weaponState,
                        onContent = { ShowWeaponsScreenContent(it, viewModel) }
                    )
                }
                ShowWeaponFilterBlock(viewModel)
                ShowSearchView(
                    isShow = viewModel.isSearchShown.value,
                    initialSearchValue = viewModel.searchQuery.value,
                    onSearchButtonClick = { viewModel.onSystemSearchButtonClick(it) },
                    onClearButtonClick = { viewModel.onClearButtonClick() }
                )
            }
        }
    }
}


@Composable
fun ShowWeaponsScreenContent(weapons: List<WeaponCardModel>, viewModel: WeaponsScreenViewModel) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 3.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = weapons, itemContent = { item ->
            WeaponCard(weapon = item, viewModel = viewModel)
        })
    }
}

@Composable
fun ShowWeaponFilterBlock(viewModel: WeaponsScreenViewModel) {
    AnimatedVisibility(
        visible = viewModel.isFilterShown.value,
        enter = fadeIn(animationSpec = tween(800)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        FilterWeaponBlock(
            initialSelectedWeaponType = viewModel.selectedWeaponType.value,
            onChipClick = { filterItemType, chipValue ->
                viewModel.onFilterChipClick(
                    filterItemType,
                    chipValue
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeaponCard(weapon: WeaponCardModel, viewModel: WeaponsScreenViewModel) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                coroutineScope.launchIO {
                    viewModel.onCardClicked(weapon)
                    viewModel.bottomState.show()
                }
            },
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
fun WeaponDetails(weapon: WeaponDetails) {
    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
        TextBlock(title = weapon.passiveName, text = weapon.passiveDesc)
        TextBlock(title = "Location:", text = weapon.location)
        weapon.ascensionMaterial?.let {
            TextBlock(title = "Ascension material:", weapon.ascensionMaterial)

        }
    }
}

enum class WeaponErrorIcons(val imageId: Int) {
    Bow(R.drawable.ic_bow_error),
    Sword(R.drawable.ic_sword_error),
    Polearm(R.drawable.ic_polearm_error),
    Catalyst(R.drawable.ic_catalyst_error),
    Claymore(R.drawable.ic_claymore_error),
}