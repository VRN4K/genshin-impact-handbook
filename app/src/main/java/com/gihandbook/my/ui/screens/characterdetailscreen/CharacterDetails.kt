package com.gihandbook.my.ui.screens.characterdetailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.ui.snippets.SkillsExpandableList
import com.gihandbook.my.ui.snippets.TextBlock
import com.gihandbook.my.ui.snippets.showContent

@Composable
fun CharacterDetailsScreen(
    onBackButtonClick: () -> Unit,
    viewModel: CharacterDetailScreenViewModel = hiltViewModel()
) {
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
                    onContent = {
                        ShowCharacterDetails(it)
                    })
            }
        }
    }
}

@Composable
fun ShowCharacterDetails(character: CharacterUIModel) {
    val palette = character.color?.let { Palette.from(it).generate() }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = character.imageUrl,
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
                    iconColor = palette?.let { Color(it.getLightVibrantColor(MaterialTheme.colors.onPrimary.toArgb())) }
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
        SkillsExpandableList(
            stringResource(id = R.string.talent_skills_title),
            character.skillTalents
        ) {
            character.skillTalents.forEach {
                AsyncImage(
                    model = it.talentImageUrlId,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
        SkillsExpandableList(
            stringResource(id = R.string.talent_passive_title),
            character.passiveTalents
        ) {
            character.passiveTalents.forEach {
                AsyncImage(
                    model = it.talentImageUrlId,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
        TextBlock(title = stringResource(id = R.string.constellation_title))
        ImageCard(character.constellationImageUrl, character.constellationTitle)
        SkillsExpandableList(
            stringResource(id = R.string.constellations_title),
            character.constellations
        ) {
            character.constellations.forEach {
                AsyncImage(
                    model = it.talentImageUrlId,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(horizontal = 4.dp)
                )
            }
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
fun IconWithText(title: String, icon: String) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = icon, contentDescription = null,
            modifier = Modifier.size(48.dp),
            colorFilter = ColorFilter.tint(Color.Black)
        )
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
