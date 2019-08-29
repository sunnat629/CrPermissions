package dev.sunnat629.crpermissions

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri.fromParts
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class CrPermissionFragment : Fragment() {

    private lateinit var permissionList: Array<String>
    private lateinit var listener: PermissionListener

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
    fun requestAllPermissions(@NonNull permissions: Array<String>) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestSinglePermissionArray(permissionArray: Array<String>) {
        setPermissionArray(permissionArray)
        requestPermissions(permissionArray, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestSinglePermission(singlePermission: String) {
        setPermissionArray(arrayOf(singlePermission))
        requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissions.mapIndexed { index, permission ->
            when (grantResults[index]) {
                    PERMISSION_GRANTED -> listener.onPermissionGranted(permission)
                    PERMISSION_DENIED -> {
                        if(shouldShowRequestPermissionRationale(permission)){
                            listener.onPermissionRationaleShouldBeShown(permission)
                        } else{
                            listener.onPermissionDenied(permission)
                        }
                    }
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
            fragmentActivity.packageManager?.isPermissionRevokedByPolicy(permission, it)
        }
    }

    private fun setPermissionArray(permissions: Array<String>) {
        this.permissionList = permissions
    }

    private fun setPermissionStatusListener(listener: PermissionListener) {
        this.listener = listener
    }

    fun requestPermissionInSetting() {
        val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
        val packageName = activity?.packageName ?: run {
            this.requireActivity().packageName
        }
        val uri = fromParts("package", packageName, null)
        intent.data = uri
        activity?.apply {
            startActivityForResult(intent, PERMISSIONS_REQUEST_CODE)
        } ?: run {
            startActivityForResult(intent, PERMISSIONS_REQUEST_CODE)
        }
    }

    fun shouldShowRequestPermission(@NonNull permission: String): Boolean {
        return shouldShowRequestPermissionRationale(permission)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 629

        fun newInstance(
            permissions: Array<String>,
            listener: PermissionListener
        ): CrPermissionFragment {
            Log.d("ASDF", "newInstance")

            val fragment = CrPermissionFragment()
            fragment.setPermissionArray(permissions)
            fragment.setPermissionStatusListener(listener)
            return fragment
        }
    }
}