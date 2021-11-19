package cherry.gamebox.tetris.utils

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

/**
 * Logger
 *
 * @author john
 * @since 2021-11-18
 */
object GameLogger {
    private fun tag(): String {
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


    private fun setLogLevel(level: Int) {
        Gdx.app.logLevel = level
    }

    fun setLogNone() {
        setLogLevel(Application.LOG_NONE)
    }

    fun setLogInfo() {
        setLogLevel(Application.LOG_INFO)
    }

    fun setLogDebug() {
        setLogLevel(Application.LOG_DEBUG)
    }

    fun setLogError() {
        setLogLevel(Application.LOG_ERROR)
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