package cherry.gamebox.tzfe.actor

import cherry.gamebox.tzfe.TzfeGame
import cherry.gamebox.tzfe.constant.Constant
import cherry.gamebox.tzfe.data.IDataModel
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import kotlin.math.abs


/**
 * MiddleGroup
 *
 * @author john
 * @since 2022-09-21
 */
// 卡片的行列数
private const val CARD_ROW_SUM = 4
private const val CARD_COL_SUM = 4
// 滑动生效的最小距离
private const val SLIDE_MIN_DIFF = 20f

class MiddleGroup(game: TzfeGame) : BaseGroup(game) {
    /** 中间区域背景  */
    private var bgImage: Image

    /** 所有数字卡片  */
    private val allCards: Array<Array<CardGroup?>> = Array(CARD_ROW_SUM) {
        arrayOfNulls(CARD_COL_SUM)
    }

    /** 移动操作的音效  */
    private var moveSound: Sound

    /** 合并数据的音效  */
    private var mergeSound: Sound

    /** 数据模型（封装了核心的数据与逻辑）  */
    private var dataModel: IDataModel

    init {
        /*
         * 背景
         */
        bgImage = Image(game.atlas.findRegion(Constant.AtlasNames.GAME_RECT_BG))
        addActor(bgImage)

        // 设置组的宽高（以组的背景宽高作为组的宽高）
        setSize(bgImage.width, bgImage.height)

        /*
         * 创建所有的数字卡片
         */
        // 创建所有卡片并添加到组中
        for (row in 0 until CARD_ROW_SUM) {
            for (col in 0 until CARD_COL_SUM) {
                allCards[row][col] = CardGroup(game)
                allCards[row][col]?.setOrigin(Align.center) // 缩放和旋转支点设置到演员的中心
                addActor(allCards[row][col])
            }
        }

        // 获取卡片的宽高
        val cardWidth: Float = allCards[0][0]!!.width
        val cardHeight: Float = allCards[0][0]!!.height

        // 计算所有卡片按指定的行列排列到组中后卡片间的水平和竖直间隙大小
        val horizontalInterval = (width - CARD_COL_SUM * cardWidth) / (CARD_COL_SUM + 1)
        val verticalInterval = (height - CARD_ROW_SUM * cardHeight) / (CARD_ROW_SUM + 1)

        // 均匀地排版卡片在组中的位置
        var cardY: Float
        for (row in 0 until CARD_ROW_SUM) {
            cardY = height - (verticalInterval + cardHeight) * (row + 1)
            for (col in 0 until CARD_COL_SUM) {
                allCards[row][col]?.x = horizontalInterval + (cardWidth + horizontalInterval) * col
                allCards[row][col]?.y = cardY
            }
        }

        /*
         * 获取音效
         */
        moveSound = game.assetManager.get(Constant.Audios.MOVE, Sound::class.java)
        mergeSound = game.assetManager.get(Constant.Audios.MERGE, Sound::class.java)

        /*
         * 添加输入监听器（用于监听手势的滑动）
         */addListener(InputListenerImpl())

        /*
         * 数据模型
         */
        // 创建一个指定行列的数据模型实例, 并指定数据监听器
        dataModel = IDataModel.Builder.createDataModel(CARD_ROW_SUM, CARD_COL_SUM, DataListenerImpl())
        // 初始化数据模型
        dataModel.dataInit()

        // 数据模型初始化后同步到演员数组
        syncDataToCardGroups()
    }

    /**
     * 重新开始游戏
     */
    fun restartGame() {
        dataModel.dataInit()
        syncDataToCardGroups()
    }

    /**
     * 同步 数据模型中的数据 到 卡片演员数组
     */
    private fun syncDataToCardGroups() {
        val data: Array<IntArray> = dataModel.getData()
        for (row in 0 until CARD_ROW_SUM) {
            for (col in 0 until CARD_COL_SUM) {
                allCards[row][col]?.setNum(data[row][col])
            }
        }
    }

    fun toTop() {
        // 操作数据模型中的数据
        dataModel.toTop()
        // 操作完数据模型中的数据后, 需要同步到卡片演员数组
        syncDataToCardGroups()
        // 播放移动操作的音效
        moveSound.play()
    }

    fun toBottom() {
        dataModel.toBottom()
        syncDataToCardGroups()
        moveSound.play()
    }

    fun toLeft() {
        dataModel.toLeft()
        syncDataToCardGroups()
        moveSound.play()
    }

    fun toRight() {
        dataModel.toRight()
        syncDataToCardGroups()
        moveSound.play()
    }

    /**
     * 输入监听器的实现
     */
    private inner class InputListenerImpl : InputListener() {
        private var downX = 0f
        private var downY = 0f
        override fun touchDown(
            event: InputEvent,
            x: Float,
            y: Float,
            pointer: Int,
            button: Int
        ): Boolean {
            downX = x
            downY = y
            return true
        }

        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            val diffX = x - downX
            val diffY = y - downY
            if (abs(diffX) >= SLIDE_MIN_DIFF && abs(diffX) * 0.5f > abs(diffY)) {
                // 左右滑动
                if (diffX > 0) {
                    toRight()
                } else {
                    toLeft()
                }
            } else if (abs(diffY) >= SLIDE_MIN_DIFF && abs(diffY) * 0.5f > abs(diffX)) {
                // 上下滑动
                if (diffY > 0) {
                    toTop()
                } else {
                    toBottom()
                }
            }
        }
    }

    /**
     * 数据监听器实现
     */
    private inner class DataListenerImpl : IDataModel.DataListener {
        override fun onGeneratorNumber(row: Int, col: Int, num: Int) {
            // 有数字生成新, 该数字所在位置的卡片附加一个动画效果, 0.2 秒内缩放值从 0.2 到 1.0
            allCards[row][col]?.setScale(0.2f)
            val scaleTo = Actions.scaleTo(1.0f, 1.0f, 0.2f)
            allCards[row][col]?.addAction(scaleTo)
        }

        override fun onNumberMerge(
            rowAfterMerge: Int,
            colAfterMerge: Int,
            numAfterMerge: Int,
            currentScoreAfterMerger: Int
        ) {
            // 有卡片合成, 在合成位置附加动画效果, 缩放值从 0.8 到 1.2, 再到 1.0
            allCards[rowAfterMerge][colAfterMerge]?.setScale(0.8f)
            val sequence = Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.1f),
                Actions.scaleTo(1.0f, 1.0f, 0.1f)
            )
            allCards[rowAfterMerge][colAfterMerge]?.addAction(sequence)
            // 播放数字合成的音效
            mergeSound.play()
            // 增加当前分数
            game.gameScreen.gameStage.addCurrScore(numAfterMerge)
        }

        override fun onGameOver(isWin: Boolean) {
            // 游戏结束, 展示结束舞台
            game.gameScreen.showGameOverStage(isWin, dataModel.getCurrentScore())
        }
    }
}