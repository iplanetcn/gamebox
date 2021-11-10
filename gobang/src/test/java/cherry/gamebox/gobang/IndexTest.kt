package cherry.gamebox.gobang

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

/**
 * IndexTest
 *
 * @author john
 * @since 2021-07-14
 */
class IndexTest {

    @Test
    fun testEquals() {
        val i1 = Index(1, 2)
        val i2 = Index(1, 2)
        val i3 = Index(2, 3)

        assertTrue(i1 == i2)
        assertFalse(i2 == i3)
    }
}