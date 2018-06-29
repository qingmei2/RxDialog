package com.qingmei2.rxdialog

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import com.qingmei2.rxdialog.core.ServiceMethod
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import com.qingmei2.rxdialog.util.Utils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
object RxDialog {

    private val serviceMethodCache = ConcurrentHashMap<Method, ServiceMethod>()

    fun <T> create(service: Class<T>): T {
        // check service class correct
        Utils.validateServiceInterface(service)

        return Proxy.newProxyInstance(
                service.classLoader,
                arrayOf(service)
        ) { _, method, args ->
            showObservableMessageDialog(loadServiceMethod(method, args).context,
                    "我是标题",
                    "我是内容",
                    EventType.CALLBACK_TYPE_OK,
                    EventType.CALLBACK_TYPE_CANCEL,
                    EventType.CALLBACK_TYPE_DISMISS)
        } as T
    }

    private fun loadServiceMethod(method: Method,
                                  args: Array<Any>): ServiceMethod {
        return serviceMethodCache[method] ?: synchronized(serviceMethodCache) {
            ServiceMethod(this, method, args).also {
                serviceMethodCache[method] = it
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun showObservableMessageDialog(context: Context,
                                            title: CharSequence = "",
                                            message: CharSequence = "",
                                            vararg buttons: EventType = arrayOf()): Any {
        val sendDismissEvent = buttons.contains(EventType.CALLBACK_TYPE_DISMISS)

        return Observable.create { emitter: ObservableEmitter<Event> ->
            val callback: (event: Event) -> Unit = { event: Event ->
                emitter.onNext(event)
            }

            val builder = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)

            configureButton(builder,
                    callback,
                    buttons)

            builder.setOnDismissListener { dialog: DialogInterface ->
                if (sendDismissEvent)
                    emitter.onNext(Event(dialog, EventType.CALLBACK_TYPE_DISMISS))
                emitter.onComplete()
            }
            builder.show()
        }
    }

    private fun configureButton(builder: AlertDialog.Builder,
                                callback: (event: Event) -> Unit,
                                buttons: Array<out EventType>) {
        if (buttons.isEmpty()) {
            builder.setPositiveButton(R.string.dialog_button_ok, null)
            return
        }

        for (button in buttons) {
            when (button) {
                EventType.CALLBACK_TYPE_CANCEL ->
                    builder.setNegativeButton(
                            R.string.dialog_button_cancel
                    ) { dialog: DialogInterface, _: Int ->
                        callback(Event(dialog, EventType.CALLBACK_TYPE_CANCEL))
                    }
                EventType.CALLBACK_TYPE_OK ->
                    builder.setPositiveButton(
                            R.string.dialog_button_ok
                    ) { dialog: DialogInterface, _: Int ->
                        callback(Event(dialog, EventType.CALLBACK_TYPE_OK))
                        dialog.dismiss()
                    }
                else -> builder.setPositiveButton(
                        R.string.dialog_button_retry
                ) { dialog: DialogInterface, _: Int ->
                    callback(Event(dialog, EventType.CALLBACK_TYPE_RETRY))
                    dialog.dismiss()
                }
            }
        }
    }
}