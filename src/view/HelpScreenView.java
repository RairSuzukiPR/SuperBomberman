package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HelpScreenView extends JFrame{
	JLabel labelBackground;
	ImageIcon icon;
	
	public HelpScreenView() {
		setSize(512, 480);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		
		icon = new ImageIcon("imgs/HelpScreen.png");
		setIconImage(icon.getImage());
		
		labelBackground = new JLabel(new ImageIcon("imgs/helpScreen.png"));
		labelBackground.setBounds(0, 0, labelBackground.getIcon().getIconWidth(), labelBackground.getIcon().getIconHeight());
		
		add(labelBackground);
		
		setVisible(true);
	}
	
	public void displayJOptionPane(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
	}

}