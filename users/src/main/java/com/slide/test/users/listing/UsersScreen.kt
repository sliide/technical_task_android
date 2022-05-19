package com.slide.test.users.listing

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.slide.test.core_ui.component.LoadingWheel
import com.slide.test.core_ui.theme.SliideTestTheme
import com.slide.test.usecase.users.model.Gender
import com.slide.test.usecase.users.model.UserStatus
import com.slide.test.users.R.string
import com.slide.test.users.UsersViewModel
import com.slide.test.users.model.UserUI

/**
 * Created by Stefan Halus on 18 May 2022
 */
@Composable
fun UsersRoute(
//    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val viewState by viewModel.observableState.subscribeAsState(viewModel.initialState)
    UsersScreen(
        modifier = modifier,
        viewState = viewState,
        onDeleteAction = { userUI -> viewModel.dispatch(Action.UserDeleteAction(userUI)) }
    )
}

@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    viewState: State,
    onDeleteAction: (UserUI) -> Unit,
) {
    when {
        viewState.isLoading -> LoadingUsers()
        viewState.isEmpty -> showEmpty()
        viewState.isIdle -> {}
        else -> UserList(modifier, viewState.userList, onDeleteAction = onDeleteAction)
    }
}

@Composable
fun UserList(modifier: Modifier, userList: List<UserUI>, onDeleteAction: (UserUI) -> Unit) {
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 3
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.padding(horizontal = 16.dp)

    ) {
        userList.forEach { userUI ->
            item {
                UserItem(
                    userUI = userUI,
                    onClick = { },
                    onLongTap = { onDeleteAction(userUI) }
                )
            }
        }

        item {
            Spacer(
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
            )
        }
    }
}

@Composable
fun LoadingUsers(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        LoadingWheel(
            contentDesc = stringResource(id = string.users_loading),
        )
    }


}

@Composable
fun showEmpty() {
    Text(text = "Empty users", fontSize = 20.sp, modifier = Modifier.padding(30.dp))
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    val userList = listOf(
        UserUI(10, "name", "name@email.com", Gender.MALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
    )
    SliideTestTheme {
        UsersScreen(modifier = Modifier, viewState = State(userList = userList), { })
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET, widthDp = 720, heightDp = 360
)
@Composable
fun UsersScreenLandScapePreview() {
    val userList = listOf(
        UserUI(10, "name", "name@email.com", Gender.MALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
        UserUI(10, "name", "name@email.com", Gender.FEMALE, UserStatus.INACTIVE, creationTime = "00:00:02"),
    )
    SliideTestTheme {
        UsersScreen(modifier = Modifier, viewState = State(userList = userList), { })
    }
}