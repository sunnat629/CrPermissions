package dev.sunnat629.crpermissions

import android.app.Activity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.PermissionChecker

class PermissionsResultHandler(
    private val listener: Listener
) {

    fun onResult(
        activity: Activity,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissions.mapIndexed { index, permission ->
            when (grantResults[index]) {
                PermissionChecker.PERMISSION_GRANTED -> listener.onPermissionGranted(permission)
                PermissionChecker.PERMISSION_DENIED -> {
                    if(shouldShowRequestPermissionRationale(activity, permission)){
                        listener.onPermissionRationaleShouldBeShown(permission)
                    } else{
                        listener.onPermissionDenied(permission)
                    }
                }
            }
        }
    }

    interface Listener {
        fun onPermissionGranted(permission: String)

        fun onPermissionDenied(permission: String)

        fun onPermissionRationaleShouldBeShown(permission: String)
    }
}