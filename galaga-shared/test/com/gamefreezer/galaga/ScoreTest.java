//package com.gamefreezer.galaga;
//
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.gamefreezer.galaga.Score;
//
//public class ScoreTest {
//
//    Score score;
//
//    @Before
//    public void setUp() {
//	score = new Score();
//    }
//
//    @Test
//    public void simpleScoring() {
//	score.incrementScore(10);
//	score.incrementScore(20);
//	score.incrementScore(20);
//	assertThat(score.getScore(), is(50));
//	assertThat(score.getLevelScore(), is(50));
//	score.clearLevelScore();
//	assertThat(score.getScore(), is(50));
//	assertThat(score.getLevelScore(), is(0));
//    }
//
//    @Test
//    public void accuracyBonusWorksOnOneLevel() {
//	score.incrementScore(100);
//	score.incremementShotsFired(10);
//	incrementHits(5);
//	assertThat(score.getScore(), is(100));
//	addBonus(score);
//	assertThat(score.getScore(), is(150));
//    }
//
//    @Test
//    public void mutlipleLevelScoreTracking() {
//	score.incrementScore(100);
//	score.incremementShotsFired(10);
//	incrementHits(5);
//	addBonus(score);
//	score.clearLevelScore();
//	score.incrementScore(100);
//	score.incremementShotsFired(10);
//	incrementHits(5);
//	addBonus(score);
//	assertThat(score.getScore(), is(300));
//    }
//
//    @Test
//    public void divideByZeroWhenAddingBonus() {
//	score.incrementScore(100);
//	score.addLevelBonus();
//	assertThat(score.getScore(), is(100));
//    }
//
//    @Test
//    public void getNumerals() {
//	assertThat(Util.getNumeral(1, 123), is(3));
//	assertThat(Util.getNumeral(10, 123), is(2));
//	assertThat(Util.getNumeral(100, 123), is(1));
//	assertThat(Util.getNumeral(1000, 123), is(0));
//
//	assertThat(Util.getNumeral(1, 90045607), is(7));
//	assertThat(Util.getNumeral(10, 90045607), is(0));
//	assertThat(Util.getNumeral(100, 90045607), is(6));
//	assertThat(Util.getNumeral(1000, 90045607), is(5));
//	assertThat(Util.getNumeral(10000, 90045607), is(4));
//	assertThat(Util.getNumeral(100000, 90045607), is(0));
//	assertThat(Util.getNumeral(1000000, 90045607), is(0));
//	assertThat(Util.getNumeral(10000000, 90045607), is(9));
//	assertThat(Util.getNumeral(100000000, 90045607), is(0));
//    }
//
//    @Test
//    public void numberOfDigitsCalculation() {
//	assertThat("0", Util.numDigits(0), is(1));
//	assertThat("1", Util.numDigits(1), is(1));
//	assertThat("2", Util.numDigits(2), is(1));
//	assertThat("9", Util.numDigits(9), is(1));
//	assertThat("10", Util.numDigits(10), is(2));
//	assertThat("123456789", Util.numDigits(123456789), is(9));
//	assertThat("999999999", Util.numDigits(999999999), is(9));
//
//    }
//
//    private void incrementHits(int hits) {
//	for (int i = 0; i < hits; i++) {
//	    score.incrementHitsMade();
//	}
//    }
//
//    private void addBonus(Score score) {
//	score.calculateBonus();
//	while (score.bonusRemaining()) {
//	    score.transferSomeBonus();
//	}
//    }
//
// }
