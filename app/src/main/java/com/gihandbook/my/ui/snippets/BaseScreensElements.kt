package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gihandbook.my.R
import com.gihandbook.my.domain.StateData
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.Vision
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.screens.charactersscreen.FilterItemsType
import com.gihandbook.my.ui.theme.FilterChipClicked
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun SearchView(
    initialSearchValue: String = "",
    onSearchButtonClick: (String) -> Unit,
    onClearButtonClick: () -> Unit,
) {
    var query by remember { mutableStateOf(initialSearchValue) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier.weight(0.1f),
                value = query, onValueChange = { query = it },
                textStyle = MaterialTheme.typography.h3,
                placeholder = {
                    Text(
                        stringResource(id = R.string.search_searchview_text),
                        style = MaterialTheme.typography.h2
                    )
                },
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
                    painter = painterResource(id = R.drawable.ic_close_svgrepo_com),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
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
        backgroundColor = MaterialTheme.colors.onPrimary
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
                initialSelectedValue = initialSelectedWeaponType,
                onItemSelected = { onChipClick(FilterItemsType.WEAPON_TYPE, it) },
            )
            SingleSelectChipGrid(
                stringResource(id = R.string.filter_title_vision),
                isFilterReset = isFilterReset,
                itemsList = Vision.values().map { it.name },
                initialSelectedValue = initialSelectedVision,
                onItemSelected = { onChipClick(FilterItemsType.VISION, it) },
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
                Text(
                    text = stringResource(id = R.string.filter_reset_button_title),
                    style = MaterialTheme.typography.h3
                )
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
                initialSelectedValues,
                Vision.values().map { it.name },
                onItemSelected = { onChipClick(FilterItemsType.VISION, it) }
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
                Text(
                    text = stringResource(id = R.string.filter_reset_button_title),
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
}

@Composable
fun MultiSelectChipGrid(
    gridTitle: String,
    isFilterReset: Boolean? = null,
    initialSelectedValues: List<String>? = null,
    itemsList: List<String>,
    onItemSelected: (String?) -> Unit
) {
    val clickedItems = remember {
        mutableStateListOf<String?>().apply { initialSelectedValues?.let { this.addAll(it) } }
    }
    isFilterReset?.let { if (it) clickedItems.clear() }

    Text(
        text = gridTitle,
        style = MaterialTheme.typography.h2,
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
        itemsList.forEach { item ->
            FilterItem(
                item,
                isClicked = clickedItems.contains(item),
                onClick = {
                    if (clickedItems.contains(item)) {
                        clickedItems.remove(item)
                    } else {
                        clickedItems.add(item)
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
    initialSelectedValue: String? = null,
    isFilterReset: Boolean? = null,
    itemsList: List<String>,
    onItemSelected: (String?) -> Unit
) {
    var clickedItem by remember { mutableStateOf(initialSelectedValue) }
    isFilterReset?.let { if (it) clickedItem = null }

    Text(
        text = gridTitle,
        style = MaterialTheme.typography.h2,
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
        itemsList.forEach { item ->
            FilterItem(
                item,
                isClicked = item == clickedItem,
                onClick = {
                    clickedItem = it
                    onItemSelected.invoke(it?.let { item })
                }
            )
        }
    }
}

@Composable
fun FilterItem(
    title: String,
    isClicked: Boolean,
    onClick: (String?) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick.invoke(if (isClicked) null else title)
            },
        backgroundColor = if (isClicked) FilterChipClicked else MaterialTheme.colors.onSecondary,
        elevation = 0.dp
    ) {
        Text(
            text = title,
            color = if (isClicked) Color.White else MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
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

@Composable
fun ShowLoading() {
    CircularProgressIndicator(color = MaterialTheme.colors.primary, modifier = Modifier)
}

@Composable
fun ShowNotFoundText() {
    Text(
        text = stringResource(id = R.string.search_character_not_found),
        style = MaterialTheme.typography.h2, color = MaterialTheme.colors.primary
    )
}

@Composable
fun <T> ShowContent(
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

