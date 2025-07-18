package cherry.gamebox.superjumper.screen

import cherry.gamebox.superjumper.Assets
import cherry.gamebox.superjumper.SuperJumperGame
import cherry.gamebox.superjumper.actors.CoinAnimationActor
import cherry.gamebox.superjumper.actors.PlayerAnimationActor
import cherry.gamebox.superjumper.config.VIEWPORT_HEIGHT
import cherry.gamebox.superjumper.config.VIEWPORT_WIDTH
import cherry.gamebox.superjumper.controller.TouchController
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport


class AssetsScreen(game: SuperJumperGame): BaseScreen(game) {
    var timePassed: Float = 0f

    private val gameStage: Stage
    private val hudStage: Stage
    private val gameViewport: Viewport
    private val hudViewport: Viewport
    private val touchController: TouchController

    init {
        // 1. 游戏逻辑视图（固定逻辑尺寸）
        gameViewport = FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
        gameStage = Stage(gameViewport, batch)

        // 2. HUD 视图（屏幕像素尺寸）
        hudViewport = ScreenViewport()
        hudStage = Stage(hudViewport, batch)

        // 3. 添加 HUD Actor（不会缩放）
        touchController = TouchController(hudStage)
        game.controllerManager.touchController = touchController
        // 让 HUD 接收输入
        Gdx.input.inputProcessor = hudStage

        // 4. 添加游戏 Actor（演示）
        val player = PlayerAnimationActor(touchController)
        player.setBounds(50f, 100f, 32f, 32f)
        gameStage.addActor(player)

        val coin = CoinAnimationActor()
        coin.setBounds(100f, 100f, 16f, 16f)
        gameStage.addActor(coin)

        val fruit = Image(Assets.sprites.fruitList.first())
        fruit.setPosition(150f, 100f)
        gameStage.addActor(fruit)



    }

    override fun render(delta: Float) {
        super.render(delta)
        // 更新
        gameStage.act(delta)
        hudStage.act(delta)

        // 清屏
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT)

        // 渲染游戏内容
        gameStage.draw()

        // 渲染 HUD
        hudStage.draw()
    }

    override fun draw(delta: Float) {
        // 绘制逻辑
    }

    override fun update(delta: Float) {
        // 更新时间
        timePassed += delta

    }

    public override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        hudViewport.update(width, height, true)
    }

}