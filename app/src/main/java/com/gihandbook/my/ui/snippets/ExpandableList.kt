package com.gihandbook.my.ui.snippets

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gihandbook.my.domain.model.CharacterTalent
import com.gihandbook.my.domain.model.Element

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ExpandableListWithIconsRow(
    title: String,
    itemsList: List<Any>,
    rowContent: @Composable () -> Unit,
    bodyContent: @Composable (Any) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        onClick = { isClicked = !isClicked },
        elevation = 2.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(vertical = 16.dp)
            )
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                rowContent.invoke()
            }
        }
    }
    AnimatedVisibility(
        visible = isClicked,
        enter = expandVertically(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MaterialTheme.colors.onPrimary,
            elevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                itemsList.forEach {
                    bodyContent.invoke(it)
                    if (it != itemsList.last()) {
                        Divider(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ExpandableList(
    title: String,
    itemsList: List<Any>,
    bodyContent: @Composable (Any) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        onClick = { isClicked = !isClicked },
        elevation = 2.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(vertical = 16.dp)
            )
        }
    }
    AnimatedVisibility(
        visible = isClicked,
        enter = expandVertically(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MaterialTheme.colors.onPrimary,
            elevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                itemsList.forEach {
                    bodyContent.invoke(it)
                    if (it != itemsList.last()) {
                        Divider(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SkillsExpandableListItem(characterTalent: CharacterTalent) {
    Column(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = characterTalent.name, style = MaterialTheme.typography.h2)
                Text(
                    text = characterTalent.unlock,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = characterTalent.talentImageUrlId,
                contentDescription = null,
                modifier = Modifier
                    .sizeIn(maxHeight = 30.dp)
                    .padding(horizontal = 4.dp)
            )
        }
        Text(
            text = characterTalent.description,
            style = MaterialTheme.typography.body1
        )
    }
}


@Composable
fun ElementExpandableListItem(element: Element) {
    Column(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = element.name,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = element.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .sizeIn(maxHeight = 30.dp)
                    .padding(horizontal = 4.dp)
            )
        }
        Text(
            text = element.description!!,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun ExpandableListIcon(iconUrl: String) {
    AsyncImage(
        model = iconUrl,
        contentDescription = null,
        modifier = Modifier
            .size(36.dp)
            .padding(horizontal = 4.dp)
    )
}