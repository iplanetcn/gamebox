package cherry.gamebox.bunny

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * GameLauncher
 *
 * @author john
 * @since 2020-12-09
 */
class GameLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useImmersiveMode = true
        initialize(BunnyGame(), config)
    }
}