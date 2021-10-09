package by.godevelopment.rsschool2021_android_task_5.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import retrofit2.HttpException

class CatPagingSource(private val webApi: WebApi) : PagingSource<Int, Cat>() {
    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
            val response = webApi.getPagingImages(pageSize, pageNumber)
            return if (response.isSuccessful) {
                val cats = response.body()!!
                val nextPageNumber = if (cats.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                LoadResult.Page(cats, prevPageNumber, nextPageNumber)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val MAX_PAGE_SIZE = 20
        const val INITIAL_PAGE_NUMBER = 1
    }
}
