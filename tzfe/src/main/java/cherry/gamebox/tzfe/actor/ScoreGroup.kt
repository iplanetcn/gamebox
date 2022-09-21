package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label


/**
 * ScoreGroup
 *
 * @author john
 * @since 2022-09-21
 */
class ScoreGroup(game: TzfeGame, bgRegion: TextureRegion): BaseGroup(game) {

    /** 背景  */
    private var bgImage: Image

    /** 分数文本显示  */
    private var scoreLabel: Label

    /** 当前显示的分数  */
    private var score = 99999

    init {
        // 首先设置组的宽高（以背景的宽高作为组的宽高）
        setSize(bgRegion.regionWidth.toFloat(), bgRegion.regionHeight.toFloat())

        /*
         * 背景
         */bgImage = Image(bgRegion)
        addActor(bgImage)

        /*
         * 分数文本显示的标签
         */
        // 创建标签样式
        val style = Label.LabelStyle()
        style.font = game.bitmapFont

        // 创建文本标签
        scoreLabel = Label("" + score, style)

        // 设置字体缩放
        scoreLabel.setFontScale(0.4f)

        // 设置标签的宽高（把标签的宽高设置为文本字体的宽高, 即标签包裹文本）
        scoreLabel.setSize(scoreLabel.prefWidth, scoreLabel.prefHeight)

        // 设置文本标签在组中水平居中显示
        scoreLabel.x = width / 2 - scoreLabel.width / 2
        scoreLabel.y = 18f
        addActor(scoreLabel)
    }

    fun getScore(): Int {
        return score
    }

    fun setScore(score: Int) {
        this.score = score
        scoreLabel.setText("" + this.score)

        // 重新设置文本后, 文本的宽度可能被改变, 需要重新设置标签的宽度, 并重新水平居中
        scoreLabel.width = scoreLabel.prefWidth
        scoreLabel.x = width / 2 - scoreLabel.width / 2
    }

    fun addScore(scoreStep: Int) {
        setScore(score + scoreStep)
    }
}