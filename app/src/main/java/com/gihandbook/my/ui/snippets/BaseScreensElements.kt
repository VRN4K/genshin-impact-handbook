package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gihandbook.my.R
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable

fun AppBarWithPager(title: Int, selectedTabIndex: Int, onSelectedTab: (TabPagesCharacters) -> Unit ) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        elevation = 0.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)
            )
            TabBar(selectedTabIndex = selectedTabIndex, onSelectedTab = { onSelectedTab(it) })
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabBar(selectedTabIndex: Int, onSelectedTab: (TabPagesCharacters) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = MaterialTheme.colors.onPrimary,
        modifier = Modifier
    ) {
        TabPagesCharacters.values().forEachIndexed { index, tabPagesCharacters ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabPagesCharacters) },
                text = {
                    Text(
                        text = stringResource(id = tabPagesCharacters.tabTitle),
                        style = MaterialTheme.typography.h1
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