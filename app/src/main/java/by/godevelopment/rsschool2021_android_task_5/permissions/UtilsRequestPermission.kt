package by.godevelopment.rsschool2021_android_task_5.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

fun Fragment.isGranted(permission: AppPermission) = run {
    Log.i("Permission: ", ".isGranted")
    context?.let {
        (PermissionChecker.checkSelfPermission(it, permission.permissionName) == PermissionChecker.PERMISSION_GRANTED)
    } ?: false
}

fun Fragment.shouldShowRationale(permission: AppPermission) = run {
    Log.i("Permission: ", ".shouldShowRationale")
    shouldShowRequestPermissionRationale(permission.permissionName)
}

fun Fragment.requestPermission(permission: AppPermission) {
    requestPermissions(arrayOf(permission.permissionName), permission.requestCode)
}

fun Fragment.handlePermission(
    permission: AppPermission,
    onGranted: (AppPermission) -> Unit,
    onDenied: (AppPermission) -> Unit,
    onRationaleNeeded: ((AppPermission) -> Unit)? = null
) {
    Log.i("Permission: ", ".handlePermission")
    when {
        isGranted(permission) -> onGranted.invoke(permission)
        shouldShowRationale(permission) -> onRationaleNeeded?.invoke(permission)
        else -> onDenied.invoke(permission)
    }
}

fun Fragment.handlePermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray,
    onPermissionGranted: (AppPermission) -> Unit,
    onPermissionDenied: ((AppPermission) -> Unit)? = null,
    onPermissionDeniedPermanently: ((AppPermission) -> Unit)? = null
) {
    AppPermission.permissions.find {
        it.requestCode == requestCode
    }?.let { appPermission ->
        val permissionGrantResult = mapPermissionsAndResults(
            permissions,
            grantResults
        )[appPermission.permissionName]
        when {
            PermissionChecker.PERMISSION_GRANTED == permissionGrantResult -> {
                onPermissionGranted(appPermission)
            }
            shouldShowRationale(appPermission) -> onPermissionDenied?.invoke(appPermission)
            else -> {
                goToAppDetailsSettings()
                onPermissionDeniedPermanently?.invoke(appPermission)
            }
        }
    }
}

private fun Fragment.goToAppDetailsSettings() {
    Log.i("Permission: ", ".goToAppDetailsSettings")
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context?.packageName, null)
    }
    activity?.let {
        it.startActivityForResult(intent, 0)
    }
}

private fun mapPermissionsAndResults(
    permissions: Array<out String>,
    grantResults: IntArray
): Map<String, Int> = permissions.mapIndexedTo(
    mutableListOf<Pair<String, Int>>()
) { index, permission -> permission to grantResults[index] }.toMap()
