package com.qingmei2.rxdialog.entity.factory

import com.qingmei2.rxdialog.entity.Event
import io.reactivex.Observable

interface RxDialogFactory {

    fun observable(): Observable<Event>
}