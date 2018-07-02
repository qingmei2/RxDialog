package com.qingmei2.rxdialog.entity

@Target(AnnotationTarget.FUNCTION)
@Retention
@MustBeDocumented
annotation class Dialog(val title: Int = DEFAULT_DIALOG_STRING_RES,
                        val message: Int = DEFAULT_DIALOG_STRING_RES,
                        val buttons: Array<EventType> = arrayOf(),
                        val positiveText: Int = DEFAULT_DIALOG_STRING_RES,
                        val negativeText: Int = DEFAULT_DIALOG_STRING_RES)

//@Target(AnnotationTarget.VALUE_PARAMETER)
//@Retention
//@MustBeDocumented
//annotation class Configration

const val DEFAULT_DIALOG_STRING_RES = -1
