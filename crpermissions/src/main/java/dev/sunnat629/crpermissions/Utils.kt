package dev.sunnat629.crpermissions

import androidx.annotation.NonNull

object Utils {

    /**
     * It will return the name of the permission
     * @param permission is the permission details of the manifest
     * */
    fun getPermissionName(@NonNull permission: String): String {
        return permission.substring(permission.lastIndexOf(".") + 1)
    }
}