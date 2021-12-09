package cherry.gamebox.solitaire.model

/**
 * Rank
 *
 * @author john
 * @since 2021-12-09
 */
enum class Rank(val value: Int, val description: String) {
    ACE(1, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K");

    override fun toString() = "Rank(value=$value, description=$description)"

    companion object {
        var all = arrayOf(
            ACE,
            TWO,
            THREE,
            FOUR,
            FIVE,
            SIX,
            SEVEN,
            EIGHT,
            NINE,
            TEN,
            JACK,
            QUEEN,
            KING
        )
    }

}