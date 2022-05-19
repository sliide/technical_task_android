package com.slide.test.core_ui.mvi

/**
 * Created by Stefan Halus on 19 May 2022
 */
typealias Reducer<S, C> = (state: S, change: C) -> S