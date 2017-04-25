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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;
import com.mygdx.colouring.Info.CircleInfo;
import com.mygdx.colouring.Info.MenuCircleInfo;

import java.util.Iterator;

/**
 * Created by Yoon on 2017-01-08.
 */

public class DifficultyScreen implements Screen {
    private Coloring game;
    private Array<MenuCircleInfo> menuCircleInfos;
    private OrthographicCamera cam;
    private Viewport difPort;
    private Stage stage;
    private Texture circleRed, circleGreen, circleBlue, circleBlack;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton btnEasy, btnNormal, btnHard, btnVeryhard, btnHell, btnLegend;
    private TextButton.TextButtonStyle s1,s2,s3,s4,s5, s6;
    private Table table;
    private long createTime;
    private int rotation;

    public DifficultyScreen(Coloring coloring){
        this.game = coloring;
        menuCircleInfos = new Array<MenuCircleInfo>();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        difPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(difPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/dif.atlas");
        skin = new Skin(atlas);
        s1 = new TextButton.TextButtonStyle(); s2 = new TextButton.TextButtonStyle(); s3 = new TextButton.TextButtonStyle(); s4 = new TextButton.TextButtonStyle(); s5 = new TextButton.TextButtonStyle(); s6 = new TextButton.TextButtonStyle();
        s1.font = game.diffont; s2.font = game.diffont; s3.font = game.diffont; s4.font = game.diffont; s5.font = game.diffont; s6.font = game.diffont;
        s1.up = skin.getDrawable("easy"); s2.up = skin.getDrawable("normal"); s3.up = skin.getDrawable("hard"); s4.up = skin.getDrawable("veryhard"); s5.up = skin.getDrawable("hell"); s6.up = skin.getDrawable("legend");

        btnEasy = new TextButton("EASY", s1); btnNormal = new TextButton("NORMAL", s2);
        btnHard = new TextButton("HARD",s3); btnVeryhard = new TextButton("VERY HARD",s4);
        btnHell = new TextButton("HELL", s5); btnLegend = new TextButton("LEGEND", s6);

        btnEasy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(190);
                game.setElapesd(0.01f);
                game.setCreateDelay(1200000000);
                game.COLOR = 4;
                game.SIZE = 100;
                game.PLAY = 1;
                game.setScreen(new GameScreen(game));
            }
        });

        btnNormal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(220);
                game.setElapesd(0.012f);
                game.setCreateDelay(1000000000);
                game.COLOR = 4;
                game.SIZE = 100;
                game.PLAY = 2;
                game.setScreen(new GameScreen(game));
            }
        });

        btnHard.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(250);
                game.setElapesd(0.014f);
                game.setCreateDelay(900000000);
                game.COLOR = 4;
                game.SIZE = 100;
                game.PLAY = 3;
                game.setScreen(new GameScreen(game));
            }
        });

        btnVeryhard.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(300);
                game.setElapesd(0.0145f);
                game.setCreateDelay(900000000);
                game.COLOR = 4;
                game.SIZE = 100;
                game.PLAY = 4;
                game.setScreen(new GameScreen(game));
            }
        });

        btnHell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(330);
                game.setElapesd(0.015f);
                game.setCreateDelay(850000000);
                game.COLOR = 5;
                game.SIZE = 76;
                game.PLAY = 5;
                game.setScreen(new GameScreen(game));
            }
        });

        btnLegend.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setSpeed(350);
                game.setElapesd(0.016f);
                game.setCreateDelay(850000000);
                game.COLOR = 6;
                game.SIZE = 60;
                game.PLAY = 6;
                game.setScreen(new GameScreen(game));
            }
        });

        table = new Table();
        table.setBounds(0,160,game.WIDTH,450);
        table.add(btnEasy).padBottom(30);
        table.add().padRight(30);
        table.add(btnNormal).padBottom(30);
        table.row();
        table.add(btnHard).padBottom(30);
        table.add().padRight(30);
        table.add(btnVeryhard).padBottom(30);
        table.row();
        table.add(btnHell);
        table.add().padRight(30);
        table.add(btnLegend);

        stage.addActor(table);

        circleRed = new Texture(Gdx.files.internal("dot/reddot.png"));
        circleGreen = new Texture(Gdx.files.internal("dot/greendot.png"));
        circleBlue = new Texture(Gdx.files.internal("dot/bluedot.png"));
        circleBlack = new Texture(Gdx.files.internal("dot/blackdot.png"));

        rotation = 0;

        game.setShow(true);
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
        for(MenuCircleInfo menuCircleInfo : menuCircleInfos){
            Sprite sprite = null;
            if(menuCircleInfo.getColor() == 1)drawCircle(game.batch, sprite, circleRed, menuCircleInfo);
            if(menuCircleInfo.getColor() == 2)drawCircle(game.batch, sprite, circleGreen, menuCircleInfo);
            if(menuCircleInfo.getColor() == 3)drawCircle(game.batch, sprite, circleBlue, menuCircleInfo);
            if(menuCircleInfo.getColor() == 4)drawCircle(game.batch, sprite, circleBlack, menuCircleInfo);
        }
        game.batch.end();

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
        difPort.update(width, height);
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
        skin.dispose();
        circleRed.dispose();
        circleGreen.dispose();
        circleBlue.dispose();
        circleBlack.dispose();
    }
}
