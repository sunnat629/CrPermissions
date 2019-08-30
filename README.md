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
    implementation 'com.github.sunnat629:CrPermissions:1.0.0'
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

### v.1.1.0 (Release Date: 31 August 2019)

#### **PermissionsResultHandler.Listener**: 
```
interface Listener {
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
```

Usages in UI -
```
class MainActivity : AppCompatActivity(), PermissionsResultHandler.Listener {
        private lateinit var crPermissions: CrPermissions
        ...
        ...
        override fun onCreate(savedInstanceState: Bundle?) {
        ...
        ...
        crPermissions = CrPermissions(this, this)
    }

    override fun onPermissionGranted(permission: String) {
        Timber.tag("TAG").d("onPermissionGranted: ${Utils.getPermissionName(permission)}")
    }

    override fun onPermissionDenied(permission: String) {
        Timber.tag("TAG").e("onPermissionDenied: ${Utils.getPermissionName(permission)}")
    }

    override fun onPermissionRationaleShouldBeShown(permission: String) {
        Timber.tag("TAG").i("onPermissionRationaleShouldBeShown: ${Utils.getPermissionName(permission)}")
    }
}

```

#### **`getPermissionWithAlertDialog(message: String, singlePermission: String)`**: This is the function will ask one given permission with a message if the app needs this particular permission which is mandatory. `message` parameter is the important message of the app. It will be showed before asking the permission. `singlePermission` parameter is a permission.
```
crPermissions.getPermissionWithAlertDialog(
        "This permission is important to open some features.",
        Manifest.permission.CAMERA
 )
```

#### **`sendSettingsToGetPermission(message: String)`**: This is the function will ask one given permission with a message if the app needs this particular permission which is mandatory. `message` parameter is the important message of the app. It will be showed before asking the permission.
```
sendSettingsToGetPermission(
            "You have permanently deny the permission of ${Utils.getPermissionName(
                permission
            )}.\nPlease go to Settings for the giving the permission"
        )
```


#### **`hasPermission(permission: String)`**: Return the permission status as Boolean before use the permitted features like Camera, Call Log, read/write storage etc. If the permission is Granted already, it will return `true`. If the user Denied without check the "Don't ask again", it will again ask the permission and return `false`. If the user Denied and check the "Don't ask again", it will again ask to go to Settings for give the permission manually and return `false`. `permission` parameter is the name of the permission user wants to use.
```
if (crPermissions.hasPermission(Manifest.permission.CAMERA)){
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intent)
        }

```

### v.1.0.0 (Release Date: 30 August 2019)
- 1st stable version

### v.0.0.2 (Release Date: 30 August 2019)
- minor bug fixed

### v.0.0.1 (Release Date: 30 August 2019)
#### **`getAllPermissions()`**: This is the function will read automatically the list of the permissions of the app manifest.
```
    fun getAllPermissions(view: View) {
        crPermissions.getAllPermissions()
    }
```
#### **`getPermissionArray(permissionArray: Array<String>)`**: This is the function will read a list of permissions which will be given by the developer. `permissionArray` parameter is the array of permissions.
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
#### **`getSinglePermission(singlePermission: String)`**: This is the function which reads only one permission which will be given by the developer. `singlePermission` parameter is a permission.
```
    fun getSinglePermission(view: View) {
        crPermissions.getSinglePermission(Manifest.permission.CAMERA)
    }
```

**NOTE:** In this version, if you check the `Don't ask again`, then you have to go manually to the settings and give the permission.

## Future Plan
- Implement Handler/Listener
- Mandatory Permission
- Ask permission with additional message
- Check the list of the permission conditions


