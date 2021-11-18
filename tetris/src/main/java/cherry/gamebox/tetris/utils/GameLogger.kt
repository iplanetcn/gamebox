package cherry.gamebox.tetris.utils

import com.badlogic.gdx.Gdx

/**
 * Logger
 *
 * @author john
 * @since 2021-11-18
 */
object GameLogger {
    private fun tag(): String? {
        return Thread.currentThread().stackTrace[4].let {
            val link = "(${it.fileName}:${it.lineNumber})"
            val path = "${it.className.substringAfterLast(".")}.${it.methodName}"
            if (path.length + link.length > 80) {
                "${path.take(80 - link.length)}...${link}"
            } else {
                "$path$link"
            }
        }
    }

    fun log(msg: String?) {
        Gdx.app.log(tag(), "💚 $msg")
    }

    fun debug(msg: String?) {
        Gdx.app.debug(tag(), "💙 $msg")
    }

    fun error(msg: String?) {
        Gdx.app.log(tag(), "💔 $msg")
    }
}