package cherry.gamebox.tzfe.data

/**
 * IDataModel
 *
 * @author john
 * @since 2022-09-21
 */
interface IDataModel {
    /**
     * 数据初始化, 创建了一个数据模型实例或重新开始游戏时调用该方法
     */
    fun dataInit()

    /**
     * 获取当前数据（4x4 二维数组）
     */
    fun getData(): Array<IntArray>

    /**
     * 获取当前的分数
     */
    fun getCurrentScore(): Int

    /**
     * 判断当前是否处于游戏胜利（过关）状态
     */
    fun getGameState(): GameState?

    /**
     * 对数据的操作, 向上滑动
     */
    fun toTop()

    /**
     * 对数据的操作, 向下滑动
     */
    fun toBottom()

    /**
     * 对数据的操作, 向左滑动
     */
    fun toLeft()

    /**
     * 对数据的操作, 向右滑动
     */
    fun toRight()

    /**
     * 游戏状态枚举
     */
    enum class GameState {
        /** 正在游戏状态  */
        Game,

        /** 游戏胜利（过关）状态  */
        Win,

        /** 游戏失败状态  */
        GameOver
    }

    /**
     * 数据监听器, 监听到数据变化后播放相应的音效和动画
     */
    interface DataListener {
        /**
         * 随机生成数字时调用
         *
         * @param row 生成的数字所在行
         * @param col 生成的数字所在列
         * @param num 生成的数字
         */
        fun onGeneratorNumber(row: Int, col: Int, num: Int)

        /**
         * 两个数字合并时调用
         *
         * @param rowAfterMerge 合并后数字所在行
         * @param colAfterMerge 合并后数字所在列
         * @param numAfterMerge 合并后的数字
         * @param currentScoreAfterMerger 合并后的当前分数
         */
        fun onNumberMerge(
            rowAfterMerge: Int,
            colAfterMerge: Int,
            numAfterMerge: Int,
            currentScoreAfterMerger: Int
        )

        /**
         * 游戏结束时调用
         *
         * @param isWin 是否胜利（过关）
         */
        fun onGameOver(isWin: Boolean)
    }

    /**
     * 数据模型构建器
     */
    object Builder {
        /**
         * 创建一个指定行列数据的数据模型
         */
        fun createDataModel(rowSum: Int, colSum: Int, dataListener: DataListener): IDataModel {
            return DataModelImpl(rowSum, colSum, dataListener)
        }
    }
}