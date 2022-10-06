package com.gihandbook.my.ui.screens.characterdetailscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.CharacterTalent
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.ui.snippets.*

@Composable
fun CharacterDetailsScreen(
    characterName: String,
    onBackButtonClick: () -> Unit,
    viewModel: CharacterDetailScreenViewModel = hiltViewModel()
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
                ShowContent(
                    stateLiveData = viewModel.character,
                    onContent = { ShowCharacterDetails(it, onBackButtonClick) }
                )
            }
        }
    }
}

@Composable
fun ShowCharacterDetails(character: CharacterUIModel, onBackButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onBackButtonClick, modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close_svgrepo_com),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = character.imageUrl,
                error = rememberAsyncImagePainter(model = character.imageUrlOnError),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 350.dp)
            )
            IconWithText(title = character.name, icon = character.imageSideUrl)
            CharacterSmallInfoRow(
                content = {
                    InfoItemWithIcon(
                        iconUrl = character.vision.iconUrl,
                        itemValue = character.vision.name,
                        itemType = stringResource(id = R.string.character_vision_title)
                    )

                    InfoItemWithIcon(
                        icon = character.weaponType.imageId,
                        itemValue = character.weaponType.title,
                        itemType = stringResource(id = R.string.character_weapon_title)
                    )

                    InfoItemWithIcon(
                        icon = R.drawable.star,
                        itemValue = character.rarity.toString(),
                        itemType = stringResource(id = R.string.character_rarity_title),
                        iconColor = Color(character.colorPalette.getDominantColor(MaterialTheme.colors.onPrimary.toArgb()))
                    )
                }
            )
            TextBlock(
                title = stringResource(id = R.string.character_region_title),
                text = character.region
            )
            TextBlock(
                title = stringResource(id = R.string.character_description_title),
                text = character.description
            )
            TextBlock(title = stringResource(id = R.string.talents_title))
            ExpandableList(
                stringResource(id = R.string.talent_skills_title),
                character.skillTalents,
                rowContent = {
                    character.skillTalents.forEach { talent ->
                        talent.talentImageUrlId?.let {
                            ExpandableListIcon(it)
                        }
                    }
                },
                bodyContent = { talent ->
                    SkillsExpandableListItem(characterTalent = talent as CharacterTalent)
                }
            )

            ExpandableList(
                stringResource(id = R.string.talent_passive_title),
                character.passiveTalents,
                rowContent = {
                    character.passiveTalents.forEach { talent ->
                        talent.talentImageUrlId?.let {
                            ExpandableListIcon(it)
                        }
                    }
                },
                bodyContent = { talent ->
                    SkillsExpandableListItem(characterTalent = talent as CharacterTalent)
                }
            )
            TextBlock(title = stringResource(id = R.string.constellation_title))
            ImageCard(character.constellationImageUrl, character.constellationTitle)
            ExpandableList(
                stringResource(id = R.string.constellations_title),
                character.constellations,
                bodyContent = { constellation ->
                    SkillsExpandableListItem(characterTalent = constellation as CharacterTalent)
                }
            )
        }
    }
}

@Composable
fun ImageCard(imageUrl: String, title: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 1.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            title?.let {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.6f),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    elevation = 3.dp
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomCenter),
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItemWithIcon(
    icon: Int? = null,
    iconUrl: String? = null,
    itemValue: String,
    itemType: String,
    iconColor: Color? = null
) {
    Row(
        modifier = Modifier.padding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                colorFilter = iconColor?.let { it1 -> ColorFilter.tint(it1) }
            )
        }

        iconUrl?.let {
            AsyncImage(
                model = it,
                modifier = Modifier
                    .sizeIn(minHeight = 30.dp, maxHeight = 30.dp),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.padding(start = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = itemValue, style = MaterialTheme.typography.h3)
            Text(text = itemType, style = MaterialTheme.typography.h5)
        }
    }
}

@Composable
fun IconWithText(title: String, icon: String?) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            AsyncImage(
                model = icon, contentDescription = null,
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun CharacterSmallInfoRow(title: String? = null, content: @Composable () -> Unit) {
    title?.let {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}
