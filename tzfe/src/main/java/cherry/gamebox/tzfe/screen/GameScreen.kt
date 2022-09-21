package cherry.gamebox.tzfe.screen

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import cherry.gamebox.tzfe.stage.ExitConfirmStage
import cherry.gamebox.tzfe.stage.GameOverStage
import cherry.gamebox.tzfe.stage.GameStage
import cherry.gamebox.tzfe.stage.HelpStage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.utils.viewport.StretchViewport


/**
 * GameScreen
 *
 * @author john
 * @since 2022-09-21
 */
class GameScreen(game: TzfeGame) : ScreenAdapter() {

    /** 1. 主游戏舞台  */
    var gameStage: GameStage

    /** 2. 帮助舞台  */
    private var helpStage: HelpStage

    /** 3. 游戏结束舞台  */
    private var gameOverStage: GameOverStage

    /** 4. 退出确认舞台  */
    private var exitConfirmStage: ExitConfirmStage

    init {
        // 1. 创建主游戏舞台
        gameStage = GameStage(
            game,
            StretchViewport(
                game.worldWidth,
                game.worldHeight
            )
        )

        // 2. 创建帮助舞台
        helpStage = HelpStage(
            game,
            StretchViewport(
                game.worldWidth,
                game.worldHeight
            )
        )
        helpStage.setVisible(false) // 除主游戏舞台外, 其他舞台先设置为不可见

        // 3. 创建游戏结束舞台
        gameOverStage = GameOverStage(
            game,
            StretchViewport(
                game.worldWidth,
                game.worldHeight
            )
        )
        gameOverStage.setVisible(false)

        // 4. 创建退出确认舞台
        exitConfirmStage = ExitConfirmStage(
            game,
            StretchViewport(
                game.worldWidth,
                game.worldHeight
            )
        )
        exitConfirmStage.setVisible(false)

        // 把输入处理设置到主游戏舞台（必须设置, 否则无法接收用户输入）
        Gdx.input.inputProcessor = gameStage
    }

    /**
     * 重新开始游戏
     */
    fun restartGame() {
        gameStage.restartGame()
    }

    /**
     * 帮助舞台 是否显示
     */
    fun setShowHelpStage(showHelpStage: Boolean) {
        helpStage.setVisible(showHelpStage)
        if (helpStage.isVisible()) {
            // 如果显示帮助舞台, 则把输入处理设置到帮助舞台
            Gdx.input.inputProcessor = helpStage
        } else {
            // 不显示帮助舞台, 把输入处理设置回主游戏舞台
            Gdx.input.inputProcessor = gameStage
        }
    }

    /**
     * 退出确认舞台 是否显示
     */
    fun setShowExitConfirmStage(showExitConfirmStage: Boolean) {
        exitConfirmStage.setVisible(showExitConfirmStage)
        if (exitConfirmStage.isVisible()) {
            Gdx.input.inputProcessor = exitConfirmStage
        } else {
            Gdx.input.inputProcessor = gameStage
        }
    }

    /**
     * 显示结束舞台（并设置结束舞台中的文本显示状态和分数）
     */
    fun showGameOverStage(isWin: Boolean, score: Int) {
        // 设置结束舞台中的文本显示状态状态和分数
        gameOverStage.setGameOverState(isWin, score)
        gameOverStage.setVisible(true)
        Gdx.input.inputProcessor = gameOverStage
    }

    /**
     * 隐藏结束舞台
     */
    fun hideGameOverStage() {
        gameOverStage.setVisible(false)
        Gdx.input.inputProcessor = gameStage
    }

    override fun render(delta: Float) {
        // 使用背景颜色清屏
        Gdx.gl.glClearColor(Constant.BG_RGBA.r, Constant.BG_RGBA.g, Constant.BG_RGBA.b, Constant.BG_RGBA.a)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        /*
         * 更新舞台逻辑, 绘制舞台
         */

        // 1. 主游戏舞台始终都需要绘制, 并且最先绘制
        gameStage.act()
        gameStage.draw()

        // 2. 帮助舞台
        if (helpStage.isVisible()) {
            helpStage.act()
            helpStage.draw()
        }

        // 3. 游戏结束舞台
        if (gameOverStage.isVisible()) {
            gameOverStage.act()
            gameOverStage.draw()
        }

        // 4. 退出游戏确认的舞台
        if (exitConfirmStage.isVisible()) {
            exitConfirmStage.act()
            exitConfirmStage.draw()
        }
    }

    override fun dispose() {
        // 场景销毁时, 同时销毁所有舞台
        gameStage.dispose()
        helpStage.dispose()
        gameOverStage.dispose()
        exitConfirmStage.dispose()
    }
}
