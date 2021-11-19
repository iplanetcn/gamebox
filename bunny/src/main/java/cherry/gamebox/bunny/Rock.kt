package cherry.gamebox.bunny

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion




class Rock(var length : Int): AbstractGameObject(){
    private val regEdge: TextureRegion
    private val middleEdge: TextureRegion

    init {
        dimension.set(1f, 1.5f)
        regEdge = Assets.rock.edge
        middleEdge = Assets.rock.middle

    }

    fun increaseLength(amount: Int ) {
        this.length += amount
    }

    override fun render(batch: SpriteBatch) {
        var reg: TextureRegion? = null // thuc chat la mot con tro ma thoi

        var relX = 0f
        val relY = 0f

        reg = regEdge
        relX -= dimension.x / 4
        batch.draw(
            reg.texture,
            position.x + relX, position.y + relY,
            origin.x, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.regionX,
            reg.regionY,
            reg.regionWidth,
            reg.regionHeight,
            false,
            false
        )
        // draw middle rock
        // draw middle rock
        relX = 0f
        reg = middleEdge // gan cho no 1 doi tuong moi


        for (i in 0 until length) {
            // khi  muon ve cai gi thi phai goi batch.draw trong do batch la
            // mot doi tuong SpriteBatch
            // VE thoi
            batch.draw(
                reg.texture,
                position.x + relX, position.y + relY,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation,
                reg.regionX, reg.regionY,
                reg.regionWidth, reg.regionHeight,
                false, false
            )
            // cap nhat lai rel (day coi nhu  la cap nhat lai vi tri de ve tren truc Ox thoi)
            relX += dimension.x
        }

        // draw right edge => don gian la t lay doi xung thoi

        // draw right edge => don gian la t lay doi xung thoi
        reg = regEdge
        batch.draw(
            reg.texture,
            position.x + relX, position.y + relY,
            origin.x + dimension.x / 8, origin.y,
            dimension.x / 4, dimension.y,
            scale.x, scale.y,
            rotation,
            reg.regionX, reg.regionY,
            reg.regionWidth, reg.regionHeight,
            true, false
        ) // chu y la co 1 lan flip

    }

}
