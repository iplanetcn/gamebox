package cherry.gamebox.tilematch

import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * GameLauncher
 *
 * @author john
 * @since 2022-09-19
 */

//Defining interface for customized methods
interface AndroidInterfaces {
    fun toast(message: String?)
}

class GameLauncher : AndroidApplication(), AndroidInterfaces {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useImmersiveMode = true
        initialize(TileMatchGame(this), config)
    }

    override fun toast(message: String?) {
        handler.post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}