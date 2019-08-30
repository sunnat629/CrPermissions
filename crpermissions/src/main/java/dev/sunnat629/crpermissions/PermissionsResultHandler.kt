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
        /**
         * After grand the permission, user can get the permission and take the action of this permission
         * @param permission is granted permission
         * */
        fun onPermissionGranted(permission: String)

        /**
         * After denied the permission with check the "Don't ask again". This permission won't show anymore.
         * Need to manually modify from the Settings.
         * @param permission is denied permission
         * */
        fun onPermissionDenied(permission: String)

        /**
         * After denied the permission without check the "Don't ask again". This permission will ask again when it will trigger.
         * @param permission is denied permission
         * */
        fun onPermissionRationaleShouldBeShown(permission: String)
    }
}