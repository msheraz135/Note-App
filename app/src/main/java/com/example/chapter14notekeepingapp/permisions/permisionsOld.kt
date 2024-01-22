package com.example.chapter14notekeepingapp.permisions//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.chapter14notekeepingapp.R
//import kotlin.collections.ArrayList
//
//class MainActivity : AppCompatActivity() {
//
//    private val PERMISSION_REQUEST_CODE = 123
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Define the array of permissions you want to request
//        val permissions = arrayOf(
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            // Add more permissions as needed
//        )
//
//        // Check for permissions at runtime (for devices running Android 6.0 or higher)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkAndRequestPermissions(permissions)
//        }
//    }
//
//    private fun checkAndRequestPermissions(permissions: Array<String>) {
//        val permissionsToRequest = ArrayList<String>()
//
//        // Check each permission and add it to the list if it's not granted
//        for (permission in permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                permissionsToRequest.add(permission)
//            }
//        }
//
//        // Request permissions
//        if (permissionsToRequest.isNotEmpty()) {
//            ActivityCompat.requestPermissions(
//                this,
//                permissionsToRequest.toTypedArray(),
//                PERMISSION_REQUEST_CODE
//            )
//        }
//    }
//
//    // Handle the result of permission requests
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            // Check if all permissions are granted
//            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
//                // All permissions are granted
//                // Continue with your application logic
//            } else {
//                // At least one permission is denied
//                // Handle accordingly (e.g., show a message, disable features)
//            }
//        }
//    }
//}
