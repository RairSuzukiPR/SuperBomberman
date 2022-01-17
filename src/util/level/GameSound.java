package util.level;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GameSound {
	private AudioClip backgroundAudio, gameOverAudio, bombAudio, deathAudio, levelCompletedAudio;
	URL url;
	
	public GameSound(){
		
		try {
			this.backgroundAudio = Applet.newAudioClip(new File("audio/background.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			this.gameOverAudio = Applet.newAudioClip(new File("audio/gameOver.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			this.bombAudio = Applet.newAudioClip(new File("audio/bomb.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			this.deathAudio = Applet.newAudioClip(new File("audio/death.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			this.levelCompletedAudio = Applet.newAudioClip(new File("audio/levelCompleted.wav").toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void gameOverAudioPlay() {
		this.gameOverAudio.play();
	}
	
	public void gameOverAudioStop() {
		this.gameOverAudio.stop();
	}
	
	public void bombAudioPlay() {
		this.bombAudio.play();
	}
	
	public void bombAudioStop() {
		this.bombAudio.stop();
	}
	
	public void backgroundAudioPlay() {
		this.backgroundAudio.play();
	}
	
	public void backgroundAudioStop() {
		this.backgroundAudio.stop();
	}
	
	public void deathAudioPlay() {
		this.deathAudio.play();
	}
	
	public void deathAudioStop() {
		this.deathAudio.stop();
	}
	
	public void levelCompletedAudioPlay() {
		this.levelCompletedAudio.play();
	}
	
	public void levelCompletedAudioStop() {
		this.levelCompletedAudio.stop();
	}
}