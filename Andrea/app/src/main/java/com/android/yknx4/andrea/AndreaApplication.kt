package com.android.yknx4.andrea

import android.app.Application
import io.paperdb.Paper

/**
 * Created by yknx4 on 3/4/18.
 */
class AndreaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }

}