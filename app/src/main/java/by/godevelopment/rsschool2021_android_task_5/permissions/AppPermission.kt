package by.godevelopment.rsschool2021_android_task_5.permissions

import android.Manifest
import by.godevelopment.rsschool2021_android_task_5.R

sealed class AppPermission(
    val permissionName: String,
    val requestCode: Int,
    val deniedMessageId: Int,
    val explanationMessageId: Int
) {
    companion object {
        val permissions: List<AppPermission> by lazy {
            listOf(
                WRITE_EXTERNAL_STORAGE
            )
        }
    }

    object WRITE_EXTERNAL_STORAGE : AppPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE, 42,
        R.string.permission_denied, R.string.permission_required
    )
}
