package by.godevelopment.rsschool2021_android_task_5.userсase

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import by.godevelopment.rsschool2021_android_task_5.model.Cat

class CatDownloadCase(private val context: Context) {
    fun downloadImage(cat: Cat): Long {
        val filename = cat.url.substringAfterLast("/")
        val request = DownloadManager.Request(Uri.parse(cat.url))
            .setTitle(filename)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        return (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
            .enqueue(request)
    }
}
