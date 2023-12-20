package com.leylapps.anandroidhistory

import com.leylapps.anandroidhistory.data.models.ApiRequestCapsule
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun kotlinScopeTest() {
        println(scopeLet())
        println(scopeApply())
        println(scopeAlso())
    }

    fun scopeLet() {
        printObj(ApiRequestCapsule().let {
            it.url = "let"
            return@let it
        })
    }

    fun scopeApply() {
        printObj(ApiRequestCapsule().apply {
            url = "apply"
        })
    }

    fun scopeAlso() {
        printObj(ApiRequestCapsule().also {
            it.url = "also"
        })
    }

    fun printObj(apiRequestCapsule: ApiRequestCapsule) {
        print(apiRequestCapsule)
    }
}