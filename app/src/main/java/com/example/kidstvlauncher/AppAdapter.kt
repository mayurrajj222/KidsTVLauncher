package com.example.kidstvlauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AppAdapter(
    private val context: Context,
    private val appList: List<ResolveInfo>
) : BaseAdapter() {

    override fun getCount() = appList.size

    override fun getItem(position: Int) = appList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_app, parent, false)

        val appIcon = view.findViewById<ImageView>(R.id.appIcon)
        val appName = view.findViewById<TextView>(R.id.appName)

        val packageManager = context.packageManager
        val appInfo = appList[position]

        appIcon.setImageDrawable(appInfo.loadIcon(packageManager))
        appName.text = appInfo.loadLabel(packageManager)

        view.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage(appInfo.activityInfo.packageName)
            context.startActivity(launchIntent)
        }

        return view
    }
}
