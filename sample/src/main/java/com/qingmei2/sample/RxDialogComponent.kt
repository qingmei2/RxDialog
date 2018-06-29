package com.qingmei2.sample

import android.content.Context
import com.qingmei2.rxdialog.entity.Dialog
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.Observable

interface RxDialogComponent {

    @Dialog(title = R.string.static_dialog_title,
            message = R.string.static_dialog_message,
            positiveText = R.string.dialog_button_ok,
            nagativeText = R.string.dialog_button_cancel,
            buttons = arrayOf(
                    EventType.CALLBACK_TYPE_CANCEL, EventType.CALLBACK_TYPE_OK
            ))
    fun instance(context: Context): Observable<Event>

}