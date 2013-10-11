//package com.gamefreezer.galaga;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.util.Properties;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class AliensTest {
//
//    Aliens aliens;
//    Alien[] asArray;
//    Properties props;
//    int yBaseLine;
//
//    @Before
//    public void setUp() throws Exception {
//	Formation formation = getFormation("data/test/aliens-test.properties");
//	props = formation.getProperties();
//	aliens = new Aliens();
//	aliens.newLevel(formation);
//	yBaseLine = alienAt(0).getY();
//	aliens.setFreeMovingAllowed(false);
//	asArray = aliens.getArray();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//	aliens = null;
//	props = null;
//    }
//
//    @Test
//    public void canLinkAliens() {
//	assertThat("Traverse matches size", traverseAliens(), is(aliens.size()));
//	assertThat("Backward traverse matches size", traverseAliensBackwards(),
//		is(aliens.size()));
//	assertThat("FIRST set ok", Aliens.FIRST, is(asArray[0]));
//    }
//
//    @Test
//    public void canUnlinkFirstAlien() {
//	asArray[0].kill();
//	assertThat(traverseAliens(), is(aliens.size() - 1));
//	assertThat("FIRST is 2nd", Aliens.FIRST, is(asArray[1]));
//    }
//
//    @Test
//    public void canUnlinkLastAlien() {
//	asArray[2].kill();
//	assertThat(traverseAliens(), is(aliens.size() - 1));
//	assertThat("FIRST is 1st", Aliens.FIRST, is(asArray[0]));
//    }
//
//    @Test
//    public void canUnlinkMiddleAlien() {
//	asArray[1].kill();
//	assertThat(traverseAliens(), is(aliens.size() - 1));
//	assertThat("FIRST is 1st", Aliens.FIRST, is(asArray[0]));
//    }
//
//    @Test
//    public void canUnlinkLastTwo() {
//	asArray[0].kill();
//	asArray[1].kill();
//	assertThat(traverseAliens(), is(aliens.size() - 2));
//	assertThat("FIRST is 3rd", Aliens.FIRST, is(asArray[2]));
//    }
//
//    @Test
//    public void canUnlinkAll() {
//	asArray[0].kill();
//	asArray[1].kill();
//	asArray[2].kill();
//	assertThat(traverseAliens(), is(aliens.size() - 3));
//	assertThat("FIRST is null", Aliens.FIRST == null, is(true));
//    }
//
//    @Test
//    public void canInsertAtBeginning() {
//	// currently unable to do this
//    }
//
//    @Test
//    public void canInsertIntoEmptyList() {
//	// aliens is a, b, c
//	// kill all, then insert c after FIRST, which is null
//	// should give c alone
//	asArray[0].kill();
//	asArray[1].kill();
//	asArray[2].kill();
//	aliens.insertAfter(Aliens.FIRST, asArray[2]);
//	assertThat("FIRST is c", Aliens.FIRST, is(asArray[2]));
//	assertThat("c->null", asArray[2].next==null, is(true));
//	assertThat("null<-c", asArray[2].prev==null, is(true));
//	assertThat("a->null", asArray[0].next==null, is(true));
//	assertThat("null<-a", asArray[0].prev==null, is(true));
//	assertThat("b->null", asArray[1].next==null, is(true));
//	assertThat("null<-b", asArray[1].prev==null, is(true));
//    }
//    
//    @Test
//    public void canInsertInMiddle() {
//	// aliens is a, b, c
//	// kill a, then insert after b
//	// should give b, a, c
//	asArray[0].kill();
//	asArray[0].regenerate();
//	aliens.insertAfter(asArray[1], asArray[0]);
//	assertThat("FIRST is b", Aliens.FIRST, is(asArray[1]));
//	assertThat("b->a", asArray[1].next, is(asArray[0]));
//	assertThat("a->c", asArray[0].next, is(asArray[2]));
//	assertThat("c->null", asArray[2].next==null, is(true));
//
//	assertThat("a<-c", asArray[2].prev, is(asArray[0]));
//	assertThat("b<-a", asArray[0].prev, is(asArray[1]));
//	assertThat("null<-b", asArray[1].prev==null, is(true));
//    }
//
//    @Test
//    public void canInsertAtEnd() {
//	// aliens is a, b, c
//	// kill b, then insert after c
//	// should give a, c, b
//	asArray[1].kill();
//	asArray[1].regenerate();
//	aliens.insertAfter(asArray[2], asArray[1]);
//	assertThat("FIRST is a", Aliens.FIRST, is(asArray[0]));
//	assertThat("a->c", asArray[0].next, is(asArray[2]));
//	assertThat("c->b", asArray[2].next, is(asArray[1]));
//	assertThat("b->null", asArray[1].next==null, is(true));
//
//	assertThat("c<-b", asArray[1].prev, is(asArray[2]));
//	assertThat("a<-c", asArray[2].prev, is(asArray[0]));
//	assertThat("null<-a", asArray[0].prev==null, is(true));
//    }
//
//    private int traverseAliens() {
//	Alien pointer = Aliens.FIRST;
//	int count = 0;
//	while (pointer != null) {
//	    pointer = pointer.next;
//	    count++;
//	}
//	return count;
//    }
//
//    private int traverseAliensBackwards() {
//	Alien pointer = asArray[aliens.size() - 1];
//	int count = 0;
//	while (pointer != null) {
//	    pointer = pointer.prev;
//	    count++;
//	}
//	return count;
//    }
//
//    private Alien alienAt(int index, Aliens someAliens) {
//	return someAliens.getArray()[index];
//    }
//
//    private Alien alienAt(int index) {
//	return alienAt(index, aliens);
//    }
//
//    private Formation getFormation(String path) {
//	return new Formation(path);
//    }
//
//    // @Test
//    // public void canCreate() {
//    // for (Alien alien : aliens) {
//    // assertThat("is an alien", alien.getClass().getName(),
//    // is(Alien.class.getName()));
//    // }
//    // }
//    //
//    // @Test
//    // public void moveRightForOneSecondMovesExpectedAmount() {
//    // int dx = dx();
//    // int[] before = getAlienXs(aliens);
//    // aliens.move(1000);
//    // int[] after = getAlienXs(aliens);
//    // assertThat("alien0 moved", after[0], is(before[0] + dx));
//    // assertThat("alien1 moved", after[1], is(before[1] + dx));
//    // assertThat("alien2 moved", after[2], is(before[2] + dx));
//    // }
//    //
//    // @Test
//    // public void canMoveToRightEdge() {
//    // int preMoveY = alienAt(2).getY();
//    // moveAliensToRightEdge();
//    // assertThat("moved to edge", alienAt(2).rightEdge(), is(Screen.width()));
//    // assertThat("no move down", alienAt(2).getY(), is(preMoveY));
//    // }
//    //
//    // @Test
//    // public void hittingRightWallStopsAllAliensHorizontalMovement() {
//    // moveAliensToRightEdge();
//    // aliens.move(1);
//    // assertThat("1st stopped", alienAt(0).getDx(), is(0));
//    // assertThat("2nd stopped", alienAt(1).getDx(), is(0));
//    // assertThat("3rd stopped", alienAt(2).getDx(), is(0));
//    // }
//    //
//    // @Test
//    // public void noBunchingWhenHittingWall() {
//    // aliens = new Aliens();
//    // aliens.newLevel(getFormation("data/test/aliens-test.properties"));
//    // assertThat("before", evenlySpaced(), is(true));
//    // moveAliensToRightEdge();
//    // aliens.move(1000);
//    // assertThat("after", evenlySpaced(), is(true));
//    // }
//    //
//    // @Test
//    // public void overShootingRightWallIsReset() {
//    // aliens.move(10000);
//    // assertThat("alien0", alienAt(0).rightEdge(), is(not(Screen.width())));
//    // assertThat("alien1", alienAt(1).rightEdge(), is(not(Screen.width())));
//    // assertThat("alien2", alienAt(2).rightEdge(), is(Screen.width()));
//    // assertThat("evenly spaced", this.evenlySpaced(), is(true));
//    // }
//    //
//    // @Test
//    // public void overShootingLeftWallIsReset() {
//    // aliens.flipDirection();
//    // aliens.move(10000);
//    //
//    // assertThat("alien0", alienAt(0).leftEdge(), is(Screen.left()));
//    // assertThat("alien1", alienAt(1).leftEdge(), is(not(Screen.left())));
//    // assertThat("alien2", alienAt(2).leftEdge(), is(not(Screen.left())));
//    // assertThat("evenly spaced", this.evenlySpaced(), is(true));
//    // }
//    //
//    // @Test
//    // public void overShootingYTargetIsReset() {
//    // int bottom = alienAt(0).getY();
//    // int vsd = verticalStepDistance();
//    // int expected = bottom - vsd;
//    //
//    // aliens.move(10000);
//    // aliens.move(10000);
//    //
//    // assertThat("alien0", alienAt(0).getY(), is(expected));
//    // assertThat("alien1", alienAt(1).getY(), is(expected));
//    // assertThat("alien2", alienAt(2).getY(), is(expected));
//    // assertThat("evenly spaced", this.evenlySpaced(), is(true));
//    // }
//    //
//    // @Test
//    // public void afterHittingWallAlienDirectionIsStraightDown() {
//    // moveAliensToRightEdge();
//    // aliens.move(1000);
//    // assertThat("horizontal speed", dx(), is(0));
//    // assertThat("vertical speed", dy(), is(-maxSpeed()));
//    // }
//    //
//    // @Test
//    // public void afterHittingWallAliensMoveDownVerticalStepDistance() {
//    // moveAliensToRightEdge();
//    // int vsd = verticalStepDistance();
//    // int[] before = getAlienYs(aliens);
//    // aliens.move(1000);
//    // int[] after = getAlienYs(aliens);
//    // assertThat("1st moved down", after[0], is(before[0] - vsd));
//    // assertThat("2nd moved down", after[1], is(before[1] - vsd));
//    // assertThat("3rd moved down", after[2], is(before[2] - vsd));
//    // }
//    //
//    // @Test
//    // public void duringMoveDownAliensDontMoveLeftOrRight() {
//    // moveAliensToRightEdge();
//    // int[] before = getAlienXs(aliens);
//    // aliens.move(1000);
//    // int[] after = getAlienXs(aliens);
//    // assertThat("1st at edge", after[0], is(before[0]));
//    // assertThat("2nd at edge", after[1], is(before[1]));
//    // assertThat("3rd at edge", after[2], is(before[2]));
//    // }
//    //
//    // @Test
//    // public void alienDirectionIsLeftAfterMovingDownRightWall() {
//    // moveAliensToRightEdge();
//    // aliens.move(1000);
//    // aliens.move(1000);
//    // assertThat("horizontal speed", dx(), is(-maxSpeed()));
//    // assertThat("vertical speed", dy(), is(0));
//    // }
//    //
//    // @Test
//    // public void alienDirectionStaysLeftFourMovesAfterMoveDownRightWall() {
//    // moveAliensToRightEdge();
//    // aliens.move(1000);
//    // aliens.move(1000);
//    // aliens.move(1000);
//    // aliens.move(1000);
//    // assertThat("horizontal speed", dx(), is(-maxSpeed()));
//    // assertThat("vertical speed", dy(), is(0));
//    // }
//    //
//    // @Test
//    // public void aliensMoveLeftAfterMovingDownAtRight() {
//    // int dx = dx();
//    // moveAliensToRightEdge();
//    // int[] before = getAlienXs(aliens);
//    // aliens.move(1000);
//    // aliens.move(1000);
//    // int[] after = getAlienXs(aliens);
//    //
//    // assertThat("alien0.x", after[0], is(before[0] - dx));
//    // assertThat("alien1.x", after[1], is(before[1] - dx));
//    // assertThat("alien2.x", after[2], is(before[2] - dx));
//    // }
//    //
//    // @Test
//    // public void canFlipDirection() {
//    // int before = aliens.getSpeed().getDx();
//    // aliens.flipDirection();
//    // int after = aliens.getSpeed().getDx();
//    // assertThat("flipped", after, is(-before));
//    //
//    // aliens.move(100);
//    // int afterMove = aliens.getSpeed().getDx();
//    // assertThat("stays flipped", afterMove, is(-before));
//    // }
//    //
//    // @Test
//    // public void canSetDirectionRight() {
//    // setDirectionRight();
//    // assertThat("right", aliens.getSpeed().getDx(), is(67));
//    // }
//    //
//    // @Test
//    // public void canSetDirectionLeft() {
//    // setDirectionLeft();
//    // assertThat("left", aliens.getSpeed().getDx(), is(-67));
//    // }
//    //
//    // @Test
//    // public void canPositionAliensAtLeftEdge() {
//    // positionAliensAtLeftEdge();
//    // int[] after = getAlienXs(aliens);
//    // assertThat("moved to left edge", after[0], is(0));
//    // }
//    //
//    // @Test
//    // public void canPositionAliensAtRightEdge() {
//    // positionAliensAtRightEdge();
//    // assertThat("moved to right edge", alienAt(2).rightEdge(), is(Screen
//    // .width()));
//    // }
//    //
//    // @Test
//    // public void doesntMoveDownTwiceAtLeftEdge() {
//    // positionAliensAtLeftEdge();
//    // aliens.move(300);
//    // aliens.move(0);
//    // aliens.move(100);
//    // assertThat("y unchanged", alienAt(0).getY(), is(yBaseLine - VERT_STEP));
//    // assertThat("x increases", alienAt(0).getX(), is(not(Screen.left())));
//    // }
//    //
//    // @Test
//    // public void doesntMoveDownTwiceAtRightEdge() {
//    // positionAliensAtRightEdge();
//    // aliens.move(300);
//    // aliens.move(0);
//    // aliens.move(100);
//    // assertThat("y unchanged", alienAt(2).getY(), is(yBaseLine - VERT_STEP));
//    // assertThat("x decreases", alienAt(2).rightEdge(),
//    // is(not(Screen.width())));
//    // }
//    //
//    // @Test
//    // public void canMoveAliensToBottomOfScreenWithoutDoubleSideMoveDowns() {
//    // int startingX = alienAt(0).getX();
//    // List<Integer> listOfYs = new ArrayList<Integer>();
//    // for (int i = 0; i < 8000; i++) {
//    // aliens.move(12);
//    // aliens.move(0);
//    // // save y when aliens in about the middle
//    // if (alienAt(0).getX() == startingX) {
//    // listOfYs.add(alienAt(0).getY());
//    // }
//    // if (alienAt(0).getY() < VERT_STEP * 2) {
//    // break;
//    // }
//    // }
//    // int lastY = yBaseLine;
//    // for (Integer thisY : listOfYs) {
//    // assertThat(lastY - thisY, is(20));
//    // lastY = thisY;
//    // }
//    // }
//    //
//    // private void positionAliensAtLeftEdge() {
//    // int distanceToLeft = alienAt(0).getX();
//    // for (Alien alien : aliens) {
//    // alien.moveBy(-distanceToLeft, 0);
//    // }
//    // setDirectionLeft();
//    // aliens.setLastDirection(1);
//    // }
//    //
//    // private void positionAliensAtRightEdge() {
//    // int distanceToRight = Screen.width() - alienAt(2).rightEdge();
//    // for (Alien alien : aliens) {
//    // alien.moveBy(distanceToRight, 0);
//    // }
//    // setDirectionRight();
//    // aliens.setLastDirection(-1);
//    // }
//    //
//    // private void moveAliensToRightEdge() {
//    // int dx = dx();
//    // assert dx > 0 : "dx must be > 0: " + dx;
//    // int distance = Screen.width() - alienAt(2).rightEdge();
//    // assert distance > 0 : "distance must be > 0: " + distance;
//    //
//    // int timeInMillis = distance * 1000 / dx;
//    // aliens.move(timeInMillis);
//    // int rightMostPoint = alienAt(2).rightEdge();
//    //
//    // assert rightMostPoint == Screen.width() : "rightMostPoint: "
//    // + rightMostPoint + " RIGHT_EDGE: " + Screen.width();
//    // }
//    //
//    // private void setDirectionLeft() {
//    // if (aliens.getSpeed().getDx() > 0) {
//    // aliens.flipDirection();
//    // }
//    // }
//    //
//    // private void setDirectionRight() {
//    // if (aliens.getSpeed().getDx() < 0) {
//    // aliens.flipDirection();
//    // }
//    // }
//    //
//    // private boolean evenlySpaced() {
//    // int spacing = Integer.parseInt(props.getProperty("spacingHorizontal"));
//    // int lastLeftEdge = alienAt(0).leftEdge() - spacing;
//    // for (Alien alien : aliens) {
//    // if (alien.leftEdge() - lastLeftEdge != spacing) {
//    // return false;
//    // }
//    // lastLeftEdge = alien.leftEdge();
//    // }
//    // return true;
//    // }
//    //
//    // private int[] getAlienXs(Aliens someAliens) {
//    // int[] theAlienXs = new int[countAliens(someAliens)];
//    // int i = 0;
//    // for (Alien alien : someAliens) {
//    // theAlienXs[i] = alien.getX();
//    // i++;
//    // }
//    // return theAlienXs;
//    // }
//    //
//    // private int[] getAlienYs(Aliens someAliens) {
//    // int[] theAlienYs = new int[countAliens(someAliens)];
//    // int i = 0;
//    // for (Alien alien : someAliens) {
//    // theAlienYs[i] = alien.getY();
//    // i++;
//    // }
//    // return theAlienYs;
//    // }
//    //
//    // private int countAliens(Aliens someAliens) {
//    // int count = 0;
//    // for (@SuppressWarnings("unused")
//    // Alien alien : someAliens) {
//    // count++;
//    // }
//    // return count;
//    // }
//    //
//    // private int maxSpeed() {
//    // return aliens.getAlienSpeeds().get(100);
//    // }
//    //
//    // private int verticalStepDistance() {
//    // return Integer.parseInt(props.getProperty("verticalStepDistance"));
//    // }
//    //
//    // private int dy() {
//    // return aliens.getSpeed().getDy();
//    // }
//    //
//    // private int dx() {
//    // return aliens.getSpeed().getDx();
//    // }
//    //
// }
