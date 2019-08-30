package dev.sunnat629.crpermissions

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri.fromParts
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class CrPermissionFragment : Fragment(){

    private lateinit var permissionsHandler: PermissionsResultHandler
    private lateinit var listener: PermissionsResultHandler.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        permissionsHandler = PermissionsResultHandler(listener)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestAllPermissions(@NonNull permissions: Array<String>) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionArray(permissionArray: Array<String>) {
        requestPermissions(permissionArray, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestSinglePermission(singlePermission: String) {
        requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.let { permissionsHandler?.onResult(it, permissions, grantResults) }
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

    private fun setPermissionsResultListener(listener: PermissionsResultHandler.Listener) {
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
            listener: PermissionsResultHandler.Listener
        ): CrPermissionFragment {
            val fragment = CrPermissionFragment()
            fragment.setPermissionsResultListener(listener)
            return fragment
        }
    }
}