package dev.sunnat629.crpermissions

import android.content.Context
import android.os.Build



class CrPermissions(private val context: Context) {

    private var crPermissions: CrPermissions = CrPermissions(context)

    fun isRevoked(permission: String): Boolean {
        return isGreaterM() && crPermissions.isRevoked(permission)
    }

    private fun isGreaterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}