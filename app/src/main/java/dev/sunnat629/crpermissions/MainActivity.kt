package dev.sunnat629.crpermissions

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var crPermissions: CrPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crPermissions = CrPermissions(this)
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
}