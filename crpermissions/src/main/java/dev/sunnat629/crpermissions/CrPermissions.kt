package dev.sunnat629.crpermissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


open class CrPermissions(private val context: Context) {


    /**
     * It will attached the CrPermissionFragment with the application activity or framework
     * */
    private val crPermissionFragment =
        getCrPermissionsFragment((context as FragmentActivity).supportFragmentManager)

    private fun getCrPermissionsFragment(fragmentManager: FragmentManager): CrPermissionFragment? {
        var crPermissionsFragment =
            fragmentManager.findFragmentByTag(CrPermissions::class.java.simpleName) as CrPermissionFragment?
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

    /**
     * This is the function will read automatically the list of the permissions of the app manifest.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getAllPermissions() {
        val permissionsArray: Array<String>? = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions

        if (permissionsArray != null && permissionsArray.isNotEmpty()) {
            crPermissionFragment?.requestAllPermissions(permissionsArray)
        }
    }

    /**
     * This is the function will read a list of permissions which will be given by the developer.
     * @param permissionArray is the array of permissions.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getPermissionArray(@NonNull permissionArray: Array<String>) {
        if (permissionArray.isNotEmpty()) {
            crPermissionFragment?.requestPermissionArray(permissionArray)
        }
    }

    /**
     * This is the function which reads only one permission which will be given by the developer.
     * @param singlePermission is a permission.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getSinglePermission(@NonNull singlePermission: String) {
        if (singlePermission.isNotEmpty()) {
            crPermissionFragment?.requestSinglePermission(singlePermission)
        }
    }
}