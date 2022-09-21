package cherry.gamebox.tzfe.data

import cherry.gamebox.tzfe.data.IDataModel.DataListener
import java.util.*

/**
 * DataModelImpl
 *
 * @author john
 * @since 2022-09-21
 */
class DataModelImpl(rowSum: Int, colSum: Int, dataListener: DataListener) : IDataModel {
    /** 数据的总行数  */
    private var rowSum = 0

    /** 数据的总列数  */
    private var colSum = 0

    /** 数据监听器  */
    private var dataListener: DataListener

    /** 二维数组数据  */
    private var data: Array<IntArray>

    /** 得分  */
    private var currentScore = 0

    /** 游戏状态, 默认为游戏状态  */
    private var mGameState = IDataModel.GameState.Game

    /** 随机数生成器  */
    private var random: Random

    init {
        this.rowSum = rowSum
        this.colSum = colSum
        this.dataListener = dataListener
        data = Array(this.rowSum) { IntArray(this.colSum) }
        random = Random()
    }

    override fun dataInit() {
        // 数据清零
        for (row in 0 until rowSum) {
            for (col in 0 until colSum) {
                data[row][col] = 0
            }
        }

        // 重置状态
        currentScore = 0
        mGameState = IDataModel.GameState.Game

        // 随机生成两个数字
        randomGeneratorNumber()
        randomGeneratorNumber()
    }

    override fun getData(): Array<IntArray> {
        return data
    }

    override fun getCurrentScore(): Int {
        return currentScore
    }

    override fun getGameState(): IDataModel.GameState {
        return mGameState
    }

    override fun toTop() {
        /*
         * 移动和合并数字, 整个 2048 游戏中最核心的算法部分, 也是最难的部分
         */

        // 非正在游戏状态时调用, 忽略
        if (mGameState !== IDataModel.GameState.Game) {
            return
        }

        // 是否有卡片移动或合并的标记
        var hasMove = false

        // 竖直方向移动, 依次遍历每一列
        for (col in 0 until colSum) {
            // 向上移动, 从第 0 行开始依次向下遍历每一行
            var row = 0
            while (row < rowSum) {

                // 找出当前遍历行 row 下面的首个非空卡片, 将该非空卡片移动到当前 row 行位置
                for (tmpRow in row + 1 until rowSum) {
                    if (data[tmpRow][col] == 0) {
                        continue
                    }
                    if (data[row][col] == 0) {
                        // 如果当前 row 行位置是空的, 则直接移动卡片
                        data[row][col] = data[tmpRow][col]
                        hasMove = true
                        // 数字移动后原位置清零
                        data[tmpRow][col] = 0
                        // 当前 row 行位置是空的, 卡片移动到当前 row 行位置后需要在下一循环中重新遍历该 row 行（移动后
                        // 的 row 行位置卡片和 row 下面下一次需要移动的卡片数字如果相同依然要相加, 没有重新校验会被忽略）
                        row--
                    } else if (data[row][col] == data[tmpRow][col]) {
                        // 如果当前 row 行位置和找到的 row 下面首个非空卡片的数字相同, 则合并数字
                        data[row][col] += data[tmpRow][col]
                        hasMove = true
                        // 增加分数
                        currentScore += data[row][col]
                        // 回调监听
                        dataListener.onNumberMerge(row, col, data[row][col], currentScore)
                        // 合并后原位置清零
                        data[tmpRow][col] = 0
                    }
                    break
                }
                row++
            }
        }

        // 滑动一次, 只有卡片有移动过时, 才需要检测是否游戏结束和生成新数字
        if (hasMove) {
            // 校验游戏是否结束（过关或失败）
            checkGameFinish()
            // 移动完一次后, 随机生成一个数字
            randomGeneratorNumber()
            // 生成数字后还需要再校验一次游戏是否结束（失败）, 防止生成数字后就是不可再移动状态
            checkGameFinish()
        }

        /* 下移, 左移, 右移 的原理相同, 不再注释 */
    }

    override fun toBottom() {
        if (mGameState !== IDataModel.GameState.Game) {
            return
        }
        var hasMove = false
        for (col in 0 until colSum) {
            var row = rowSum - 1
            while (row >= 0) {
                for (tmpRow in row - 1 downTo 0) {
                    if (data[tmpRow][col] == 0) {
                        continue
                    }
                    if (data[row][col] == 0) {
                        hasMove = true
                        data[row][col] = data[tmpRow][col]
                        data[tmpRow][col] = 0
                        row++
                    } else if (data[row][col] == data[tmpRow][col]) {
                        data[row][col] += data[tmpRow][col]
                        hasMove = true
                        currentScore += data[row][col]
                        dataListener.onNumberMerge(row, col, data[row][col], currentScore)
                        data[tmpRow][col] = 0
                    }
                    break
                }
                row--
            }
        }
        if (hasMove) {
            checkGameFinish()
            randomGeneratorNumber()
            checkGameFinish()
        }
    }

