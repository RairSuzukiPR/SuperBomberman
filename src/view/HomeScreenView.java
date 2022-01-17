package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HomeScreenView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage screen, homeScreenImg, chooser;
	private int chooserX = 138;
	private int chooserY = 325;
	ImageIcon icon;
	
	public HomeScreenView() {
		super();
		setSize(512, 480);
		setLocationRelativeTo(null);
		setLayout(null);
		setUndecorated(true);

		icon = new ImageIcon("imgs/icon.png");
		setIconImage(icon.getImage());
		
		screen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		try {
			homeScreenImg = ImageIO.read(new File("imgs/homeScreen.png"));
			chooser = ImageIO.read(new File("imgs/chooser.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Imagens da home screen nao foram carregadas. O jogo sera finalizado!");
			e.printStackTrace();
		}
		
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		screen.getGraphics().drawImage(homeScreenImg, 0, 0, this);
		screen.getGraphics().drawImage(chooser, chooserX, chooserY, this);
		
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.drawImage(screen, 0, 0, this);
	}

	public boolean displayJOptionPaneQuestion(String title, String message) {
		if(JOptionPane.showConfirmDialog(null, message, title,  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon)== JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	public BufferedImage getTela() {
		return screen;
	}

	public void setTela(BufferedImage screen) {
		this.screen = screen;
	}

	public int getChooserX() {
		return chooserX;
	}

	public void setChooserX(int chooserX) {
		this.chooserX = chooserX;
	}

	public int getChooserY() {
		return chooserY;
	}

	public void setChooserY(int chooserY) {
		this.chooserY = chooserY;
	}

	
	
	
	
	
}
