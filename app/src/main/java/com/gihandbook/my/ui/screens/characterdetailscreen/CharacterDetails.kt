package com.gihandbook.my.ui.screens.characterdetailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.ui.snippets.SkillsExpandableList
import com.gihandbook.my.ui.snippets.TextBlock
import com.gihandbook.my.ui.snippets.showContent

@Composable
fun CharacterDetailsScreen(viewModel: CharacterDetailScreenViewModel = hiltViewModel()) {
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
                    onContent = { ShowCharacterDetails(it) })
            }
        }
    }
}

@Composable
fun ShowCharacterDetails(character: CharacterUIModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
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
                    itemType = "Vision"
                )

                InfoItemWithIcon(
                    icon = character.weaponType.imageId,
                    itemValue = character.weaponType.title,
                    itemType = "Weapon"
                )

                InfoItemWithIcon(
                    icon = R.drawable.star,
                    itemValue = character.rarity.toString(),
                    itemType = "Rarity"
                )
            }
        )
        TextBlock(title = "About", text = character.description)
        TextBlock(title = "Talents")
        SkillsExpandableList("Skill Talents", character.skillTalents) {
            character.skillTalents.forEach {
                AsyncImage(
                    model = it.talentImageUrlId,
                    contentDescription = null,
                    modifier = Modifier
                        .sizeIn(maxHeight = 30.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
        SkillsExpandableList("Passive Talents", character.passiveTalents) {
            character.passiveTalents.forEach {
                AsyncImage(
                    model = it.talentImageUrlId,
                    contentDescription = null,
                    modifier = Modifier
                        .sizeIn(maxHeight = 30.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
    TextBlock(title = "Constellation")
}

@Composable
fun InfoItemWithIcon(
    icon: Int? = null,
    iconUrl: String? = null,
    itemValue: String,
    itemType: String
) {
    Row(
        modifier = Modifier.padding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
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
