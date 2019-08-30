package dev.sunnat629.crpermissions

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.Uri.fromParts
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import timber.log.Timber


class CrPermissionFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestAllPermissions(@NonNull permissionArray: Array<String>){
        requestPermissions(permissionArray, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestSinglePermission(@NonNull singlePermission: String){
        requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionArray(@NonNull permissionArray: Array<String>) {
        requestPermissions(permissionArray, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissions.mapIndexed { index, permission ->
            when(grantResults[index]){
                PERMISSION_GRANTED -> Timber.d("$permission is granted.")
                PERMISSION_DENIED -> Timber.d("$permission is denied.")
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isGranted(permission: String): Boolean {
        val fragmentActivity: FragmentActivity = activity
            ?: throw IllegalStateException("This fragment must be attached to an activity.")

        return fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun isRevoked(permission: String): Boolean? {
        val fragmentActivity: FragmentActivity = activity
            ?: throw IllegalStateException("This fragment must be attached to an activity.")

        return fragmentActivity.packageName?.let {
            fragmentActivity.packageManager?.isPermissionRevokedByPolicy(permission,it)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 629
    }
}