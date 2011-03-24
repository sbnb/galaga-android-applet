package com.gamefreezer.galaga;

public class StateTimes {
    public final int BETWEEN_LIVES;
    public final int WAIT_CLEAR;
    public final int LEVEL_CLEARED;
    public final int BONUS_MESSAGE;
    public final int BONUS_PAYOUT;
    public final int LEVEL_DELAY;

    public StateTimes(int levelDelay, int betweenLives, int waitClear,
	    int levelCleared, int bonusMessage, int bonusPayout) {
	BETWEEN_LIVES = betweenLives;
	WAIT_CLEAR = waitClear;
	LEVEL_CLEARED = levelCleared;
	BONUS_MESSAGE = bonusMessage;
	BONUS_PAYOUT = bonusPayout;
	LEVEL_DELAY = levelDelay;
    }
}
