package com.mygdx.colouring.Interface;

/**
 * Created by Yoon on 2017-02-17.
 */

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(float i);
    public void submitScore(int highScore,int combo, int i);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}