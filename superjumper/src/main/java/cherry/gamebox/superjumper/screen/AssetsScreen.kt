package cherry.gamebox.superjumper.screen

import cherry.gamebox.superjumper.Assets
import cherry.gamebox.superjumper.SuperJumperGame

class AssetsScreen(game: SuperJumperGame): BaseScreen(game) {
    var timePassed: Float = 0f

    override fun draw(delta: Float) {
        batch.begin()
        val first = Assets.sprites.coinList[(timePassed * 10).toInt() % 12]
        first.setPosition(100f, 100f)
        first.setSize(64f, 64f)
        first.draw(batch)

        val sprite = Assets.sprites.fruitList.first()
        sprite.setPosition(200f, 100f)
        sprite.setSize(64f, 64f)
        sprite.draw(batch)
        batch.end()

        timePassed += delta;
    }

    override fun update(delta: Float) {

    }

}