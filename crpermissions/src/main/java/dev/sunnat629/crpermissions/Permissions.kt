package dev.sunnat629.crpermissions

class Permissions() {

    private lateinit var name: String
    private var granted: Boolean = false
    private var showRequestPermissionAgain: Boolean = false

    private val permissionList: List<String> = emptyList()

    constructor(
        name: String,
        granted: Boolean): this(){
        Permissions(name, granted, false)
    }
    constructor(
        name: String,
        granted: Boolean,
        showRequestPermissionAgain: Boolean): this(){
        this.name = name
        this.granted = granted
        this.showRequestPermissionAgain = showRequestPermissionAgain
    }




}