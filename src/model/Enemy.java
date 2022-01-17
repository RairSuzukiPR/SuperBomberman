package model;

import java.awt.Rectangle;
import java.io.File;

public class Enemy {
	private Sprite enemySprite, deadEnemySprite;
	private int speed, lives;
	
	public Enemy(Sprite enemySprite, int speed) {
		this.enemySprite = enemySprite;
		this.deadEnemySprite = new Sprite(new File("imgs/deadEnemy.png"), 0, 5, 1, enemySprite.getPosX(), enemySprite.getPosY());
		this.enemySprite.atualizarPosicao(enemySprite.getPosX(), enemySprite.getPosY());
		this.speed = speed;
		this.lives = 1;
	}


	public Sprite getEnemySprite() {
		return enemySprite;
	}

	public void setEnemySprite(Sprite enemySprite) {
		this.enemySprite = enemySprite;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Sprite getDeadEnemySprite() {
		return deadEnemySprite;
	}

	public void setDeadEnemySprite(Sprite deadEnemySprite) {
		this.deadEnemySprite = deadEnemySprite;
	}
	
	
}
