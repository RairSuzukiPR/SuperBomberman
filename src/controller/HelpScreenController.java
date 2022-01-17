package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.HelpScreenView;

public class HelpScreenController {
	HelpScreenView helpScreenView;

	public HelpScreenController(HelpScreenView helpScreenView) {
		this.helpScreenView = helpScreenView;
	}
	
	public void control() {
		helpScreenView.addKeyListener(new KeyboardHandler());
		helpScreenView.addMouseListener(new MouseHandler());
	}
	
	public class KeyboardHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_ESCAPE) {
				helpScreenView.setVisible(false);
			}
		}
	}
	
	public class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if ( ((e.getX() >= 221 && e.getX() <= 298)) && ((e.getY() >= 428 && e.getY() <= 450)) ) {
				helpScreenView.setVisible(false);
			}
			
		}
	}
}
