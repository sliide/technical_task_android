package com.slide.test.repository.model

import com.slide.test.core.Page
import com.slide.test.core.PageMetadata
import com.slide.test.network.model.ListResponseDto
import com.slide.test.network.model.PageMetadataDto
import com.slide.test.network.model.UserDto

/**
 * Created by Stefan Halus on 19 May 2022
 */

fun ListResponseDto<UserDto>.toModel(): Page<UserModel> {
    return Page(
        code = code,
        meta = meta.toModel(),
        data = data.map { it.toModel() }
    )
}


fun PageMetadataDto.toModel(): PageMetadata {
    return PageMetadata(
        totalCount = pagination.totalCount,
        pages = pagination.pages,
        currentPage = pagination.currentPage,
        pageSize = pagination.pageSize
    )
}
