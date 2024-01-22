package com.example.chapter14notekeepingapp.permisions//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//
//class MainActivity : AppCompatActivity() {
//
//    private val permissions = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//        // Add more permissions as needed
//    )
//
//    private val requestMultiplePermissions =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            // Check if all permissions are granted
//            if (permissions.all { it.value }) {
//                // All permissions are granted
//                // Continue with your application logic
//            } else {
//                // At least one permission is denied
//                // Handle accordingly (e.g., show a message, disable features)
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Check for permissions at runtime (for devices running Android 6.0 or higher)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkAndRequestPermissions()
//        }
//    }
//
//    private fun checkAndRequestPermissions() {
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
//        // Request permissions using ActivityResultLauncher
//        if (permissionsToRequest.isNotEmpty()) {
//            requestMultiplePermissions.launch(permissionsToRequest.toTypedArray())
//        }
//    }
//}
