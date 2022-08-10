package com.sliide.demoapp.utils.redux

import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import io.mockk.verify
import org.junit.Test

class StoreTest {

    @Test
    fun `dispatch send action to reducer`() {
        // Given
        val inputState = TestState
        val inputAction = TestAction
        val reducer = TestReducer()

        val store = Store(
            inputState,
            reducer,
        )

        // When
        runBlocking {
            store.dispatch(inputAction)
        }

        // Then
        reducer.assertActionProcessed(inputAction)
    }
}

object TestState : State

object TestAction : Action

class TestReducer : Reducer<State, Action> {

    private val actionHistory: MutableList<Action> = mutableListOf()

    override fun reduce(currentState: State, action: Action): State {
        actionHistory.add(action)

        return currentState
    }

    fun assertActionProcessed(expectedAction: Action) {
        assertThat(actionHistory).contains(expectedAction)
    }
}
