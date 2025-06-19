package com.ije.rampage

import com.badlogic.gdx.Game

class MyGdxGame : Game() {
    override fun create() {
        setScreen(GameScreen())
    }
}
