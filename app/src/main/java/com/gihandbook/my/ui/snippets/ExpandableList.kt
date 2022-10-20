package com.gihandbook.my.ui.snippets

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.ArtifactUI
import com.gihandbook.my.domain.model.CharacterTalent
import com.gihandbook.my.domain.model.DropUI
import com.gihandbook.my.domain.model.Element

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ExpandableList(
    title: String,
    itemsList: List<Any>,
    arrowColor: Color? = null,
    rowContent: (@Composable () -> Unit)? = null,
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = isClicked,
                transitionSpec = { animationVerticalSideInAndOut(1000) }) { isClicked ->
                Image(
                    painter = painterResource(
                        id = if (isClicked) R.drawable.ic_chevron_up_svgrepo_com else R.drawable.ic_chevron_down_svgrepo_com
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(arrowColor ?: MaterialTheme.colors.primary),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            rowContent?.let {
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    it.invoke()
                }
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
                        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ExpandableCardsListWithCountIndicator(
    title: String,
    itemsList: List<Any>,
    bodyContent: @Composable (Any) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        onClick = { isClicked = !isClicked },
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = isClicked,
                transitionSpec = { animationVerticalSideInAndOut(1000) }) { isClicked ->
                if (itemsList.isNotEmpty()) {
                    Image(
                        painter = painterResource(
                            id = if (isClicked) R.drawable.ic_chevron_up_svgrepo_com else R.drawable.ic_chevron_down_svgrepo_com
                        ),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.size(46.dp))
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Red, CircleShape),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = itemsList.size.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
    AnimatedVisibility(
        visible = if(itemsList.isEmpty()) false else isClicked,
        enter = expandVertically(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)) {
                bodyContent.invoke(itemsList)
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
fun DropsExpandableListItem(drop: DropUI) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = drop.name,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.h2
            )
            Text(
                text = stringResource(id = R.string.enemy_minimum_level, drop.minimum_level),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            RarityIcons(drop.rarity)
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = drop.imageUrl,
            error = rememberAsyncImagePainter(
                model = drop.imageUrlOnError
            ),
            contentDescription = null,
            modifier = Modifier
                .sizeIn(maxHeight = 50.dp)
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ArtifactsExpandableListItem(artifact: ArtifactUI) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = artifact.name,
                modifier = Modifier.padding(bottom = 4.dp),
                style = MaterialTheme.typography.h2
            )
            Text(
                text = stringResource(id = R.string.enemy_set_title, artifact.set),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            RarityIcons(count = artifact.rarity)
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = artifact.imageUrl.also { println(it) },
            contentDescription = null,
            modifier = Modifier
                .sizeIn(maxHeight = 70.dp)
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ExpandableListIcon(iconUrl: String, iconOnError: String? = null) {
    AsyncImage(
        model = iconUrl,
        contentDescription = null,
        error = iconOnError?.let { rememberAsyncImagePainter(model = it) },
        modifier = Modifier
            .size(36.dp)
            .padding(horizontal = 4.dp)
    )
}

@Composable
fun RarityIcons(
    count: Int,
    title: String? = stringResource(id = R.string.enemy_rarity_title),
    starsSize: Dp = 12.dp
) {
    Row(modifier = Modifier.padding(2.dp)) {
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(bottom = 4.dp, end = 4.dp)
            )
        }
        for (star in 0 until count) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .size(starsSize)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}