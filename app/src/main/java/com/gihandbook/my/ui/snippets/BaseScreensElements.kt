package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gihandbook.my.FilterItemsType
import com.gihandbook.my.R
import com.gihandbook.my.domain.StateData
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.model.Vision
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.theme.FilterChipClicked
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun SearchView(
    onSearchButtonClick: (String) -> Unit,
    onClearButtonClick: () -> Unit,
) {
    var query by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(0.1f),
                value = query, onValueChange = { query = it },
                textStyle = MaterialTheme.typography.h2,
                placeholder = { Text("Text for searching", style = MaterialTheme.typography.h2) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchButtonClick(query)
                    }
                )
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp),
                onClick = {
                    onClearButtonClick()
                    query = ""
                }
            ) {
                Image(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun FilterHeroesBlock(
    initialSelectedWeaponType: String?,
    initialSelectedVision: String?,
    onChipClick: (FilterItemsType?, String?) -> Unit
) {
    var isFilterReset by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp),

        shape = RoundedCornerShape(2.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            SingleSelectChipGrid(
                stringResource(id = R.string.filter_title_weapon_type),
                isFilterReset = isFilterReset,
                itemsList = WeaponType.values().map { it.title },
                initialSelectedValue = initialSelectedWeaponType?.let {
                    WeaponType.values().map { it.name }.indexOf(it)
                },
                onItemSelected = { onChipClick(FilterItemsType.weaponType, it) },
            )
            SingleSelectChipGrid(
                stringResource(id = R.string.filter_title_vision),
                isFilterReset = isFilterReset,
                itemsList = Vision.values().map { it.name },
                initialSelectedValue = initialSelectedVision?.let {
                    Vision.values().map { it.name }.indexOf(it)
                },
                onItemSelected = { onChipClick(FilterItemsType.vision, it) },
            )

            isFilterReset = false

            TextButton(
                onClick = {
                    onChipClick(null, null)
                    isFilterReset = true
                }, modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Reset", style = MaterialTheme.typography.h2)
            }
        }
    }
}

@Composable
fun FilterEnemiesBlock(
    initialSelectedValues: List<String>? = null,
    onChipClick: (FilterItemsType?, String?) -> Unit
) {
    var isFilterReset by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp),

        shape = RoundedCornerShape(2.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            MultiSelectChipGrid(
                stringResource(id = R.string.filter_title_vision),
                isFilterReset,
                initialSelectedValues?.map { Vision.values().map { it.name }.indexOf(it) },
                Vision.values().map { it.name },
                onItemSelected = { onChipClick(FilterItemsType.vision, it) }
            )

            isFilterReset = false

            TextButton(
                onClick = {
                    onChipClick(null, null)
                    isFilterReset = true
                }, modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Reset", style = MaterialTheme.typography.h2)
            }
        }
    }
}

@Composable
fun MultiSelectChipGrid(
    gridTitle: String,
    isFilterReset: Boolean? = null,
    initialSelectedValues: List<Int>? = null,
    itemsList: List<String>,
    onItemSelected: (String?) -> Unit
) {
    val clickedIndexes = remember {
        mutableStateListOf<Int?>().apply { initialSelectedValues?.let { this.addAll(it) } }
    }
    isFilterReset?.let { if (it) clickedIndexes.clear() }

    Text(
        text = gridTitle,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp
    ) {
        itemsList.onEachIndexed { index, item ->
            FilterItem(
                item,
                index,
                isClicked = clickedIndexes.contains(index),
                onClick = {
                    if (clickedIndexes.contains(index)) {
                        clickedIndexes.remove(index)
                    } else {
                        clickedIndexes.add(index)
                    }
                    onItemSelected.invoke(item)
                }
            )
        }
    }
}

@Composable
fun SingleSelectChipGrid(
    gridTitle: String,
    initialSelectedValue: Int? = null,
    isFilterReset: Boolean? = null,
    itemsList: List<String>,
    onItemSelected: (String?) -> Unit
) {
    var clickedIndex by remember { mutableStateOf(initialSelectedValue) }
    isFilterReset?.let { if (it) clickedIndex = null }

    Text(
        text = gridTitle,
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp
    ) {
        itemsList.onEachIndexed { index, item ->
            FilterItem(
                item,
                index,
                isClicked = index == clickedIndex,
                onClick = {
                    clickedIndex = it
                    onItemSelected.invoke(it?.let { item })
                }
            )
        }
    }
}

@Composable
fun FilterItem(
    title: String,
    index: Int,
    isClicked: Boolean,
    onClick: (Int?) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick.invoke(if (isClicked) null else index)
            },
        backgroundColor = if (isClicked) FilterChipClicked else MaterialTheme.colors.onSecondary,
        elevation = 0.dp
    ) {
        Text(
            text = title,
            color = if (isClicked) Color.White else MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ShowLoading() {
    CircularProgressIndicator(color = Color.Black, modifier = Modifier)
}

@Composable
fun ShowNotFoundText() {
    Text(
        text = stringResource(id = R.string.search_character_not_found),
        style = MaterialTheme.typography.h1, color = MaterialTheme.colors.primary
    )
}

@Composable
fun <T> showContent(
    stateLiveData: StateLiveData<T>,
    onContent: @Composable (T) -> Unit,
    onLoading: @Composable () -> Unit = { ShowLoading() },
    onNotFound: @Composable () -> Unit = { ShowNotFoundText() },
) {
    val state = stateLiveData.observeAsState().value

    when (state?.status) {
        StateData.DataStatus.NOT_FOUND -> onNotFound()
        StateData.DataStatus.ERROR -> TODO()
        StateData.DataStatus.LOADING -> onLoading()
        StateData.DataStatus.COMPLETE -> onContent(state.data!!)
    }
}

