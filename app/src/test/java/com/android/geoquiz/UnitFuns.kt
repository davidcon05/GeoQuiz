package com.android.geoquiz

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitFuns {

    @Test
    fun calculateScore(correct: Int, bankSize: Int):Int {
        return (correct.toDouble() / bankSize.toDouble() * 100).roundToInt()
    }
}