package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameOverView extends JFrame{
	BufferedImage screen, background;
	
	public GameOverView() {
		setSize(512, 448);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		
		screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		try {
			background = ImageIO.read(new File("imgs/SinglePlayerGameOver.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		setVisible(false);
	}
	
	public void paint(Graphics g) {
		screen.getGraphics().drawImage(background, 0, 0, null);
		
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		g2d.drawImage(screen, 0, 0, null);
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}
	
	
}