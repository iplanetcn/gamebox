package cherry.gamebox.tetris.screens

import cherry.gamebox.tetris.TetrisGame
import cherry.gamebox.tetris.assets.Assets
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * GameScreen
 *
 * @author john
 * @since 2021-11-17
 */
class GameScreen(game: TetrisGame) : BaseScreen(game) {

    init {
        val board = Image(Assets.backgrounds.gameBoard)
        board.setPosition(
            SCREEN_WIDTH / 2f - board.width / 2f,
            SCREEN_HEIGHT / 2 - board.height / 2f
        )
        stage.addActor(board)

        val blueBlock = Image(Assets.blocks.blue)
        blueBlock.setPosition(SCREEN_WIDTH / 2f - blueBlock.width / 2f, SCREEN_HEIGHT / 2)
        stage.addActor(blueBlock)

        val redBlock = Image(Assets.blocks.red)
        redBlock.setPosition(SCREEN_WIDTH / 2f - redBlock.width / 2f, SCREEN_HEIGHT / 2)
        stage.addActor(redBlock)
        redBlock.addAction(
            Actions.repeat(
                -1, Actions.sequence(
                    Actions.moveTo(SCREEN_WIDTH - redBlock.width, SCREEN_HEIGHT / 2, 1f),
                    Actions.moveTo(0f, SCREEN_HEIGHT / 2, 1f)
                )
            )
        )

        blueBlock.addAction(
            Actions.repeat(
                -1, Actions.sequence(
                    Actions.moveTo(SCREEN_WIDTH / 2f - blueBlock.width / 2f, 0f, 1f),
                    Actions.moveTo(
                        SCREEN_WIDTH / 2f - blueBlock.width / 2f,
                        SCREEN_HEIGHT - blueBlock.height,
                        1f
                    )
                )
            )
        )
    }

    override fun draw(delta: Float) {
        batcher.begin()
        batcher.draw(Assets.backgrounds.background, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
        batcher.end()
    }

    override fun update(delta: Float) {

    }

}