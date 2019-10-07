package dev.sunnat629.crpermissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData

/**
 * Listens to Runtime Permission Status of provided [permissionToListen] which comes under the
 * category of "Dangerous" and then responds with appropriate state specified in {@link PermissionStatus}
 */
class PermissionStatusListener(
    private val context: Context,
    private val permissionToListen: String
) : LiveData<PermissionStatus>() {

    init {
        handlePermissionCheck()
    }

    private fun handlePermissionCheck() {
        val isPermissionGranted = ActivityCompat.checkSelfPermission(context,
            permissionToListen) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted)
            postValue(PermissionStatus.Granted())
        else
            postValue(PermissionStatus.Denied())
    }
}