    override fun toLeft() {
        if (mGameState !== IDataModel.GameState.Game) {
            return
        }
        var hasMove = false
        for (row in 0 until rowSum) {
            var col = 0
            while (col < colSum) {
                for (tmpCol in col + 1 until colSum) {
                    if (data[row][tmpCol] == 0) {
                        continue
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol]
                        hasMove = true
                        data[row][tmpCol] = 0
                        col--
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][tmpCol]
                        hasMove = true
                        currentScore += data[row][col]
                        dataListener.onNumberMerge(row, col, data[row][col], currentScore)
                        data[row][tmpCol] = 0
                    }
                    break
                }
                col++
            }
        }
        if (hasMove) {
            checkGameFinish()
            randomGeneratorNumber()
            checkGameFinish()
        }
    }

    override fun toRight() {
        if (mGameState !== IDataModel.GameState.Game) {
            return
        }
        var hasMove = false
        for (row in 0 until rowSum) {
            var col = colSum - 1
            while (col >= 0) {
                for (tmpCol in col - 1 downTo 0) {
                    if (data[row][tmpCol] == 0) {
                        continue
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol]
                        hasMove = true
                        data[row][tmpCol] = 0
                        col++
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][tmpCol]
                        hasMove = true
                        currentScore += data[row][col]
                        dataListener.onNumberMerge(row, col, data[row][col], currentScore)
                        data[row][tmpCol] = 0
                    }
                    break
                }
                col--
            }
        }
        if (hasMove) {
            checkGameFinish()
            randomGeneratorNumber()
            checkGameFinish()
        }
    }

    /**
     * 校验游戏是否结束（过关或失败）
     */
    private fun checkGameFinish() {
        // 判断是否游戏胜利（过关）
        for (row in 0 until rowSum) {
            for (col in 0 until colSum) {
                if (data[row][col] == 2048) {
                    mGameState = IDataModel.GameState.Win // 只要有一个卡片拼凑出 2048, 游戏即胜利（过关）
                    dataListener.onGameOver(true)
                    return
                }
            }
        }

        // 游戏还没有胜利, 则判断是否还可移动
        if (!isMovable()) {
            mGameState = IDataModel.GameState.GameOver // 如果游戏没有胜利, 卡片又不可再移动, 则游戏失败
            dataListener.onGameOver(false)
            return
        }
    }

    /**
     * 判断是否还可移动
     */
    private fun isMovable(): Boolean {
        // 校验水平方向上是否可移动
        for (row in 0 until rowSum) {
            for (col in 0 until colSum - 1) {
                // 有空位或连续的两个卡片位置数字相等, 则可移动
                if (data[row][col] == 0 || data[row][col + 1] == 0 || data[row][col] == data[row][col + 1]) {
                    return true
                }
            }
        }

        // 校验竖直方向上是否可移动
        for (col in 0 until colSum) {
            for (row in 0 until rowSum - 1) {
                if (data[row][col] == 0 || data[row + 1][col] == 0 || data[row][col] == data[row + 1][col]) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 随机在指定的行列生成一个数字（2或4）, 整个 2048 游戏中的核心算法之一
     */
    private fun randomGeneratorNumber() {
        // 计算出空卡片的数量（数字为 0 的卡片）
        var emptyCardsCount = 0
        for (row in 0 until rowSum) {
            for (col in 0 until colSum) {
                if (data[row][col] == 0) {
                    emptyCardsCount++
                }
            }
        }

        // 如果没有空卡片, 则游戏结束
        if (emptyCardsCount == 0) {
            /*
             * 有卡片移动时才会调用该方法, 所以程序不会到达这里,
             * 但为了逻辑严谨, 这里还是要做一下是否有空卡片的判断。
             */
            mGameState = IDataModel.GameState.GameOver // 游戏失败（没有过关）
            dataListener.onGameOver(false)
            return
        }

        // 有空卡片, 则在这些空卡片中随机挑一个位置生成数字
        val newNumOnEmptyCardsPosition: Int =
            random.nextInt(emptyCardsCount) // 在第 newNumOnEmptyCardsPosition 个空卡片位置生成数字
        val newNum = if (random.nextFloat() < 0.2f) 4 else 2 // 20% 的概率生成 4

        // 把生成的数字（newNum）放到指定的空卡片中（第 newNumOnEmptyCardsPosition 个空卡片）
        var emptyCardPosition = 0
        for (row in 0 until rowSum) {
            for (col in 0 until colSum) {
                // 忽略非空卡片
                if (data[row][col] != 0) {
                    continue
                }
                // 如果找到指定位置的空卡片, 则放入数字
                if (emptyCardPosition == newNumOnEmptyCardsPosition) {
                    data[row][col] = newNum
                    // 有数字生成, 回调监听
                    dataListener.onGeneratorNumber(row, col, newNum)
                    return
                }
                // 还没有遍历到指定位置的空卡片, 继续遍历
                emptyCardPosition++
            }
        }
    }
}