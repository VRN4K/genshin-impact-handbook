package com.gihandbook.my.ui.screens.charactersscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.theme.ImagesBackgroundColorLight

@Composable
fun CharacterCard(character: CharacterCardModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = character.imageUrl, contentDescription = null,
                error = rememberAsyncImagePainter(model = character.imageUrlOnError),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(minHeight = 100.dp, maxHeight = 100.dp)
                    .width(100.dp)
                    .background(ImagesBackgroundColorLight)
            )
            Column(
                modifier =
                Modifier.padding(top = 4.dp, start = 12.dp, bottom = 4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                    text = character.name, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                    text = character.region, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h2
                )
                Row(modifier = Modifier.padding(4.dp)) {
                    ElementTitle(element = character.element)
                    WeaponTitle(weaponType = character.weaponType)
                }
            }
        }
    }
}

@Composable
fun EnemyCard(enemy: EnemyCardModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = enemy.imageUrl, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sizeIn(minHeight = 100.dp, maxHeight = 100.dp)
                    .width(100.dp)
                    .background(ImagesBackgroundColorLight)
            )
            Column(modifier = Modifier.padding(top = 4.dp, start = 12.dp, bottom = 4.dp)) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                    text = enemy.name, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                    text = enemy.region, color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h2
                )
                Row(modifier = Modifier.padding(4.dp)) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        enemy.element?.let {
                            items(items = it, itemContent = { item ->
                                ElementTitle(element = item)
                            })
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ElementTitle(element: Element) {
    Row {
        AsyncImage(
            model = element.iconUrl,
            modifier = Modifier
                .sizeIn(minHeight = 30.dp, maxHeight = 30.dp),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = element.name, color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun WeaponTitle(weaponType: WeaponType) {
    Row(modifier = Modifier.background(MaterialTheme.colors.onPrimary)) {
        Image(
            modifier = Modifier
                .sizeIn(maxHeight = 30.dp)
                .padding(start = 8.dp),
            painter = painterResource(id = weaponType.imageId),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = weaponType.title, color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h2
        )
    }
}
