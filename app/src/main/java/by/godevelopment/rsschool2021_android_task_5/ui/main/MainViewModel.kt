package by.godevelopment.rsschool2021_android_task_5.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import by.godevelopment.rsschool2021_android_task_5.network.CatPagingSource
import by.godevelopment.rsschool2021_android_task_5.network.WebApi
import by.godevelopment.rsschool2021_android_task_5.network.Webservice
import kotlinx.coroutines.flow.Flow

class MainViewModel() : ViewModel() {
    var webService: WebApi = Webservice.retrofitService

    fun getListData(): Flow<PagingData<Cat>> {
        return Pager (
            config = PagingConfig(pageSize = 10, maxSize = 100),
            pagingSourceFactory = { CatPagingSource(webService) }
        ).flow.cachedIn(viewModelScope)
    }

    // TODO = "back save state model"
    var saveLastPagingData: PagingData<Cat>? = null
}
