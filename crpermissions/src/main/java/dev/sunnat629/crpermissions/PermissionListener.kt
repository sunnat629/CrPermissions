package dev.sunnat629.crpermissions

interface PermissionListener {

    fun onPermissionGranted(permission: String)

    fun onPermissionDenied(permission: String)

    fun onPermissionRationaleShouldBeShown(permission: String)
}