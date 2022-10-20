package com.gihandbook.my.ui.snippets

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.charactersscreen.CharacterCard
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreenViewModel
import com.gihandbook.my.ui.screens.charactersscreen.ShowCharacters
import com.gihandbook.my.ui.screens.favoritesscreen.FavoritesScreenViewModel
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.theme.Shapes

@Composable
fun TextBlock(title: String, text: String? = null) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = title, style = MaterialTheme.typography.h2)
        text?.let { Text(text = it, style = MaterialTheme.typography.body1) }
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
fun InfoItemWithBigText(itemValue: String, itemType: String) {
    Column(modifier = Modifier.padding(start = 4.dp)) {
        Text(text = itemType, style = MaterialTheme.typography.h4)
        Text(text = itemValue, style = MaterialTheme.typography.h3)
    }
}

@Composable
fun ElementTitle(element: Element) {
    Row(modifier = Modifier.wrapContentWidth()) {
        AsyncImage(
            model = element.iconUrl,
            modifier = Modifier
                .sizeIn(minHeight = 30.dp, maxHeight = 30.dp),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = element.name, color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun CardWithItem(title: String, imageUrl: String? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = imageUrl?.also { println(it) },
                contentDescription = null,
                modifier = Modifier
                    .sizeIn(maxHeight = 70.dp)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun WeaponTitle(weaponType: WeaponType) {
    Row(modifier = Modifier.wrapContentWidth()) {
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
            style = MaterialTheme.typography.body1
        )
    }
}

