package com.appat.squircleavatar.utilities

import android.app.Application

class SquircleApplication: Application() {

    companion object {
        var app: SquircleApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}