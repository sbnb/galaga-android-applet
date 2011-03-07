package com.gamefreezer.galaga;

import static com.gamefreezer.galaga.Constants.*;

public class CollisionDetector extends AllocGuard {

    public CollisionDetector() {
	super();
    }

    public void checkCollisions(Aliens aliens, Ship ship, Score score,
	    Bullets playerBullets, Bullets alienBullets, KillPoints killPoints) {
	checkPlayerShootsAliens(playerBullets, aliens, score, killPoints);
	checkAliensShootPlayer(alienBullets, ship, score);
	checkAlienPlayerCollision(aliens, ship, score);
    }

    private void checkPlayerShootsAliens(Bullets playerBullets, Aliens aliens,
	    Score score, KillPoints killPoints) {
	for (Entity bullet : playerBullets) {
	    if (bullet.isAlive()) {
		checkBulletAgainstAllAliens(bullet, aliens, score, killPoints);
	    }
	}
    }

    private void checkAliensShootPlayer(Bullets alienBullets, Ship ship,
	    Score score) {
	for (Entity bullet : alienBullets) {
	    if (bullet.isAlive() && bullet.intersects(ship)) {
		bullet.kill();
		score.decrementHealth(HEALTH_HIT_LIGHT);
	    }
	}
    }

    private void checkAlienPlayerCollision(Aliens aliens, Ship ship, Score score) {
	Alien[] aliensArray = aliens.getArray();
	for (int i = 0; i < aliens.size(); i++) {
	    Alien alien = aliensArray[i];
	    if (alien.isAlive() && alien.intersects(ship)) {
		if (alien.isSolo()) {
		    alien.kill();
		    alien.explode();
		    score.decrementHealth(HEALTH_HIT_SEVERE);
		} else {
		    score.setHealth(0);
		}
	    }
	}
    }

    private void checkBulletAgainstAllAliens(Entity bullet, Aliens aliens,
	    Score score, KillPoints killPoints) {
	Alien[] aliensArray = aliens.getArray();
	for (int i = 0; i < aliens.size(); i++) {
	    Alien alien = aliensArray[i];
	    if (alien.isAlive() && bullet.intersects(alien)) {
		alien.kill();
		alien.explode();
		bullet.kill();
		score.incrementHitsMade();
		score.incrementScore(alien.points());
		killPoints.add(alien);
		break;
	    }
	}
    }
}
