package com.sliide.presentation.users.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sliide.interactor.users.list.LoadPageResult
import com.sliide.interactor.users.list.PagesInteractor
import com.sliide.interactor.users.list.UserItem
import javax.inject.Inject

class PagingSourceFactoryImpl @Inject constructor(
    private val interactor: PagesInteractor
) : PagingSourceFactory {

    override fun create(): PagingSource<Int, UserItem> = object : PagingSource<Int, UserItem>() {
        override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
            return state.anchorPosition?.let { position ->
                val anchorPage = state.closestPageToPosition(position)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
            val page = params.key ?: 1

            return when (val data = interactor.users(page)) {
                is LoadPageResult.Loaded -> {
                    val nextKey = if (data.items.isEmpty()) null else page + 1
                    val prevKey = if (page > 1) page - 1 else null
                    LoadResult.Page(data.items, prevKey, nextKey)
                }
                is LoadPageResult.UnknownError -> LoadResult.Error(data.throwable)
            }
        }
    }
}