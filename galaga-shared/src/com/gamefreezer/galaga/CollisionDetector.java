package com.gamefreezer.galaga;

public class CollisionDetector extends AllocGuard {

    private Explosions explosions;

    public CollisionDetector(Explosions explosions) {
	super();
	this.explosions = explosions;
    }

    public void checkCollisions(Aliens aliens, Ship ship, Score score,
	    Bullets playerBullets, Bullets alienBullets, KillPoints killPoints) {
	checkPlayerShootsAliens(playerBullets, aliens, score, killPoints);
	checkAliensShootPlayer(alienBullets, ship, score);
	checkAlienPlayerCollision(aliens, ship, score);
    }

    private void checkPlayerShootsAliens(Bullets playerBullets, Aliens aliens,
	    Score score, KillPoints killPoints) {
	Bullet[] bulletArray = playerBullets.getArray();
	int len = bulletArray.length;
	for (int i = 0; i < len; i++) {
	    if (bulletArray[i].isAlive()) {
		checkBulletAgainstAllAliens(bulletArray[i], aliens, score,
			killPoints);
	    }
	}
    }

    private void checkAliensShootPlayer(Bullets alienBullets, Ship ship,
	    Score score) {
	Bullet[] bulletArray = alienBullets.getArray();
	int len = bulletArray.length;
	for (int i = 0; i < len; i++) {
	    if (bulletArray[i].isAlive() && bulletArray[i].intersects(ship)) {
		bulletArray[i].kill();
		score.applyLightHealthHit();
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
		    explosions.newExplosion(alien);
		    score.applySevereHealthHit();
		} else {
		    score.setHealth(0);
		}
	    }
	}
    }

    private void checkBulletAgainstAllAliens(Bullet bullet, Aliens aliens,
	    Score score, KillPoints killPoints) {
	Alien[] aliensArray = aliens.getArray();
	for (int i = 0; i < aliens.size(); i++) {
	    Alien alien = aliensArray[i];
	    if (alien.isAlive() && bullet.intersects(alien)) {
		score.incrementHitsMade();
		alien.registerHit(bullet.getHitAnimation(), bullet
			.getLocation());
		bullet.kill();
		alien.decrementHealth(bullet.getDamage());

		if (alien.getHealth() <= 0) { // killed
		    alien.setHealth(0);
		    alien.kill();
		    explosions.newExplosion(alien);
		    score.incrementScore(alien.points());
		    killPoints.add(alien);
		}

		break;
	    }
	}
    }
}
