package cherry.gamebox.tzfe.stage

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.actor.BottomGroup
import cherry.gamebox.tzfe.actor.MiddleGroup
import cherry.gamebox.tzfe.actor.TopGroup
import cherry.gamebox.tzfe.constant.Constant
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.viewport.Viewport


/**
 * GameStage
 *
 * @author john
 * @since 2022-09-21
 */
class GameStage(game: TzfeGame, viewport: Viewport) : BaseStage(game, viewport) {
    /** 顶部演员组（包括 LOGO, 分数显示）  */
    private var topGroup: TopGroup

    /** 中间部分演员组（2048 数字卡片展示区域, 主要游戏逻辑区域）  */
    private var middleGroup: MiddleGroup = MiddleGroup(getMainGame())

    /** 底部演员组（包括 游戏帮助 和 退出游戏 按钮）  */
    private var bottomGroup: BottomGroup


    init {
        /*
         * 中间部分演员组（先创建中间组作为位置参考）
         */
        // 设置到舞台中心
        middleGroup.setPosition(
            width / 2 - middleGroup.width / 2,
            height / 2 - middleGroup.height / 2
        )
        addActor(middleGroup)

        /*
         * 顶部演员组
         */topGroup = TopGroup(getMainGame())
        topGroup.x = width / 2 - topGroup.width / 2 // 水平居中
        val middleGroupTopY = middleGroup.y + middleGroup.height // 中间组的顶部Y坐标
        val topSurplusHeight = height - middleGroupTopY // 被中间组占去后顶部剩余的高度
        topGroup.y = middleGroupTopY + (topSurplusHeight / 2 - topGroup.height / 2) // 顶部竖直居中
        addActor(topGroup)

        /*
         * 底部演员组
         */bottomGroup = BottomGroup(getMainGame())
        bottomGroup.x = width / 2 - bottomGroup.width / 2 // 水平居中
        bottomGroup.y = middleGroup.y / 2 - bottomGroup.height / 2 // 底部竖直居中
        addActor(bottomGroup)

        /*
         * 初始化分数显示
         */
        // 当前分数清零
        topGroup.currScoreGroup.setScore(0)
        // 读取本地最佳分数
        val prefs: Preferences = Gdx.app.getPreferences(Constant.Prefs.FILE_NAME)
        val bestScore: Int = prefs.getInteger(Constant.Prefs.KEY_BEST_SCORE, 0)
        // 设置最佳分数
        topGroup.bestScoreGroup.setScore(bestScore)

        /*
         * 添加舞台监听器
         */addListener(InputListenerImpl())
    }

    /**
     * 重新开始游戏
     */
    fun restartGame() {
        middleGroup.restartGame()
        // 当前分数清零
        topGroup.currScoreGroup.setScore(0)
    }

    /**
     * 增加当前分数
     */
    fun addCurrScore(scoreStep: Int) {
        // 增加分数
        topGroup.currScoreGroup.addScore(scoreStep)
        // 如果当前分数大于最佳分数, 则更新最佳分数
        val currSore = topGroup.currScoreGroup.getScore()
        val bestSore = topGroup.bestScoreGroup.getScore()
        if (currSore > bestSore) {
            topGroup.bestScoreGroup.setScore(currSore)
        }
    }

    override fun dispose() {
        super.dispose()
        // 舞台销毁时保存最佳分数
        val prefs: Preferences = Gdx.app.getPreferences(Constant.Prefs.FILE_NAME)
        prefs.putInteger(Constant.Prefs.KEY_BEST_SCORE, topGroup.bestScoreGroup.getScore())
        prefs.flush()
    }

    /**
     * 输入事件监听器
     */
    private inner class InputListenerImpl : InputListener() {
        override fun keyDown(event: InputEvent, keycode: Int): Boolean {
            /*
        	 * 对于 PC 平台, 可同时通过按键控制游戏,
        	 * 监听方向键的按下, 根据方向键移动卡片
        	 */
            when (keycode) {
                Input.Keys.UP -> {
                    middleGroup.toTop()
                    return true
                }
                Input.Keys.DOWN -> {
                    middleGroup.toBottom()
                    return true
                }
                Input.Keys.LEFT -> {
                    middleGroup.toLeft()
                    return true
                }
                Input.Keys.RIGHT -> {
                    middleGroup.toRight()
                    return true
                }
            }
            return super.keyDown(event, keycode)
        }

        override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
            if (keycode == Input.Keys.BACK) {
                // 在主游戏舞台界面按下返回键并弹起后, 提示是否退出游戏（显示退出确认舞台）
                getMainGame().gameScreen.setShowExitConfirmStage(true)
                return true
            }
            return super.keyUp(event, keycode)
        }
    }
}