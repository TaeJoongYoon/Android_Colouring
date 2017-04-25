package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;

/**
 * Created by Yoon on 2017-01-08.
 */

public class OptionScreen implements Screen {
    private Coloring game;
    private OrthographicCamera cam;
    private Viewport optionPort;
    private Stage stage;
    private TextureAtlas atlas, atlas1, atlas2;
    private Skin skin, skin1, skin2;
    private ImageButton btnHome, bgmOn, bgmOff, effOn, effOff, basic, star, circle;
    private Table table, table1, table2;
    private Sound click;

    public OptionScreen(Coloring coloring){
        this.game = coloring;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        optionPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(optionPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        click = Gdx.audio.newSound(Gdx.files.internal("sound/menuclick.wav"));

        atlas = new TextureAtlas("ui/sound.atlas");
        skin = new Skin(atlas);
        atlas1 = new TextureAtlas("ui/mainbutton.atlas");
        skin1 = new Skin(atlas1);
        atlas2 = new TextureAtlas("ui/setting.atlas");
        skin2 = new Skin(atlas2);

        bgmOn = new ImageButton(skin.getDrawable("soundonoff"), skin.getDrawable("soundon"), skin.getDrawable("soundon"));
        bgmOff = new ImageButton(skin.getDrawable("soundoffoff"), skin.getDrawable("soundoff"), skin.getDrawable("soundoff"));
        effOn = new ImageButton(skin.getDrawable("soundonoff"), skin.getDrawable("soundon"), skin.getDrawable("soundon"));
        effOff = new ImageButton(skin.getDrawable("soundoffoff"), skin.getDrawable("soundoff"), skin.getDrawable("soundoff"));
        basic = new ImageButton(skin2.getDrawable("basicoff"), skin2.getDrawable("basicon"), skin2.getDrawable("basicon"));
        star = new ImageButton(skin2.getDrawable("staroff"), skin2.getDrawable("staron"), skin2.getDrawable("staron"));
        circle = new ImageButton(skin2.getDrawable("circleoff"), skin2.getDrawable("circleon"), skin2.getDrawable("circleon"));
        btnHome = new ImageButton(skin1.getDrawable("back"));

        settingCheck(game.SETTING,basic,star,circle);

        soundCheck(game.bgmCheck, bgmOn, bgmOff); soundCheck(game.efsCheck, effOn, effOff);

        bgmOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.efsCheck == true) click.play();
                game.bgmCheck = true;
                game.backgroundMusic.play();
            }
        });

        bgmOff.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.efsCheck == true) click.play();
                game.bgmCheck = false;
                game.backgroundMusic.pause();
            }
        });

        effOn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.efsCheck = true;
                if(game.efsCheck == true) click.play();
            }
        });

        effOff.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.efsCheck = false;
            }
        });

        basic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.efsCheck == true) click.play();
                game.SETTING = 1;
                game.strDOT1 = "dot/reddot.png";
                game.strDOT2 = "dot/greendot.png";
                game.strDOT3 = "dot/bluedot.png";
                game.strDOT4 = "dot/blackdot.png";
                game.strDOT5 = "dot/yellowdot.png";
                game.strDOT6 = "dot/purpledot.png";
            }
        });

        star.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.efsCheck == true) click.play();
                game.SETTING = 2;
                game.strDOT1 = "dot/redstar.png";
                game.strDOT2 = "dot/greenstar.png";
                game.strDOT3 = "dot/bluestar.png";
                game.strDOT4 = "dot/blackstar.png";
                game.strDOT5 = "dot/yellowstar.png";
                game.strDOT6 = "dot/purplestar.png";
            }
        });

        circle.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.efsCheck == true) click.play();
                game.SETTING = 3;
                game.strDOT1 = "dot/redcircle.png";
                game.strDOT2 = "dot/greencircle.png";
                game.strDOT3 = "dot/bluecircle.png";
                game.strDOT4 = "dot/blackcircle.png";
                game.strDOT5 = "dot/yellowcircle.png";
                game.strDOT6 = "dot/purplecircle.png";
            }
        });

        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
                dispose();
            }
        });

        table1 = new Table();
        table1.setBounds(0,30,game.WIDTH,250);
        table1.add(btnHome);

        table2 = new Table();
        table2.setBounds(0,table1.getHeight(),game.WIDTH,130);
        table2.add(basic).size(100,100).padRight(40);
        table2.add(star).size(100,100).padRight(40);
        table2.add(circle).size(100,100);

        table = new Table();
        table.setBounds(game.WIDTH/2,table1.getHeight()+table2.getHeight(),game.WIDTH/2,400);
        table.add(bgmOn).padBottom(50);
        table.add().padRight(30);
        table.add(bgmOff).padBottom(50);
        table.row();
        table.add(effOn);
        table.add().padRight(30);
        table.add(effOff);

        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(table2);
        game.setShow(false);
    }

    public void soundCheck(boolean b, ImageButton btn1, ImageButton btn2){
        if(b==true){
            btn1.setChecked(true);
            btn2.setChecked(false);
        }
        if(b==false){
            btn1.setChecked(false);
            btn2.setChecked(true);
        }
    }

    public void settingCheck(int i, ImageButton btn1, ImageButton btn2, ImageButton btn3){
         if(i==1){
            btn1.setChecked(true);
            btn2.setChecked(false);
            btn3.setChecked(false);
        }
        if(i==2){
            btn1.setChecked(false);
            btn2.setChecked(true);
            btn3.setChecked(false);
        }
        if(i==3){
            btn1.setChecked(false);
            btn2.setChecked(false);
            btn3.setChecked(true);
        }
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
        game.soundfont.draw(game.batch,"배경음",bgmOn.getX()-30,bgmOn.getY()+450);
        game.soundfont.draw(game.batch,"효과음",effOn.getX()-30,effOn.getY()+450);
        game.batch.end();
        soundCheck(game.bgmCheck, bgmOn, bgmOff); soundCheck(game.efsCheck, effOn, effOff);
        settingCheck(game.SETTING, basic,star,circle);
    }

    @Override
    public void resize(int width, int height) {
        optionPort.update(width, height);
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
        atlas.dispose();
        atlas1.dispose();
        skin.dispose();
        skin1.dispose();
        click.dispose();
    }
}
