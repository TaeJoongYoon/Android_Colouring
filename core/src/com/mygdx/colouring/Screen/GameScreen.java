package com.mygdx.colouring.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.colouring.Coloring;
import com.mygdx.colouring.Info.BonusInfo;
import com.mygdx.colouring.Info.CircleInfo;
import com.mygdx.colouring.Info.ComboLifeInfo;
import com.mygdx.colouring.Info.HeartInfo;

import java.util.Iterator;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Created by Yoon on 2017-01-08.
 */

public class GameScreen implements Screen {
    final Coloring game;

    private Array<CircleInfo> circleInfos;
    private Array<ComboLifeInfo> comboLifeInfos;
    private Array<HeartInfo> heartInfos;
    private Array<BonusInfo> bonusInfos;
    private OrthographicCamera cam;
    private Viewport gamePort;
    private Stage stage;
    private Table table, lifeTable;
    private Texture arc, arcRed, arcGreen, arcBlue, arcBlack, arcYellow, arcPurple;
    private Texture circleRed, circleGreen, circleBlue, circleBlack, circleYellow, circlePurple;
    private Texture lifeimg;
    private Image imgLife1, imgLife2, imgLife3, imgLife4, imgLife5;
    private TextureAtlas btnAtlas;
    private Skin btnSkin;
    private ImageButton btnRed, btnGreen, btnBlue, btnBlack, btnYellow, btnPurple;
    private boolean touch,horizon;
    private long createTime, countdelay, createheart,overtime, createDelay;
    private int correctColor, score,  life, rotation, combo, starting, counttime, heartdelay, nCount, heartSize, btnSize, maxCombo;
    private float center,elapsed,gravity;
    private Sound correctSound, incorrectSound, buttonclickSound,  heartSound;
    private Music countdown;
    private Vector2 pOver;

