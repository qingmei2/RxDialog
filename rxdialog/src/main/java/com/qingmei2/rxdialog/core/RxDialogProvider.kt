package com.qingmei2.rxdialog.core

import android.content.Context
import com.qingmei2.rxdialog.RxDialog
import com.qingmei2.rxdialog.entity.RxEvent
import io.reactivex.Observable
import java.util.*

object RxDialogProvider {

    fun <T> show(context: Context,
                 factory: RxDialog.Factory<T>): Observable<RxEvent<T>> {
        return factory.create().showDialog(context)
    }
}