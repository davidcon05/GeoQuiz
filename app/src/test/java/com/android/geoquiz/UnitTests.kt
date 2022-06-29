package com.android.geoquiz

import org.junit.Assert.assertEquals
import org.junit.Test

internal class UnitTests {

    private val testFuns: UnitFuns = UnitFuns()

    @Test
    fun testScoreCalculation() {
        val expected = 67
        assertEquals(expected, testFuns.calculateScore(6, 9))
    }

}