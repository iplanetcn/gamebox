package cherry.gamebox.bunny.game

import cherry.gamebox.bunny.game.objects.BunnyHead.JumpState
import cherry.gamebox.bunny.game.objects.Carrot
import cherry.gamebox.bunny.game.objects.Feather
import cherry.gamebox.bunny.game.objects.GoldCoin
import cherry.gamebox.bunny.game.objects.Rock
import cherry.gamebox.bunny.screen.DirectedGame
import cherry.gamebox.bunny.screen.MenuScreen
import cherry.gamebox.bunny.screen.transitions.ScreenTransition
import cherry.gamebox.bunny.screen.transitions.ScreenTransitionSlide
import cherry.gamebox.bunny.screen.transitions.ScreenTransitionSlide.init
import cherry.gamebox.bunny.util.AudioManager
import cherry.gamebox.bunny.util.CameraHelper
import cherry.gamebox.bunny.util.Constants
import cherry.gamebox.core.GameLogger
import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Peripheral
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import kotlin.math.abs


/**
 * WorldController
 *
 * @author john
 * @since 2020-12-09
 */
class WorldController(val game: DirectedGame) : InputAdapter() {
    lateinit var level: Level
    var lives = 0
    var livesVisual = 0f
    var score = 0
    var scoreVisual = 0f
    private var goalReached = false
    private var accelerometerAvailable = false
    var cameraHelper: CameraHelper
    // Rectangles for collision detection
    private val r1: Rectangle = Rectangle()
    private val r2: Rectangle = Rectangle()
    private var timeLeftGameOverDelay = 0f
    var b2world: World? = null
    var stateTime = 0f

    private var helpTiltWasVisible = false
    private var helpTouchWasVisible = false
    private var helpFlyWasVisible = false

    init {
        helpTiltWasVisible = false
        helpTouchWasVisible = false
        helpFlyWasVisible = false
        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        cameraHelper = CameraHelper()
        lives = Constants.LIVES_START
        livesVisual = lives.toFloat()
        timeLeftGameOverDelay = 0f
        stateTime = 0f
        initLevel()
    }

    private fun restart() {
        helpTiltWasVisible = false
        helpTouchWasVisible = false
        helpFlyWasVisible = false
        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        cameraHelper = CameraHelper()
        lives = Constants.LIVES_START
        livesVisual = lives.toFloat()
        timeLeftGameOverDelay = 0f
        stateTime = 0f
        initLevel()
    }

    private fun initLevel() {
        score = 0
        scoreVisual = 0f
        goalReached = false
        level = Level(Constants.LEVEL_01)
        cameraHelper.setTarget(level.bunnyHead)
        initPhysics()
        if (helpTouchWasVisible) showHelpTouch(true)
        if (helpTiltWasVisible) showHelpTilt(true)
        if (helpFlyWasVisible) showHelpFly(true)
    }

    private fun initPhysics() {
        if (b2world != null) b2world!!.dispose()
        b2world = World(Vector2(0f, -9.81f), true)
        // Rocks
        val origin = Vector2()
        for (rock in level.rocks) {
            val bodyDef = BodyDef()
            bodyDef.type = BodyDef.BodyType.KinematicBody
            bodyDef.position.set(rock.position)
            val body: Body = b2world!!.createBody(bodyDef)
            rock.body = body
            val polygonShape = PolygonShape()
            origin.x = rock.bounds.width / 2.0f
            origin.y = rock.bounds.height / 2.0f
            polygonShape.setAsBox(rock.bounds.width / 2.0f, rock.bounds.height / 2.0f, origin, 0f)
            val fixtureDef = FixtureDef()
            fixtureDef.shape = polygonShape
            body.createFixture(fixtureDef)
            polygonShape.dispose()
        }
    }

