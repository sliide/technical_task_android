package com.sliide.demoapp.utils.redux

interface Reducer<S: State, A: Action> {

    /**
     * Given a [currentState] and some [action] return [State].
     *
     * This will give us clear and predictable state management, that ensures each state is associated
     * with some specific user intent or action.
     */
    fun reduce(currentState: S, action: A): S
}