    public GameScreen(final Coloring coloring){
        this.game = coloring;
        circleInfos = new Array<CircleInfo>();
        comboLifeInfos = new Array<ComboLifeInfo>();
        heartInfos = new Array<HeartInfo>();
        bonusInfos = new Array<BonusInfo>();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);
        gamePort = new StretchViewport(game.WIDTH, game.HEIGHT, cam);
        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);

        btnAtlas = new TextureAtlas("ui/button.atlas");
        btnSkin = new Skin(btnAtlas);

        correctSound = Gdx.audio.newSound(Gdx.files.internal("sound/correct.wav"));
        incorrectSound = Gdx.audio.newSound(Gdx.files.internal("sound/incorrect.wav"));
        buttonclickSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonclick.wav"));
        heartSound = Gdx.audio.newSound(Gdx.files.internal("sound/heart.wav"));
        countdown = Gdx.audio.newMusic(Gdx.files.internal("sound/countdown.wav"));
        countdown.setVolume(0.2f);

        btnRed = new ImageButton(btnSkin.getDrawable("redbutton1"),btnSkin.getDrawable("redbutton2"));
        btnGreen = new ImageButton(btnSkin.getDrawable("greenbutton1"),btnSkin.getDrawable("greenbutton2"));
        btnBlue = new ImageButton(btnSkin.getDrawable("bluebutton1"),btnSkin.getDrawable("bluebutton2"));
        btnBlack = new ImageButton(btnSkin.getDrawable("blackbutton1"),btnSkin.getDrawable("blackbutton2"));
        btnYellow = new ImageButton(btnSkin.getDrawable("yellowbutton1"),btnSkin.getDrawable("yellowbutton2"));
        btnPurple = new ImageButton(btnSkin.getDrawable("purplebutton1"),btnSkin.getDrawable("purplebutton2"));


        btnSize = game.SIZE;

        btnRed.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 1;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        btnGreen.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 2;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        btnBlue.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 3;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        btnBlack.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 4;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        btnYellow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 5;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        btnPurple.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                correctColor = 6;
                if(game.efsCheck == true) buttonclickSound.play();
                return  true;
            }
        });
        arc = null;
        arcRed = new Texture(Gdx.files.internal("arc/redarc.png"));
        arcGreen = new Texture(Gdx.files.internal("arc/greenarc.png"));
        arcBlue = new Texture(Gdx.files.internal("arc/bluearc.png"));
        arcBlack = new Texture(Gdx.files.internal("arc/blackarc.png"));
        arcYellow = new Texture(Gdx.files.internal("arc/yellowarc.png"));
        arcPurple = new Texture(Gdx.files.internal("arc/purplearc.png"));

        circleRed = new Texture(Gdx.files.internal(game.strDOT1));
        circleGreen = new Texture(Gdx.files.internal(game.strDOT2));
        circleBlue = new Texture(Gdx.files.internal(game.strDOT3));
        circleBlack = new Texture(Gdx.files.internal(game.strDOT4));
        circleYellow = new Texture(Gdx.files.internal(game.strDOT5));
        circlePurple = new Texture(Gdx.files.internal(game.strDOT6));

        table = new Table();
        table.setBounds(0,50,game.WIDTH,220);
        table.add(btnRed).size(btnSize,btnSize);
        table.add().padRight(20);
        table.add(btnGreen).size(btnSize,btnSize);
        table.add().padRight(20);
        table.add(btnBlue).size(btnSize,btnSize);
        table.add().padRight(20);
        table.add(btnBlack).size(btnSize,btnSize);
        switch (game.PLAY){
            case 5 : table.add().padRight(20);
                table.add(btnYellow).size(btnSize,btnSize);
                break;
            case 6 : table.add().padRight(20);
                table.add(btnYellow).size(btnSize,btnSize);
                table.add().padRight(20);
                table.add(btnPurple).size(btnSize,btnSize);
                break;
        }

        lifeimg = new Texture(Gdx.files.internal("imageLife.png"));
        imgLife1 = new Image(lifeimg); imgLife2 = new Image(lifeimg); imgLife3 = new Image(lifeimg); imgLife4 = new Image(lifeimg); imgLife5 = new Image(lifeimg);

        lifeTable = new Table();
        lifeTable.setBounds(game.WIDTH/2,725,game.WIDTH/2,60);

        stage.addActor(table);
        stage.addActor(lifeTable);

        correctColor = 1;
        score = 0;
        life = 3;
        rotation = 0;
        combo = 0;
        maxCombo = 0;
        starting = 0;
        counttime = 3;
        createDelay = game.createDelay;
        countdelay = TimeUtils.nanoTime();
        createheart = TimeUtils.millis();
        heartdelay = (MathUtils.random(1,11)+9)*1000;
        touch = false;
        nCount = 0;
        heartSize = 40;
        horizon = true;
        center = table.getHeight() + game.WIDTH/2;
        elapsed = 0.018f;
        gravity = 10;
        pOver = new Vector2(game.WIDTH/2,table.getHeight() + game.WIDTH);
        game.setShow(true);
    }

    public void drawArc(SpriteBatch batch, Texture texture, int i){
        switch (i){
            case  1 : texture = arcRed;
                break;
            case  2 : texture = arcGreen;
                break;
            case  3 : texture = arcBlue;
                break;
            case  4 : texture = arcBlack;
                break;
            case  5 : texture = arcYellow;
                break;
            case  6 : texture = arcPurple;
                break;
            default : texture = arcRed;
                break;
        }
        batch.draw(texture,0,240,480,480);
    }
    
    public void drawCircle(SpriteBatch batch, Sprite sprite, Texture texture, CircleInfo circleInfo, int i){
        switch (i){
            case 1 : texture = circleRed;
                break;
            case 2 : texture = circleGreen;
                break;
            case 3 : texture = circleBlue;
                break;
            case 4 : texture = circleBlack;
                break;
            case 5 : texture = circleYellow;
                break;
            case 6 : texture = circlePurple;
                break;
            default : texture = circleRed;
                break;
        }
        sprite = new Sprite(texture);
        sprite.setPosition(circleInfo.getObject().x-texture.getWidth()/2,circleInfo.getObject().y-texture.getHeight()/2);
        sprite.setSize(texture.getWidth(),texture.getHeight());
        sprite.setOrigin(texture.getWidth()/2,texture.getHeight()/2);
        sprite.setRotation(rotation += 2);
        sprite.draw(batch);
    }

    public void drawHeart(SpriteBatch batch, Sprite sprite, Texture texture, HeartInfo heartInfo){
        sprite = new Sprite(texture);
        sprite.setPosition(heartInfo.getObject().x-15,heartInfo.getObject().y-15);
        sprite.setSize(30,30);
        sprite.draw(batch);
    }

    public void drawLife(){
        if(life == 5){
            lifeTable.clear();
            lifeTable.add(imgLife1).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife2).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife3).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife4).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife5).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
        }
        if(life == 4){
            lifeTable.clear();
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife2).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife3).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife4).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife5).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
        }
        if(life == 3){
            lifeTable.clear();
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife3).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife4).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife5).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
        }
        if(life == 2){
            lifeTable.clear();
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife4).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife5).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
        }
        if(life == 1){
            lifeTable.clear();
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add().size(heartSize,heartSize);
            lifeTable.add().padRight(5);
            lifeTable.add(imgLife5).size(heartSize,heartSize);
            lifeTable.add().padRight(5);
        }
        if(life == 0) lifeTable.clear();
    }

    public void createCircle(){
        CircleInfo circleInfo = new CircleInfo(game.COLOR);
        circleInfos.add(circleInfo);
        createTime = TimeUtils.nanoTime();
    }

    public void createHeart(){
        HeartInfo heartInfo = new HeartInfo();
        heartInfos.add(heartInfo);
        createheart = TimeUtils.millis();
        heartdelay = (MathUtils.random(1,11)+9)*1000;
        touch = false;
    }

    public void createComboLife(int i, CircleInfo circleInfo){
        comboLifeInfos.clear();
        ComboLifeInfo comboLifeInfo = new ComboLifeInfo(circleInfo);
        comboLifeInfos.add(comboLifeInfo);
        if(i == 1) comboLifeInfo.setCombo(true);
        if(i == 2) comboLifeInfo.setLife(true);
    }

    public void createBonus(int i, HeartInfo heartInfo){
        bonusInfos.clear();
        BonusInfo bonusInfo = new BonusInfo(heartInfo);
        bonusInfos.add(bonusInfo);
        if(i == 1) bonusInfo.setBonus(true);
        if(i == 2) bonusInfo.setBonus(false);
    }

    public boolean checkColor(CircleInfo circleInfo){
            if (circleInfo.getColor() == correctColor) return true;
            else return false;
    }

    public void timeCheck(){

        if(score >= 500000) createDelay = game.createDelay - 800000000;
        else if(score >= 800000) createDelay = game.createDelay - 800000000;
        else if(score >= 500000) createDelay = game.createDelay - 700000000;
        else if(score >= 400000) createDelay = game.createDelay - 650000000;
        else if(score >= 200000) createDelay = game.createDelay - 600000000;
        else if(score >= 150000) createDelay = game.createDelay - 550000000;
        else if(score >= 100000) createDelay = game.createDelay - 500000000;
        else if(score >= 80000) createDelay = game.createDelay - 450000000;
        else if(score >= 50000) createDelay = game.createDelay - 400000000;
        else if(score >= 30000) createDelay = game.createDelay - 300000000;
        else if(score >= 10000) createDelay = game.createDelay - 200000000;
        else if(score >= 1000) createDelay = game.createDelay - 100000000;
    }

    public void heartCheck(HeartInfo heartInfo){
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        boolean xCheck=false; boolean yCheck=false;
        float x = touchPos.x;
        float y = touchPos.y;
        if(x>heartInfo.getObject().x-20 && x<heartInfo.getObject().x+20) xCheck=true;
        if(y>heartInfo.getObject().y-20 && y<heartInfo.getObject().y+20) yCheck=true;
        if(xCheck&&yCheck) touch=true;
    }

    public void pDown(){
        pOver.y -= gravity * elapsed;
        gravity += 10;
        if(pOver.y <= center){
            nCount++;
            horizon = false;
        }
    }

    public void pUp(float h){
        pOver.y += gravity * elapsed;
        gravity -= 20;
        if (pOver.y >= center + h) horizon = true;
    }

    public void gameOverMove(float h){
        if(horizon == true){
            pDown();
            overtime = TimeUtils.nanoTime();
        }

        else if(horizon == false){
            if(nCount == 1 ) pUp(h/2);
            if(nCount == 2 ) pUp(h/4);
        }
    }

    public void waitPhase(){
        game.backgroundMusic.setVolume(0.4f);
        String count = Integer.toString(counttime);
        if(counttime <= 0){
            game.batch.begin();
            game.soundfont.draw(game.batch,"시작 !", game.WIDTH/2-100, game.WIDTH/2+table.getHeight()+40);
            game.batch.end();
        }

        else{
            game.batch.begin();
            game.soundfont.draw(game.batch,count, game.WIDTH/2-20, game.WIDTH/2+table.getHeight()+40);
            game.batch.end();
        }

    }

    public void gamePhase(){
        timeCheck();
        if (TimeUtils.nanoTime() - createTime > createDelay) createCircle();
        Iterator<CircleInfo> iter = circleInfos.iterator();
        while (iter.hasNext()) {
            CircleInfo circleInfo = iter.next();
            circleInfo.moveCircle(game.getSpeed(), game.getElapesd());
            if (circleInfo.isMoving() == false) {
                if (checkColor(circleInfo) == true) {
                    if (game.efsCheck == true) correctSound.play();
                    score += (200 + combo * 30);
                    combo++;
                    createComboLife(1, circleInfo);
                }
                if (checkColor(circleInfo) == false) {
                    if (game.efsCheck == true) incorrectSound.play();
                    life--;
                    score -=3;
                    if(score <= 0) score = 0;
                    if(combo > maxCombo) maxCombo = combo;
                    combo = 0;
                    createComboLife(2, circleInfo);
                }
                iter.remove();
            }

        }

        Iterator<ComboLifeInfo> iter1 = comboLifeInfos.iterator();
        while (iter1.hasNext()) {
            ComboLifeInfo comboLifeInfo = iter1.next();
            comboLifeInfo.moveCircle();
            if(comboLifeInfo.isMoving() == false) iter1.remove();
        }


        if (TimeUtils.millis() - createheart > heartdelay) createHeart();
        Iterator<HeartInfo> iter2 = heartInfos.iterator();
        while (iter2.hasNext()) {
            HeartInfo heartInfo = iter2.next();
            heartInfo.moveCircle();
            if (touch == true){
                if(game.bgmCheck == true) heartSound.play();
                life++;
                createBonus(1, heartInfo);
                if(life > 5){
                    life=5;
                    score+=1000;
                    createBonus(2, heartInfo);
                }
                iter2.remove();
            }
            if (heartInfo.isMoving() == false) iter2.remove();
        }

        Iterator<BonusInfo> iter3 = bonusInfos.iterator();
        while (iter3.hasNext()) {
            BonusInfo bonusInfo = iter3.next();
            bonusInfo.moveCircle();
            if(bonusInfo.isMoving() == false) iter3.remove();
        }

        if (life == 0) starting = 2;

    }

    public void endPhase(){
        comboLifeInfos.clear();
        gameOverMove(game.WIDTH/2);
        game.batch.begin();
        game.mainfont.draw(game.batch,"GAME OVER", pOver.x-110, pOver.y+40);
        if( TimeUtils.nanoTime() - overtime > 1000000000) {
            game.mainfont.draw(game.batch,"Touch the Screen", pOver.x-155, pOver.y-20);
            if (Gdx.input.isTouched()) {
                game.setScore(score);
                game.setMaxCombo(maxCombo);
                game.setScreen(new EndScreen(game));
                dispose();
            }
        }
        game.batch.end();
    }
    @Override
    public void show() { if(game.efsCheck==true) countdown.play(); }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        cam.update();
        drawLife();
        stage.draw();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.scorefont.draw(game.batch,"" + score, 10, game.HEIGHT-25);
        drawArc(game.batch, arc , correctColor);
        for(CircleInfo circleInfo : circleInfos){
            Sprite sprite = null;
            Texture texture = null;
            drawCircle(game.batch, sprite, texture, circleInfo, circleInfo.getColor());
        }
        for(ComboLifeInfo comboLifeInfo : comboLifeInfos){
            if(comboLifeInfo.isCombo()==true) game.combofont.draw(game.batch,combo + " COMBO!", comboLifeInfo.getObject().x, comboLifeInfo.getObject().y);
            if(comboLifeInfo.isLife()==true) game.lifefont.draw(game.batch,"LIFE - 1", comboLifeInfo.getObject().x, comboLifeInfo.getObject().y);
        }
        for(BonusInfo bonusInfo : bonusInfos){
            if(bonusInfo.isBonus()==true) game.bonusfont.draw(game.batch,"LIFE UP!", bonusInfo.getObject().x, bonusInfo.getObject().y);
            if(bonusInfo.isBonus()==false) game.bonusfont.draw(game.batch,"Bonus 1000!", bonusInfo.getObject().x, bonusInfo.getObject().y);
        }
        for(HeartInfo heartInfo : heartInfos){
            Sprite sprite = null;
            drawHeart(game.batch, sprite, lifeimg, heartInfo);
            heartCheck(heartInfo);
        }
        game.batch.end();

        if(starting == 0){
            waitPhase();
            if(counttime > 0) {
                if (TimeUtils.timeSinceNanos(countdelay) > 1000000000) {
                    counttime--;
                    countdelay = TimeUtils.nanoTime();
                }
            }
            if(counttime == 0){
                if (TimeUtils.timeSinceNanos(countdelay) > 500000000){
                    starting=1;
                }
                game.backgroundMusic.setVolume(1f);
            }
        }

        if(starting==1) gamePhase();

        if(starting == 2) endPhase();
     }

    @Override
    public void resize(int width, int height) { gamePort.update(width, height); }

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
        circleInfos.clear();
        heartInfos.clear();
        comboLifeInfos.clear();
        bonusInfos.clear();
        stage.dispose();
        btnAtlas.dispose();
        arcRed.dispose();
        arcBlack.dispose();
        arcGreen.dispose();
        arcBlue.dispose();
        arcYellow.dispose();
        arcPurple.dispose();
        circleRed.dispose();
        circleBlue.dispose();
        circleGreen.dispose();
        circleBlack.dispose();
        circleYellow.dispose();
        circlePurple.dispose();
        lifeimg.dispose();
        buttonclickSound.dispose();
        correctSound.dispose();
        incorrectSound.dispose();
        countdown.dispose();
        heartSound.dispose();
    }
}
