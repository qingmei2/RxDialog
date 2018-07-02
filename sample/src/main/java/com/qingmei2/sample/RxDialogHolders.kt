package com.qingmei2.sample

import android.content.Context
import com.qingmei2.rxdialog.entity.Dialog
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.Observable

interface RxDialogHolders {

    @Dialog(title = R.string.static_dialog_title,
            message = R.string.static_dialog_message,
            positiveText = R.string.static_dialog_button_ok,
            negativeText = R.string.static_dialog_button_cancel,
            buttons = [(EventType.CALLBACK_TYPE_NEGATIVE), (EventType.CALLBACK_TYPE_POSITIVE)])
    fun simpleDialog(context: Context): Observable<Event>

}