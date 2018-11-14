package com.puzzle;

import java.net.MalformedURLException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sound class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

public class Sounder {

	public void playMainSound() throws MalformedURLException, InterruptedException {
		Media m = new Media(getClass().getResource("/Bossa Antigua.mp3").toString());		
			new MediaPlayer(m).play();			
		}

}
