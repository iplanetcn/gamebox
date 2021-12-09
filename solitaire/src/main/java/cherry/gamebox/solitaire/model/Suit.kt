package cherry.gamebox.solitaire.model

/**
 * Suit
 *
 * @author john
 * @since 2021-12-09
 */
enum class Suit(val description: String) {
    SPADES("♠️"), HEARTS("♥️"), CLUBS("♣️"), DIAMONDS("♦️");

    fun isRed() = when (this) {
        DIAMONDS, HEARTS -> true
        else -> false
    }

    fun isBlack() = when (this) {
        SPADES, CLUBS -> true
        else -> false
    }

    override fun toString() = "Suit(description=$description)"

    companion object {
        var all = arrayOf(SPADES, HEARTS, CLUBS, DIAMONDS)
    }
}