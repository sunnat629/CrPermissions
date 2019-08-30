package dev.sunnat629.crpermissions

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), CrMandatoryPermissionHandler {

    private lateinit var crPermissions: CrPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crPermissions = CrPermissions(this, this)
    }

    fun getAllPermissions(view: View) {
        crPermissions.getAllPermissions()
    }

    fun getPermissionArray(view: View) {
        crPermissions.getPermissionArray(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            )
        )
    }

    fun getSinglePermission(view: View) {
        crPermissions.getSinglePermission(Manifest.permission.CAMERA)
    }

    fun openCamera(view: View){
        if (crPermissions.hasPermission(Manifest.permission.CAMERA)){
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intent)
        }
    }

    fun permissionWithAlertDialog(view: View){
        crPermissions.getPermissionWithAlertDialog("This permission is important to open some features.", Manifest.permission.CAMERA)
    }

    fun mandatoryPermission(view: View){
        crPermissions.getMandatoryPermission("This is mandatory Permission, Please allow.", Manifest.permission.CAMERA)
    }

    override fun onMandatoryPermissionDenied() {
        finish()
    }
}