package view;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Camada;
import model.CharacterPlayer;
import model.Enemy;
import model.Sprite;

public abstract class LevelView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CharacterPlayer character1, character2;
	Sprite timeFirstDigit, timeSecondDigit, timeThirdDigit, twoDotsDigit, totemSprite, livesSprite;
	Camada layer1;
	ImageIcon icon;
	boolean multiplayer, putBombP1, putBombP2, bombExplodedP1, bombExplodedP2;// deadP1, deadP2;
	int bombXP1, bombYP1, bombXP2, bombYP2;
	int totems;
	
	ArrayList<Rectangle> boundaries = new ArrayList<Rectangle>();
	ArrayList<Rectangle> fixedBlocks = new ArrayList<Rectangle>(); 
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<BufferedImage> destructibleBlocksBufferedImages = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> totemBufferedImages = new ArrayList<BufferedImage>();
	ArrayList<Rectangle> destructibleBlocksRectangles = new ArrayList<Rectangle>();
	ArrayList<Rectangle> totemRectangles = new ArrayList<Rectangle>();
	ArrayList<Rectangle> explosionRectanglesP1 = new ArrayList<Rectangle>();
	ArrayList<Rectangle> explosionRectanglesP2 = new ArrayList<Rectangle>();
	
	public LevelView() {
		super();
		setSize(960, 896);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		
		icon = new ImageIcon("imgs/icon.png");
		setIconImage(icon.getImage());
		
		timeFirstDigit = new Sprite(new File("imgs/scoreNumbers.png"), 2, 11, 1, 546, 14);
		twoDotsDigit = new Sprite(new File("imgs/scoreNumbers.png"), 10, 11, 1, 574, 14);
		timeSecondDigit = new Sprite(new File("imgs/scoreNumbers.png"), 5, 11, 1, 602, 14);
		timeThirdDigit = new Sprite(new File("imgs/scoreNumbers.png"), 9, 11, 1, 630, 14);
		
		character1 = new CharacterPlayer(new Sprite(new File("imgs/bombermanP11ws.png"), 0, 3, 4, 63, 93));
		character1.getCharacterSprite().atualizarPosicao(60, 94);
		
		livesSprite = new Sprite(new File("imgs/scoreNumbers.png"), character1.getLives(), 11, 1, 250, 15);
		
		//offsets: xInicial = +12, yInicial= +13 , width = -24 , height = 13
		boundaries.add(new Rectangle(0, 0, 960, 92)); //wall n
		boundaries.add(new Rectangle(64, 845, 960, 64)); //wall s
		boundaries.add(new Rectangle(0, 128, 57, 896)); //wall w
		boundaries.add(new Rectangle(900, 128, 58, 768)); //wall e
		fixedBlocks.add(new Rectangle(140, 205, 40, 13)); //0
		fixedBlocks.add(new Rectangle(268, 205, 40, 13)); //1
		fixedBlocks.add(new Rectangle(396, 205, 40, 13)); //2
		fixedBlocks.add(new Rectangle(524, 205, 40, 13)); //3
		fixedBlocks.add(new Rectangle(652, 205, 40, 13)); //4
		fixedBlocks.add(new Rectangle(780, 205, 40, 13)); //5
		fixedBlocks.add(new Rectangle(140, 333, 40, 13)); //6
		fixedBlocks.add(new Rectangle(268, 333, 40, 13)); //7
		fixedBlocks.add(new Rectangle(396, 333, 40, 13)); //8
		fixedBlocks.add(new Rectangle(524, 333, 40, 13)); //9
		fixedBlocks.add(new Rectangle(652, 333, 40, 13)); //10
		fixedBlocks.add(new Rectangle(780, 333, 40, 13)); //11
		fixedBlocks.add(new Rectangle(140, 461, 40, 13)); //12
		fixedBlocks.add(new Rectangle(268, 461, 40, 13)); //13
		fixedBlocks.add(new Rectangle(396, 461, 40, 13)); //14
		fixedBlocks.add(new Rectangle(524, 461, 40, 13)); //15
		fixedBlocks.add(new Rectangle(652, 461, 40, 13)); //16
		fixedBlocks.add(new Rectangle(780, 461, 40, 13)); //17
		fixedBlocks.add(new Rectangle(140, 589, 40, 13)); //18
		fixedBlocks.add(new Rectangle(268, 589, 40, 13)); //19
		fixedBlocks.add(new Rectangle(396, 589, 40, 13)); //20
		fixedBlocks.add(new Rectangle(524, 589, 40, 13)); //21
		fixedBlocks.add(new Rectangle(652, 589, 40, 13)); //22
		fixedBlocks.add(new Rectangle(780, 589, 40, 13)); //23
		fixedBlocks.add(new Rectangle(140, 717, 40, 13)); //24
		fixedBlocks.add(new Rectangle(268, 717, 40, 13)); //25
		fixedBlocks.add(new Rectangle(396, 717, 40, 13)); //26
		fixedBlocks.add(new Rectangle(524, 717, 40, 13)); //27
		fixedBlocks.add(new Rectangle(652, 717, 40, 13)); //28
		fixedBlocks.add(new Rectangle(780, 717, 40, 13)); //29
	}
	
	public void generateDestructibleBlocksRectangles(Camada camada, String item) {
		//offsets: xInicial = +12, yInicial= +13 , width = -24 , height = 13
		for (String posXY : (findXYofItems(camada, item))) {
			destructibleBlocksRectangles.add(new Rectangle(Integer.parseInt(posXY.split(",")[0])+12, Integer.parseInt(posXY.split(",")[1])+13, 40, 13 ) );
		}
	}
	
	public void generateDestrucBlocksBufferedImages(Camada camada, String item) { 
		for (int i = 0 ; i < findXYofItems(camada, item).size() ; i++) {
			try {
				destructibleBlocksBufferedImages.add(ImageIO.read(new File("imgs/oneBlockTile.png")));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro na adicao automatica de imagens, blocos. O jogo sera finalizado!");
				e.printStackTrace();
			}
		}
	}
	
	public void generateTotemsRectangles(Camada camada, String item) {
		for (String posXY : (findXYofItems(camada, item))) {
			totemRectangles.add(new Rectangle(Integer.parseInt(posXY.split(",")[0])+12, Integer.parseInt(posXY.split(",")[1])+13, 40, 13 ) );
		}
	}
	
	public void generateTotemsBufferedImages(Camada camada, String item) {
		for (int i = 0 ; i < findXYofItems(camada, item).size() ; i++) {
			try {
				totemBufferedImages.add(ImageIO.read(new File("imgs/totemTile.png")));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro na adicao automatica de imagens, totems. O jogo sera finalizado!");
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> findXYofItems(Camada layer, String item) { 
		ArrayList<String> itemsCoordinates = new ArrayList<String>();
		String line = "";
		int x0 = 0;
		int y0 = 0;
		int aux = 14;
		
		for (int i = 0; i < layer.getMapaWidth(); i++) {
			for (int j = 0; j < layer.getMapaHeight(); j++) {
				line += Integer.toString(layer.getMapa()[i][j]);
			}
		}
		
		for (int j = 0 ; j <line.length() ; j++) {
			if (line.charAt(j) == item.charAt(0)) {
				itemsCoordinates.add(x0 + "," + y0);
			}
			x0 += 64;
			if (j == aux) {
				y0 += 64;
				x0 = 0;
				aux += 15;
			}			
		}
		return itemsCoordinates; 
	}
	
	public void generateExplosionRectangles(int playerNumber) { 
		//offsets: xInicial = +12, yInicial= +13 , width = -24 , height = 13
		if (playerNumber == 1) {
			explosionRectanglesP1.add(new Rectangle(bombXP1+12, bombYP1+13, 40, 13)); //central
			explosionRectanglesP1.add(new Rectangle(bombXP1+12, bombYP1-64+13, 40, 13)); //sup
			explosionRectanglesP1.add(new Rectangle(bombXP1+12, bombYP1+64+13, 40, 13)); //inf
			explosionRectanglesP1.add(new Rectangle(bombXP1-64+12, bombYP1+13, 40, 13)); //esq
			explosionRectanglesP1.add(new Rectangle(bombXP1+64+12, bombYP1+13, 40, 13)); //dir
		} else if (playerNumber == 2) {
			explosionRectanglesP2.add(new Rectangle(bombXP2+12, bombYP2+13, 40, 13)); //central
			explosionRectanglesP2.add(new Rectangle(bombXP2+12, bombYP2-64+13, 40, 13)); //sup
			explosionRectanglesP2.add(new Rectangle(bombXP2+12, bombYP2+64+13, 40, 13)); //inf
			explosionRectanglesP2.add(new Rectangle(bombXP2-64+12, bombYP2+13, 40, 13)); //esq
			explosionRectanglesP2.add(new Rectangle(bombXP2+64+12, bombYP2+13, 40, 13)); //dir
		}
		
	}
	
	public void resetExplosionRectangles(int playerNumber) { 
		if (playerNumber == 1) {
			explosionRectanglesP1 = new ArrayList<Rectangle>();	
		} else if (playerNumber == 2) {
			explosionRectanglesP2 = new ArrayList<Rectangle>();	
		}
		
	}
	
	public boolean checkFireCollision(Rectangle e) {
		boolean aux = false;
		ArrayList<Integer> destrucBlocksIndexesToBeRemoved = new ArrayList<Integer>();
		ArrayList<Integer> totemsIndexesToBeRemoved = new ArrayList<Integer>();
		
		for (Rectangle b: boundaries) {
			if (e.intersects(b)) aux = true;
		}
			
		for (Rectangle f: fixedBlocks) {
			if (e.intersects(f)) aux = true;
		}
		
		for (Rectangle d: destructibleBlocksRectangles) {
			if (e.intersects(d)) {
				aux = true;
				destrucBlocksIndexesToBeRemoved.add(destructibleBlocksRectangles.indexOf(d));
			}
		}
		
		for (Rectangle t: totemRectangles) {
			if (e.intersects(t)) {
				aux = true;
				totemsIndexesToBeRemoved.add(totemRectangles.indexOf(t));
			}
		}
		
		if(destrucBlocksIndexesToBeRemoved.size() > 0) {
			for (int i: destrucBlocksIndexesToBeRemoved) {
				destructibleBlocksRectangles.remove(i);
				destructibleBlocksBufferedImages.remove(i);
			}
		}
		
		if(totemsIndexesToBeRemoved.size() > 0) {
			for (int j: totemsIndexesToBeRemoved) {
				System.out.println("J = " +j);
				totemRectangles.remove(j);
				totemBufferedImages.remove(totemBufferedImages.size()-1);
				totems--;
			}
		}
		
		return aux; 
	}
	
	public void displayJOptionPane(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, getIcon());
	}
	
	public boolean displayJOptionPaneQuestion(String title, String message) {
		if(JOptionPane.showConfirmDialog(null, message, title,  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, getIcon())== JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public CharacterPlayer getCharacter1() {
		return character1;
	}

	public void setCharacter1(CharacterPlayer character1) {
		this.character1 = character1;
	}

	public CharacterPlayer getCharacter2() {
		return character2;
	}

	public void setCharacter2(CharacterPlayer character2) {
		this.character2 = character2;
	}

	public Camada getLayer1() {
		return layer1;
	}


	public void setLayer1(Camada layer1) {
		this.layer1 = layer1;
	}


	public ArrayList<Rectangle> getFixedBlocks() {
		return fixedBlocks;
	}


	public void setFixedBlocks(ArrayList<Rectangle> fixedBlocks) {
		this.fixedBlocks = fixedBlocks;
	}


	public ArrayList<Rectangle> getBoundaries() {
		return boundaries;
	}


	public void setBoundaries(ArrayList<Rectangle> boundaries) {
		this.boundaries = boundaries;
	}

	public boolean isPutBombP1() {
		return putBombP1;
	}

	public void setPutBombP1(boolean putBombP1) {
		this.putBombP1 = putBombP1;
	}
	
	public boolean isPutBombP2() {
		return putBombP2;
	}

	public void setPutBombP2(boolean putBombP2) {
		this.putBombP2 = putBombP2;
	}

	public boolean isBombExplodedP1() {
		return bombExplodedP1;
	}

	public void setBombExplodedP1(boolean bombExploded) {
		this.bombExplodedP1 = bombExploded;
	}

//	public boolean isDeadP1() {
//		return deadP1;
//	}
//
//	public void setDeadP1(boolean deadP1) {
//		this.deadP1 = deadP1;
//	}

	public int getBombXP1() {
		return bombXP1;
	}

	public void setBombXP1(int bombXP1) {
		this.bombXP1 = bombXP1;
	}

	public int getBombYP1() {
		return bombYP1;
	}

	public void setBombYP1(int bombYP1) {
		this.bombYP1 = bombYP1;
	}

	public int getTotems() {
		return totems;
	}

	public void setTotems(int totems) {
		this.totems = totems;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<BufferedImage> getDestructibleBlocksBufferedImages() {
		return destructibleBlocksBufferedImages;
	}

	public void setDestructibleBlocksBufferedImages(ArrayList<BufferedImage> destructibleBlocksBufferedImages) {
		this.destructibleBlocksBufferedImages = destructibleBlocksBufferedImages;
	}

	public ArrayList<BufferedImage> getTotemBufferedImages() {
		return totemBufferedImages;
	}

	public void setTotemBufferedImages(ArrayList<BufferedImage> totemBufferedImages) {
		this.totemBufferedImages = totemBufferedImages;
	}

	public ArrayList<Rectangle> getDestructibleBlocksRectangles() {
		return destructibleBlocksRectangles;
	}

	public void setDestructibleBlocksRectangles(ArrayList<Rectangle> destructibleBlocksRectangles) {
		this.destructibleBlocksRectangles = destructibleBlocksRectangles;
	}

	public ArrayList<Rectangle> getTotemRectangles() {
		return totemRectangles;
	}

	public void setTotemRectangles(ArrayList<Rectangle> totemRectangles) {
		this.totemRectangles = totemRectangles;
	}

	public ArrayList<Rectangle> getExplosionRectanglesP1() {
		return explosionRectanglesP1;
	}

	public void setExplosionRectanglesP1(ArrayList<Rectangle> explosionRectanglesP1) {
		this.explosionRectanglesP1 = explosionRectanglesP1;
	}
	
	public ArrayList<Rectangle> getExplosionRectanglesP2() {
		return explosionRectanglesP2;
	}

	public void setExplosionRectanglesP2(ArrayList<Rectangle> explosionRectanglesP2) {
		this.explosionRectanglesP2 = explosionRectanglesP2;
	}
	
	public boolean isBombExplodedP2() {
		return bombExplodedP2;
	}

	public void setBombExplodedP2(boolean bombExplodedP2) {
		this.bombExplodedP2 = bombExplodedP2;
	}

	public int getBombXP2() {
		return bombXP2;
	}

	public void setBombXP2(int bombXP2) {
		this.bombXP2 = bombXP2;
	}

	public int getBombYP2() {
		return bombYP2;
	}

	public void setBombYP2(int bombYP2) {
		this.bombYP2 = bombYP2;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}
	
	public Sprite getTimeFirstDigit() {
		return timeFirstDigit;
	}

	public void setTimeFirstDigit(Sprite timeFirstDigit) {
		this.timeFirstDigit = timeFirstDigit;
	}

	public Sprite getTimeSecondDigit() {
		return timeSecondDigit;
	}

	public void setTimeSecondDigit(Sprite timeSecondDigit) {
		this.timeSecondDigit = timeSecondDigit;
	}

	public Sprite getTimeThirdDigit() {
		return timeThirdDigit;
	}

	public void setTimeThirdDigit(Sprite timeThirdDigit) {
		this.timeThirdDigit = timeThirdDigit;
	}

	public Sprite getTwoDotsDigit() {
		return twoDotsDigit;
	}

	public void setTwoDotsDigit(Sprite twoDotsDigit) {
		this.twoDotsDigit = twoDotsDigit;
	}

	public Sprite getTotemSprite() {
		return totemSprite;
	}

	public void setTotemSprite(Sprite totemSprite) {
		this.totemSprite = totemSprite;
	}
	
}