    fun update(deltaTime: Float) {
        handleDebugInput(deltaTime)
        if (isGameOver() || goalReached) {
            timeLeftGameOverDelay -= deltaTime
            if (timeLeftGameOverDelay < 0) backToMenu()
        } else {
            handleInputGame(deltaTime)
        }
        level.update(deltaTime)
        testCollisions()
        b2world!!.step(deltaTime, 8, 3)
        cameraHelper.update(deltaTime)
        if (!isGameOver() && isPlayerInWater()) {
            AudioManager.play(Assets.sounds.liveLost)
            lives--
            if (isGameOver()) timeLeftGameOverDelay =
                Constants.TIME_DELAY_GAME_OVER else initLevel()
        }
        level.mountains.updateScrollPosition(cameraHelper.getPosition())
        if (livesVisual > lives) livesVisual =
            lives.toFloat().coerceAtLeast(livesVisual - 1 * deltaTime)
        if (scoreVisual < score) scoreVisual =
            score.toFloat().coerceAtMost(scoreVisual + 250 * deltaTime)
        stateTime += deltaTime
        if (stateTime >= 1) {
            showHelpTilt(false)
        }
    }

    fun isGameOver(): Boolean {
        return lives < 0
    }

    private fun isPlayerInWater(): Boolean {
        return level.bunnyHead!!.position.y < -5
    }

    private fun testCollisions() {
        r1.set(
            level.bunnyHead!!.position.x,
            level.bunnyHead!!.position.y,
            level.bunnyHead!!.bounds.width,
            level.bunnyHead!!.bounds.height
        )

        // Test collision: Bunny Head <-> Rocks
        for (rock in level.rocks) {
            r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height)
            if (!r1.overlaps(r2)) continue
            onCollisionBunnyHeadWithRock(rock)
            // IMPORTANT: must do all collisions for valid edge testing on rocks.
        }

        // Test collision: Bunny Head <-> Gold Coins
        for (goldcoin in level.goldcoins) {
            if (goldcoin.collected) continue
            r2.set(
                goldcoin.position.x,
                goldcoin.position.y,
                goldcoin.bounds.width,
                goldcoin.bounds.height
            )
            if (!r1.overlaps(r2)) continue
            onCollisionBunnyWithGoldCoin(goldcoin)
            break
        }

        // Test collision: Bunny Head <-> Feathers
        for (feather in level.feathers) {
            if (feather.collected) continue
            r2.set(
                feather.position.x,
                feather.position.y,
                feather.bounds.width,
                feather.bounds.height
            )
            if (!r1.overlaps(r2)) continue
            onCollisionBunnyWithFeather(feather)
            break
        }

