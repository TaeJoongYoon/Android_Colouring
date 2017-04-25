package com.mygdx.colouring;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.colouring.Interface.AdHandler;
import com.mygdx.colouring.Interface.PlayServices;
import com.mygdx.colouring.Screen.SplashScreen1;

public class Coloring extends Game {
	public static PlayServices playServices;
	public static AdHandler adHandler;
	public SpriteBatch batch;
	public BitmapFont mainfont, scorefont, combofont, lifefont, soundfont, rankfont, bonusfont, diffont, titlefont;
	public final static int WIDTH = 480;
	public final static int HEIGHT = 800;
	public Music backgroundMusic;
	public boolean bgmCheck, efsCheck;
	private int score, maxCombo;
	public int PLAY, COLOR, SIZE, SETTING;
	private float speed, elapesd;
	public long createDelay;
	private boolean running, show;
	public String strDOT1, strDOT2, strDOT3, strDOT4,strDOT5, strDOT6;

	public Coloring() {}

	public Coloring(PlayServices playServices, AdHandler adHandler){
		this.playServices = playServices;
		this.adHandler = adHandler;
	}
	@Override
	public void create () {
		batch = new SpriteBatch();
		mainfont = new BitmapFont(Gdx.files.internal("font/mainFont.fnt"));
		scorefont = new BitmapFont(Gdx.files.internal("font/scoreFont.fnt"));
		combofont = new BitmapFont(Gdx.files.internal("font/comboFont.fnt"));
		lifefont = new BitmapFont(Gdx.files.internal("font/lifeFont.fnt"));
		soundfont = new BitmapFont(Gdx.files.internal("font/soundFont.fnt"));
		rankfont = new BitmapFont(Gdx.files.internal("font/rankFont.fnt"));
		bonusfont = new BitmapFont(Gdx.files.internal("font/bonusFont.fnt"));
		diffont = new BitmapFont(Gdx.files.internal("font/difFont.fnt"));
		titlefont = new BitmapFont(Gdx.files.internal("font/titleFont.fnt"));
		bgmCheck = true;
		efsCheck = true;
		running = true;
		show = false;
		SETTING = 1;
		strDOT1 = "dot/reddot.png";
		strDOT2 = "dot/greendot.png";
		strDOT3 = "dot/bluedot.png";
		strDOT4 = "dot/blackdot.png";
		strDOT5 = "dot/yellowdot.png";
		strDOT6 = "dot/purpledot.png";
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/background.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
		this.setScreen(new SplashScreen1(this));
	}

	@Override
	public void render () {
		if (running == true) {
			super.render();
			adHandler.showAds(show);
		}

	}

	@Override
	public void resume() {
		running = true;
		super.resume();
	}

	@Override
	public void pause() {
		running = false;
		super.pause();
	}

	@Override
	public void dispose () {
		batch.dispose();
		mainfont.dispose();
		scorefont.dispose();
		combofont.dispose();
		lifefont.dispose();
		soundfont.dispose();
	}

	public int getScore() {
		return score;
	}
	public void setShow(boolean s) { this.show = s;}
	public void setScore(int score) {
		this.score = score;
	}
	public float getSpeed() {return speed;}
	public float getElapesd() {return elapesd;}
	public void setSpeed(float speed) {this.speed = speed;}
	public void setElapesd(float elapesd) {this.elapesd = elapesd;}
	public void setCreateDelay(long createDelay) {this.createDelay = createDelay;}
	public int getMaxCombo() {return maxCombo;}
	public void setMaxCombo(int maxCombo) {this.maxCombo = maxCombo;}
}
