package model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Camada {
	private int mapa[][];
	private BufferedImage camada;
	private BufferedImage tileSet;
	private int mapaWidth;
	private int mapaHeight;
	private int tileWidth;
	private int tileHeight;
	
	public Camada(String img, int mapaWidth, int mapaHeight, int tileWidth, int tileHeight, String arquivo) {

		this.mapaWidth = mapaWidth;
		this.mapaHeight = mapaHeight;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		mapa = new int [mapaWidth][mapaHeight];
		mapa = carregarMatriz(mapa, arquivo);
		
		try {
			tileSet = ImageIO.read(new File(img));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "erro no carregamento do tileset");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private int[][] carregarMatriz(int[][] matriz, String arquivo) {
		ArrayList<String> linhasMatrizCamada = new ArrayList<String>();
		InputStream is = getClass().getResourceAsStream(arquivo);
		
		//br e o arquivo que contem dos dados da martiz do cenario
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String linha = "";
		StringTokenizer token;
		
		try {
			while ((linha=br.readLine()) != null) {
				linhasMatrizCamada.add(linha);
			}
			
			int j=0;
			
			 for (int i=0 ; i< linhasMatrizCamada.size() ; i++) {
				 token = new StringTokenizer(linhasMatrizCamada.get(i), ",");
			 
				 while (token.hasMoreElements()) {
					 matriz[i][j] = Integer.parseInt(token.nextToken());
					 j++;
				 }
				 j = 0;
			 }
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro na leitura do arquivo");
			System.exit(0);
		}
		
		return matriz;
	}
	
	public void montarMapa (int largura, int altura){
		camada = new BufferedImage(largura, altura, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		camada.createGraphics();
		
		int tile;
		int tileRow;
		int tileCol;
		int colunasTileSet = tileSet.getWidth()/tileHeight;
		
		for (int i = 0; i < mapaWidth; i++) {
			for (int j = 0; j < mapaHeight; j++) {
				tile = (mapa[i][j] != 0) ? (mapa[i][j]-1) : 0;
				tileRow = (tile / (colunasTileSet)) | 0;
				tileCol = (tile % (colunasTileSet)) | 0;
				camada.getGraphics().drawImage(tileSet, (j * tileHeight), (i * tileWidth), (j * tileHeight) + tileHeight,
						(i * tileWidth) + tileWidth, (tileCol * tileHeight), (tileRow * tileWidth),
						(tileCol * tileHeight) + tileHeight, (tileRow * tileWidth) + tileWidth, null);
			}
		} 
		
	}

	public int[][] getMapa() {
		return mapa;
	}

	public void setMapa(int[][] mapa) {
		this.mapa = mapa;
	}

	public BufferedImage getCamada() {
		return camada;
	}

	public void setCamada(BufferedImage camada) {
		this.camada = camada;
	}

	public BufferedImage getTileSet() {
		return tileSet;
	}

	public void setTileSet(BufferedImage tileSet) {
		this.tileSet = tileSet;
	}

	public int getMapaWidth() {
		return mapaWidth;
	}

	public void setMapaWidth(int mapaWidth) {
		this.mapaWidth = mapaWidth;
	}

	public int getMapaHeight() {
		return mapaHeight;
	}

	public void setMapaHeight(int mapaHeight) {
		this.mapaHeight = mapaHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	
	
	
}
