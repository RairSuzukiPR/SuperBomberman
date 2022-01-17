package controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import view.HelpScreenView;
import view.HomeScreenView;
import view.Level1View;


public class HomeScreenController{
	HomeScreenView homeScreenView;
	int aux = 0;
	boolean singlePlayer = false;
	
	public HomeScreenController(HomeScreenView homeScreenView) {
		this.homeScreenView = homeScreenView;
	}
	
	public void control() { 
		homeScreenView.addKeyListener(new KeyboardHandler());
		homeScreenView.addMouseListener(new MouseHandler());
		homeScreenView.addMouseMotionListener(new MouseHandler());
	}
	
	public class KeyboardHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
				if(homeScreenView.displayJOptionPaneQuestion("Sair", "Deseja encerrar Super Bomberman?")) {
					System.exit(0);
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_UP) {
				if (aux == 0) {
					homeScreenView.setChooserX(138);
					homeScreenView.setChooserY(325);
					homeScreenView.repaint();
				}else if (aux == 1) {
					aux--;
					homeScreenView.setChooserX(138);
					homeScreenView.setChooserY(325);
					homeScreenView.repaint();
				}else if (aux == 2) {
					aux--;
					homeScreenView.setChooserX(150);
					homeScreenView.setChooserY(352);
					homeScreenView.repaint();
				}else if (aux == 3) {
					aux--;
					homeScreenView.setChooserX(215);
					homeScreenView.setChooserY(380);
					homeScreenView.repaint();
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				if (aux == 0) {
					aux++;
					homeScreenView.setChooserX(150);
					homeScreenView.setChooserY(352);
					homeScreenView.repaint();
				}else if (aux == 1) {
					aux++;
					homeScreenView.setChooserX(215);
					homeScreenView.setChooserY(380);
					homeScreenView.repaint();
				}else if (aux == 2) {
					aux++;
					homeScreenView.setChooserX(218);
					homeScreenView.setChooserY(405);
					homeScreenView.repaint();
				}else if (aux == 3) {
					homeScreenView.setChooserX(218);
					homeScreenView.setChooserY(405);
					homeScreenView.repaint();
					homeScreenView.repaint();
				}
			}
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				if (aux == 0) {
					System.out.println("SinglePlayer"); 
					homeScreenView.setVisible(false);
					new Level1Controller(new Level1View(false), false).control();
				}else if (aux == 1) {
					System.out.println("MultiplayerPlayer");
					homeScreenView.setVisible(false);
					new Level1Controller(new Level1View(true), true).control();
				}else if (aux == 2) {
					System.out.println("HELP");
					new HelpScreenController(new HelpScreenView()).control();
				}else if (aux == 3) {
					System.out.println("EXIT");
					if(homeScreenView.displayJOptionPaneQuestion("Sair", "Deseja encerrar Super Bomberman?")) {
						System.exit(0);	
					}
				}
			}

		}
	}
	
	public class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if ( ((e.getX() >= 150 && e.getX() <= 392)) && ((e.getY() >= 325 && e.getY() <= 341)) ) { 
				System.out.println("SinglePlayer");  
				homeScreenView.setVisible(false);
				new Level1Controller(new Level1View(false), false).control();
			}
			if ( ((e.getX() >= 170 && e.getX() <= 377)) && ((e.getY() >= 350 && e.getY() <= 368)) ) { 
				System.out.println("MultiplayerPlayer");
				homeScreenView.setVisible(false);
				new Level1Controller(new Level1View(true), true).control();
			}
			if ( ((e.getX() >= 234 && e.getX() <= 312)) && ((e.getY() >= 378 && e.getY() <= 395)) ) { 
				System.out.println("HELP");
				new HelpScreenController(new HelpScreenView()).control();
			}
			if ( ((e.getX() >= 238 && e.getX() <= 308)) && ((e.getY() >= 405 && e.getY() <= 421)) ) { 
				System.out.println("EXIT");
				if(homeScreenView.displayJOptionPaneQuestion("Sair", "Deseja encerrar Super Bomberman?")) {
					System.exit(0);
				}
			}
		}
		public void mouseMoved(MouseEvent e){
			if ( ((e.getX() >= 150 && e.getX() <= 392)) && ((e.getY() >= 325 && e.getY() <= 341)) ) { 
				aux = 0;
				homeScreenView.setChooserX(138);
				homeScreenView.setChooserY(325);
				homeScreenView.repaint();
			}
			if ( ((e.getX() >= 170 && e.getX() <= 377)) && ((e.getY() >= 350 && e.getY() <= 368)) ) { 
				aux = 1;
				homeScreenView.setChooserX(150);
				homeScreenView.setChooserY(352);
				homeScreenView.repaint();
			}
			if ( ((e.getX() >= 234 && e.getX() <= 312)) && ((e.getY() >= 378 && e.getY() <= 395)) ) { 
				aux = 2;
				homeScreenView.setChooserX(215);
				homeScreenView.setChooserY(380);
				homeScreenView.repaint();
			}
			if ( ((e.getX() >= 238 && e.getX() <= 308)) && ((e.getY() >= 405 && e.getY() <= 421)) ) { 
				aux = 3;
				homeScreenView.setChooserX(218);
				homeScreenView.setChooserY(405);
				homeScreenView.repaint();

			}
		}
	}	
	
} //end HomeScreenController

