package dev.sunnat629.crpermissions

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PermissionListener {
    override fun onPermissionRationaleShouldBeShown(permission: String) {
        Log.d("ASDFG", "$permission: onPermissionRationaleShouldBeShown")

    }

    override fun onPermissionGranted(permission: String) {
        Log.d("ASDFG", "$permission: onPermissionGranted")

    }

    override fun onPermissionDenied(permission: String) {
        Log.d("ASDFG", "$permission: onPermissionDenied")
    }

    private lateinit var crPermissions: CrPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ASDF", "onCreate")
        setContentView(R.layout.activity_main)
        crPermissions = CrPermissions(this, this)

        ask_permission.setOnClickListener{
            if (crPermissions.hasPermission(Manifest.permission.CAMERA)){
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivity(intent)
            }
//            crPermissions.getAllPermissions()

//            crPermissions.getPermissionWithAlertDialog("Need Camera Permission", Manifest.permission.CAMERA)
        }
//        crPermissions.getAllPermissions()
//        crPermissions.getPermission(Manifest.permission.CAMERA)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("ASDF", "onRestoreInstanceState")

        crPermissions = CrPermissions(this, this)
    }
}