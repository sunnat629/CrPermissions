package dev.sunnat629.crpermissions

interface CrPermissionsResultHandler {

    /**
     * After grand the permission, user can get the permission and take the action of this permission
     * @param permission is granted permission
     * */
    fun onPermissionGranted(permission: String)

    /**
     * After denied the permission with check the "Don't ask again". This permission won't show anymore.
     * Need to manually modify from the Settings.
     * @param permission is denied permission
     * */
    fun onPermissionDenied(permission: String)

    /**
     * After denied the permission without check the "Don't ask again". This permission will ask again when it will trigger.
     * @param permission is denied permission
     * */
    fun onPermissionRationaleShouldBeShown(permission: String)
}