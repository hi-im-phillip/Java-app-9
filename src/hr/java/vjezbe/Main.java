package hr.java.vjezbe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	private static BorderPane root;
	public static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("Pocetna.fxml"));
			scene = new Scene(root, 350, 420);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Aplikacija");
			primaryStage.getIcons().add(new Image("https://cdn4.iconfinder.com/data/icons/google-i-o-2016/512/google_assistant-512.png"));
			primaryStage.resizableProperty().setValue(Boolean.FALSE);
			primaryStage.show();
			BackgroundFill myBF = new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(1),
			         new Insets(0.0,0.0,0.0,0.0));
			root.setBackground(new Background(myBF));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void setCenterPane(BorderPane centerPane) {
		root.setBottom(centerPane);

	}
}