        // Test collision: Bunny Head <-> Goal
        if (!goalReached) {
            r2.set(level.goal?.bounds)
            r2.x += level.goal!!.position.x
            r2.y += level.goal!!.position.y
            if (r1.overlaps(r2)) onCollisionBunnyWithGoal()
        }
        if (level.bunnyHead!!.position.x in 3.0..4.0) {
            showHelpTouch(false)
        } else if (level.bunnyHead!!.position.x in 20.0..21.0) {
            showHelpFly(false)
        }
    }

    private fun onCollisionBunnyHeadWithRock(rock: Rock) {
        val bunnyHead = level.bunnyHead
        val heightDifference =
            abs(bunnyHead!!.position.y - (rock.position.y + rock.bounds.height))
        if (heightDifference > 0.25f) {
            val hitLeftEdge = bunnyHead.position.x > rock.position.x + rock.bounds.width / 2.0f
            if (hitLeftEdge) {
                bunnyHead.position.x = rock.position.x + rock.bounds.width
            } else {
                bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width
            }
            return
        }
        when (bunnyHead.jumpState) {
            JumpState.GROUNDED -> {}
            JumpState.FALLING, JumpState.JUMP_FALLING -> {
                bunnyHead.position.y =
                    rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y
                bunnyHead.jumpState = JumpState.GROUNDED
            }
            JumpState.JUMP_RISING -> bunnyHead.position.y =
                rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y
        }
    }

    private fun onCollisionBunnyWithGoldCoin(goldcoin: GoldCoin) {
        goldcoin.collected = true
        AudioManager.play(Assets.sounds.pickupCoin)
        score += goldcoin.getScore()
        GameLogger.log( "Gold coin collected")
    }

    private fun onCollisionBunnyWithFeather(feather: Feather) {
        feather.collected = true
        AudioManager.play(Assets.sounds.pickupFeather)
        score += feather.getScore()
        level.bunnyHead!!.setFeatherPowerup(true)
        GameLogger.log( "Feather collected")
    }

    private fun onCollisionBunnyWithGoal() {
        goalReached = true
        timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_FINISHED
        val centerPosBunnyHead = Vector2(level.bunnyHead!!.position)
        centerPosBunnyHead.x += level.bunnyHead!!.bounds.width
        spawnCarrots(
            centerPosBunnyHead,
            Constants.CARROTS_SPAWN_MAX,
            Constants.CARROTS_SPAWN_RADIUS
        )
    }

    private fun handleDebugInput(deltaTime: Float) {
        if (Gdx.app.type != ApplicationType.Desktop) return
        if (!cameraHelper.hasTarget(level.bunnyHead!!)) {
            // Camera Controls (move)
            var camMoveSpeed = 5 * deltaTime
            val camMoveSpeedAccelerationFactor = 5f
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0f)
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0f)
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0f, camMoveSpeed)
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0f, -camMoveSpeed)
            if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.setPosition(0f, 0f)
        }

        // Camera Controls (zoom)
        var camZoomSpeed = 1 * deltaTime
        val camZoomSpeedAccelerationFactor = 5f
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed)
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) cameraHelper.setZoom(1f)
    }

    private fun handleInputGame(deltaTime: Float) {
        if (cameraHelper.hasTarget(level.bunnyHead!!)) {
            // Player Movement
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                level.bunnyHead!!.velocity.x = -level.bunnyHead!!.terminalVelocity.x
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                level.bunnyHead!!.velocity.x = level.bunnyHead!!.terminalVelocity.x
            } else {
                // Use accelerometer for movement if available
                if (accelerometerAvailable) {
                    // normalize accelerometer values from [-10, 10] to [-1, 1]
                    // which translate to rotations of [-90, 90] degrees
                    var amount = Gdx.input.accelerometerY / 10.0f
                    amount *= 90.0f
                    // is angle of rotation inside dead zone?
                    if (abs(amount) < Constants.ACCEL_ANGLE_DEAD_ZONE) {
                        amount = 0f
                    } else {
                        // use the defined max angle of rotation instead of
                        // the full 90 degrees for maximum velocity
                        amount /= Constants.ACCEL_MAX_ANGLE_MAX_MOVEMENT
                    }
                    level.bunnyHead!!.velocity.x = level.bunnyHead!!.terminalVelocity.x * amount
                } else if (Gdx.app.type != ApplicationType.Desktop) {
                    level.bunnyHead!!.velocity.x = level.bunnyHead!!.terminalVelocity.x
                }
            }

            // Bunny Jump
            if (Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                level.bunnyHead!!.setJumping(true)
            } else {
                level.bunnyHead!!.setJumping(false)
            }
        }
    }

    private fun moveCamera(x: Float, y: Float) {
        var x = x
        var y = y
        x += cameraHelper.getPosition().x
        y += cameraHelper.getPosition().y
        cameraHelper.setPosition(x, y)
    }

    override fun keyUp(keycode: Int): Boolean {
        // Reset game world
        when (keycode) {
            Input.Keys.R -> {
                restart()
                GameLogger.debug("Game world reset")
            }
            Input.Keys.ENTER -> {
                cameraHelper.setTarget(if (cameraHelper.hasTarget()) null else level.bunnyHead)
                GameLogger.debug("Camera follow enabled: " + cameraHelper.hasTarget())
            }
            Input.Keys.ESCAPE, Input.Keys.BACK -> {
                backToMenu()
                // } else if (keycode == Input.Keys.B) {
                // level.helpTilt.position.y = 10000;
                // level.helpTouch.position.y = 10000;
                // } else if (keycode == Keys.N) {
                // showHelpTilt(false);
                // } else if (keycode == Keys.M) {
                // showHelpTouch(false);
            }
        }
        return false
    }

    private fun showHelpTilt(instantaneous: Boolean) {
        helpTiltWasVisible = true
        val xSrc = 0.25f
        val ySrc = 2f
        val xDst = 0f
        val yDst = -2f
        var duration = 3f
        val easing: Interpolation = Interpolation.elasticOut
        if (instantaneous) duration = 0f
        level.helpTilt.moveBy(xSrc, ySrc, xDst, yDst, duration, easing)
    }

    private fun showHelpTouch(instantaneous: Boolean) {
        helpTouchWasVisible = true
        val xSrc = 4f
        val ySrc = 2f
        val xDst = 0f
        val yDst = -2.25f
        var duration = 3f
        val easing: Interpolation = Interpolation.elasticOut
        if (instantaneous) duration = 0f
        level.helpTouch.moveBy(xSrc, ySrc, xDst, yDst, duration, easing)
    }

    private fun showHelpFly(instantaneous: Boolean) {
        helpFlyWasVisible = true
        val xSrc = 22f
        val ySrc = 2f
        val xDst = 0f
        val yDst = -2f
        var duration = 3f
        val easing: Interpolation = Interpolation.elasticOut
        if (instantaneous) duration = 0f
        level.helpFly.moveBy(xSrc, ySrc, xDst, yDst, duration, easing)
    }

    private fun backToMenu() {
        // switch to menu screen
        val transition: ScreenTransition =
            init(0.75f, ScreenTransitionSlide.DOWN, false, Interpolation.bounceOut)
        game.setScreen(MenuScreen(game), transition)
    }

    fun dispose() {
        if (b2world != null) b2world!!.dispose()
    }

    private fun spawnCarrots(pos: Vector2, numCarrots: Int, radius: Float) {
        val carrotShapeScale = 0.5f
        // create carrots with box2d body and fixture
        for (i in 0 until numCarrots) {
            val carrot = Carrot()
            // calculate random spawn position, rotation, and scale
            val x: Float = MathUtils.random(-radius, radius)
            val y: Float = MathUtils.random(5.0f, 15.0f)
            val rotation: Float = MathUtils.random(0.0f, 360.0f) * MathUtils.degreesToRadians
            val carrotScale: Float = MathUtils.random(0.5f, 1.5f)
            carrot.scale[carrotScale] = carrotScale
            // create box2d body for carrot with start position
            // and angle of rotation
            val bodyDef = BodyDef()
            bodyDef.position.set(pos)
            bodyDef.position.add(x, y)
            bodyDef.angle = rotation
            val body: Body = b2world!!.createBody(bodyDef)
            body.type = BodyDef.BodyType.DynamicBody
            carrot.body = body
            // create rectangular shape for carrot to allow
            // interactions (collisions) with other objects
            val polygonShape = PolygonShape()
            val halfWidth = carrot.bounds.width / 2.0f * carrotScale
            val halfHeight = carrot.bounds.height / 2.0f * carrotScale
            polygonShape.setAsBox(halfWidth * carrotShapeScale, halfHeight * carrotShapeScale)
            // set physics attributes
            val fixtureDef = FixtureDef()
            fixtureDef.shape = polygonShape
            fixtureDef.density = 50f
            fixtureDef.restitution = 0.5f
            fixtureDef.friction = 0.5f
            body.createFixture(fixtureDef)
            polygonShape.dispose()
            // finally, add new carrot to list for updating/rendering
            level.carrots.add(carrot)
        }
    }
}
