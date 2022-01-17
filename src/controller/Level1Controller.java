package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import model.CharacterPlayer;
import model.Enemy;
import model.Sprite;
import util.level.GameSound;
import view.GameOverView;
import view.Level1View;
import view.Level2View;

public class Level1Controller{
	Level1View level1View;
	GameLoop gameLoop;
	int FPS = 10;
	boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed; 
	boolean wPressed, sPressed, aPressed, dPressed, ePressed;
	boolean  multiplayer, intersectsFixedBlock1, intersectsFixedBlock2, intersectsFixedBlock3, intersectsFixedBlock4, intersectsFixedBlock5, intersectsFixedBlock6, intersectsFixedBlock7, intersectsFixedBlock8, intersectsFixedBlock9, intersectsFixedBlock10, intersectsFixedBlock11;
	Sprite bomb;
	ArrayList<EnemyMovement> enemiesMovement;
	Timer timeToWin, timerScreen;
	PlayerMovement player1Movement, player2Movement;
	GameSound audio;
	
	public Level1Controller(Level1View level1View, boolean multiplayer) {
		this.level1View = level1View;
		this.enemiesMovement = new ArrayList<EnemyMovement>();
		this.multiplayer = multiplayer;
		for (Enemy e : level1View.getEnemies()) {
			enemiesMovement.add(new EnemyMovement(e));
		}
		this.gameLoop = new GameLoop();
		this.player1Movement = new PlayerMovement(level1View.getCharacter1(), 1);
		this.player2Movement = new PlayerMovement(level1View.getCharacter2(), 2);
		audio = new GameSound();
	}
	
