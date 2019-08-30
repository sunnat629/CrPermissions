package dev.sunnat629.crpermissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

open class CrPermissions(
    @NonNull private val context: Context
) {

    private var crPermissionsHandler: CrPermissionsHandler? = null
    private var mandatoryPermissionHandler: CrMandatoryPermissionHandler? = null

    constructor(
        @NonNull context: Context,
        @NonNull handler: CrPermissionsHandler
    ) : this(context) {
        this.crPermissionsHandler = handler
    }

    constructor(
        @NonNull context: Context,
        @NonNull mandatoryPermissionHandler: CrMandatoryPermissionHandler
    ) : this(context) {
        this.mandatoryPermissionHandler = mandatoryPermissionHandler
    }

    constructor(
        @NonNull context: Context,
        @NonNull handler: CrPermissionsHandler,
        @NonNull mandatoryPermissionHandler: CrMandatoryPermissionHandler
    ) : this(context, handler) {
        this.mandatoryPermissionHandler = mandatoryPermissionHandler
    }

    /**
     * It will attached the CrPermissionFragment with the application activity or framework
     * */
    private val crPermissionFragment =
        getCrPermissionsFragment((context as FragmentActivity).supportFragmentManager)

    private fun getCrPermissionsFragment(fragmentManager: FragmentManager): CrPermissionFragment? {
        val permissionFragment = CrPermissionFragment.newInstance(crPermissionsHandler)
        fragmentManager
            .beginTransaction()
            .add(permissionFragment, CrPermissions::class.java.simpleName)
            .commitNow()
        return permissionFragment
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
        if (singlePermission.isNotEmpty()){
            crPermissionFragment?.requestSinglePermission(singlePermission)
        }
    }

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
                dialog.cancel()
            }
            .setCancelable(false)
            .show()
    }

    fun getMandatoryPermission(message: String, singlePermission: String) {
        if (!isGranted(singlePermission)){
            AlertDialog.Builder(context).setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                    getSinglePermission(singlePermission)
                }
                .setNegativeButton("Exit App") { dialog, _ ->
                    mandatoryPermissionHandler?.onMandatoryPermissionDenied()
                    dialog.cancel()
                }
                .setCancelable(false)
                .show()
        }
    }

    /**
     * This is the function will ask one given permission with a message if the app needs this particular permission
     * which is mandatory.
     * @param message is the important message of the app. It will be showed before asking the permission
     * It will ask Run time permission if the Android OS is Marshmallow (M) or newer version
     * */
    private fun sendSettingsToGetPermission(message: String) {
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

    /**
     * Return the permission status as Boolean before use the permitted features like Camera, Call Log, read/write storage etc.
     * If the permission is Granted already, it will return true.
     * If the user Denied without check the "Don't ask again", it will again ask the permission and return false
     * If the user Denied and check the "Don't ask again", it will again ask to go to Settings for give the permission manually and return false
     * @param permission is the name of the permission user wants to use.
     * */
    fun hasPermission(permission: String): Boolean {
        return when {
            shouldShowRequestPermission(permission) -> {
                getSinglePermission(permission)
                false
            }
            isGranted(permission) -> true
            else -> {
                sendSettingsToGetPermission(
                    "You have permanently deny the permission of ${Utils.getPermissionName(
                        permission
                    )}.\nPlease go to Settings for the giving the permission"
                )
                false
            }
        }
    }

    private fun isRevoked(permission: String): Boolean {
        return isGreaterM() && crPermissionFragment?.isRevoked(permission)!!
    }

    private fun isGranted(permission: String): Boolean {
        return isGreaterM() && crPermissionFragment?.isGranted(permission)!!
    }

    private fun shouldShowRequestPermission(permission: String): Boolean {
        return crPermissionFragment?.shouldShowRequestPermission(permission) ?: false
    }

    private fun isGreaterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}