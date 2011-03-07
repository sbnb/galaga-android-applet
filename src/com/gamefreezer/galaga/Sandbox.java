package com.gamefreezer.galaga;

//import static com.gamefreezer.galaga.Constants.*;

public class Sandbox extends AllocGuard {

    private Alien alien;
    Location start = new Location(Screen.left() + 20, Screen.height() - 50);
    // Location start = new Location(Screen.left() + 200, Screen.height() -
    // 400);
    private Location target = new Location(200, 200);

    public Sandbox() {
	super();
	alien = getAlien(start);
    }

    public void update(int delta) {
	Controller.adjustSpeed(alien.getSpeed(), alien.getLocation(), target);
	alien.move(delta);
    }

    public void draw(AbstractGraphics graphics) {
	alien.draw(graphics);
	graphics.drawRect(Screen.translateX(target.getX()), Screen
		.translateY(target.getY()), 1, 1);
    }

    public static Alien getAlien(Location location) {
	int dx = 0;
	int dy = 0;
	int baseDx = 100;
	int baseDy = 100;
	String imgPath = "alien_b.png";
	int points = 10;
	String renderTimes = "";
	String renderTicks = "";

	return new Alien(location, dx, dy, baseDx, baseDy, imgPath, points,
		renderTimes, renderTicks);
    }
}
