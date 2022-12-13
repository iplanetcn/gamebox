package cherry.gamebox.solitaire.model

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage

/**
 * Stack
 *
 * @author john
 * @since 2022-12-11
 */
class Stack : Pile() {
    override fun display(stage: Stage) {
        val group = Group()
        for (card in cardList) {
            group.addActor(card.image)
        }

        group.setPosition(position.x, position.y)
        stage.addActor(group)
    }
}