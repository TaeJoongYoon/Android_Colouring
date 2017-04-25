package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;

/**
 * Created by Yoon on 2017-01-08.
 */

public class WaitScreen implements Screen {
    private Coloring game;
    private OrthographicCamera cam;
    private Viewport waitPort;
    private Stage stage;

    public WaitScreen(Coloring coloring){
        this.game = coloring;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        waitPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(waitPort, game.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        stage.draw();
        game.batch.begin();
        game.batch.end();
        if(game.playServices.isSignedIn()) game.setScreen(new SplashScreen1(game));
    }

    @Override
    public void resize(int width, int height) {
        waitPort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
