package cherry.gamebox.puzzles

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * GameLauncher
 *
 * @author john
 * @since 2021-12-01
 */
class GameLauncher  : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useImmersiveMode = true
        initialize(PuzzlesGame(), config)
    }
}