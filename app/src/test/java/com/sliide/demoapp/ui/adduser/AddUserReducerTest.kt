package com.sliide.demoapp.ui.adduser

import com.google.common.truth.Truth.assertThat
import com.sliide.demoapp.model.User
import org.junit.Test

class AddUserReducerTest {

    @Test
    fun `email changes updates email`() {
        val inputState = AddUserViewState()
        val inputAction = AddUserAction.EmailChanged("test@test.com")

        val expectedState = inputState.copy(
            email = "test@test.com",
            emailError = ""
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }

    @Test
    fun `name changes updates name`() {
        val inputState = AddUserViewState()
        val inputAction = AddUserAction.NameChanged("Demo Name")

        val expectedState = inputState.copy(
            name = "Demo Name",
            nameError = ""
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }

    @Test
    fun `submit save with empty name`() {
        val inputState = AddUserViewState()
        val inputAction = AddUserAction.SaveButtonClicked

        val expectedState = inputState.copy(
            nameError =  "Name size should be bigger than 3"
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }

    @Test
    fun `submit save with small name`() {
        val inputState = AddUserViewState(
            name = "ab"
        )
        val inputAction = AddUserAction.SaveButtonClicked

        val expectedState = inputState.copy(
            nameError =  "Name size should be bigger than 3"
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }

    @Test
    fun `submit save with correct name but wrong email`() {
        val inputState = AddUserViewState(
            name = "Demo name",
            email = "mail@test"
        )
        val inputAction = AddUserAction.SaveButtonClicked

        val expectedState = inputState.copy(
            emailError =  "Invalid email format"
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }

    @Test
    fun `submit save with correct data`() {
        val inputState = AddUserViewState(
            name = "Demo name",
            email = "mail@test.com"
        )
        val inputAction = AddUserAction.SaveButtonClicked

        val expectedState = inputState.copy(
            user = User(
                0,
                "Demo name",
                "mail@test.com",
                inputState.gender,
                inputState.status,
            )
        )

        val reducer = AddUserReducer()
        val newState = reducer.reduce(inputState, inputAction)
        assertThat(newState).isEqualTo(expectedState)
    }
}