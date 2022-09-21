package cherry.gamebox.tzfe.stage

import cherry.gamebox.tzfe.TzfeGame
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * BaseStage
 *
 * @author john
 * @since 2022-09-21
 */
abstract class BaseStage(val game: TzfeGame, viewport: Viewport) : Stage(viewport) {

    /** 舞台是否可见（是否更新和绘制）  */
    private var visible = true

    fun getMainGame(): TzfeGame {
        return game
    }

    fun isVisible(): Boolean {
        return visible
    }

    fun setVisible(visible: Boolean) {
        this.visible = visible
    }
}

