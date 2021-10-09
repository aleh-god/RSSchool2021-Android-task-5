package by.godevelopment.rsschool2021_android_task_5

import android.app.Application
import by.godevelopment.rsschool2021_android_task_5.userсase.CatDownloadCase

class CatApp : Application() {

    private var _catDownloadCase: CatDownloadCase? = null
    val catDownloadCase: CatDownloadCase
        get() = _catDownloadCase!!

    override fun onCreate() {
        super.onCreate()
        _catDownloadCase = CatDownloadCase(this)
    }
}
