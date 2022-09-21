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

    // Location for texture atlas, we have to add *.atlas extension in addition of file name
    // defined in TexturePacker.process()
    const val TEXTURE_ATLAS_OBJECTS = "assets/images/canyonbunny.atlas"

    // Location of image file for level 01
    const val LEVEL_01 = "assets/levels/level-01.png"

    // Amount of extra lives at level start
    const val LIVES_START = 3

    // Duration of feather power-up in seconds
    const val ITEM_FEATHER_POWERUP_DURATION = 9f

    // Delay after game over
    const val TIME_DELAY_GAME_OVER = 3f

    const val TEXTURE_ATLAS_UI = "assets/images/canyonbunny-ui.atlas"
    const val TEXTURE_ATLAS_LIBGDX_UI = "assets/images/uiskin.atlas"

    // Location of description file for skins
    const val SKIN_LIBGDX_UI = "assets/images/uiskin.json"
    const val SKIN_CANYONBUNNY_UI = "assets/images/canyonbunny-ui.json"

    // Game preferences file
    const val PREFERENCES = "canyonbunny.prefs"
}