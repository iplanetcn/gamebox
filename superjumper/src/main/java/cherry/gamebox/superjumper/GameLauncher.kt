package cherry.gamebox.superjumper

import android.app.Activity
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.annotation.RequiresApi
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * GameLauncher
 *
 * @author john
 * @since 2021-12-09
 */
class GameLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useImmersiveMode = false
        initialize(SuperJumperGame(), config)
        GameLogger.setLogDebug()
        if (SDK_INT > Build.VERSION_CODES.Q) {
//            notchHeight = getNotchHeight()?.toFloat() ?: 0f
        }
    }

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun Activity.getNotchHeight(): Int? =
        if (SDK_INT >= Build.VERSION_CODES.R)
            windowManager.currentWindowMetrics.windowInsets
                .displayCutout?.safeInsetTop
        else
            windowManager.defaultDisplay.cutout?.safeInsetTop
}