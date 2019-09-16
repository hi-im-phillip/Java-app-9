package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class Controller implements Initializable {



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}

	//	public void comboChanged(ActionEvent event) {
	//		profesoriComboBox.getItems().addAll("Bla", "Baba");
	//		myLabel.setText(profesoriComboBox.getValue());
	//	}

	//	public void buttonAction(ActionEvent event) {
	//		profesoriComboBox.getItems().addAll("lala", "po");
	//	}



	@FXML
	public void loadProfesorPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("Profesori.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	@FXML
	public void loadStudentiPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("Studenti.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	@FXML
	public void loadPredmetiPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("Predmeti.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	@FXML
	public void loadIspitiPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("Ispiti.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	public void loadProfesorUnosPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("ProfesorUnos.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	public void loadStudentUnosPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("StudentUnos.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	public void loadPredmetUnosPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("PredmetUnos.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}

	public void loadIspitUnosPanel(ActionEvent actionEvent) throws IOException {
		BorderPane borderPane = FXMLLoader.load(Main.class.getResource("IspitUnos.fxml"));
		Main.setCenterPane(borderPane);
		borderPane.setMaxHeight(300);
	}




}
