package com.qingmei2.rxdialog.entity.factory

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import com.qingmei2.rxdialog.entity.DEFAULT_DIALOG_COLOR_RES
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import com.qingmei2.rxdialog.entity.options.SimpleDialogOption
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class SimpleDialogFactory(private val options: SimpleDialogOption) : RxDialogFactory {

    override fun observable(): Observable<Event> =
            initSimpleObservableDialog(options = options)

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initSimpleObservableDialog(options: SimpleDialogOption): Observable<Event> {
        val sendDismissEvent = options.buttons.contains(EventType.CALLBACK_TYPE_DISMISS)

        return Observable.create { emitter: ObservableEmitter<Event> ->
            val callback: (event: Event) -> Unit = { event: Event ->
                emitter.onNext(event)
            }

            val builder = AlertDialog.Builder(options.context)
                    .setTitle(options.title)
                    .setMessage(options.message)
                    .setCancelable(options.cancelable)

            configureButton(builder, callback, options)

            // If the device sdk version >= 17, set the dismiss listener.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                builder.setOnDismissListener { dialog: DialogInterface ->
                    if (sendDismissEvent)
                        emitter.onNext(Event(dialog, EventType.CALLBACK_TYPE_DISMISS))
                    emitter.onComplete()
                }
            }
            val dialog = builder.show()

            if (options.negativeTextColor != DEFAULT_DIALOG_COLOR_RES) {
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                        ?.setTextColor(options.negativeTextColor)
            }

            if (options.positiveTextColor != DEFAULT_DIALOG_COLOR_RES) {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        ?.setTextColor(options.positiveTextColor)
            }
        }
    }

    private fun configureButton(builder: AlertDialog.Builder,
                                callback: (event: Event) -> Unit,
                                options: SimpleDialogOption) {
        if (options.buttons.isEmpty()) {
            throw NullPointerException("the buttons value in the '@Dialog' annotation can't be empty!")
        }

        for (button in options.buttons) {
            when (button) {
                EventType.CALLBACK_TYPE_NEGATIVE ->
                    builder.setNegativeButton(
                            options.negativeText
                    ) { dialog: DialogInterface, _: Int ->
                        callback(Event(dialog, EventType.CALLBACK_TYPE_NEGATIVE))
                    }
                EventType.CALLBACK_TYPE_POSITIVE ->
                    builder.setPositiveButton(
                            options.positiveText
                    ) { dialog: DialogInterface, _: Int ->
                        callback(Event(dialog, EventType.CALLBACK_TYPE_POSITIVE))
                        dialog.dismiss()
                    }
                else -> {
                }
            }
        }
    }
}