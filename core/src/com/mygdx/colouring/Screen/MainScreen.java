package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;
import com.mygdx.colouring.Info.MenuCircleInfo;

import java.util.Iterator;

/**
 * Created by Yoon on 2017-01-08.
 */

public class MainScreen implements Screen {
    private Array<MenuCircleInfo> menuCircleInfos;

    private Coloring game;
    private OrthographicCamera cam;
    private Viewport mainPort;
    private Stage stage;
    private Texture circleRed, circleGreen, circleBlue, circleBlack;
    private TextureAtlas atlas;
    private Skin skin;
    private ImageButton btnStart, btnRank, btnOption, btnExit, btnAch;
    private Table table;
    private int rotation;
    private long createTime;
    private float fade;
    private boolean fadeinout;

    public MainScreen(Coloring coloring){
        this.game = coloring;
        menuCircleInfos = new Array<MenuCircleInfo>();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        mainPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(mainPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/mainbutton.atlas");
        skin = new Skin(atlas);

        btnStart = new ImageButton(skin.getDrawable("play"));
        btnRank = new ImageButton(skin.getDrawable("rank"));
        btnAch = new ImageButton(skin.getDrawable("ach"));
        btnOption = new ImageButton(skin.getDrawable("option"));
        btnExit = new ImageButton(skin.getDrawable("close"));

        btnStart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new DifficultyScreen(game));
                dispose();
            }
        });
        btnRank.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playServices.showScore();
            }
        });
        btnAch.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playServices.showAchievement();
            }
        });
        btnOption.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionScreen(game));
                dispose();
            }
        });
        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table = new Table();
        table.setBounds(0,30,game.WIDTH,250);
        table.add(btnStart);
        table.add().padRight(20);
        table.add(btnRank);
        table.add().padRight(20);
        table.add(btnAch);
        table.add().padRight(20);
        table.add(btnOption);
        table.add().padRight(20);
        table.add(btnExit);

        circleRed = new Texture(Gdx.files.internal("dot/reddot.png"));
        circleGreen = new Texture(Gdx.files.internal("dot/greendot.png"));
        circleBlue = new Texture(Gdx.files.internal("dot/bluedot.png"));
        circleBlack = new Texture(Gdx.files.internal("dot/blackdot.png"));

        rotation = 0;
        fade = 1.0f;
        fadeinout = true;

        stage.addActor(table);
        game.setShow(false);
    }

    public void drawCircle(SpriteBatch batch, Sprite sprite, Texture texture, MenuCircleInfo menuCircleInfo){
        sprite = new Sprite(texture);
        sprite.setPosition(menuCircleInfo.getObject().x-texture.getWidth()/2,menuCircleInfo.getObject().y-texture.getHeight()/2);
        sprite.setSize(texture.getWidth(),texture.getHeight());
        sprite.setOrigin(texture.getWidth()/2,texture.getHeight()/2);
        sprite.setRotation(rotation += 2);
        sprite.draw(batch);
    }
    public void createCircle(){
        MenuCircleInfo menuCircleInfo = new MenuCircleInfo();
        menuCircleInfos.add(menuCircleInfo);
        createTime = TimeUtils.nanoTime();
    }
    public void fadeInOut(){
        if(fade <= 0.01f) fadeinout = false;
        else if(fade >= 1.0f) fadeinout = true;
        if(fadeinout == true) fade -= 0.01f;
        else if(fadeinout == false) fade += 0.01f;
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
        game.batch.begin();
        game.titlefont.setColor(0,204,255,fade);
        game.titlefont.draw(game.batch, "COLOURING", 7, game.HEIGHT/2+80);
        for(MenuCircleInfo menuCircleInfo : menuCircleInfos){
            Sprite sprite = null;
            if(menuCircleInfo.getColor() == 1)drawCircle(game.batch, sprite, circleRed, menuCircleInfo);
            if(menuCircleInfo.getColor() == 2)drawCircle(game.batch, sprite, circleGreen, menuCircleInfo);
            if(menuCircleInfo.getColor() == 3)drawCircle(game.batch, sprite, circleBlue, menuCircleInfo);
            if(menuCircleInfo.getColor() == 4)drawCircle(game.batch, sprite, circleBlack, menuCircleInfo);
        }
        game.batch.end();
        stage.draw();
        fadeInOut();

        if (TimeUtils.nanoTime() - createTime > 700000000) createCircle();

        Iterator<MenuCircleInfo> iter = menuCircleInfos.iterator();
        while (iter.hasNext()) {
            MenuCircleInfo menuCircleInfo = iter.next();
            menuCircleInfo.moveCircle();
            if(menuCircleInfo.isMoving() == false) iter.remove();
        }
    }

    @Override
    public void resize(int width, int height) {
        mainPort.update(width, height);
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
        menuCircleInfos.clear();
        stage.dispose();
        atlas.dispose();
        circleBlack.dispose();
        circleBlue.dispose();
        circleGreen.dispose();
        circleRed.dispose();
        skin.dispose();
    }
}
