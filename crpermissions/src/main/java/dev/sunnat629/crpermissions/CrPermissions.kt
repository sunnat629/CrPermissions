package dev.sunnat629.crpermissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


open class CrPermissions() {

    private lateinit var context: Context
    private lateinit var listener: PermissionListener
    private lateinit var crPermissionFragment: CrPermissionFragment

    /**
     * It will read the list of the permissions of the app manifest
     * */
    private lateinit var permissionsArray: Array<String>

    constructor(context: Context, listener: PermissionListener): this(){
        this.context = context
        this.listener = listener
        permissionsArray =
            context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions
        crPermissionFragment =
            getCrPermissionsFragment((context as FragmentActivity).supportFragmentManager)
    }


    /**
     * It will attached the CrPermissionFragment with the application activity or framework
     * */

    private fun getCrPermissionsFragment(fragmentManager: FragmentManager): CrPermissionFragment {
        var crPermissionsFragment = fragmentManager
            .findFragmentByTag(CrPermissions::class.java.simpleName) as CrPermissionFragment?

        if (crPermissionsFragment == null) {
            crPermissionsFragment = CrPermissionFragment.newInstance(permissionsArray, listener)
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
    fun getAllPermissions() = crPermissionFragment?.requestAllPermissions(permissionsArray)

    /**
     * This is the function will read a list of permissions which will be given by the developer.
     * @param permissionArray is the array of permissions.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getPermissionArray(permissionArray: Array<String>) = crPermissionFragment?.requestSinglePermissionArray(permissionArray)

    /**
     * This is the function which reads only one permission which will be given by the developer.
     * @param singlePermission is a permission.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getSinglePermission(singlePermission: String) = crPermissionFragment?.requestSinglePermission(singlePermission)

    /**
     * This is the function will ask one given permission with a message if the app needs this particular permission
     * which is mandatory.
     * @param message is the important message of the app. It will be showed before asking the permission
     * @param singlePermission is a permission.
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun getPermissionWithAlertDialog(message: String, singlePermission: String) {
        AlertDialog.Builder(context).setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                getSinglePermission(singlePermission)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
//                smsAndStoragePermissionHandler.cancel()
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }

    /**
     * This is the function will ask one given permission with a message if the app needs this particular permission
     * which is mandatory.
     * @param message is the important message of the app. It will be showed before asking the permission
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    fun sendSettingsToGetPermission(message: String) {
        AlertDialog.Builder(context).setMessage(message)
            .setPositiveButton("Settings") { _, _ ->
                crPermissionFragment?.requestPermissionInSetting()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }

    private fun isRevoked(permission: String): Boolean {
        return isGreaterM() && crPermissionFragment?.isRevoked(permission)!!
    }

    private fun isGranted(permission: String): Boolean {
        return isGreaterM() && crPermissionFragment?.isGranted(permission)!!
    }

    private fun isGreaterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
    private fun shouldShowRequestPermission(permission: String): Boolean {
        return crPermissionFragment?.shouldShowRequestPermission(permission) ?: false
    }

    fun hasPermission(permission: String): Boolean {
        return when {
            shouldShowRequestPermission(permission) -> {
                getSinglePermission(permission)
                false
            }
            isGranted(permission) -> true
            else -> {
                sendSettingsToGetPermission("You have permanently deny the permission of $permission.\nPlease go to Settings for the giving the permission")
                false
            }
        }
    }
}