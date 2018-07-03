package com.qingmei2.rxdialog.core.factory

import com.qingmei2.rxdialog.entity.Event
import io.reactivex.Observable

interface DialogFactory {

    fun observable(): Observable<Event>
}