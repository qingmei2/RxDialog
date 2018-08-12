package com.qingmei2.rxdialog

import android.content.Context
import com.qingmei2.rxdialog.entity.RxEvent
import io.reactivex.Observable

interface RxDialog<T> {

    fun showDialog(context: Context): Observable<RxEvent<T>>

}