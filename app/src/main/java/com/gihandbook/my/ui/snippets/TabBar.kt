package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gihandbook.my.R
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun AppBarWithPager(
    title: String,
    selectedTabIndex: Int,
    onSelectedTab: (TabPagesCharacters) -> Unit,
    onFilterClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp)
            .padding(horizontal = 8.dp)
            .padding(top = 6.dp, bottom = 4.dp)
            .background(MaterialTheme.colors.onPrimary, RoundedCornerShape(4.dp))
    ) {
        AppBarWithSidesButton(title = title, leftButton = {
            IconButton(
                onClick = { onFilterClick() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_filter_svgrepo_com),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = null
                )
            }
        },
            rightButton = {
                IconButton(onClick = { onSearchClick() }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search_svgrepo_com__4_),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                        contentDescription = null
                    )
                }
            })
        TabBar(selectedTabIndex = selectedTabIndex, onSelectedTab = { onSelectedTab(it) })
    }
}

@Composable
fun AppBarWithSidesButton(
    title: String,
    leftButton: @Composable () -> Unit,
    rightButton: @Composable () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leftButton.invoke()
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
            rightButton.invoke()
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabBar(selectedTabIndex: Int, onSelectedTab: (TabPagesCharacters) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex, backgroundColor = MaterialTheme.colors.onPrimary) {
        TabPagesCharacters.values().forEachIndexed { index, tabPagesCharacters ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabPagesCharacters) },
                text = {
                    Text(
                        text = stringResource(id = tabPagesCharacters.tabTitle),
                        style = MaterialTheme.typography.h2
                    )
                }
            )
        }
    }
}

enum class TabPagesCharacters(val tabTitle: Int) {
    CHARACTERS(R.string.pager_title_heroes),
    ENEMIES(R.string.pager_title_enemies)
}