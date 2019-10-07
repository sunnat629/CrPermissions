package dev.sunnat629.crpermissions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class CRPViewModel : ViewModel() {

    private lateinit var permissionsHandler: CrPermissionsResultHandler

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private var _allGrantedPermission = NonNullMediatorLiveData<List<String>>()
    val allGrantedPermission: LiveData<List<String>>
        get() = _allGrantedPermission

    private var _allDeniedPermission = NonNullMediatorLiveData<List<String>>()
    val allDeniedPermission: LiveData<List<String>>
        get() = _allDeniedPermission

    private var _allBlockedPermission = NonNullMediatorLiveData<List<String>>()
    val allBlockedPermission: LiveData<List<String>>
        get() = _allBlockedPermission


    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}