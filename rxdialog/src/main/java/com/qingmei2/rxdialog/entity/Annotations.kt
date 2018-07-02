package com.qingmei2.rxdialog.entity

@Target(AnnotationTarget.FUNCTION)
@Retention
@MustBeDocumented
annotation class Dialog(val title: Int = DEFAULT_DIALOG_STRING_RES,
                        val message: Int = DEFAULT_DIALOG_STRING_RES,
                        val buttons: Array<EventType> = [],
                        val positiveText: Int = DEFAULT_DIALOG_STRING_RES,
                        val positiveTextColor: Int = DEFAULT_DIALOG_COLOR_RES,
                        val negativeText: Int = DEFAULT_DIALOG_STRING_RES,
                        val negativeTextColor: Int = DEFAULT_DIALOG_COLOR_RES)

const val DEFAULT_DIALOG_STRING_RES = -1
const val DEFAULT_DIALOG_COLOR_RES = -2
