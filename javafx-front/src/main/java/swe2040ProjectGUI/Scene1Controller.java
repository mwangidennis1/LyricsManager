package swe2040ProjectGUI;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Scene1Controller implements Initializable {
	

	@FXML
	private TableView<Music> lyricsTable;

	@FXML
	private TableColumn<Music, String> indexCol;
	
	@FXML
	private TableColumn<Music, String> songTitleCol;

	@FXML
	private URL location;

	@FXML
	private ResourceBundle resources;

	private static DataFetcher dataFetcher = new DataFetcher();

	private static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	List<Music> lyrics ;

	public Scene1Controller() {
		
	}


	@FXML
	public void initialize(URL location, ResourceBundle resources) {
 		songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));	
	}

	public void init(Stage stage, Scene scene) {
		
		Thread taskThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Future<List<Music>> getLyrics = executor.submit(() -> dataFetcher.fetchAllLyrics());
				while (!getLyrics.isDone()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
					Platform.runLater(()-> { 
						try {
							lyrics = getLyrics.get();
							stage.setScene(scene);
							updateTableData(lyrics);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					
					});
		}});
					
		taskThread.start();
		
	}
	
	public void updateTableData(List<Music> data) {
		Callback<TableColumn<Music, String>, TableCell<Music, String>> cellFactory
		= //
		new Callback<TableColumn<Music, String>, TableCell<Music, String>>() {
			@Override
			public TableCell<Music, String> call(final TableColumn<Music, String> param) {
				final TableCell<Music, String> cell = new TableCell<Music, String>() {

					final Button btn = new Button("view");
				
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								Music lyric = getTableView().getItems().get(getIndex());
								Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
								setLyricsScene(stage, "read", lyric);
							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		indexCol.setCellFactory(cellFactory);
	    lyricsTable.setItems(FXCollections.observableArrayList(data));
	}
	
	
	public void setCreateScene(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		setLyricsScene(stage, "create", new Music());
	}
	

	public void setReadScene(ActionEvent event) {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		setLyricsScene(stage, "read", lyrics.get(0));

	}
	
	public void setEditScene(ActionEvent event) {
		Music music = lyricsTable.getSelectionModel().getSelectedItem();
		if (!Objects.isNull(music)) {
			Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			setLyricsScene(stage, "edit", music);
		}
	}
	
	public void setLyricsScene(Stage stage, String mode, Music music) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene"+3+".fxml"));
			Scene scene2 = new Scene(loader.load());
			Scene2Controller scene2Controller = loader.getController();
			scene2Controller.setMode(mode);
			if (!Objects.isNull(music))
				scene2Controller.setLyrics(music);
			stage.setScene( scene2 );
			stage.show();	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	


}
