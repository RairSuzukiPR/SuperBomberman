package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import model.Camada;
import model.CharacterPlayer;
import model.Enemy;
import model.Sprite;

public class Level2View extends LevelView{
	Camada layerAux;
	BufferedImage screen;
	
	public Level2View(boolean multiplayer) {
		this.multiplayer = multiplayer;
		screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		
		if(multiplayer) {
			layer1 = new Camada("imgs/mapTiles2.png", 15, 15, 64, 64, "/util/level/layerMultiPlayer.txt");	
		} else {
			layer1 = new Camada("imgs/mapTiles2.png", 15, 15, 64, 64, "/util/level/layerSinglePlayer.txt");	
		}
		
		layerAux = new Camada("imgs/oneBlockTile.png", 15, 15, 64, 64, "/util/level2/layerAux.txt"); 
		
		character2 = new CharacterPlayer(new Sprite(new File("imgs/bombermanP22ws.png"), 0, 3, 4, 830, 720));
		character2.getCharacterSprite().atualizarPosicao(825, 740); 
		
		enemies.add(new Enemy(new Sprite(new File("imgs/enemy2.png"), 0, 3, 4, 576, 246), 5)); 
		enemies.add(new Enemy(new Sprite(new File("imgs/enemy2.png"), 0, 3, 4, 320, 460), 5));
		
		layer1.montarMapa(this.getWidth(), this.getHeight());
		
		generateDestructibleBlocksRectangles(layerAux, "1"); 
		generateDestrucBlocksBufferedImages(layerAux, "1");
		
		generateTotemsRectangles(layerAux, "2");
		generateTotemsBufferedImages(layerAux, "2");
		totems = totemRectangles.size();
		totemSprite = new Sprite(new File("imgs/scoreNumbers.png"), totems, 11, 1, 77, 15);
		
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		screen.getGraphics().drawImage(layer1.getCamada(), 0, 0, null);
		
		screen.getGraphics().drawImage(timeFirstDigit.getSprites()[timeFirstDigit.getAppearance()], timeFirstDigit.getPosX(), timeFirstDigit.getPosY(), null);
		screen.getGraphics().drawImage(twoDotsDigit.getSprites()[twoDotsDigit.getAppearance()], twoDotsDigit.getPosX(), twoDotsDigit.getPosY(), null);
		screen.getGraphics().drawImage(timeSecondDigit.getSprites()[timeSecondDigit.getAppearance()], timeSecondDigit.getPosX(), timeSecondDigit.getPosY(), null);
		screen.getGraphics().drawImage(timeThirdDigit.getSprites()[timeThirdDigit.getAppearance()], timeThirdDigit.getPosX(), timeThirdDigit.getPosY(), null);
		
		screen.getGraphics().drawImage(totemSprite.getSprites()[totems], totemSprite.getPosX(), totemSprite.getPosY(), null);
		
		if (character1.getLives() == 0 || character2.getLives() == 0) {
			screen.getGraphics().drawImage(livesSprite.getSprites()[0], livesSprite.getPosX(), livesSprite.getPosY(), null);
		} else {
			screen.getGraphics().drawImage(livesSprite.getSprites()[character1.getLives()], livesSprite.getPosX(), livesSprite.getPosY(), null);	
		}
		
		for (BufferedImage bI : destructibleBlocksBufferedImages) {
			int posX = (int) destructibleBlocksRectangles.get(destructibleBlocksBufferedImages.indexOf(bI)).getX() -12; //offsets
			int posY = (int) destructibleBlocksRectangles.get(destructibleBlocksBufferedImages.indexOf(bI)).getY() -13; //offsets
			screen.getGraphics().drawImage(bI, posX, posY, null);
		}

		for (BufferedImage bI2 : totemBufferedImages) {
			int posX2 = (int) totemRectangles.get(totemBufferedImages.indexOf(bI2)).getX() -12; //offsets
			int posY2 = (int) totemRectangles.get(totemBufferedImages.indexOf(bI2)).getY() -13; //offsets
			screen.getGraphics().drawImage(bI2, posX2, posY2, null);
		}
		
		for (Enemy e: enemies) {
			screen.getGraphics().drawImage(e.getEnemySprite().getSprites()[e.getEnemySprite().getAppearance()], e.getEnemySprite().getPosX(), e.getEnemySprite().getPosY(), null);	
		}
		
		if (putBombP1) {
			screen.getGraphics().drawImage(character1.getBombSprite().getSprites()[character1.getBombSprite().getAppearance()], bombXP1, bombYP1, null );
			if (bombExplodedP1) { 
				if (!checkFireCollision(explosionRectanglesP1.get(1)))
					screen.getGraphics().drawImage(character1.getBombSpriteUpperEnd().getSprites()[character1.getBombSpriteUpperEnd().getAppearance()], bombXP1, bombYP1-64, null );
				if (!checkFireCollision(explosionRectanglesP1.get(2)))
					screen.getGraphics().drawImage(character1.getBombSpriteBottomEnd().getSprites()[character1.getBombSpriteBottomEnd().getAppearance()], bombXP1, bombYP1+64, null );
				if (!checkFireCollision(explosionRectanglesP1.get(3)))
					screen.getGraphics().drawImage(character1.getBombSpriteLeftEnd().getSprites()[character1.getBombSpriteLeftEnd().getAppearance()], bombXP1-64, bombYP1, null );
				if (!checkFireCollision(explosionRectanglesP1.get(4)))
					screen.getGraphics().drawImage(character1.getBombSpriteRightEnd().getSprites()[character1.getBombSpriteRightEnd().getAppearance()], bombXP1+64, bombYP1, null );
			}
						
		}
		
		if (character1.getLives() == 0) { 
			screen.getGraphics().drawImage(character1.getDeadCharacterSprite().getSprites()[character1.getDeadCharacterSprite().getAppearance()], character1.getCharacterSprite().getPosX(), character1.getCharacterSprite().getPosY(), null);
		} else {
			screen.getGraphics().drawImage(character1.getCharacterSprite().getSprites()[character1.getCharacterSprite().getAppearance()], character1.getCharacterSprite().getPosX(), character1.getCharacterSprite().getPosY(), null);
		}
		
		if (multiplayer) {
			if (putBombP2) {
				screen.getGraphics().drawImage(character2.getBombSprite().getSprites()[character2.getBombSprite().getAppearance()], bombXP2, bombYP2, null );
				if (bombExplodedP2) { 
					if (!checkFireCollision(explosionRectanglesP2.get(1)))
						screen.getGraphics().drawImage(character2.getBombSpriteUpperEnd().getSprites()[character2.getBombSpriteUpperEnd().getAppearance()], bombXP2, bombYP2-64, null );
					if (!checkFireCollision(explosionRectanglesP2.get(2)))
						screen.getGraphics().drawImage(character2.getBombSpriteBottomEnd().getSprites()[character2.getBombSpriteBottomEnd().getAppearance()], bombXP2, bombYP2+64, null );
					if (!checkFireCollision(explosionRectanglesP2.get(3)))
						screen.getGraphics().drawImage(character2.getBombSpriteLeftEnd().getSprites()[character2.getBombSpriteLeftEnd().getAppearance()], bombXP2-64, bombYP2, null );
					if (!checkFireCollision(explosionRectanglesP2.get(4)))
						screen.getGraphics().drawImage(character2.getBombSpriteRightEnd().getSprites()[character2.getBombSpriteRightEnd().getAppearance()], bombXP2+64, bombYP2, null );
				}
				
							
			}
		}
		
		if (multiplayer) {
			if (character2.getLives() == 0) { 
				screen.getGraphics().drawImage(character2.getDeadCharacterSprite().getSprites()[character2.getDeadCharacterSprite().getAppearance()], character2.getCharacterSprite().getPosX(), character2.getCharacterSprite().getPosY(), null);
			} else {
				screen.getGraphics().drawImage(character2.getCharacterSprite().getSprites()[character2.getCharacterSprite().getAppearance()], character2.getCharacterSprite().getPosX(), character2.getCharacterSprite().getPosY(), null);
			}
			
		}
		
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		g2d.drawImage(screen, 0, 0, null);
		
	}
	
	
} //end Level2View
