package com.comye1.itunessearch.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comye1.itunessearch.R
import com.comye1.itunessearch.ui.theme.ITunesSearchTheme

@Preview
@Composable
fun SearchBarPreview() {
    ITunesSearchTheme {
        Column(Modifier.fillMaxSize()) {
            SearchBar(searchWord = "", setSearchWord = {}, onSearch = {})
        }
    }
}

@Composable
fun SearchBar(
    searchWord: String,
    setSearchWord: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextField(
            modifier = Modifier.weight(1f),
            searchWord = searchWord,
            setSearchWord = setSearchWord,
            onSearch = onSearch
        )
        Spacer(modifier = Modifier.width(8.dp))
        SearchButton(onSearch = onSearch)
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchWord: String,
    setSearchWord: (String) -> Unit,
    onSearch: () -> Unit,
) {
    TextField(
        modifier = modifier,
        value = searchWord,
        onValueChange = setSearchWord,
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun SearchButton(modifier: Modifier = Modifier, onSearch: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onSearch
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
        )
    }
}