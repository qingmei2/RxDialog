package com.qingmei2.rxdialog.entity

import android.content.Context

class DialogOptions private constructor(val context: Context,
                                        val title: String,
                                        val message: String,
                                        val positiveText: String,
                                        val negativeText: String,
                                        val buttons: Array<EventType>) {

    private constructor(builder: DialogOptions.Builder) : this(
            builder.context,
            builder.title,
            builder.message,
            builder.positiveText,
            builder.negativeText,
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

        fun nagativeText(init: Builder.() -> Int) = apply {
            negativeText = init().let {
                parseStringRes(it)
            }
        }

        fun buttons(init: Builder.() -> Array<EventType>) = apply {
            buttons = init()
        }

        private fun parseStringRes(stringRes: Int): String {
            if (stringRes == DEFAULT_DIALOG_STRING_RES)
                return ""
            else
                return context.getString(stringRes)
        }

        fun build() = DialogOptions(this)
    }
}