	public void control() {
		level1View.addKeyListener(new TAdapter());
		
		audio.backgroundAudioPlay();
		
		player1Movement.start();
		if (multiplayer) {
			player2Movement.start();
		}
		
		timerScreen = new Timer(1000, new ActionListener() {
			int m = 60;
			int s = 10;
			public void actionPerformed(ActionEvent e) {
				if (m == 1) { 
					m = 60;
					level1View.getTimeFirstDigit().setAppearance((level1View.getTimeFirstDigit().getAppearance() != 0)? level1View.getTimeFirstDigit().getAppearance()-1 : level1View.getTimeFirstDigit().getAppearance()); //
				} else {
					m--;
				}
				if (s == 1 ) {
					s = 10;
					level1View.getTimeSecondDigit().setAppearance((level1View.getTimeSecondDigit().getAppearance() != 0)? level1View.getTimeSecondDigit().getAppearance()-1 : 5);			
				} else {
					s--;
				}
				
				level1View.getTimeThirdDigit().setAppearance((level1View.getTimeThirdDigit().getAppearance() != 0)? level1View.getTimeThirdDigit().getAppearance()-1 : 9);
			}
		});
		
		timeToWin = new Timer(180000, new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				audio.backgroundAudioStop();
				audio.gameOverAudioPlay();
				
				level1View.setVisible(false);
				gameOver("Tempo esgotou!");
			}
		});
		
		for (EnemyMovement eM : enemiesMovement) {
			eM.start();
		}
		timeToWin.start();
		timerScreen.start();
		gameLoop.start();
		
	}
	
	private class GameLoop extends Thread {
		public void run() {
			synchronized (this) {
				while (true) {
					isCharacterAlive(1);
					if (multiplayer) {
						isCharacterAlive(2);	
					}
					isEnemyAlive(0);
					isEnemyAlive(1);
					if (level1View.getTotems() == 0) {
						audio.backgroundAudioStop();
						audio.levelCompletedAudioPlay();
						level1View.displayJOptionPane("", "Fase 1 concluída!\nInimigos serão mais rápidos, cuidado!");
						enemiesMovement.get(0).stop();
						enemiesMovement.get(1).stop();
						player1Movement.stop();
						player2Movement.stop();
						level1View.setVisible(false);
						audio.backgroundAudioStop();
						timeToWin.stop();
						new Level2Controller(new Level2View(multiplayer), multiplayer).control();
						gameLoop.stop();
					}
					try {
						Thread.sleep(1000/FPS);
						level1View.repaint();
					} catch (Exception e) {
						level1View.displayJOptionPane("", "Erro no run()");
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}
	
	public boolean isCharacterAlive(int playerNumber) {
		if (playerNumber == 1) {
			if (level1View.getExplosionRectanglesP1().size() > 0) {
				for (Rectangle e: level1View.getExplosionRectanglesP1()) {
					if (level1View.getCharacter1().getCharacterSprite().getHitBox().intersects(e) ) {
						level1View.getCharacter1().setLives(level1View.getCharacter1().getLives()-1);
						deadPlayerAnimation(1, "bomb");
						restartGameSettings();
						return false;
					}
					if (multiplayer) {
						if (level1View.getCharacter2().getCharacterSprite().getHitBox().intersects(e)) {
							level1View.getCharacter2().setLives(level1View.getCharacter2().getLives()-1);
							deadPlayerAnimation(2, "bomb");
							restartGameSettings();
							return false;
						}
					}
				}
			}
			if (level1View.getEnemies().size() > 0) {
				for(Enemy enemy: level1View.getEnemies()) {
					if (level1View.getCharacter1().getCharacterSprite().getHitBox().intersects(enemy.getEnemySprite().getHitBox())) {
						level1View.getCharacter1().setLives(level1View.getCharacter1().getLives()-1);
						deadPlayerAnimation(1, "enemy");
						restartGameSettings();
						return false;
					}
				}
			}
		} else if (playerNumber == 2) {
			if (level1View.getExplosionRectanglesP2().size() > 0) {
				for (Rectangle e: level1View.getExplosionRectanglesP2()) {
					if (level1View.getCharacter2().getCharacterSprite().getHitBox().intersects(e)) {
						level1View.getCharacter2().setLives(level1View.getCharacter2().getLives()-1);
						deadPlayerAnimation(2, "bomb");
						restartGameSettings();
						return false;
					}
					if (level1View.getCharacter1().getCharacterSprite().getHitBox().intersects(e) ) {
						level1View.getCharacter1().setLives(level1View.getCharacter1().getLives()-1);
						deadPlayerAnimation(1, "bomb");
						restartGameSettings();
						return false;
					}
				}
			}
			if (level1View.getEnemies().size() > 0) {
				for(Enemy enemy: level1View.getEnemies()) {
					if (level1View.getCharacter2().getCharacterSprite().getHitBox().intersects(enemy.getEnemySprite().getHitBox())) {
						level1View.getCharacter2().setLives(level1View.getCharacter2().getLives()-1);
						deadPlayerAnimation(2, "enemy");
						restartGameSettings();
						return false;
					}
				}
			}
			
		}
		return true;
	}
	
	public void gameOver(String message) {
		GameOverView gameOverView = new GameOverView();
		
		if (multiplayer) {
			try {
				gameOverView.setBackground(ImageIO.read(new File("imgs/MultiPlayerGameOver.png")));
			} catch (IOException e1) { e1.printStackTrace(); }
		}
		
		gameOverView.setVisible(true);
		level1View.displayJOptionPane("Game Over", message);
		
		if (level1View.displayJOptionPaneQuestion("Super Bomberman", "Deseja jogar novamente?")) { 
			timeToWin.stop();
			audio.gameOverAudioStop();
			level1View.setVisible(false);
			gameOverView.setVisible(false);
			new Level1Controller(new Level1View(multiplayer), multiplayer).control();
			gameLoop.stop();
		} else {
			System.exit(0);
		}
	}
	
	public void restartGameSettings() {
		audio.backgroundAudioStop();
		audio.gameOverAudioPlay();
		level1View.setVisible(false);
		String message = "Voce morreu!";
		player1Movement.stop();
		if (multiplayer) {
			player2Movement.stop();
			message = "Um de voces morreu!";
		}
		
		enemiesMovement.get(0).stop();
		enemiesMovement.get(1).stop();
		gameOver(message);
	}
	
	public boolean isEnemyAlive(int enemyIndex) {
		
		if (enemyIndex <= level1View.getEnemies().size()-1) {
			if (level1View.getExplosionRectanglesP1().size() > 0) {
				for (Rectangle explosion: level1View.getExplosionRectanglesP1()) {
					if (level1View.getEnemies().get(enemyIndex).getEnemySprite().getHitBox().intersects(explosion)) {
						level1View.getEnemies().remove(enemyIndex);
						return false;
					}
				}
			}
			
			if(multiplayer) {
				if (level1View.getExplosionRectanglesP2().size() > 0) {
					for (Rectangle explosion: level1View.getExplosionRectanglesP2()) {
						if (level1View.getEnemies().get(enemyIndex).getEnemySprite().getHitBox().intersects(explosion)) {
							level1View.getEnemies().remove(enemyIndex);
							return false;
						}
					}
				}
			}
			
		}
		return true;
	}
	
	private boolean checkIntersectsCollision(Sprite characterSprite) {
		ArrayList<Rectangle> fixedBlocks = level1View.getFixedBlocks();
		ArrayList<Rectangle> destructibleBlocks = level1View.getDestructibleBlocksRectangles();
		ArrayList<Rectangle> totems = level1View.getTotemRectangles();
		
		for (Rectangle f: fixedBlocks) {
			if (characterSprite.getHitBox().intersects(f)) {
				return true;
			}
		}
		
		for (Rectangle d: destructibleBlocks) {
			if (characterSprite.getHitBox().intersects(d)) {
				return true;
			}
		}
		
		for (Rectangle t: totems) {
			if (characterSprite.getHitBox().intersects(t)) {
				return true;
			}
		}
		
		return false; 
	}
	
	private class PlayerMovement extends Thread{
		int playerNumber;
		int up, down, left, right;
		CharacterPlayer character;
        public PlayerMovement(CharacterPlayer character, int playerNumber) {
        	this.character = character;
			this.playerNumber = playerNumber;
		}

		@Override
        public void run() {
            try {
                while (true) {  
                	if ((playerNumber == 1)? upPressed : wPressed) {
                    	if( !(checkIntersectsCollision(character.getCharacterSprite())) && !(level1View.getBoundaries().get(0).intersects(character.getCharacterSprite().getHitBox()) )) {	
                    		character.getCharacterSprite().atualizarPosicao(character.getCharacterSprite().getPosX(), character.getCharacterSprite().getPosY() - character.getSpeed());
            				switch (up) { 
            				case 0:
            					character.getCharacterSprite().setAppearance(1);
            					break;
            				case 1:
            					character.getCharacterSprite().setAppearance(5);
            					break;
            				case 2:
            					character.getCharacterSprite().setAppearance(9);
            					break;
            				}
            				if (up == 3) up=0;
            				else up++;
            			}
                    }else if ((playerNumber == 1)? downPressed : sPressed) {
                     	if(!checkIntersectsCollision(character.getCharacterSprite()) && !(level1View.getBoundaries().get(1).intersects(character.getCharacterSprite().getHitBox()) ) ) {	
                     		character.getCharacterSprite().atualizarPosicao(character.getCharacterSprite().getPosX(), character.getCharacterSprite().getPosY() + character.getSpeed());
             				switch (down) {
             				case 0:
             					character.getCharacterSprite().setAppearance(0);
             					break;
             				case 1:
             					character.getCharacterSprite().setAppearance(4);
             					break;
             				case 2:
             					character.getCharacterSprite().setAppearance(8);
             					break;
             				}
             				if (down == 3) down=0;
             				else down++;
             			}
                     } else if ((playerNumber == 1)? leftPressed : aPressed) {
                     	if(!checkIntersectsCollision(character.getCharacterSprite()) && !(level1View.getBoundaries().get(2).intersects(character.getCharacterSprite().getHitBox()) ) ) {	
                     		character.getCharacterSprite().atualizarPosicao(character.getCharacterSprite().getPosX() - character.getSpeed(), character.getCharacterSprite().getPosY());
             				switch (left) {
             				case 0:
             					character.getCharacterSprite().setAppearance(2);
             					break;
             				case 1:
             					character.getCharacterSprite().setAppearance(6);
             					break;
             				case 2:
             					character.getCharacterSprite().setAppearance(10);
             					break;
             				}
             				if (left == 3) left=0;
             				else left++;
             			}
                     } else if ((playerNumber == 1)? rightPressed : dPressed) {
                    	if(!checkIntersectsCollision(character.getCharacterSprite()) && !(level1View.getBoundaries().get(3).intersects(character.getCharacterSprite().getHitBox())) ) {	
                    		character.getCharacterSprite().atualizarPosicao(character.getCharacterSprite().getPosX() + character.getSpeed(), character.getCharacterSprite().getPosY());
            				switch (right) {
            				case 0:
            					character.getCharacterSprite().setAppearance(3);
            					break;
            				case 1:
            					character.getCharacterSprite().setAppearance(7);
            					break;
            				case 2:
            					character.getCharacterSprite().setAppearance(11);
            					break;
            				}
            				if (right == 3) right=0;
            				else right++;
            			}
                    }  
                	if (spacePressed) {
                    	if (level1View.getCharacter1().getAvailableBombs() != 0) {
            				level1View.setPutBombP1(true);
            				
            				level1View.setBombXP1( ((level1View.getCharacter1().getCharacterSprite().getPosX()/64)*64) +64 );
            				level1View.setBombYP1( ((level1View.getCharacter1().getCharacterSprite().getPosY()/64)*64) +64 );
            				
            				new DropBomb(level1View.getCharacter1(), 1).start();
            			}
                    }
                	if (ePressed) {
                    	if (level1View.getCharacter2().getAvailableBombs() != 0) {
            				level1View.setPutBombP2(true);
            				level1View.setBombXP2( ((level1View.getCharacter2().getCharacterSprite().getPosX()/64)*64) +64 );
            				level1View.setBombYP2( ((level1View.getCharacter2().getCharacterSprite().getPosY()/64)*64) +64 );
            				
            				new DropBomb(level1View.getCharacter2(), 2).start();
            			}
                	}
                    Thread.sleep(40); 
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
		}
	}
	
	public class TAdapter extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
				if(level1View.displayJOptionPaneQuestion("Sair", "Deseja encerrar Super Bomberman?")) {
					System.exit(0);	
				}
			}
	
			if (e.getKeyCode()==KeyEvent.VK_UP) upPressed = true;
			else if (e.getKeyCode()==KeyEvent.VK_DOWN) downPressed = true;
			else if (e.getKeyCode()==KeyEvent.VK_LEFT) leftPressed = true;
			else if (e.getKeyCode()==KeyEvent.VK_RIGHT) rightPressed = true;
			else if (e.getKeyCode()==KeyEvent.VK_SPACE)	spacePressed =  true;	
			
			if (multiplayer) {
				if (e.getKeyCode()==KeyEvent.VK_W) wPressed = true;
				else if (e.getKeyCode()==KeyEvent.VK_S) sPressed = true;
				else if (e.getKeyCode()==KeyEvent.VK_A) aPressed = true;
				else if (e.getKeyCode()==KeyEvent.VK_D) dPressed = true;
				else if (e.getKeyCode()==KeyEvent.VK_E) ePressed = true;
			}
		}
		
		public void keyReleased(KeyEvent e) { 
			
			if (e.getKeyCode()==KeyEvent.VK_UP)	{
				upPressed = false;
				level1View.getCharacter1().getCharacterSprite().setAppearance(1);
				if(checkIntersectsCollision(level1View.getCharacter1().getCharacterSprite())) {	
					level1View.getCharacter1().getCharacterSprite().atualizarPosicao(level1View.getCharacter1().getCharacterSprite().getPosX(), level1View.getCharacter1().getCharacterSprite().getPosY() + level1View.getCharacter1().getSpeed());
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN)	{
				downPressed = false;
				
				level1View.getCharacter1().getCharacterSprite().setAppearance(0);
				if(checkIntersectsCollision(level1View.getCharacter1().getCharacterSprite())) {
					level1View.getCharacter1().getCharacterSprite().atualizarPosicao(level1View.getCharacter1().getCharacterSprite().getPosX(), level1View.getCharacter1().getCharacterSprite().getPosY() - level1View.getCharacter1().getSpeed());
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_LEFT)	{
				leftPressed = false;
				level1View.getCharacter1().getCharacterSprite().setAppearance(10);
				if(checkIntersectsCollision(level1View.getCharacter1().getCharacterSprite())) {
					level1View.getCharacter1().getCharacterSprite().atualizarPosicao(level1View.getCharacter1().getCharacterSprite().getPosX() + level1View.getCharacter1().getSpeed(), level1View.getCharacter1().getCharacterSprite().getPosY());
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_RIGHT)	{
				rightPressed = false;
				level1View.getCharacter1().getCharacterSprite().setAppearance(3);
				if(checkIntersectsCollision(level1View.getCharacter1().getCharacterSprite())) {
					level1View.getCharacter1().getCharacterSprite().atualizarPosicao(level1View.getCharacter1().getCharacterSprite().getPosX() - level1View.getCharacter1().getSpeed(), level1View.getCharacter1().getCharacterSprite().getPosY());
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_SPACE) spacePressed = false;

			
			if (multiplayer) {
				if (e.getKeyCode()==KeyEvent.VK_W)	{
					wPressed = false;
					level1View.getCharacter2().getCharacterSprite().setAppearance(1);
					if(checkIntersectsCollision(level1View.getCharacter2().getCharacterSprite())) {	
						level1View.getCharacter2().getCharacterSprite().atualizarPosicao(level1View.getCharacter2().getCharacterSprite().getPosX(), level1View.getCharacter2().getCharacterSprite().getPosY() + level1View.getCharacter2().getSpeed());
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_S) {
					sPressed = false;
					level1View.getCharacter2().getCharacterSprite().setAppearance(0);
					if(checkIntersectsCollision(level1View.getCharacter2().getCharacterSprite())) {
						level1View.getCharacter2().getCharacterSprite().atualizarPosicao(level1View.getCharacter2().getCharacterSprite().getPosX(), level1View.getCharacter2().getCharacterSprite().getPosY() - level1View.getCharacter2().getSpeed());
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_A) {
					aPressed = false;
					level1View.getCharacter2().getCharacterSprite().setAppearance(10);
					if(checkIntersectsCollision(level1View.getCharacter2().getCharacterSprite())) {
						level1View.getCharacter2().getCharacterSprite().atualizarPosicao(level1View.getCharacter2().getCharacterSprite().getPosX() + level1View.getCharacter2().getSpeed(), level1View.getCharacter2().getCharacterSprite().getPosY());
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_D) {
					dPressed = false;
					level1View.getCharacter2().getCharacterSprite().setAppearance(3);
					if(checkIntersectsCollision(level1View.getCharacter2().getCharacterSprite())) {
						level1View.getCharacter2().getCharacterSprite().atualizarPosicao(level1View.getCharacter2().getCharacterSprite().getPosX() - level1View.getCharacter2().getSpeed(), level1View.getCharacter2().getCharacterSprite().getPosY());
					}
				}
				if (e.getKeyCode()==KeyEvent.VK_E) ePressed = false;
			}
			
		}
	} //end TAdapter
	
	
	
	private class DropBomb extends Thread{
		CharacterPlayer characterPlayer;
		int playerNumber;
		
		public DropBomb(CharacterPlayer characterPlayer, int playerNumber) {
			this.characterPlayer = characterPlayer;
			this.playerNumber = playerNumber;
		}

		public void run() {
			synchronized (this) {
				bombAnimation(characterPlayer, playerNumber);
			}
		}
	}
	
	public void bombAnimation(CharacterPlayer characterPlayer, int playerNumber) {
		if (characterPlayer.getAvailableBombs() > 0) {
			characterPlayer.setAvailableBombs(characterPlayer.getAvailableBombs()-1);
		}
		
		characterPlayer.getBombSprite().setAppearance(9);
		int aux = 0;
		while (aux < 18) {
			switch (characterPlayer.getBombSprite().getAppearance()) {
			case 9:
				characterPlayer.getBombSprite().setAppearance(18);
				level1View.repaint();
				break;
			case 18:
				characterPlayer.getBombSprite().setAppearance(27);
				level1View.repaint();
				break;
			case 27:
				characterPlayer.getBombSprite().setAppearance(0);
				level1View.repaint();
				break;
			case 0:
				characterPlayer.getBombSprite().setAppearance(9);
				level1View.repaint();
				break;
			}
			try {
				Thread.sleep(1000/(FPS/2));
			} catch (InterruptedException i) {i.printStackTrace();}	
			aux++;
		}
		
		audio.bombAudioPlay();
		characterPlayer.getBombSprite().setAppearance(1);
		
		if (playerNumber == 1) {
			level1View.setBombExplodedP1(true);	
			
		} else if (playerNumber == 2) {
			level1View.setBombExplodedP2(true);
		}
		
		level1View.generateExplosionRectangles(playerNumber);
		aux = 0;
		while (aux <8) { 
			switch (characterPlayer.getBombSprite().getAppearance()) {
			case 1:
				characterPlayer.getBombSprite().setAppearance(10);
				characterPlayer.getBombSpriteUpperEnd().setAppearance(2);
				characterPlayer.getBombSpriteBottomEnd().setAppearance(5);
				characterPlayer.getBombSpriteLeftEnd().setAppearance(3);
				characterPlayer.getBombSpriteRightEnd().setAppearance(31);
				characterPlayer.getBombSpriteHorizontal().setAppearance(7);
				characterPlayer.getBombSpriteVertical().setAppearance(6);
				level1View.repaint();
				break;
			case 10:
				characterPlayer.getBombSprite().setAppearance(19);
				characterPlayer.getBombSpriteUpperEnd().setAppearance(11);
				characterPlayer.getBombSpriteBottomEnd().setAppearance(14);
				characterPlayer.getBombSpriteLeftEnd().setAppearance(12);
				characterPlayer.getBombSpriteRightEnd().setAppearance(22);
				characterPlayer.getBombSpriteHorizontal().setAppearance(16);
				characterPlayer.getBombSpriteVertical().setAppearance(15);
				level1View.repaint();
				break;
			case 19:
				characterPlayer.getBombSprite().setAppearance(28);
				characterPlayer.getBombSpriteUpperEnd().setAppearance(20);
				characterPlayer.getBombSpriteBottomEnd().setAppearance(23);
				characterPlayer.getBombSpriteLeftEnd().setAppearance(21);
				characterPlayer.getBombSpriteRightEnd().setAppearance(13);
				characterPlayer.getBombSpriteHorizontal().setAppearance(25);
				characterPlayer.getBombSpriteVertical().setAppearance(24);
				level1View.repaint();
				break;
			case 28:
				if (playerNumber == 1) {
					level1View.setPutBombP1(false);
					level1View.setBombExplodedP1(false);
				} else if (playerNumber == 2) {
					level1View.setPutBombP2(false);
					level1View.setBombExplodedP2(false);
				}
				
				level1View.repaint();
				level1View.resetExplosionRectangles(playerNumber);
				break;
			}
			try {
				Thread.sleep(1000/(FPS/2));
			} catch (InterruptedException i) {i.printStackTrace();}	
			aux++;
		}
		characterPlayer.setAvailableBombs(characterPlayer.getAvailableBombs()+1);
	}
	
	public void deadPlayerAnimation(int playerNumber, String typeOfDeath) {
		audio.backgroundAudioStop();
		audio.deathAudioPlay();
		CharacterPlayer characterPlayer = level1View.getCharacter1();
		if(playerNumber == 2) {
			characterPlayer = level1View.getCharacter2();
		}
		
		if (typeOfDeath.equalsIgnoreCase("enemy")) {
			if(playerNumber == 2) {
				characterPlayer.setDeadCharacterSprite(new Sprite(new File("imgs/deadBombermanp2.png"), 0, 6, 1, level1View.getCharacter1().getCharacterSprite().getPosX(), level1View.getCharacter1().getCharacterSprite().getPosY()));		
			} else {
				characterPlayer.setDeadCharacterSprite(new Sprite(new File("imgs/deadBomberman.png"), 0, 6, 1, level1View.getCharacter1().getCharacterSprite().getPosX(), level1View.getCharacter1().getCharacterSprite().getPosY()));
			}
			
		}
		characterPlayer.getDeadCharacterSprite().setAppearance(0);
		int aux = 0;
		while (aux < 5) {
			switch (characterPlayer.getDeadCharacterSprite().getAppearance()) {
			case 0:
				characterPlayer.getDeadCharacterSprite().setAppearance(1);
				level1View.repaint();
				break;
			case 1:
				characterPlayer.getDeadCharacterSprite().setAppearance(2);
				level1View.repaint();
				break;
			case 2:
				characterPlayer.getDeadCharacterSprite().setAppearance(3);
				level1View.repaint();
				break;
			case 3:
				characterPlayer.getDeadCharacterSprite().setAppearance(4);
				level1View.repaint();
				break;
			case 4:
				characterPlayer.getDeadCharacterSprite().setAppearance(5);
				level1View.repaint();
				break;
			case 5:
				characterPlayer.getDeadCharacterSprite().setAppearance(6);
				level1View.repaint();
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException i) {i.printStackTrace();}	
			aux++;
		}
	}
	
//	public void deadEnemyAnimation() {
//		level1View.getEnemy1().getDeadEnemySprite().setAppearance(0);
//		int aux = 0;
//		while (aux < 4) {
//			switch (level1View.getEnemy1().getDeadEnemySprite().getAppearance()) {
//				case 0:
//					level1View.getEnemy1().getDeadEnemySprite().setAppearance(1);
//					break;
//				case 1:
//					level1View.getEnemy1().getDeadEnemySprite().setAppearance(1);
//					break;
//				case 2:
//					level1View.getEnemy1().getDeadEnemySprite().setAppearance(1);
//					break;
//				case 3:
//					level1View.getEnemy1().getDeadEnemySprite().setAppearance(1);
//					break;
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException i) {i.printStackTrace();}	
//			aux++;
//		}
//	}
	
	private class EnemyMovement extends Thread{
		Enemy enemy;
		boolean collided;
		int dir, old_dir;
		int aux;
		public EnemyMovement(Enemy enemy) {
			this.enemy = enemy;
			this.dir = new Random().nextInt(4);
			this.aux = 0;
			this.old_dir = -1;
		}
		
		public void run() {
			synchronized (this) {
				while(true) {
					
					collided = moveEnemy(dir, aux, enemy);
					if(collided) {
						old_dir = dir;
						dir = new Random().nextInt(4);
						if (dir == 0) { 
							aux = 3;
							
						}
						if (dir == 1) { 
							aux = 2;
							
						}
						if (dir == 2) { 
							aux = 0;
							
						}
						if (dir == 3) { 
							aux = 1;
							
						}
					}else {
						if (dir == 0) { 
							aux += 4;
							if(aux > 11) {
								aux = 3;
							}
						}
						if (dir == 1) { 
							aux += 4;
							if(aux > 11) {
								aux = 2;
							}
						}
						if (dir == 2) { 
							aux += 4;
							if(aux > 11) {
								aux = 0;
							}
						}
						if (dir == 3) { 
							aux += 4;
							if(aux > 11) {
								aux = 1;
							}
						}
					}
					try {
						sleep(1000/10);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		}
		
	}
	
	private boolean moveEnemy(int dir, int aux, Enemy enemy) {
		boolean intersectsWallNorth = level1View.getBoundaries().get(0).intersects(enemy.getEnemySprite().getHitBox());
		boolean intersectsWallSouth = level1View.getBoundaries().get(1).intersects(enemy.getEnemySprite().getHitBox());
		boolean intersectsWallWest = level1View.getBoundaries().get(2).intersects(enemy.getEnemySprite().getHitBox());
		boolean intersectsWallEast = level1View.getBoundaries().get(3).intersects(enemy.getEnemySprite().getHitBox());

		if (dir == 0) {
			if (!intersectsWallNorth && !checkIntersectsCollision(enemy.getEnemySprite())) {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX(), enemy.getEnemySprite().getPosY() - enemy.getSpeed());
				enemy.getEnemySprite().setAppearance(aux);		
			} else {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX(), enemy.getEnemySprite().getPosY() + enemy.getSpeed());
				return true;
			}
		}
		if (dir == 1) {
			if (!intersectsWallWest && !checkIntersectsCollision(enemy.getEnemySprite())) {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX() - enemy.getSpeed(), enemy.getEnemySprite().getPosY());
				enemy.getEnemySprite().setAppearance(aux);		
			} else {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX() + enemy.getSpeed(), enemy.getEnemySprite().getPosY());
				return true;
			}
		}
		if(dir == 2) {
			if(!intersectsWallSouth && !checkIntersectsCollision(enemy.getEnemySprite()) ) {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX(), enemy.getEnemySprite().getPosY() + enemy.getSpeed());
				enemy.getEnemySprite().setAppearance(aux);		
			} else {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX(), enemy.getEnemySprite().getPosY() - enemy.getSpeed());
				return true;
			}		
		}
		if (dir == 3) {
			if (!intersectsWallEast && !checkIntersectsCollision(enemy.getEnemySprite())) {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX() + enemy.getSpeed(), enemy.getEnemySprite().getPosY());
				enemy.getEnemySprite().setAppearance(aux);		
			} else {
				enemy.getEnemySprite().atualizarPosicaoEnemy(enemy.getEnemySprite().getPosX() - enemy.getSpeed(), enemy.getEnemySprite().getPosY());
				return true;
			}
		}
		
		level1View.repaint();
		return false;
	}
	
}//end Fase1Controller


