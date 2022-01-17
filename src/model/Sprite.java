package model;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Sprite {
	private BufferedImage spriteSheet;
	private int width, height;
	private int rows, colums;
	private int posX, posY;
	private BufferedImage [] sprites;
	private int appearance;
	private Rectangle hitBox;
	
	
	public Sprite(File file, int appearance, int colums, int rows, int posX, int posY) {
		try {
			spriteSheet = ImageIO.read(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Imagem sprite nao encontrada");
			e.printStackTrace();
			System.exit(0);
		}
		
		this.width = spriteSheet.getWidth()/colums;
		this.height = spriteSheet.getHeight()/rows;
		this.rows = rows; 
		this.colums = colums;
		this.posX = posX;
		this.posY = posY;
		this.appearance = appearance;
		this.hitBox = new Rectangle(this.width, this.height);
		
		
		sprites = new BufferedImage[colums*rows];
		
		for (int i = 0 ; i<colums ; i++) {
			for (int j = 0 ; j<rows ; j++){
				sprites [(i*rows) +j] = spriteSheet.getSubimage(i*width, j*height, width, height);
			}
		}
	}
	
	public void atualizarPosicao(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.hitBox.x = posX;
		this.hitBox.y = posY;
	}
	
	public void atualizarPosicaoEnemy(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.hitBox = new Rectangle(this.width, this.height-15);
		this.hitBox.x = posX;
		this.hitBox.y = posY+15;
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColums() {
		return colums;
	}

	public void setColums(int colums) {
		this.colums = colums;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public BufferedImage[] getSprites() {
		return sprites;
	}

	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}

	public int getAppearance() {
		return appearance;
	}

	public void setAppearance(int appearance) {
		this.appearance = appearance;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}


	
	
	
	
}
