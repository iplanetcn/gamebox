package cherry.gamebox.bunny.util

import com.badlogic.gdx.graphics.Color

/**
 * CharacterSkin
 *
 * @author john
 * @since 2022-09-21
 */
enum class CharacterSkin(name: String, r: Float, g: Float, b: Float) {
    WHITE("White", 1.0f, 1.0f, 1.0f),
    GRAY("Gray", 0.7f, 0.7f, 0.7f),
    BROWN("Brown", 0.7f, 0.5f, 0.3f);
    val color: Color = Color()
    val alias: String
    init {
        alias = name
        color.set(r, g, b, 1.0f)
    }
}