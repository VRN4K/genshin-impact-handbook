package com.gihandbook.my.ui.screens.enemydetailsscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.ArtifactUI
import com.gihandbook.my.domain.model.DropUI
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyUIModel
import com.gihandbook.my.ui.screens.characterdetailscreen.*
import com.gihandbook.my.ui.snippets.*

@Composable
fun EnemyDetailsScreen(
    characterName: String,
    onBackButtonClick: () -> Unit, viewModel: EnemyDetailsScreenViewModel = hiltViewModel()
) {
    viewModel.setInitSettings(characterName)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { insets ->
        Box(
            modifier = Modifier
                .padding(insets)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                showContent(
                    stateLiveData = viewModel.character,
                    onContent = { ShowEnemyDetails(it, onBackButtonClick) })
            }
        }
    }
}

@Composable
fun ShowEnemyDetails(enemy: EnemyUIModel, onBackButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = enemy.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(maxHeight = 350.dp)
        )
        IconWithText(title = enemy.name, icon = null)
        CharacterSmallInfoRow(
            content = {
                InfoItemWithIcon(
                    icon = R.drawable.ic_map_svgrepo_com,
                    itemType = stringResource(id = R.string.character_region_title),
                    itemValue = enemy.region!!
                )

                InfoItemWithIcon(
                    icon = R.drawable.ic_user_svgrepo_com__1_,
                    itemType = "Type",
                    itemValue = enemy.type
                )

                InfoItemWithIcon(
                    icon = R.drawable.ic_mora,
                    itemType = stringResource(id = R.string.enemy_mora_gained),
                    itemValue = enemy.moraGained
                )
            })

        TextBlock(
            title = stringResource(id = R.string.character_description_title),
            text = enemy.description
        )

        TextBlock(title = stringResource(id = R.string.enemy_materials_title))
        enemy.elements?.let {
            ExpandableList(
                title = stringResource(id = R.string.enemy_vision_title),
                itemsList = it,
                rowContent = {
                    it.forEach { element -> ExpandableListIcon(element.iconUrl) }
                },
                bodyContent = { element ->
                    ElementExpandableListItem(element = element as Element)
                }
            )
        }

        enemy.drops?.let {
            ExpandableList(
                title = stringResource(id = R.string.enemy_drops_title),
                itemsList = it,
                rowContent = {
                    enemy.drops.forEach { element ->
                        ExpandableListIcon(
                            element.imageUrl,
                            element.imageUrlOnError
                        )
                    }
                },
                bodyContent = { element ->
                    DropsExpandableListItem(element as DropUI)
                }
            )
        }
        enemy.artifacts?.let {
            ExpandableList(
                title = stringResource(id = R.string.enemy_artifact_title),
                itemsList = it,
                rowContent = {
                    enemy.artifacts.forEach { element ->
                        ExpandableListIcon(
                            element.imageUrl
                        )
                    }
                },
                bodyContent = { element ->
                    ArtifactsExpandableListItem(element as ArtifactUI)
                }
            )
        }
    }
}