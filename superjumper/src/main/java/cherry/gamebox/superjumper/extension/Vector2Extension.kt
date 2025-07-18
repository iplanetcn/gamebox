package cherry.gamebox.superjumper.extension

import com.badlogic.gdx.math.Vector2
import kotlin.math.sqrt

operator fun Vector2.compareTo(other: Vector2): Int {
    val thisMagnitude = sqrt((x * x + y * y).toDouble())
    val otherMagnitude = sqrt((other.x * other.x + other.y * other.y).toDouble())
    return thisMagnitude.compareTo(otherMagnitude)
}