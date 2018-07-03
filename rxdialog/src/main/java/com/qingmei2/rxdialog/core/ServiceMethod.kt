package com.qingmei2.rxdialog.core

import android.content.Context
import android.util.Log
import com.qingmei2.rxdialog.core.options.DialogOption
import com.qingmei2.rxdialog.core.options.SimpleDialogOption
import com.qingmei2.rxdialog.entity.Dialog
import java.lang.reflect.Method

@Suppress("UNCHECKED_CAST")
internal class ServiceMethod(private val method: Method,
                             private val objectsMethod: Array<Any>)
    : DialogOption.Factory {

    private lateinit var build: () -> DialogOption

    init {

        val methodAnnotations: Array<Annotation> = method.annotations

        Log.d("tag", "methodAnnotations.size = ${methodAnnotations.size}")
        for (annotation in methodAnnotations) {
            if (annotation is Dialog) {
                build = {
                    SimpleDialogOption.build(
                            // get context object from method param.
                            getObjectFromMethodParam(method, Context::class.java, objectsMethod))
                    {
                        title { annotation.title }
                        message { annotation.message }
                        positiveText { annotation.positiveText }
                        positiveTextColor { annotation.positiveTextColor }
                        negativeText { annotation.negativeText }
                        negativeTextColor { annotation.negativeTextColor }
                        cancelable { annotation.cancelable }
                        buttons { annotation.buttons }
                    }
                }
            }
        }
//        build ?: throw IllegalStateException("the function should be provided the '@Dialog' annotation only.")
    }

    override fun buildOption(): DialogOption = build()

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
                    + " requires just one object of type.")
        }

        return expectedObject!!
    }
}