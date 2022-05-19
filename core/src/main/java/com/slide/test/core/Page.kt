package com.slide.test.core

data class Page<T>(
    val code: Long,
    val meta: PageMetadata,
    val data: List<T>
)

data class PageMetadata(
    val totalCount: Long,
    val pages: Long,
    val currentPage: Long,
    val pageSize: Long
)


fun <T, R> Page<T>.map(mapper: (T) -> R): Page<R> {
    return Page(
        code,
        meta,
        data.map(mapper)
    )
}