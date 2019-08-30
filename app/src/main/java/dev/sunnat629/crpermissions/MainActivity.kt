package dev.sunnat629.crpermissions

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var crPermissions: CrPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crPermissions = CrPermissions(this)
//        crPermissions.getAllPermissions()
        crPermissions.getPermission(Manifest.permission.CAMERA)
//        crPermissions.getPermissionAA(Manifest.permission.CAMERA)

    }
}