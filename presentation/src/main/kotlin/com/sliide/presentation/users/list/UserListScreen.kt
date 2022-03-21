package com.sliide.presentation.users.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.sliide.interactor.users.list.UserItem
import com.sliide.presentation.R
import com.sliide.presentation.components.FullScreenProgress
import com.sliide.presentation.components.ScaffoldHideFabByScroll
import com.sliide.presentation.theme.Dimens
import com.sliide.presentation.theme.EmailBlue
import com.sliide.presentation.theme.Shapes

@Composable
fun UserListScreen(viewModel: UserListViewModel, showDialog: @Composable (Dialogs) -> Unit) {
    val dialog by viewModel.dialog.collectAsState()
    if (dialog != Dialogs.None) showDialog(dialog)

    val items = viewModel.items.collectAsLazyPagingItems()

    when (val state = items.loadState.source.refresh) {
        LoadState.Loading -> FullScreenProgress()
        is LoadState.Error -> viewModel.loadListError(state.error)

        is LoadState.NotLoading -> ScaffoldHideFabByScroll(
            fab = { modifier -> Fab(modifier) { viewModel.onFabClick() } }
        ) {
            UsersList(pagingItems = items) { item -> viewModel.onItemLongClick(item) }
        }
    }
}

@Composable
private fun Fab(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .navigationBarsPadding()
            .then(modifier)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_new_user)
        )
    }
}

@Composable
private fun UsersList(
    pagingItems: LazyPagingItems<UserItem>,
    onItemLongClick: (UserItem) -> Unit
) {
    LazyColumn(
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyTop = true,
            applyBottom = true
        )
    ) {
        itemsIndexed(items = pagingItems, key = { _, item -> item.id }) { _, item ->
            if (item != null) UserItem(item = item) { onItemLongClick(item) }
        }

        when (pagingItems.loadState.append) {
            LoadState.Loading -> item { PageLoadingProgress() }
            is LoadState.Error -> item { PageLoadingError() }
            is LoadState.NotLoading -> { // ignore it. when is using for exhausted feature
            }
        }
    }
}

// experimental because combinedClickable is used. It provides longClick listener and ripple effect
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserItem(item: UserItem, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.normal),
        shape = Shapes.medium,
        elevation = Dimens.elevation
    ) {
        // Box for clip ripple effect
        Box(
            modifier = Modifier.combinedClickable(
                onClick = { /*ignore it*/ },
                onLongClick = onLongClick
            )
        ) {
            Column(
                modifier = Modifier.padding(Dimens.normal)
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = item.email,
                    style = TextStyle(color = EmailBlue, fontSize = Dimens.emailTextSize)
                )
            }
        }
    }
}

@Composable
private fun PageLoadingProgress() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun PageLoadingError() {
    Text(
        modifier = Modifier
            .padding(all = Dimens.default)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        text = stringResource(R.string.loading_list_failed),
        style = TextStyle(color = MaterialTheme.colors.error)
    )
}