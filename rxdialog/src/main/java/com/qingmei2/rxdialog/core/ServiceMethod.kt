package com.qingmei2.rxdialog.core

import android.content.Context
import com.qingmei2.rxdialog.core.options.DialogOption
import com.qingmei2.rxdialog.core.options.SimpleDialogOption
import com.qingmei2.rxdialog.entity.Dialog
import java.lang.reflect.Method

@Suppress("UNCHECKED_CAST")
internal class ServiceMethod(private val method: Method,
                             private val objectsMethod: Array<Any>)
    : DialogOption.Factory {

    private val methodAnnotation: Annotation by lazy {
        checkMethodAnnotations()
    }

    override fun buildOption(): DialogOption {
        when (methodAnnotation) {
            is Dialog -> {
                val dialog = methodAnnotation as Dialog
                return SimpleDialogOption.build(
                        // get context object from method param.
                        getObjectFromMethodParam(method, Context::class.java, objectsMethod),
                        dialog)
            }
            else -> throw IllegalArgumentException(
                    "You should add the correct annotation on the top of interface method!")

        }
    }

    private fun checkMethodAnnotations(): Annotation {
        val methodAnnotations: Array<Annotation> = method.annotations

        if (methodAnnotations.size != 1)
            throw IllegalArgumentException(
                    "You can only add one annotation on the top of interface method.")

        return methodAnnotations[0]
    }

    fun <T> getObjectFromMethodParam(method: Method,
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