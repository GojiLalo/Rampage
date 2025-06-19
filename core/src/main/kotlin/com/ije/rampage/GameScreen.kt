package com.ije.rampage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.ScreenUtils

class GameScreen : Screen {
    private lateinit var batch: SpriteBatch
    private lateinit var camera: OrthographicCamera
    private lateinit var monsterTexture: Texture
    private lateinit var wallTexture: Texture
    private lateinit var monster: Rectangle
    private lateinit var wall: Rectangle
    private var wallIsDestroyed = false
    private val monsterSpeed = 200f // Píxeles por segundo

    private lateinit var upButtonTexture: Texture
    private lateinit var downButtonTexture: Texture
    private lateinit var leftButtonTexture: Texture
    private lateinit var rightButtonTexture: Texture

    private lateinit var upButton: Rectangle
    private lateinit var downButton: Rectangle
    private lateinit var leftButton: Rectangle
    private lateinit var rightButton: Rectangle

    override fun show() {
        // Esta función se llama una vez cuando se muestra la pantalla
        batch = SpriteBatch()
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f) // Tamaño de nuestra vista de juego

        // Cargar las imágenes desde la carpeta 'assets'
        monsterTexture = Texture("monster.png")
        wallTexture = Texture("wall.png")

        // Crear los rectángulos que representarán a nuestros objetos
        // El rectángulo nos da posición (x, y), dimensiones (width, height) y nos ayudará con las colisiones
        monster = Rectangle(100f, 100f, 64f, 64f)
        wall = Rectangle(400f, 100f, 32f, 128f)

        // --- CODIGO PARA CARGAR BOTONES ---
        upButtonTexture = Texture("arrow_up.png")
        downButtonTexture = Texture("arrow_down.png")
        leftButtonTexture = Texture("arrow_left.png")
        rightButtonTexture = Texture("arrow_right.png")

        val buttonSize = 64f // El tamaño de nuestros botones
        // Posicionamos los botones en la pantalla
        leftButton = Rectangle(20f, 84f, buttonSize, buttonSize)
        rightButton = Rectangle(148f, 84f, buttonSize, buttonSize)
        upButton = Rectangle(84f, 148f, buttonSize, buttonSize)
        downButton = Rectangle(84f, 20f, buttonSize, buttonSize)
        // --- FIN DE CÓDIGO PARA BOTONES---
    }

    // ... aquí irán los otros métodos ...
    // Dentro de la clase com.ije.rampage.GameScreen

    override fun render(delta: Float) {
        // 'delta' es el tiempo en segundos desde el último fotograma.
        // --- 1. LÓGICA DE INPUT (MOVIMIENTO) ---
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            monster.x -= monsterSpeed * delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            monster.x += monsterSpeed * delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            monster.y += monsterSpeed * delta
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            monster.y -= monsterSpeed * delta
        }
        if (Gdx.input.isTouched) {
            val touchX = Gdx.input.x.toFloat()
            // ¡IMPORTANTE! Invertimos la coordenada Y del toque
            val touchY = Gdx.graphics.height - Gdx.input.y.toFloat()
            if (upButton.contains(touchX, touchY)) {
                monster.y += monsterSpeed * delta
            } else if (downButton.contains(touchX, touchY)) {
                monster.y -= monsterSpeed * delta
            } else if (leftButton.contains(touchX, touchY)) {
                monster.x -= monsterSpeed * delta
            } else if (rightButton.contains(touchX, touchY)) {
                monster.x += monsterSpeed * delta
            }
        }

        // --- 2. LÓGICA DE JUEGO (COLISIÓN Y DESTRUCCIÓN) ---
        if (monster.overlaps(wall)) {
            wallIsDestroyed = true
        }

        // --- 3. RENDERIZADO (DIBUJAR EN PANTALLA) ---
        ScreenUtils.clear(0f, 0f, 0.2f, 1f) // Un azul oscuro
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(monsterTexture, monster.x, monster.y, monster.width, monster.height)

        if (!wallIsDestroyed) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height)
        }

        batch.end()

        batch.projectionMatrix = camera.projection

        batch.begin()
        batch.draw(upButtonTexture, upButton.x, upButton.y, upButton.width, upButton.height)
        batch.draw(downButtonTexture, downButton.x, downButton.y, downButton.width, downButton.height)
        batch.draw(leftButtonTexture, leftButton.x, leftButton.y, leftButton.width, leftButton.height)
        batch.draw(rightButtonTexture, rightButton.x, rightButton.y, rightButton.width, rightButton.height)
        batch.end()
    }

    override fun dispose() {
        monsterTexture.dispose()
        wallTexture.dispose()
        batch.dispose()
        upButtonTexture.dispose()
        downButtonTexture.dispose()
        leftButtonTexture.dispose()
        rightButtonTexture.dispose()
    }

    // Metodos vacíos que requiere la interfaz Screen
    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
}
