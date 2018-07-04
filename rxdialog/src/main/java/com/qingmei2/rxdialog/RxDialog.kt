package com.qingmei2.rxdialog

import android.content.Context
import com.qingmei2.rxdialog.entity.Event
import com.qingmei2.rxdialog.entity.EventType
import io.reactivex.Observable

class RxDialog private constructor(val context: Context,
                                   val title: String,
                                   val message: String,
                                   val positiveText: String,
                                   val negativeText: String,
                                   val buttons: Array<EventType>) {

    private constructor(builder: RxDialog.Builder) : this(
            builder.context,
            builder.title,
            builder.message,
            builder.positiveText,
            builder.negativeText,
            builder.buttons
    )

    fun observable(): Observable<Event> {
        return RxDialogController.observable(this)
    }

    companion object {

        fun build(context: Context, builder: Builder.() -> Unit) = Builder(context, builder).build()

        private const val DEFAULT_DIALOG_STRING_RES = -1
        private const val DEFAULT_DIALOG_COLOR_RES = -2
        private const val DEFAULT_DIALOG_CANCELABLE = true
        private const val DEFAULT_DIALOG_STRING = ""
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
        var buttons: Array<EventType> = arrayOf()

        fun withTitle(init: Builder.() -> Int) = apply {
            title = init().let {
                parseStringRes(it)
            }
        }

        fun withMessage(init: Builder.() -> Int) = apply {
            message = init().let {
                parseStringRes(it)
            }
        }

        fun withPositiveText(init: Builder.() -> Int) = apply {
            positiveText = init().let {
                parseStringRes(it)
            }
        }

        fun withNegativeText(init: Builder.() -> Int) = apply {
            negativeText = init().let {
                parseStringRes(it)
            }
        }

        fun withButtons(init: Builder.() -> Array<EventType>) = apply {
            buttons = init()
        }

        private fun parseStringRes(stringRes: Int): String {
            return context.getString(stringRes)
        }

        fun build() = RxDialog(this)
    }
}