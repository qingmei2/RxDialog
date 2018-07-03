package com.qingmei2.rxdialog.core.options

import android.content.Context
import android.support.v4.content.ContextCompat
import com.qingmei2.rxdialog.core.factory.DialogFactory
import com.qingmei2.rxdialog.core.factory.SimpleDialogFactory
import com.qingmei2.rxdialog.entity.DEFAULT_DIALOG_CANCELABLE
import com.qingmei2.rxdialog.entity.DEFAULT_DIALOG_COLOR_RES
import com.qingmei2.rxdialog.entity.DEFAULT_DIALOG_STRING_RES
import com.qingmei2.rxdialog.entity.EventType

internal class SimpleDialogOption private constructor(val context: Context,
                                             val title: String,
                                             val message: String,
                                             val positiveText: String,
                                             val positiveTextColor: Int,
                                             val negativeText: String,
                                             val negativeTextColor: Int,
                                             val cancelable: Boolean,
                                             val buttons: Array<EventType>)
    : DialogOption {

    private constructor(builder: Builder) : this(
            builder.context,
            builder.title,
            builder.message,
            builder.positiveText,
            builder.positiveTextColor,
            builder.negativeText,
            builder.negativeTextColor,
            builder.cancelable,
            builder.buttons
    )

    override fun seekDialogFactory(): DialogFactory = SimpleDialogFactory(this)

    companion object {

        fun build(context: Context, init: Builder.() -> Unit) = Builder(context, init).build()

    }

    class Builder private constructor() {

        constructor(context: Context, init: Builder.() -> Unit) : this() {
            this.context = context
            init()
        }

        lateinit var context: Context
        lateinit var title: String
        lateinit var message: String
        lateinit var positiveText: String
        lateinit var negativeText: String
        lateinit var buttons: Array<EventType>
        var cancelable: Boolean = DEFAULT_DIALOG_CANCELABLE
        var positiveTextColor: Int = DEFAULT_DIALOG_COLOR_RES
        var negativeTextColor: Int = DEFAULT_DIALOG_COLOR_RES

        fun title(init: Builder.() -> Int) = apply {
            title = init().let {
                parseStringRes(it)
            }
        }

        fun message(init: Builder.() -> Int) = apply {
            message = init().let {
                parseStringRes(it)
            }
        }

        fun positiveText(init: Builder.() -> Int) = apply {
            positiveText = init().let {
                parseStringRes(it)
            }
        }

        fun positiveTextColor(init: Builder.() -> Int) = apply {
            positiveTextColor = init().let {
                parseColorRes(it)
            }
        }

        fun negativeText(init: Builder.() -> Int) = apply {
            negativeText = init().let {
                parseStringRes(it)
            }
        }

        fun negativeTextColor(init: Builder.() -> Int) = apply {
            negativeTextColor = init().let {
                parseColorRes(it)
            }
        }

        fun buttons(init: Builder.() -> Array<EventType>) = apply {
            buttons = init()
        }

        fun cancelable(init: Builder.() -> Boolean) = apply {
            cancelable = init()
        }

        private fun parseStringRes(stringRes: Int): String =
                if (stringRes == DEFAULT_DIALOG_STRING_RES)
                    "" else context.getString(stringRes)

        private fun parseColorRes(colorRes: Int): Int =
                if (colorRes == DEFAULT_DIALOG_COLOR_RES)
                    DEFAULT_DIALOG_COLOR_RES else ContextCompat.getColor(context, colorRes)


        fun build() = SimpleDialogOption(this)
    }
}

