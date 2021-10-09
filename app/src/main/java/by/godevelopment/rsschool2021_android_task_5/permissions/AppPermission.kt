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
//                ,READ_EXTERNAL_STORAGE,
//                INTERNET,
//                ACCESS_NETWORK_STATE
            )
        }
    }

    object WRITE_EXTERNAL_STORAGE : AppPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE, 42,
        R.string.permission_denied, R.string.permission_required
    )

//    object READ_EXTERNAL_STORAGE : AppPermission(
//        Manifest.permission.READ_EXTERNAL_STORAGE, 42,
//        R.string.permission_required, R.string.permission_required
//    )

//    object ACCESS_DOWNLOAD_MANAGER : AppPermission(
//        Manifest.permission.ACCESS_DOWNLOAD_MANAGER, 42,
//        R.string.permission_required_text, R.string.permission_required_text
//    )

//    object INTERNET : AppPermission(
//        Manifest.permission.INTERNET, 42,
//        R.string.permission_required_text, R.string.permission_required_text
//    )
//
//    object ACCESS_NETWORK_STATE : AppPermission(
//        Manifest.permission.ACCESS_NETWORK_STATE, 42,
//        R.string.permission_required_text, R.string.permission_required_text
//    )
}
