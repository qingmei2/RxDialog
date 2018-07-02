package com.qingmei2.rxdialog.core

import android.content.Context
import com.qingmei2.rxdialog.RxDialog
import com.qingmei2.rxdialog.entity.Dialog
import com.qingmei2.rxdialog.entity.DialogOptions
import java.lang.IllegalStateException
import java.lang.reflect.Method

@Suppress("UNCHECKED_CAST")
internal class ServiceMethod(private val rxDialog: RxDialog,
                             private val method: Method,
                             private val objectsMethod: Array<Any>) {

    var dialogOptions: DialogOptions
    private var methodAnnotations: Array<Annotation>

    init {
        var dialog: Dialog? = null
        methodAnnotations = method.annotations

        for (annotation in methodAnnotations) {
            if (annotation is Dialog) dialog = annotation
        }

        if (dialog == null)
            throw IllegalStateException("the function should be provided the '@Dialog' annotation only.")

        dialogOptions = buildOptions(dialog)
    }

    private fun buildOptions(annotation: Dialog): DialogOptions = DialogOptions.build(
            // get context instance from method param.
            getObjectFromMethodParam(method, Context::class.java, objectsMethod)
    ) {
        title { annotation.title }
        message { annotation.message }
        positiveText { annotation.positiveText }
        positiveTextColor { annotation.positiveTextColor }
        negativeText { annotation.negativeText }
        negativeTextColor { annotation.negativeTextColor }
        cancelable { annotation.cancelable }
        buttons { annotation.buttons }
    }

    private fun <T> getObjectFromMethodParam(method: Method,
                                             expectedClass: Class<T>,
                                             objectsMethod: Array<Any>): T {
        var countSameObjectsType = 0
        var expectedObject: T? = null

        for (objectParam in objectsMethod) {
            if (expectedClass.isAssignableFrom(objectParam.javaClass)) {
                expectedObject = objectParam as T
                countSameObjectsType++
            }
        }

        if (countSameObjectsType > 1) {
            throw IllegalArgumentException(method.name
                    + " requires just one instance of type.")
        }

        return expectedObject!!
    }
}