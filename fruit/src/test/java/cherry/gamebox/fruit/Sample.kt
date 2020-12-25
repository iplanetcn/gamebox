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
    fun testRange(){
        for (i in 6 downTo  1) {
            print("$i ")
        }
    }
}