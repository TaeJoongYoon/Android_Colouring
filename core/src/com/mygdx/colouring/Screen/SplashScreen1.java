package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;

/**
 * Created by Yoon on 2017-02-09.
 */

public class SplashScreen1 implements Screen {

    private Coloring game;
    private OrthographicCamera cam;
    private Viewport splashPort;
    private Stage stage;
    private long createTime;
    private Texture splash;
    private Sprite sp;
    private float fade;
    private boolean fadeinout;

    public SplashScreen1(Coloring coloring){
        this.game = coloring;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        splashPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(splashPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        createTime = TimeUtils.millis();
        splash = new Texture(Gdx.files.internal("background.jpg"));
        sp = new Sprite(splash);
        fade = 0.02f;
        fadeinout = false;
        game.backgroundMusic.pause();
    }

    public void fadeInOut(){
        if(fadeinout == false) fade += 0.007f;
        else if(fadeinout == true) fade -= 0.007f;
        if(fade >= 1.0f){
            fade = 1.0f;
            fadeinout = true;
        }
        else if(fade <= 0.01f){
            fade = 0;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        sp.setAlpha(fade);
        sp.draw(game.batch);
        game.batch.end();
        stage.draw();
        fadeInOut();
        if(TimeUtils.timeSinceMillis(createTime) > 5500) {
            game.backgroundMusic.play();
            game.setScreen(new MainScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {splashPort.update(width, height); }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        splash.dispose();
    }
}
