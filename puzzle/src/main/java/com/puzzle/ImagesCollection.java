package com.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that creates the collections of images.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

class ImagesCollection extends ImageView {
	private static final int numOfPairs = 7;
	private Image birdImage1 = new Image(getClass().getResourceAsStream("/p1.jpg"));
	private Image birdImage2 = new Image(getClass().getResourceAsStream("/p2.jpg"));
	private Image birdImage3 = new Image(getClass().getResourceAsStream("/p3.jpg"));
	private Image birdImage4 = new Image(getClass().getResourceAsStream("/p4.jpg"));
	private Image birdImage5 = new Image(getClass().getResourceAsStream("/p5.jpg"));
	private Image birdImage6 = new Image(getClass().getResourceAsStream("/p6.jpg"));
	private Image birdImage7 = new Image(getClass().getResourceAsStream("/p7.jpg"));
	private Image birdImage8 = new Image(getClass().getResourceAsStream("/p8.jpg"));
	private Image birdImage9 = new Image(getClass().getResourceAsStream("/p9.jpg"));
	private Image birdImage10 = new Image(getClass().getResourceAsStream("/p10.jpg"));
	private Image seaImage1 = new Image(getClass().getResourceAsStream("/s1.jpg"));
	private Image seaImage2 = new Image(getClass().getResourceAsStream("/s2.jpg"));
	private Image seaImage3 = new Image(getClass().getResourceAsStream("/s3.png"));
	private Image seaImage4 = new Image(getClass().getResourceAsStream("/s4.jpg"));
	private Image seaImage5 = new Image(getClass().getResourceAsStream("/s5.jpg"));
	private Image seaImage6 = new Image(getClass().getResourceAsStream("/s6.jpg"));
	private Image seaImage7 = new Image(getClass().getResourceAsStream("/s7.jpg"));
	private Image seaImage8 = new Image(getClass().getResourceAsStream("/s8.jpg"));
	private Image seaImage9 = new Image(getClass().getResourceAsStream("/s9.jpg"));
	private Image seaImage10 = new Image(getClass().getResourceAsStream("/s10.jpg"));
	private Image treasureImage1 = new Image(getClass().getResourceAsStream("/t1.jpg"));
	private Image treasureImage2 = new Image(getClass().getResourceAsStream("/t2.jpg"));
	private Image treasureImage3 = new Image(getClass().getResourceAsStream("/t3.jpg"));
	private Image treasureImage4 = new Image(getClass().getResourceAsStream("/t4.jpg"));
	private Image treasureImage5 = new Image(getClass().getResourceAsStream("/t5.gif"));
	private Image treasureImage6 = new Image(getClass().getResourceAsStream("/t6.jpg"));
	private Image treasureImage7 = new Image(getClass().getResourceAsStream("/t7.jpg"));
	private Image treasureImage8 = new Image(getClass().getResourceAsStream("/t8.png"));
	private Image treasureImage9 = new Image(getClass().getResourceAsStream("/t9.jpg"));
	private Image treasureImage10 = new Image(getClass().getResourceAsStream("/t10.jpg"));
	private Image imageBirds = new Image(getClass().getResourceAsStream("/coverBirds.jpg"));
	private Image imageSea = new Image(getClass().getResourceAsStream("/seaCover.jpg"));
	private Image imageTreasures = new Image(getClass().getResourceAsStream("/treasureCover.jpg"));
	
	public ImagesCollection() {
	}

	public List<Image> createBirdsCollection() {

		List<Image> birds = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs; i++) {
			birds.add(birdImage1);
			birds.add(birdImage2);
			birds.add(birdImage3);
			birds.add(birdImage4);
			birds.add(birdImage5);
			birds.add(birdImage6);
			birds.add(birdImage7);
			birds.add(birdImage8);
			birds.add(birdImage9);
			birds.add(birdImage10);
		}
		Collections.shuffle(birds);
		return birds;

	}

	public List<Image> createSeaCollection() {
		List<Image> sea = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs; i++) {
			sea.add(seaImage1);
			sea.add(seaImage2);
			sea.add(seaImage3);
			sea.add(seaImage4);
			sea.add(seaImage5);
			sea.add(seaImage6);
			sea.add(seaImage7);
			sea.add(seaImage8);
			sea.add(seaImage9);
			sea.add(seaImage10);
		}
		Collections.shuffle(sea);
		return sea;
	}

	public List<Image> createTreasureCollection() {
		List<Image> treasure = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs; i++) {
			treasure.add(treasureImage1);
			treasure.add(treasureImage2);
			treasure.add(treasureImage3);
			treasure.add(treasureImage4);
			treasure.add(treasureImage5);
			treasure.add(treasureImage6);
			treasure.add(treasureImage7);
			treasure.add(treasureImage8);
			treasure.add(treasureImage9);
			treasure .add(treasureImage10);
		}
		Collections.shuffle(treasure);
		return treasure;
	}	
	
	public List<Image> createBirdCovers() {
		List<Image> cover = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs*10; i++) {
			cover.add(imageBirds);			
		}	
		return cover;
	}	
	
	public List<Image> createSeaCovers() {
		List<Image> cover = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs*10; i++) {
			cover.add(imageSea);			
		}	
		return cover;
	}
	
	public List<Image> createTreasureCovers() {
		List<Image> cover = new ArrayList<Image>();
		for (int i = 0; i < numOfPairs*10; i++) {
			cover.add(imageTreasures);			
		}	
		return cover;
	}

}
