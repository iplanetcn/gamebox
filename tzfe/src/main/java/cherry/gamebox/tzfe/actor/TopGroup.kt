package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * TopGroup
 *
 * @author john
 * @since 2022-09-21
 */
class TopGroup(game: TzfeGame) : BaseGroup(game) {

    /** 2048 LOGO  */
    private var logoImage: Image

    /** 最佳分数  */
    var bestScoreGroup: ScoreGroup

    /** 当前分数  */
    var currScoreGroup: ScoreGroup

    init {
        /*
         * 2048 LOGO
         */
        logoImage = Image(game.atlas.findRegion(Constant.AtlasNames.GAME_LOGO))
        logoImage.x = 20f
        addActor(logoImage)

        // 设置组的宽高（以世界的宽度, LOGO 的高度 作为组的宽高）
        setSize(game.worldWidth, logoImage.height)

        /*
         * 当前分数
         */currScoreGroup = ScoreGroup(
            game,
            game.atlas.findRegion(Constant.AtlasNames.GAME_SCORE_BG_NOW)
        )
        currScoreGroup.x = 186f
        currScoreGroup.y = height - currScoreGroup.height // 设置到组的顶部
        addActor(currScoreGroup)

        /*
         * 最佳分数
         */
        bestScoreGroup = ScoreGroup(
            game,
            game.atlas.findRegion(Constant.AtlasNames.GAME_SCORE_BG_BEST)
        )
        bestScoreGroup.x = 334f
        bestScoreGroup.y = height - bestScoreGroup.height
        addActor(bestScoreGroup)
    }
}