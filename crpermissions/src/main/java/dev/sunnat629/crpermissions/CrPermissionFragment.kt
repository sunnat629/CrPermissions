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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


class CrPermissionFragment: Fragment() {

    private lateinit var permissionList: ArrayList<String>


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
    fun requestAllPermissions(@NonNull permissions: Array<String>){
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestSinglePermission(singlePermission: String){
        requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("ASDFG", "${permissions.contentToString()} ")
        Log.i("ASDFG", "${grantResults.contentToString()}} ")
    }



    private fun requestSinglePermissionaaa(singlePermission: String) {
        if (activity?.let {
                checkSelfPermission(it, singlePermission)
            } != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(singlePermission)) {
                requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
//                val intent = Intent()
//                intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
//                val uri = fromParts("package", activity?.packageName, null)
//                intent.data = uri
//                startActivity(intent)
                Log.e("ASDFG", "$singlePermission go to Settings")
            } else {
                requestPermissions(arrayOf(singlePermission), PERMISSIONS_REQUEST_CODE)
                Log.e("ASDFG", "$singlePermission is requestPermissions!")
            }
        } else {
            Log.e("ASDFG", "$singlePermission is granted!")
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

    private fun setPermissionList(permissions: ArrayList<String>){
        this.permissionList = permissions
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
            this.startActivityForResult(intent, PERMISSIONS_REQUEST_CODE)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 629

        fun newInstance(permissions: ArrayList<String>): CrPermissionFragment {
            val fragment = CrPermissionFragment()
            fragment.setPermissionList(permissions)
            return fragment
        }
    }
}