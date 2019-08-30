package dev.sunnat629.crpermissions

interface CrPermissionsHandler {

    fun onPermissionGranted(permission: String)

    fun onPermissionDenied(permission: String)

    fun onPermissionRationaleShouldBeShown(permission: String)
}

interface CrMandatoryPermissionHandler {

    fun onMandatoryPermissionDenied()
}