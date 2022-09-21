package cherry.gamebox.tzfe

import cherry.gamebox.core.GameLogger
import cherry.gamebox.tzfe.constant.Constant
import cherry.gamebox.tzfe.screen.GameScreen
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas


/**
 * TzfeGame
 *
 * @author john
 * @since 2022-09-21
 */
class TzfeGame : Game() {

    /** 世界宽度  */
    var worldWidth: Float = 0f

    /** 世界高度  */
    var worldHeight: Float = 0f

    /** 资源管理器  */
    lateinit var assetManager: AssetManager

    /** 纹理图集  */
    lateinit var atlas: TextureAtlas

    /** 位图字体  */
    lateinit var bitmapFont: BitmapFont

    /** 主游戏场景  */
    lateinit var gameScreen: GameScreen

    override fun create() {
        // 设置 log 输出级别
        GameLogger.setLogDebug()

        // 为了不压扁或拉长图片, 按实际屏幕比例计算世界宽高
        worldWidth = Constant.FIX_WORLD_WIDTH
        worldHeight = Gdx.graphics.height * worldWidth / Gdx.graphics.width

        GameLogger.log("World Size: $worldWidth * $worldHeight")
        // 创建资源管理器
        assetManager = AssetManager()

        // 加载资源
        assetManager.load(Constant.ATLAS_PATH, TextureAtlas::class.java)
        assetManager.load(Constant.BITMAP_FONT_PATH, BitmapFont::class.java)
        assetManager.load(Constant.Audios.MOVE, Sound::class.java)
        assetManager.load(Constant.Audios.MERGE, Sound::class.java)
        // 等待资源加载完毕
        assetManager.finishLoading()
        // 获取资源
        atlas = assetManager.get(Constant.ATLAS_PATH, TextureAtlas::class.java)
        bitmapFont = assetManager.get(Constant.BITMAP_FONT_PATH, BitmapFont::class.java)
        // 创建主游戏场景
        gameScreen = GameScreen(this)
        // 设置当前场景
        setScreen(gameScreen)
        // 捕获返回键, 手动处理应用的退出（防止“弹出”帮助界面或对话框时按返回键退出应用）
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
    }

    override fun dispose() {
        super.dispose()
        // 应用退出时, 需要手动销毁场景
        gameScreen.dispose()
        // 应用退出时释放资源
        assetManager.dispose()
    }

}