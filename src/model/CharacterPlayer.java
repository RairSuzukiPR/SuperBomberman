package model;

import java.io.File;

public class CharacterPlayer {
	
	private Sprite characterSprite, deadCharacterSprite;
	private Sprite bombSprite, bombSpriteUpperEnd, bombSpriteBottomEnd, bombSpriteLeftEnd, bombSpriteRightEnd, bombSpriteHorizontal, bombSpriteVertical;
	private int lives, speed, availableBombs, sizeExplosion;
	
	public CharacterPlayer(Sprite characterSprite) {
		this.characterSprite = characterSprite;
		this.deadCharacterSprite = new Sprite(new File("imgs/explodedBomberman.png"), 0, 6, 1, characterSprite.getPosX(), characterSprite.getPosY());
		this.bombSprite = new Sprite(new File("imgs/bombs.png"), 9, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteUpperEnd = new Sprite(new File("imgs/bombs.png"), 2, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteBottomEnd = new Sprite(new File("imgs/bombs.png"), 5, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteLeftEnd = new Sprite(new File("imgs/bombs.png"), 3, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteRightEnd = new Sprite(new File("imgs/bombs.png"), 31, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteHorizontal = new Sprite(new File("imgs/bombs.png"), 7, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.bombSpriteVertical = new Sprite(new File("imgs/bombs.png"), 6, 4, 9, characterSprite.getPosX(), characterSprite.getPosY()); //alterar appearecne
		this.lives = 1;
		this.speed = 5; 
		this.availableBombs = 1; 
	}
	
	
	public Sprite getCharacterSprite() {
		return characterSprite;
	}

	public void setCharacterSprite(Sprite characterSprite) {
		this.characterSprite = characterSprite;
	}

	public Sprite getBombSprite() {
		return bombSprite;
	}

	public void setBombSprite(Sprite bombSprite) {
		this.bombSprite = bombSprite;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}



	public Sprite getBombSpriteUpperEnd() {
		return bombSpriteUpperEnd;
	}



	public void setBombSpriteUpperEnd(Sprite bombSpriteUpperEnd) {
		this.bombSpriteUpperEnd = bombSpriteUpperEnd;
	}



	public Sprite getBombSpriteBottomEnd() {
		return bombSpriteBottomEnd;
	}



	public void setBombSpriteBottomEnd(Sprite bombSpriteBottomEnd) {
		this.bombSpriteBottomEnd = bombSpriteBottomEnd;
	}



	public Sprite getBombSpriteLeftEnd() {
		return bombSpriteLeftEnd;
	}



	public void setBombSpriteLeftEnd(Sprite bombSpriteLeftEnd) {
		this.bombSpriteLeftEnd = bombSpriteLeftEnd;
	}



	public Sprite getBombSpriteRightEnd() {
		return bombSpriteRightEnd;
	}



	public void setBombSpriteRightEnd(Sprite bombSpriteRightEnd) {
		this.bombSpriteRightEnd = bombSpriteRightEnd;
	}



	public Sprite getBombSpriteHorizontal() {
		return bombSpriteHorizontal;
	}



	public void setBombSpriteHorizontal(Sprite bombSpriteHorizontal) {
		this.bombSpriteHorizontal = bombSpriteHorizontal;
	}



	public Sprite getBombSpriteVertical() {
		return bombSpriteVertical;
	}



	public void setBombSpriteVertical(Sprite bombSpriteVertical) {
		this.bombSpriteVertical = bombSpriteVertical;
	}



	public int getAvailableBombs() {
		return availableBombs;
	}



	public void setAvailableBombs(int availableBombs) {
		this.availableBombs = availableBombs;
	}



	public int getSizeExplosion() {
		return sizeExplosion;
	}



	public void setSizeExplosion(int sizeExplosion) {
		this.sizeExplosion = sizeExplosion;
	}



	public Sprite getDeadCharacterSprite() {
		return deadCharacterSprite;
	}



	public void setDeadCharacterSprite(Sprite deadCharacterSprite) {
		this.deadCharacterSprite = deadCharacterSprite;
	}



	public int getLives() {
		return lives;
	}



	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
