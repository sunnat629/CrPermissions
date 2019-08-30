# CrPermissions
This library which will allow the usage of co-routines with the new Android M permission model.

Setup
To use this library your minSdkVersion must be new than 13.

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```
dependencies {
    ...
    implementation 'com.github.sunnat629:CrPermissions:0.0.1'
}
```

### Usage
Create a `CrPermissions` instance (It can be use in `Activity` or `Fragment`):

```
private lateinit var crPermissions: CrPermissions

override fun onCreate(savedInstanceState: Bundle?) {
    ...
    crPermissions = CrPermissions(this)
    ...
}
```
## What's New

### v.0.0.1 (Release Date: 30 August 2019)
- **`getAllPermissions()`**: This is the function will read automatically the list of the permissions of the app manifest.
```
    fun getAllPermissions(view: View) {
        crPermissions.getAllPermissions()
    }
```
- **`getPermissionArray(permissionArray: Array<String>)`**: This is the function will read a list of permissions which will be given by the developer. `permissionArray` parameter is the array of permissions.
```
    fun getPermissionArray(view: View) {
        crPermissions.getPermissionArray(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            )
        )
    }
```
- **`getSinglePermission(singlePermission: String)`**: This is the function which reads only one permission which will be given by the developer. `singlePermission` parameter is a permission.
```
    fun getSinglePermission(view: View) {
        crPermissions.getSinglePermission(Manifest.permission.CAMERA)
    }
```

**NOTE:** In this version, if you check the `Don't ask again`, then you have to go manually to the settings and give the permission.

### Future Plan
- Implement Handler/Listener
- Mandatory Permission
- Ask permission with additional message
- Check the list of the permission conditions


