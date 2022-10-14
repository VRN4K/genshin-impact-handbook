package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.WeaponType

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
            style = MaterialTheme.typography.body1
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
            style = MaterialTheme.typography.body1
        )
    }
}