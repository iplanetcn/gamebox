package cherry.gamebox.fruit

/**
 * Sample
 *
 * @author john
 * @since 2020-12-25
 */

fun main() {
    Sample().testRange()
}

class Sample {
    fun testRange() {
        for (i in 6 downTo 1) {
            print("$i ")
        }
        val bl = ArrayList<ArrayList<B>>()
        for (j in 0..3) {
            val al = ArrayList<B>()
            for (i in 0..5) {
                al.add(B())
            }
            bl.add(al)
        }

        setA(bl)
    }

    interface A {

    }

    class B : A {

    }

    fun setA(array: ArrayList<out ArrayList<out A>>) {

    }
}