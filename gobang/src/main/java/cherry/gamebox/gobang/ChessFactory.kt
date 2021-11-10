package cherry.gamebox.gobang

/**
 * ChessFactory
 *
 * @author john
 * @since 2021-07-13
 */
class ChessFactory {
    private val chessList = listOf(
        WhiteChess(),
        BlackChess()
    )

    fun getWhiteChess(): Chess {
        return chessList[0]
    }

    fun getBlackChess(): Chess {
        return chessList[1]
    }

    fun getChess(type: ChessType): Chess {
        return when (type) {
            ChessType.Black -> getBlackChess()
            ChessType.White -> getWhiteChess()
        }
    }


}