package dev.sunnat629.crpermissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


open class CrPermissions(private val context: Context) {

    private val crPermissionFragment =
        getCrPermissionsFragment((context as FragmentActivity).supportFragmentManager)

    private fun getCrPermissionsFragment(fragmentManager: FragmentManager): CrPermissionFragment? {
        var crPermissionsFragment = findCrPermissionsFragment(fragmentManager)
        val isNewInstance = crPermissionsFragment == null
        if (isNewInstance) {
            crPermissionsFragment = CrPermissionFragment()
            fragmentManager
                .beginTransaction()
                .add(crPermissionsFragment, CrPermissions::class.java.simpleName)
                .commitNow()
        }
        return crPermissionsFragment
    }

    private fun findCrPermissionsFragment(fragmentManager: FragmentManager): CrPermissionFragment? {
        return fragmentManager.findFragmentByTag(CrPermissions::class.java.simpleName) as CrPermissionFragment?
    }

    fun getAllPermissions() {
        val permissionsArray = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions

        crPermissionFragment?.requestAllPermissions(permissionsArray)
    }

    fun getPermission(singlePermission: String) {
        crPermissionFragment?.requestSinglePermission(singlePermission)
    }
    fun getPermissionAA(singlePermission: String) {
        AlertDialog.Builder(context).setMessage("Go Settings -> Permission. " + "Make SMS on and Storage on")
            .setPositiveButton("Settings") { _, _ ->
                crPermissionFragment?.requestPermissionInSetting()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
//                smsAndStoragePermissionHandler.cancel()
                dialog.cancel()
            }
            .show()
    }

    private fun isRevoked(permission: String): Boolean {
        return isGreaterM() && crPermissionFragment?.isRevoked(permission)!!
    }

    private fun isGreaterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}