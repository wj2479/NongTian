package com.qdhc.ny

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val saa = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l",
            "z", "x", "c", "v", "b", "n", "m")

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun random() {
        var sb = StringBuilder()
        val random = Random()
        while (sb.length < 5) {
            sb.append(saa[random.nextInt(saa.size)])
        }
        print(sb.toString())
    }
}
