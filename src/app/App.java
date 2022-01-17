package app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import controller.HomeScreenController;
import view.HomeScreenView;

public class App {
	/***
	 * OBS.:
	 * precisei lidar com rectangles no view para ter controle de onde seria exibido ou nao parte do fogo de bombas e tambem para ficar uma estrutura em todas as fases q herdarem de LevelView.
	 * Há um bug na hora de colocar a bomba quando muito encostado a direita de algum objeto, a bomba e colocada em cima de onde n deveria.
	 * Queria ter implementado um score, mas n deu tempo.
	 * No mais tudo funciona como deveria.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new HomeScreenController(new HomeScreenView()).control();
	}
}

