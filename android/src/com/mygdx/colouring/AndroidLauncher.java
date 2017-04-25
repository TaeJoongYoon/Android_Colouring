package com.mygdx.colouring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.mygdx.colouring.Interface.AdHandler;
import com.mygdx.colouring.Interface.PlayServices;

public class AndroidLauncher extends AndroidApplication implements PlayServices, AdHandler {

	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adview;
	private String KEY = "ca-app-pub-7366768152685715/4081131585";

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case SHOW_ADS:
					adview.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adview.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adview = new AdView(this);
		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useImmersiveMode = true;
		View gameView = initializeForView(new Coloring(this,this), config);

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);

		adview.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visiblity = adview.getVisibility();
				adview.setVisibility(AdView.GONE);
				adview.setVisibility(visiblity);
			}
		});

		AdRequest.Builder builder = new AdRequest.Builder();

		adview.setAdSize(AdSize.SMART_BANNER);
		adview.setAdUnitId(KEY);
		adview.loadAd(builder.build());

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(layout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		layout.addView(gameView);
		layout.addView(adview, adParams);

		setContentView(layout);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
		System.out.println("aaaaaaaaa");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e) {}
	}

	@Override
	public void rateGame()
	{
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(float i)
	{
		if(i>=1000000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_millionclub));
		else if(i>=300000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_sparta));
		else if(i>=150000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_150000));
		else if(i>=100000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_hunnit));
		else if(i>=50000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_50000));
		else if(i>=10000)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_tenthousand));
		if(i==22222)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_yellow));
		if(i==11111)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_11111));
		if(i==0)Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_zeroclub));
	}

	@Override
	public void submitScore(int highScore, int combo, int i)
	{
		if (isSignedIn() == true)
		{
			switch (i){
				case 1:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_easy_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_easy_combo), combo);
					break;
				case 2:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_normal_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_normal_combo), combo);
					break;
				case 3:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_hard_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_hard_combo), combo);
					break;
				case 4:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_very_hard_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_very_hard_combo), combo);
					break;
				case 5:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_hell_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_hell_combo), combo);
					break;
				case 6:	Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_legend_score), highScore);
						Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_legend_combo), combo);
					break;
			}
		}
	}
	@Override
	public void showAchievement()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

	@Override
	public void showAds(boolean show){
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

}
