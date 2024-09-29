package swe2040ProjectGUI;




import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			String loadingScene = "/Scene1.fxml";
			String dashboardSceneFile = "/Scene2.fxml";
		
			FXMLLoader loadingSceneLoader = new FXMLLoader(getClass().getResource(loadingScene));
			FXMLLoader dashboardSceneLoader = new FXMLLoader(getClass().getResource(dashboardSceneFile));
			
			Parent root = loadingSceneLoader.load();
			Scene scene = new Scene(root);
			Image logo = new Image(getClass().getResource("/logo.png").toString());
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
			primaryStage.setTitle("Lyrics Manager");
			primaryStage.getIcons().add(logo);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.show();
			
			Scene scene1 = new Scene(dashboardSceneLoader.load());
			Scene1Controller scene1Controller = dashboardSceneLoader.getController();
			scene1Controller.init(primaryStage, scene1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
