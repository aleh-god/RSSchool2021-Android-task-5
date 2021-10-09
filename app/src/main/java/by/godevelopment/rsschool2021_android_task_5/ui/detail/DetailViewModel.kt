package by.godevelopment.rsschool2021_android_task_5.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.godevelopment.rsschool2021_android_task_5.model.Cat
import by.godevelopment.rsschool2021_android_task_5.userсase.CatDownloadCase

class DetailViewModel(private val catDownloadCase: CatDownloadCase) : ViewModel() {
    fun downloadImage(cat: Cat): Long {
        return catDownloadCase.downloadImage(cat)
    }
}

class CatViewModelFactory(private val catDownloadCase: CatDownloadCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(catDownloadCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
