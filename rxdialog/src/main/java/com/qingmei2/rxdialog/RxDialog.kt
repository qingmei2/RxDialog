package com.qingmei2.rxdialog

import android.content.Context
import android.support.annotation.ColorRes
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.*
import io.reactivex.internal.operators.completable.CompletableEmpty

class RxDialog private constructor(val context: Context,
                                   val title: String,
                                   val message: String,
                                   val positiveText: String,
                                   @ColorRes val positiveTextColor: Int,
                                   val negativeText: String,
                                   @ColorRes val negativeTextColor: Int,
                                   val buttons: Array<EventType>,
                                   val cancelable: Boolean) {

    private constructor(builder: RxDialog.Builder) : this(
            builder.context,
            builder.title,
            builder.message,
            builder.positiveText,
            builder.positiveTextColor,
            builder.negativeText,
            builder.negativeTextColor,
            builder.buttons,
            builder.cancelable
    )

    fun observable(): Observable<Event> {
        return RxDialogController.observable(this)
    }

    fun single(): Single<Event> {
        return observable().singleOrError()
    }

    fun flowable(strategy: BackpressureStrategy = BackpressureStrategy.BUFFER): Flowable<Event> {
        return observable().toFlowable(strategy)
    }

    fun maybe(): Maybe<Event> {
        return single().toMaybe()
    }

    fun completable(): Completable {
        return observable().flatMapCompletable {
            CompletableEmpty.INSTANCE
        }
    }

    companion object {

        fun build(context: Context, builder: Builder.() -> Unit) = Builder(context, builder).build()

        internal const val DEFAULT_DIALOG_STRING_RES = -1
        internal const val DEFAULT_DIALOG_COLOR_RES = -2
        internal const val DEFAULT_DIALOG_CANCELABLE = true
        internal const val DEFAULT_DIALOG_STRING = ""
    }

    class Builder private constructor() {

        constructor(context: Context, builder: Builder.() -> Unit) : this() {
            this.context = context
            builder()
        }

        lateinit var context: Context

        var title: String = DEFAULT_DIALOG_STRING
        var message: String = DEFAULT_DIALOG_STRING
        var positiveText: String = DEFAULT_DIALOG_STRING
        var negativeText: String = DEFAULT_DIALOG_STRING

        @ColorRes
        var positiveTextColor: Int = DEFAULT_DIALOG_COLOR_RES
        @ColorRes
        var negativeTextColor: Int = DEFAULT_DIALOG_COLOR_RES

        var buttons: Array<EventType> = arrayOf()
        var cancelable: Boolean = DEFAULT_DIALOG_CANCELABLE

        fun withTitle(stringRes: Int) = apply {
            title = context.getStringByResId(stringRes)
        }

        fun withMessage(stringRes: Int) = apply {
            message = context.getStringByResId(stringRes)
        }

        fun withPositiveText(stringRes: Int) = apply {
            positiveText = context.getStringByResId(stringRes)
        }

        fun withNegativeText(stringRes: Int) = apply {
            negativeText = context.getStringByResId(stringRes)
        }

        fun withPositiveTextColor(@ColorRes colorRes: Int) = apply {
            positiveTextColor = colorRes
        }

        fun withNegativeTextColor(@ColorRes colorRes: Int) = apply {
            negativeTextColor = colorRes
        }

        fun withButtons(buttons: Array<EventType>) = apply {
            this.buttons = buttons
        }

        fun withCancelable(cancelable: Boolean) = apply {
            this.cancelable = cancelable
        }

        fun build() = RxDialog(this)
    }
}