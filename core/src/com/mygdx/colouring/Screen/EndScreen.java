package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;

/**
 * Created by Yoon on 2017-01-08.
 */

public class EndScreen implements Screen {
    private Coloring game;
    private OrthographicCamera cam;
    private Viewport optionPort;
    private Stage stage;
    private GlyphLayout glyphLayout, glyphLayout2;
    private TextureAtlas atlas;
    private Skin skin;
    private ImageButton btnHome, btnExit, btnRank, btnAch;
    private Table table,table1;
    private Sound click;
    private Music count;
    private int score,i,dup, combo;
    private String str;
    private float w, w1, elapsed;

    public EndScreen(Coloring coloring){
        this.game = coloring;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        optionPort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(optionPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        click = Gdx.audio.newSound(Gdx.files.internal("sound/menuclick.wav"));
        count = Gdx.audio.newMusic(Gdx.files.internal("sound/count.wav"));
        count.setLooping(true);

        atlas = new TextureAtlas("ui/mainbutton.atlas");
        skin = new Skin(atlas);

        btnHome = new ImageButton(skin.getDrawable("back"));
        btnExit = new ImageButton(skin.getDrawable("close"));
        btnRank = new ImageButton(skin.getDrawable("rank"));
        btnAch = new ImageButton(skin.getDrawable("ach"));

        btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
                dispose();
            }
        });

        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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

        table = new Table();
        table.setBounds(0,30,game.WIDTH,250);
        table.add(btnHome);
        table.add().padRight(240);
        table.add(btnExit);

        table1 = new Table();
        table1.setBounds(game.WIDTH/2+50,table.getHeight(),game.WIDTH/2-50,400);
        table1.add(btnRank).padBottom(50);
        table1.row();
        table1.add(btnAch);

        stage.addActor(table);
        stage.addActor(table1);
        glyphLayout = new GlyphLayout();
        glyphLayout2 = new GlyphLayout();
        score = game.getScore();
        combo = game.getMaxCombo();
        i = 0;
        elapsed = 1.7f;
        if(score<=50000)dup=100;
        else if (score<=150000)dup=500;
        else if (score<=300000)dup=700;
        else if (score<=500000)dup=1000;
        else dup = 1500;
        if(score!=0 && game.efsCheck == true) count.play();
        game.setShow(true);
    }

    @Override
    public void show() {
        game.playServices.unlockAchievement(score);
        game.playServices.submitScore(score, combo, game.PLAY);
    }

    @Override
    public void render(float delta) {
        if (i < score){
            i+=dup*elapsed;
            if(i >= score){
                i = score;
                count.stop();
            }
        }
        str = Integer.toString(i);
        glyphLayout.setText(game.scorefont, str);
        glyphLayout2.setText(game.scorefont, "COMBO : " + combo);
        w = glyphLayout.width;
        w1 = glyphLayout2.width;
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);
        stage.draw();
        game.batch.begin();
        game.scorefont.draw(game.batch,glyphLayout, game.WIDTH/2-w/2, game.HEIGHT-50);
        game.scorefont.draw(game.batch,glyphLayout2, game.WIDTH/2-w1/2, game.HEIGHT-100);
        game.rankfont.draw(game.batch,"랭킹보기",btnRank.getX()-30,btnRank.getY()+330);
        game.rankfont.draw(game.batch,"업적보기",btnAch.getX()-30,btnAch.getY()+330);
        game.batch.end();
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
        skin.dispose();
        click.dispose();
        count.dispose();
    }
}
