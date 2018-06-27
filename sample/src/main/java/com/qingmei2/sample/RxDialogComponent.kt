package com.qingmei2.sample

import android.content.Context
import com.qingmei2.rxdialog.DContext
import com.qingmei2.rxdialog.DProvider
import com.qingmei2.rxdialog.Event
import io.reactivex.Observable

interface RxDialogComponent {

    @DProvider
    fun instance(@DContext context: Context): Observable<Event>
}