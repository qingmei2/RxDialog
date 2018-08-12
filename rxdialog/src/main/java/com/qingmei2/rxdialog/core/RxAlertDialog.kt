package com.qingmei2.rxdialog.core

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import com.qingmei2.rxdialog.RxDialog
import com.qingmei2.rxdialog.entity.RxEvent
import com.qingmei2.rxdialog.entity.SystemEvent
import com.qingmei2.rxdialog.ext.getColorByResId
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class RxAlertDialog private constructor(private val title: Supplier<String>,
                                        private val message: Supplier<String>,
                                        private val positiveText: Supplier<String>,
                                        private val negativeText: Supplier<String>,
                                        private val positiveTextColor: Supplier<Int>,
                                        private val negativeTextColor: Supplier<Int>,
                                        private val buttons: Array<SystemEvent>,
                                        private val cancelable: Boolean) : RxDialog<SystemEvent> {

    private constructor(builder: RxAlertDialog.InnerBuilder) : this(
            builder.title,
            builder.message,
            builder.positiveText,
            builder.negativeText,
            builder.positiveTextColor,
            builder.negativeTextColor,
            builder.buttons,
            builder.cancelable
    )

    override fun showDialog(context: Context): Observable<RxEvent<SystemEvent>> {
        val sendDismissEvent = buttons.contains(SystemEvent.CALLBACK_TYPE_DISMISS)

        return Observable.create { emitter: ObservableEmitter<RxEvent<SystemEvent>> ->
            val callback: (event: RxEvent<SystemEvent>) -> Unit = { event: RxEvent<SystemEvent> ->
                emitter.onNext(event)
            }

            val builder = AlertDialog.Builder(context)
                    .setTitle(title())
                    .setMessage(message())
                    .setCancelable(cancelable)
                    .apply { configureButton(this, callback, buttons) }

            // If the device sdk version >= 17, set the dismiss listener.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                builder.setOnDismissListener { dialog: DialogInterface ->
                    if (sendDismissEvent)
                        emitter.onNext(RxEvent(dialog, SystemEvent.CALLBACK_TYPE_DISMISS))
                    emitter.onComplete()
                }
            }
            builder.show().also {

                it.getButton(DialogInterface.BUTTON_POSITIVE).apply {
                    val colorRes = positiveTextColor()
                    if (colorRes != DEFAULT_DIALOG_COLOR)
                        setTextColor(context.getColorByResId(colorRes))
                }

                it.getButton(DialogInterface.BUTTON_NEGATIVE).apply {
                    val colorRes = negativeTextColor()
                    if (colorRes != DEFAULT_DIALOG_COLOR)
                        setTextColor(context.getColorByResId(colorRes))
                }
            }
        }
    }

    private fun configureButton(builder: AlertDialog.Builder,
                                callback: (event: RxEvent<SystemEvent>) -> Unit,
                                buttons: Array<out SystemEvent>) {
        if (buttons.isEmpty()) {
            throw NullPointerException("the buttons value in the '@Dialog' annotation can't be empty!")
        }

        for (button in buttons) {
            when (button) {
                SystemEvent.CALLBACK_TYPE_NEGATIVE ->
                    builder.setNegativeButton(negativeText()) { d: DialogInterface, _: Int ->
                        callback(RxEvent(d, SystemEvent.CALLBACK_TYPE_NEGATIVE))
                    }
                SystemEvent.CALLBACK_TYPE_POSITIVE ->
                    builder.setPositiveButton(positiveText()) { d: DialogInterface, _: Int ->
                        callback(RxEvent(d, SystemEvent.CALLBACK_TYPE_POSITIVE))
                    }
                else -> {

                }
            }
        }
    }

    companion object Builder {

        fun build(suppiler: InnerBuilder.() -> Unit): InnerBuilder = InnerBuilder(suppiler).build()

        internal const val DEFAULT_DIALOG_COLOR = -1
        internal const val DEFAULT_DIALOG_CANCELABLE = true
        internal const val DEFAULT_DIALOG_STRING = ""
    }

    class InnerBuilder private constructor() : RxDialog.Factory<SystemEvent> {

        override fun create(): RxAlertDialog {
            return RxAlertDialog(this)
        }

        constructor(builder: InnerBuilder.() -> Unit) : this() {
            builder()
        }

        @StringRes
        var title: Supplier<String> = { DEFAULT_DIALOG_STRING }
        @StringRes
        var message: Supplier<String> = { DEFAULT_DIALOG_STRING }
        @StringRes
        var positiveText: Supplier<String> = { DEFAULT_DIALOG_STRING }
        @StringRes
        var negativeText: Supplier<String> = { DEFAULT_DIALOG_STRING }
        @ColorRes
        var positiveTextColor: Supplier<Int> = { DEFAULT_DIALOG_COLOR }
        @ColorRes
        var negativeTextColor: Supplier<Int> = { DEFAULT_DIALOG_COLOR }

        var buttons: Array<SystemEvent> = arrayOf()
        var cancelable: Boolean = DEFAULT_DIALOG_CANCELABLE

        fun withTitle(title: String) = withTitle { title }

        fun withTitle(suppiler: Supplier<String>) = apply {
            title = suppiler
        }

        fun withMessage(message: String) = withMessage { message }

        fun withMessage(suppiler: Supplier<String>) = apply {
            message = suppiler
        }

        fun withPositiveText(positiveText: String) = withPositiveText { positiveText }

        fun withPositiveText(suppiler: Supplier<String>) = apply {
            positiveText = suppiler
        }

        fun withNegativeText(negativeText: String) = withNegativeText { negativeText }

        fun withNegativeText(suppiler: Supplier<String>) = apply {
            negativeText = suppiler
        }

        fun withPositiveTextColor(color: Int) = withPositiveTextColor { color }

        fun withPositiveTextColor(suppiler: Supplier<Int>) = apply {
            positiveTextColor = suppiler
        }

        fun withNegativeTextColor(color: Int) = withNegativeTextColor { color }

        fun withNegativeTextColor(suppiler: Supplier<Int>) = apply {
            negativeTextColor = suppiler
        }

        fun withButtons(buttons: Array<SystemEvent>) = apply {
            this.buttons = buttons
        }

        fun withCancelable(cancelable: Boolean) = apply {
            this.cancelable = cancelable
        }

        fun build() = this
    }
}

typealias Supplier<T> = () -> T