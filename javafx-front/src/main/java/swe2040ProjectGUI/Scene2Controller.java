package swe2040ProjectGUI;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene2Controller implements Initializable {

	String mode;
	
	Music music;
	
	@FXML
	Label songTitle;
	
	@FXML
	TextField songTitleInput;

	@FXML
	TextArea lyricsTextArea;
	
	@FXML
	Button backToDashBoardBtn;

	@FXML
	Button uploadBtn;

	@FXML
	Button deleteBtn;
	
	@FXML
	Label loadingLabel;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	private static DataFetcher dataFetcher = new DataFetcher();

	
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		backToDashBoardBtn.setVisible(true);
	}

	public void setMode(String newMode) {
		loadingLabel.setVisible(false);
		mode = newMode;
		if (mode == "read") {
			songTitle.setVisible(true);
			songTitleInput.setVisible(false);
			uploadBtn.setVisible(false);
			uploadBtn.setDisable(true);
			lyricsTextArea.setEditable(false);
			deleteBtn.setVisible(false);
			deleteBtn.setDisable(true);
		} else {
			songTitle.setVisible(false);
			songTitleInput.setVisible(true);
			uploadBtn.setVisible(true);
			uploadBtn.setDisable(false);
			lyricsTextArea.setEditable(true);
			deleteBtn.setVisible(false);
			deleteBtn.setDisable(true);
			if(mode=="edit")
				deleteBtn.setVisible(true);
				deleteBtn.setDisable(false);
		}
	}

	public void setLyrics(Music music) {
		this.music = music;
		songTitle.setText(music.getSongTitle());
		songTitleInput.setText(music.getSongTitle());
		lyricsTextArea.setText(music.getLyrics());
	}

	public void uploadLyrics(ActionEvent event) {
		String title = songTitleInput.getText();
		String lyrics = lyricsTextArea.getText();
		this.music.setSongTitle(title);
		this.music.setLyrics(lyrics);
		if (mode == "edit" || mode == "create") {
			Thread taskThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if ( !Objects.isNull(title) && !Objects.isNull(lyrics)) {
						Function<Music, List<Music>> uploadNewSong = (s) -> Arrays.asList(dataFetcher.postNewLyric(s));
						Function<Music, List<Music>> editSong = (s) -> Arrays.asList(dataFetcher.editExistingLyric(s));
						Function<Music, List<Music>> action = mode == "edit"? editSong: uploadNewSong;
						Future<List<Music>> uploadLyrics =  executor.submit(() -> action.apply(music));
						loadingLabel.setVisible(true);
						while (!uploadLyrics.isDone()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue;
						}
							Platform.runLater(()-> { 
								try {
									if(!Objects.isNull(uploadLyrics.get()))
										setDashBoardScene(event);
								} catch (InterruptedException | ExecutionException e) {
									e.printStackTrace();
								}
						
							});
			}}});
						
			taskThread.start();
		}

	}


	public void deleteLyrics(ActionEvent event) {
		Boolean toDelete = mode == "edit" && !Objects.isNull(music) && !Objects.isNull(music.getId()); 
		if (toDelete == true) {
			Thread taskThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if ( !Objects.isNull(music) && !Objects.isNull(music.getId())) {
						Future<Music> uploadLyrics =  executor.submit(() -> dataFetcher.deleteLyric(music));
						loadingLabel.setVisible(true);
						while (!uploadLyrics.isDone()) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue;
						}
							Platform.runLater(()-> { 
								try {
									if(!Objects.isNull(uploadLyrics.get()))
										setDashBoardScene(event);
								} catch (InterruptedException | ExecutionException e) {
									e.printStackTrace();
								}
						
							});
			}}});
						
			taskThread.start();
		}

	}

	public void setDashBoardScene(ActionEvent event) {
		try {
			loadingLabel.setVisible(true);
			String dashboardSceneFile = "/Scene2.fxml";
			FXMLLoader dashboardSceneLoader = new FXMLLoader(getClass().getResource(dashboardSceneFile));
			Scene scene1 = new Scene(dashboardSceneLoader.load());
			Scene1Controller scene1Controller = dashboardSceneLoader.getController();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene1Controller.init(stage, scene1);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
