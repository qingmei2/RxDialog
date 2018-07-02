package com.qingmei2.rxdialog.entity

import android.content.Context
import android.support.v4.content.ContextCompat

class DialogOptions private constructor(val context: Context,
                                        val title: String,
                                        val message: String,
                                        val positiveText: String,
                                        val positiveTextColor: Int,
                                        val negativeText: String,
                                        val negativeTextColor: Int,
                                        val buttons: Array<EventType>) {

    private constructor(builder: DialogOptions.Builder) : this(
            builder.context,
            builder.title,
            builder.message,
            builder.positiveText,
            builder.positiveTextColor,
            builder.negativeText,
            builder.negativeTextColor,
            builder.buttons
    )

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

        private fun parseStringRes(stringRes: Int): String =
                if (stringRes == DEFAULT_DIALOG_STRING_RES)
                    "" else context.getString(stringRes)

        private fun parseColorRes(colorRes: Int): Int =
                if (colorRes == DEFAULT_DIALOG_COLOR_RES)
                    DEFAULT_DIALOG_COLOR_RES else ContextCompat.getColor(context, colorRes)


        fun build() = DialogOptions(this)
    }
}

