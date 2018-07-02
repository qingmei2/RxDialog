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
            positiveTextColor = R.color.positive_color,
            negativeText = R.string.static_dialog_button_cancel,
            negativeTextColor = R.color.negative_color,
            buttons = [
                EventType.CALLBACK_TYPE_NEGATIVE,
                EventType.CALLBACK_TYPE_POSITIVE
            ])
    fun simpleDialog(context: Context): Observable<Event>

    @Dialog(message = R.string.static_dialog_message_alert,
            positiveText = R.string.static_dialog_button_ok,
            buttons = [
                EventType.CALLBACK_TYPE_POSITIVE,
                EventType.CALLBACK_TYPE_DISMISS
            ])
    fun alertDialog(context: Context): Observable<Event>

}