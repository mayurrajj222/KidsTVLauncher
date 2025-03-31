package com.example.kidstvlauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridApps: GridView
    private lateinit var tvError: TextView
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        tvError = findViewById(R.id.tvError)
        gridApps = findViewById(R.id.gridApps)
        btnExit = findViewById(R.id.btnExit)

        // Exit button click listener
        btnExit.setOnClickListener {
            val intent = Intent(this, ExitPinActivity::class.java)
            startActivity(intent)
        }

        // Load apps when the activity starts
        loadApps()
    }

    private fun loadApps() {
        // Create an intent to get all installed apps with launcher category
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val packageManager: PackageManager = packageManager
        val allApps: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)

        // Filter approved apps from the list (based on package name)
        val approvedApps = allApps.filter { app ->
            AppConfig.approvedApps.contains(app.activityInfo.packageName)
        }

        if (approvedApps.isEmpty()) {
            // If no approved apps, show error message
            showError("No approved apps available")
        } else {
            // If approved apps are found, display them in the GridView
            tvError.visibility = View.GONE
            gridApps.visibility = View.VISIBLE

            // Apply fade-in animation for smooth display
            val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            gridApps.startAnimation(fadeInAnimation)

            // Set the adapter for the GridView to show the app icons
            gridApps.adapter = AppAdapter(this, approvedApps)
        }
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
        gridApps.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
