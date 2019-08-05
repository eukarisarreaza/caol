@file:Suppress("DEPRECATION")

package com.cplus.android.utilities

import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager


class Utils {
    companion object {
        private const val preferences = "preferences"
        private var sharedPreferences: SharedPreferences? = null
        private var progressDialog: ProgressDialog? = null


        fun twoDigits(n: Int): String {
            return if (n <= 9) "0$n" else n.toString()
        }


        fun dateFormat(date: Date, dFormat: String = "dd-MM-yyyy"): String {
            val dateFormat = SimpleDateFormat(dFormat, Locale.getDefault())
            return dateFormat.format(date)
        }

        fun dateFormat(date: String, dFormat: String = "dd-MM-yyyy"): Date {
            val dateFormat = SimpleDateFormat(dFormat, Locale.getDefault())
            return dateFormat.parse(date)
        }

        fun setPreferences(ctx: Context, key: String, currentValue: Any) {
            sharedPreferences = ctx.getSharedPreferences(preferences, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()

            when (currentValue) {
                is Int -> editor?.putInt(key, currentValue)
                is Long -> editor?.putLong(key, currentValue)
                is Boolean -> editor?.putBoolean(key, currentValue)
                else -> editor?.putString(key, "$currentValue")
            }
            editor?.apply()
        }

        fun getPreferences(ctx: Context, key: String, defaultStr: Any, type: String? = ""): Any? {
            sharedPreferences = ctx.getSharedPreferences(preferences, Context.MODE_PRIVATE)
            return when (type) {
                "i" ->
                    sharedPreferences?.getInt(key, -1)
                "l" ->
                    sharedPreferences?.getLong(key, -1L)
                "b" ->
                    sharedPreferences?.getBoolean(key, defaultStr as Boolean)
                else ->
                    sharedPreferences?.getString(key, "$defaultStr")
            }
        }

        fun removePreferences(ctx: Context, key: String) {
            sharedPreferences = ctx.getSharedPreferences(preferences, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.remove(key)?.apply()
        }

        fun clearPreference(ctx: Context) {
            sharedPreferences = ctx.getSharedPreferences(preferences, Context.MODE_PRIVATE)
            val edit = sharedPreferences?.edit()
            edit?.clear()
            edit?.apply()
        }

        fun showProgressDialog(activity: AppCompatActivity, msg: String?) {
            progressDialog = ProgressDialog(activity)
            progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog?.setMessage(msg)
            progressDialog?.setCancelable(false)
            progressDialog?.isIndeterminate = true
            progressDialog?.show()
        }

        fun dismissProgressDialog() {
            progressDialog?.dismiss()
        }

        /**
         * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
         *
         * @return
         */
        fun isNetworkConnected(activity: AppCompatActivity): Boolean {
            val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }



        fun getDateFormatted(startDate: String): String{
            val fecha= dateFormat(startDate, "yyyy-MM-dd")

            var formato_dia= SimpleDateFormat("dd").format(fecha)
            var formato_mes= SimpleDateFormat("MMM").format(fecha)
            var formato_anio= SimpleDateFormat("yyyy").format(fecha)

            return "$formato_dia de $formato_mes $formato_anio"
        }


        fun getDateFormatted(startDate: String,  dFormat: String = "yyyy-MM-dd HH:mm:ss"): String{
            val fecha= dateFormat(startDate, dFormat)

            var formato_dia= SimpleDateFormat("dd").format(fecha)
            var formato_mes= SimpleDateFormat("MMM").format(fecha)
            var formato_anio= SimpleDateFormat("yyyy").format(fecha)

            return "$formato_dia de $formato_mes $formato_anio"
        }

        fun getVersionName(ctx: Context): String {
            try {
                return ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

    }
}

