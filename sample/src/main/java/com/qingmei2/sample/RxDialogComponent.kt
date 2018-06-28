package com.qingmei2.sample

import android.content.Context
import com.qingmei2.rxdialog.entity.DContext
import com.qingmei2.rxdialog.entity.DProvider
import com.qingmei2.rxdialog.entity.Event
import io.reactivex.Observable

interface RxDialogComponent {

    @DProvider
    fun instance(@DContext context: Context): Observable<Event>

}