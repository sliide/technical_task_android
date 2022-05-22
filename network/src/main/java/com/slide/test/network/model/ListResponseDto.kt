package com.slide.test.network.model

import com.squareup.moshi.Json

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class ListResponseDto<T>(
    val code: Long,
    val meta: PageMetadataDto,
    val data: List<T>
)

data class PageMetadataDto(
    val pagination: PaginationDto
)

data class PaginationDto(
    @Json(name = "total")
    val totalCount: Long,

    val pages: Long,

    @Json(name = "page")
    val currentPage: Long,

    @Json(name = "limit")
    val pageSize: Long
)