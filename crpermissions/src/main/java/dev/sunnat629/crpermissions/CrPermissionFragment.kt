package dev.sunnat629.crpermissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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

    fun requestPermissions(){

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
}