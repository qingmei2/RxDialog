package com.qingmei2.sample

import android.app.Application
import com.qingmei2.rxdialog.RxDialog

class SampleApplication : Application() {

    // make RxDialogHolders object singleton
    val dialogHolders: RxDialogHolders by lazy {
        RxDialog.create(RxDialogHolders::class.java)
    }
}