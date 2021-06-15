package com.appat.squircleavatar.utilities

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.appat.squircleavatar.utilities.Constants.ddMMyyyyTime
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utility {

    private fun getContext(): Context {
        return SquircleApplication.app!!.applicationContext
    }

    fun getString(resId: Int):String {
        return SquircleApplication.app?.getString(resId) ?: ""
    }

    fun formatLong(value: Long): String
    {
        return String.format("%,d", value)
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(getContext(), color)
    }

    fun getDrawable(drawable: Int): Drawable? {
        return ContextCompat.getDrawable(getContext(), drawable)
    }

    fun getDateTime(timeStamp: Long?): String {
        try {
            if(timeStamp != null)
            {
                val sdf = SimpleDateFormat(ddMMyyyyTime, getLocaleForDate())
                val netDate = Date(timeStamp)
                return sdf.format(netDate)
            }
        } catch (ex: Exception) {
            return ""
        }
        return ""
    }

    private fun getLocaleForDate(): Locale
    {
        return Locale("en")
    }

    fun fromDateString(value: String?, format: String): Date? {
        val df: DateFormat = SimpleDateFormat(format, getLocaleForDate())
        return if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }

    fun dateToString(date: Date?, format: String): String {
        if(date == null)
        {
            return ""
        }
        val df: DateFormat = SimpleDateFormat(format, getLocaleForDate())
        return df.format(date) ?: ""
    }

    fun formatDate(date: String?, inputFormat: String, outputFormat: String): String
    {
        if(date.isNullOrEmpty())
        {
            return ""
        }
        val inputDateFormat = SimpleDateFormat(inputFormat, getLocaleForDate())
        val outputDateFormat = SimpleDateFormat(outputFormat, getLocaleForDate())
        return try {
            val inputDate = inputDateFormat.parse(date)
            outputDateFormat.format(inputDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun getDrawableFromUrl(context: Context, url: String, success: (Drawable?) -> Unit)
    {
        Glide.with(context).load(url).into(object : CustomTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                success(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
    }

    fun openUrlInCustomTab(uriString: String, context: AppCompatActivity)
    {
        Log.d("openUrlInCustomTab", uriString)
        val uri = Uri.parse(uriString)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent :CustomTabsIntent  = builder.build()
        customTabsIntent.launchUrl(context, uri)
    }
}