package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class Score extends AllocGuard {

    private SpriteCache spriteStore;
    private int lives = 0;
    private int health = 100;
    private int totalScore = 0;
    private int levelScore = 0;
    private int totalShotsFired = 0;
    private int levelShotsFired = 0;
    private int levelHitsMade = 0;
    private int totalHitsMade = 0;
    private int bonus;
    private int bonusDecrement;

    public Score(SpriteCache spriteStore) {
	super();
	this.spriteStore = spriteStore;
    }

    public int getScore() {
	return totalScore;
    }

    public int getLevelScore() {
	return levelScore;
    }

    public void setScore(int score) {
	this.totalScore = score;
    }

    public int getLives() {
	return lives;
    }

    public void setLives(int lives) {
	this.lives = lives;
    }

    public int getHealth() {
	return health;
    }

    public void setHealth(int health) {
	this.health = health;
    }

    public void restoreHealth() {
	this.health = 100;
    }

    public void decrementHealth(int amount) {
	health -= amount;
	if (health < 0) {
	    health = 0;
	}
    }

    public void incremementShotsFired(int shots) {
	totalShotsFired += shots;
	levelShotsFired += shots;
    }

    public void incremementShotsFired() {
	incremementShotsFired(1);
    }

    public void incrementHitsMade() {
	levelHitsMade++;
	totalHitsMade++;
    }

    public void incrementScore(int amount) {
	this.totalScore += amount;
	this.levelScore += amount;
    }

    public void clearLevelScore() {
	levelScore = 0;
	levelShotsFired = 0;
	levelHitsMade = 0;
    }

    public void calculateBonus() {
	bonus = bonus();
	bonusDecrement = 1;
	if (bonus < BONUS_THRESHOLD) {
	    bonusDecrement = 1;
	} else {
	    bonusDecrement = bonus / BONUS_THRESHOLD;
	}
    }

    public boolean bonusRemaining() {
	return bonus > 0;
    }

    public void transferSomeBonus() {
	if (bonus >= bonusDecrement) {
	    bonus -= bonusDecrement;
	    totalScore += bonusDecrement;
	} else if (bonus > 0) {
	    bonus -= bonus;
	    totalScore += bonus;
	}
    }

    public void addLevelBonus() {
	// guard clause: levelShotsFired 0 means no bonus, and accuracy of 0
	if (levelShotsFired == 0) {
	    return;
	}
	totalScore += bonus;
    }

    private int bonus() {
	int bonus = Math.round(levelScore * accuracy());
	return bonus;
    }

    private float accuracy() {
	if (levelShotsFired == 0)
	    return 0f;
	float accuracy = (float) levelHitsMade / (float) levelShotsFired;
	return accuracy;
    }

    public void draw(AbstractGraphics graphics) {
	Util.drawNumber(spriteStore, graphics, SCORE_LEFT, SCORE_TOP,
		SCORE_SPACING, SCORE_COMMAS, totalScore);
    }

    public void drawBonuses(AbstractGraphics graphics) {
	// TODO replace magic numbers for bonus details placement
	// shots fired
	// System.out.println("draw levelShotsFired: " + levelShotsFired);
	Util.drawNumber(spriteStore, graphics, 170, 168, SCORE_SPACING,
		SCORE_COMMAS, levelShotsFired);
	// hits made
	Util.drawNumber(spriteStore, graphics, 170, 191, SCORE_SPACING,
		SCORE_COMMAS, levelHitsMade);
	// accuracy
	Util.drawNumber(spriteStore, graphics, 170, 214, SCORE_SPACING,
		SCORE_COMMAS, (int) (accuracy() * 100));
	// bonus
	Util.drawNumber(spriteStore, graphics, 170, 237, SCORE_SPACING,
		SCORE_COMMAS, bonus);

    }
}
