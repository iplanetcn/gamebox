package cherry.gamebox.bunny.util

/**
 * Constants
 *
 * @author john
 * @since 2020-12-06
 */
object Constants {

    // Visible game world is 5 meters wide
    const val VIEWPORT_WIDTH = 5.0f

    // Visible game world is 5 meters tall
    const val VIEWPORT_HEIGHT = 5.0f

    // GUI Width
    const val VIEWPORT_GUI_WIDTH = 800.0f

    // GUI Height
    const val VIEWPORT_GUI_HEIGHT = 480.0f

    // Location of description file for texture atlas
    const val TEXTURE_ATLAS_OBJECTS = "images/canyonbunny.pack"
    const val TEXTURE_ATLAS_UI = "images/canyonbunny-ui.pack"
    const val TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas"

    // Location of description file for skins
    const val SKIN_LIBGDX_UI = "images/uiskin.json"
    const val SKIN_CANYONBUNNY_UI = "images/canyonbunny-ui.json"

    // Location of image file for level 01
    const val LEVEL_01 = "levels/level-01.png"

    // Amount of extra lives at level start
    const val LIVES_START = 3

    // Duration of feather power-up in seconds
    const val ITEM_FEATHER_POWERUP_DURATION = 9f

    // Number of carrots to spawn
    const val CARROTS_SPAWN_MAX = 100

    // Spawn radius for carrots
    const val CARROTS_SPAWN_RADIUS = 3.5f

    // Delay after game over
    const val TIME_DELAY_GAME_OVER = 3f

    // Delay after game finished
    const val TIME_DELAY_GAME_FINISHED = 6f

    // Game preferences file
    const val PREFERENCES = "canyonbunny.prefs"

    // Shader
    const val shaderMonochromeVertex = "shaders/monochrome.vs"
    const val shaderMonochromeFragment = "shaders/monochrome.fs"

    // Angle of rotation for dead zone (no movement)
    const val ACCEL_ANGLE_DEAD_ZONE = 5.0f

    // Max angle of rotation needed to gain maximum movement velocity
    const val ACCEL_MAX_ANGLE_MAX_MOVEMENT = 20.0f
}