package com.qingmei2.rxdialog.core

import android.content.Context
import com.qingmei2.rxdialog.DProvider
import com.qingmei2.rxdialog.RxDialog
import java.lang.reflect.Method

internal class ServiceMethod(private val rxDialog: RxDialog,
                             private val method: Method,
                             private val objectsMethod: Array<Any>) {

    @JvmField
    var context: Context? = null
    private var methodAnnotations: Array<out Annotation>? = null

    init {
        methodAnnotations = method.annotations

        for (annotation in methodAnnotations!!) {
            parseMethodAnnotation(annotation)
        }
        context = getContext()
    }

    private fun getContext(): Context? =
            getObjectFromMethodParam(method, Context::class.java, objectsMethod)

    private fun parseMethodAnnotation(annotation: Annotation) {
        if (annotation !is DProvider)
            throw IllegalArgumentException("the method should has the '@DProvider' tag.")
    }

    private fun checkParametersCorrect() {
        context ?: throw NullPointerException("Context can't be null!")
    }

    private fun <T> getObjectFromMethodParam(method: Method,
                                             expectedClass: Class<T>,
                                             objectsMethod: Array<Any>): T? {
        var countSameObjectsType = 0
        var expectedObject: T? = null

        for (objectParam in objectsMethod) {
            if (expectedClass.isAssignableFrom(objectParam.javaClass)) {
                expectedObject = objectParam as T
                countSameObjectsType++
            }
        }

        if (countSameObjectsType > 1) {
            val errorMessage = (method.name
                    + " requires just one instance of type.")
            throw IllegalArgumentException(errorMessage)
        }

        return expectedObject
    }
}