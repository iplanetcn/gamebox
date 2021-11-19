package cherry.gamebox.tetris.shape

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException


/**
 * Circle
 *
 * @author john
 * @since 2021-11-19
 */
class Circle(x: Float, y: Float, r: Float, color: Color) {
    val VERT_SHADER = """
        attribute vec2 a_position;
        uniform mat4 u_projTrans;
        uniform vec2 u_pos_center;
        varying vec4 v_pos_pixel;
        varying vec4 v_pos_center;
        void main() {
        v_pos_pixel  =  u_projTrans * vec4(a_position.xy, 0.0 , 1.0);
        v_pos_center = u_projTrans * vec4(u_pos_center.xy, 0.0 , 1.0);
        gl_Position = v_pos_pixel;
        }
        """.trimIndent()
    val FRAG_SHADER = """
        #ifdef GL_ES
        precision mediump float;
        #endif
        varying vec4 v_pos_pixel;
        varying vec4 v_pos_center;
        uniform mat4  u_projTrans;
        uniform vec4  u_color;
        uniform float u_radius;
        uniform float u_scale;
        void main() {
        vec4 r = u_projTrans * vec4(u_radius,0.0,0.0,0.0);
        vec4 p_pixel = v_pos_pixel,p_center = v_pos_center;
        p_pixel.x /= u_scale;
        p_center.x /= u_scale;
        float dist = distance(p_pixel.xy, p_center.xy);
        float alpha = smoothstep(r.x, r.x + 0.05, dist);
        vec4 col = mix(u_color, vec4(0.0), alpha);
        col.w = smoothstep(r.x + 0.005, r.x,dist);
        col.w *= u_color.w / 1.0;gl_FragColor = col;
        }
        """.trimIndent()
    var x: Float
    var y: Float
    var r: Float
    var color: Color
    var mesh: Mesh
    var shader: ShaderProgram
    var camera: OrthographicCamera

    protected fun createMesh(): Mesh {
        val mesh =
            Mesh(true, 4, 6, VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"))
        mesh.setVertices(generateVertices())
        mesh.setIndices(shortArrayOf(0, 1, 2, 1, 2, 3))
        return mesh
    }

    protected fun generateVertices(): FloatArray {
        val v = FloatArray(4 * 2)
        v[0] = x - r * 1.5f
        v[1] = y - r * 1.5f
        v[2] = x - r * 1.5f
        v[3] = y + r * 1.5f
        v[4] = x + r * 1.5f
        v[5] = y - r * 1.5f
        v[6] = x + r * 1.5f
        v[7] = y + r * 1.5f
        return v
    }

    protected fun createMeshShader(): ShaderProgram {
        ShaderProgram.pedantic = false
        val shader = ShaderProgram(VERT_SHADER, FRAG_SHADER)
        val log = shader.log
        if (!shader.isCompiled) throw GdxRuntimeException(log)
        if (log != null && log.length != 0) println("Shader Log: $log")
        return shader
    }

    fun dispose() {
        mesh.dispose()
        shader.dispose()
    }

    fun draw() {
        Gdx.gl.glDepthMask(false)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        shader.bind()
        shader.setUniformMatrix("u_projTrans", camera.combined)
        shader.setUniformf("u_color", color)
        shader.setUniformf("u_scale", Gdx.graphics.height.toFloat() / Gdx.graphics.width.toFloat())
        shader.setUniformf("u_radius", r / 1.51f)
        shader.setUniformf("u_pos_center", Vector2(x, y))
        mesh.render(shader, GL20.GL_TRIANGLES, 0, 6)
    }

    init {
        this.x = x
        this.y = y
        this.r = r
        this.color = color
        mesh = createMesh()
        shader = createMeshShader()
        camera = OrthographicCamera()
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }

    companion object {
        val circleRed =
            Circle(0f, 0f, 20f, Color(231.0f / 255.0f, 76.0f / 255.0f, 60.0f / 255.0f, 1f));
        val circleGreen =
            Circle(0f, 0f, 20f, Color(46.0f / 255.0f, 204.0f / 255.0f, 113.0f / 225.0f, 1f));
        val circleBlue =
            Circle(0f, 0f, 20f, Color(52.0f / 255.0f, 152.0f / 255.0f, 219.0f / 255.0f, 1f));
    }
}