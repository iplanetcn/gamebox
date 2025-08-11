package cherry.gamebox.superjumper.controller

import cherry.gamebox.superjumper.Assets
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class TouchController(hudStage: Stage) {
    var leftPad: Touchpad
    var hitButton: ImageButton

    init {
        val background = TextureRegionDrawable(Assets.joystick.backgroundTexture)
        val knob = TextureRegionDrawable(Assets.joystick.knobTexture)
        leftPad = Touchpad(50f, Touchpad.TouchpadStyle(background, knob))
        leftPad.setPosition(50f,0f)
        hudStage.addActor(leftPad)

        val hitTexture = TextureRegionDrawable(Assets.joystick.knobTexture)
        hitButton =  ImageButton(hitTexture)
        hitButton.setBounds(Gdx.graphics.width - 100f - hitButton.width, 200f, 100f, 100f)
        hudStage.addActor(hitButton)
    }

    fun update(delta: Float) {

    }

    fun hide() {
        leftPad.isVisible = false
        hitButton.isVisible = false
    }

    fun show() {
        leftPad.isVisible = true
        hitButton.isVisible = true
    }

    fun getLeftPadKnobPosition(): Vector2 {
        return Vector2(leftPad.knobX, leftPad.knobY)
    }

    fun getLeftPadKnobPercent(): Vector2 {
        return Vector2(leftPad.knobPercentX, leftPad.knobPercentY)
    }
}