package com.puzzle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Start application class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

public class Start extends Application {

	private Stage primaryStage;
	private Button birdsButton;
	private Button seaButton;
	private Button treasureButton;
	private Button quitButton;
	private Button rulesButton;
	private Button backButton = new Button("TO MAIN MENU");
	private Label label;
	private Reader reader;
	private transient Label labelTimer = new Label();
	private FlowPane flowpane;
	private FlowPane flowpane1;
	private ImagesCollection images = new ImagesCollection();
	private int clickCount = 2;
	private ImageView selected = null;
	private transient Label scoreLabel = new Label();
	private transient int score = 0;
	private transient int timer = 60;
	private boolean isRunning = true;
	private boolean isSound = false;
	
	public Scene createContent() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setId("menu");
		label = new Label("SELECT YOUR PUZZLE");
		birdsButton = new Button("FOREST");
		seaButton = new Button("SEA");
		treasureButton = new Button("ISELAND");
		rulesButton = new Button("RULES");
		quitButton = new Button("QUIT");
		VBox hb = new VBox(5);
		hb.getChildren().add(label);
		hb.getChildren().add(birdsButton);
		hb.getChildren().add(seaButton);
		hb.getChildren().add(treasureButton);
		hb.getChildren().add(rulesButton);
		hb.getChildren().add(quitButton);
		grid.setPadding(new Insets(40, 40, 40, 40));
		grid.add(label, 0, 0);
		grid.add(hb, 0, 1);
		grid.setHgap(10);
		grid.setVgap(10);
		Scene scene = new Scene(grid, 1400, 900);
		scene.getStylesheets().add(Start.class.getResource("application2.css").toExternalForm());
		return scene;

	}

	public void startTask() {
		Runnable task = new Runnable() {
			public void run() {
				try {
					timerTask();
				} catch (InterruptedException e) {			
					e.printStackTrace();
				}
			}
		};
		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	public void soundTask() {
		Runnable task = new Runnable() {
			public void run() {
				Sounder sound = new Sounder();
				try {
					sound.playMainSound();
					isSound=true;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	public void timerTask() throws InterruptedException {
		
		do {
			try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					labelTimer.setText(String.valueOf(timer));
					System.out.println(timer);
					scoreLabel.setText(String.valueOf(score));
					timer--;
					if (timer<=0) {
						timer=60;
						isRunning=false;
						primaryStage.setScene(getSceneScore());
						primaryStage.show();
					}
					
				}

			});
			Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (isRunning);

	}

	public boolean isOpen(ImageView selected) {
		return selected.getOpacity() == 1;
	}

	public void openSelected(Runnable action, ImageView selected) {
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), selected);
		ft.setToValue(1);
		ft.setOnFinished(e -> action.run());
		ft.play();

	}

	public void close(ImageView selected) {
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), selected);
		ft.setToValue(0);
		ft.play();
	}

	public boolean hasSameValue(ImageView other, ImageView select) {
		return select.getImage().equals(other.getImage());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setScene(createContent());
			primaryStage.show();
			if(isSound==false) soundTask();
		} catch (Exception e) {
			e.printStackTrace();
		}

		initListeners();
	}

	public Scene getSceneStartBirds() throws InterruptedException {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 1400, 900);
		isRunning=true;
		score=0;
		startTask();
		grid.setId("birdMenu");
		grid.setAlignment(Pos.CENTER);
		Label timeLabel = new Label("TIME LEFT:");
		Label scLabel = new Label("SCORE:");
		VBox hb = new VBox(5);
		hb.getChildren().add(new Label("BIRDS"));
		hb.getChildren().add(timeLabel);
		hb.getChildren().add(labelTimer);
		hb.getChildren().add(scLabel);
		hb.getChildren().add(scoreLabel);
		labelTimer.setText(String.valueOf(timer));
		scoreLabel.setText(String.valueOf(score));
		
		flowpane = new FlowPane();
		flowpane.setOrientation(Orientation.VERTICAL);
		flowpane.setPrefSize(800, 800);
		flowpane.setVgap(5);
		flowpane.setHgap(5);

		flowpane1 = new FlowPane();
		flowpane1.setOrientation(Orientation.VERTICAL);
		flowpane1.setVgap(5);
		flowpane1.setHgap(5);
		flowpane1.setPrefSize(800, 800);

		List<Image> tiles = images.createBirdsCollection();
		List<Image> covers = images.createBirdCovers();
		for (int i = 0; i < tiles.size(); i++) {
			ImageView selectedNext = new ImageView(tiles.get(i));
			ImageView cover = new ImageView(covers.get(i));
			close(selectedNext);
			flowpane.getChildren().add(selectedNext);
			flowpane1.getChildren().add(cover);
			selectedNext.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent e) {

					if (isOpen(selectedNext) || clickCount == 0 || e.getClickCount()>=2) {
						return;
					}
					clickCount--; 
					if (selected == null) {
						selected = selectedNext;
						openSelected(() -> { 
						}, selected);
					} else {

						openSelected(() -> {

							if (!hasSameValue(selected, selectedNext)) {
								close(selected);
								close(selectedNext);
							}

							if (hasSameValue(selected, selectedNext))
								score++;
							selected = null;
							clickCount = 2;
						}, selectedNext);
					}

				}
			});

		}

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(flowpane1, 1, 0);
		grid.add(flowpane, 1, 0);
		grid.add(hb, 0, 0);
		
		scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		return scene;
	}

	public Scene getSceneStartSea() throws InterruptedException {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 1400, 900);
		isRunning=true;
		score=0;
		startTask();
		grid.setId("seaMenu");
		grid.setAlignment(Pos.CENTER);
		Label timeLabel = new Label("TIME LEFT:");
		Label scLabel = new Label("SCORE:");
		VBox hb = new VBox(5);
		hb.getChildren().add(new Label("SEA"));
		hb.getChildren().add(timeLabel);
		hb.getChildren().add(labelTimer);
		hb.getChildren().add(scLabel);
		hb.getChildren().add(scoreLabel);
		labelTimer.setText(String.valueOf(timer));
		scoreLabel.setText(String.valueOf(score));
		
		flowpane = new FlowPane();
		flowpane.setOrientation(Orientation.VERTICAL);
		flowpane.setPrefSize(800, 800);
		flowpane.setVgap(5);
		flowpane.setHgap(5);

		flowpane1 = new FlowPane();
		flowpane1.setOrientation(Orientation.VERTICAL);
		flowpane1.setVgap(5);
		flowpane1.setHgap(5);
		flowpane1.setPrefSize(800, 800);

		List<Image> tiles = images.createSeaCollection();
		List<Image> covers = images.createSeaCovers();
		for (int i = 0; i < tiles.size(); i++) {
			ImageView selectedNext = new ImageView(tiles.get(i));
			ImageView cover = new ImageView(covers.get(i));
			close(selectedNext);
			flowpane.getChildren().add(selectedNext);
			flowpane1.getChildren().add(cover);
			selectedNext.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent e) {

					if (isOpen(selectedNext) || clickCount == 0 || e.getClickCount()>=2) {
						return;
					}
					clickCount--; 
					if (selected == null) {
						selected = selectedNext;
						openSelected(() -> { 
						}, selected);
					} else {

						openSelected(() -> {

							if (!hasSameValue(selected, selectedNext)) {
								close(selected);
								close(selectedNext);
							}

							if (hasSameValue(selected, selectedNext))
								score++;
							selected = null;
							clickCount = 2;
						}, selectedNext);
					}

				}
			});

		}

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(flowpane1, 1, 0);
		grid.add(flowpane, 1, 0);
		grid.add(hb, 0, 0);
		
		scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		return scene;

	}

	public Scene getSceneStartTreasure() throws InterruptedException {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 1400, 900);
		isRunning=true;
		score=0;
		startTask();
		grid.setId("treasureMenu");
		grid.setAlignment(Pos.CENTER);
		Label timeLabel = new Label("TIME LEFT:");
		Label scLabel = new Label("SCORE:");
		VBox hb = new VBox(5);
		hb.getChildren().add(new Label("TREASURE"));
		hb.getChildren().add(timeLabel);
		hb.getChildren().add(labelTimer);
		hb.getChildren().add(scLabel);
		hb.getChildren().add(scoreLabel);
		labelTimer.setText(String.valueOf(timer));
		scoreLabel.setText(String.valueOf(score));
		
		flowpane = new FlowPane();
		flowpane.setOrientation(Orientation.VERTICAL);
		flowpane.setPrefSize(800, 800);
		flowpane.setVgap(5);
		flowpane.setHgap(5);

		flowpane1 = new FlowPane();
		flowpane1.setOrientation(Orientation.VERTICAL);
		flowpane1.setVgap(5);
		flowpane1.setHgap(5);
		flowpane1.setPrefSize(800, 800);

		List<Image> tiles = images.createTreasureCollection();
		List<Image> covers = images.createTreasureCovers();
		for (int i = 0; i < tiles.size(); i++) {
			ImageView selectedNext = new ImageView(tiles.get(i));
			ImageView cover = new ImageView(covers.get(i));
			close(selectedNext);
			flowpane.getChildren().add(selectedNext);
			flowpane1.getChildren().add(cover);
			selectedNext.setOnMouseClicked(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent e) {

					if (isOpen(selectedNext) || clickCount == 0 || e.getClickCount()>=2) {
						return;
					}
					clickCount--; 
					if (selected == null) {
						selected = selectedNext;
						openSelected(() -> { 
						}, selected);
					} else {

						openSelected(() -> {

							if (!hasSameValue(selected, selectedNext)) {
								close(selected);
								close(selectedNext);
							}

							if (hasSameValue(selected, selectedNext))
								score++;
							selected = null;
							clickCount = 2;
						}, selectedNext);
					}

				}
			});

		}

		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(flowpane1, 1, 0);
		grid.add(flowpane, 1, 0);
		grid.add(hb, 0, 0);
		scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		return scene;
	}

	public Scene getSceneScore() {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 1400, 900);
		grid.setAlignment(Pos.CENTER);
		grid.setId("scoreMenu");
		Label label = new Label("GAME is over. YOUR SCORE IS "+score);
		label.setId("labelK");
		grid.setPadding(new Insets(40, 40, 40, 40));
		grid.add(label, 0, 0);
		grid.add(backButton, 0, 1);
		grid.setHgap(10);
		grid.setVgap(10);
		scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		return scene;
	}
	
	public Scene getSceneRules() throws IOException {
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, 1400, 900);
		grid.setAlignment(Pos.CENTER);
		grid.setId("scoreMenu");
		Label label = new Label();
		reader = new Reader();
		label.setText(reader.read("src/main/resources/rules.txt"));
		label.setId("labelR");
		grid.setPadding(new Insets(40, 40, 40, 40));
		grid.add(label, 0, 0);
		grid.add(backButton, 0, 1);
		grid.setHgap(10);
		grid.setVgap(10);
		
		scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
		return scene;
	}

	private void initListeners() {
		birdsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				try {
					primaryStage.setScene(getSceneStartBirds());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				primaryStage.show();
			}
		});
		seaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {

				try {
					primaryStage.setScene(getSceneStartSea());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				primaryStage.show();
			}

		});
		treasureButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {

				try {
					primaryStage.setScene(getSceneStartTreasure());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				primaryStage.show();
			}
		});
		quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				primaryStage.close();
			}
		});

		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {

				try {
					
					start(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				primaryStage.show();

			}
		});

		rulesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {

				try {
					primaryStage.setScene(getSceneRules());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				primaryStage.show();
			}
		});

	}

	public static void main(String[] args) {		
		launch(args);
	}
}