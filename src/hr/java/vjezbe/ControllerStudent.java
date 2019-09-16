package hr.java.vjezbe;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Metode;
import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerStudent implements Initializable {

	public static final String STUDENTI = "dat\\student.txt";

	@FXML
	private TableView<Student> studentTableView;
	@FXML
	private TableColumn<Student, String> jmbagColumn;
	@FXML
	private TableColumn<Student, String> imeColumn;
	@FXML
	private TableColumn<Student, String> prezimeColumn;
	@FXML
	private TableColumn<Student, String> dateColumn;
	@FXML
	private TableColumn<Student, String> odjelColumn;

	@FXML
	private TextField sifraStudentTextField;
	@FXML
	private TextField imeStudentTextField;
	@FXML
	private TextField prezimeStudentTextField;
	@FXML
	private DatePicker datumStudentDatePicker;
	@FXML
	private TextField odjelTextField;

//	MetodeDatoteka metodedatoteka = new MetodeDatoteka();
//
//	List<String> listaStudentaString = metodedatoteka.getTxtAndRetrnList(STUDENTI);
//	List<Student> listaStudenta = metodedatoteka.fillStudFromFile(listaStudentaString);

	BazaPodataka bazaPodataka = new BazaPodataka();

	List<Student> listaStudentaSQL = bazaPodataka.getAllStud();

	@FXML
	void handleButtonAction() {
		try {

			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Studenti.fxml"));

			Main.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Proba");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		studentTableView.setOnMouseClicked(e -> System.out.println("Nece ici"));
	//	studentTableView.setOnMouseClicked(e -> System.out.println(listaStudenta.get(1).toString()));

//		listaStudenta.get(0).setOdjel("BackOffice");
//		listaStudenta.get(1).setOdjel("FrontOffice");
//		listaStudenta.get(2).setOdjel("Razvoj");

		odjelColumn.setCellValueFactory(new PropertyValueFactory<>("odjel"));
		jmbagColumn.setCellValueFactory(new PropertyValueFactory<>("jmbag"));
		imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		//dateColumn.setCellValueFactory(new PropertyValueFactory<>("datumRodenja"));
		dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>()  {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Student, String> student) {
				SimpleStringProperty property = new SimpleStringProperty();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Metode.FORMAT_DATE);
				property.setValue(student.getValue().getDatumRodenja().format(formatter));
				return property;
			}
		});

		ObservableList<Student> listaStudosa = FXCollections.observableArrayList(listaStudentaSQL);
		studentTableView.setItems(listaStudosa);

	}


	public void fill() {

		//	profesorTableView.getItems().stream().filter(item -> item.getIme().equals(sifraColumn.toString())).findAny().ifPresent(item -> {profesorTableView.getSelectionModel().select(item);profesorTableView.scrollTo(item);});;

		List<Student> filtriraniStudList = new ArrayList<>();

		if (imeStudentTextField.getText().isEmpty() == false) {
			filtriraniStudList = listaStudentaSQL.stream()
					.filter(m -> m.getIme().toLowerCase().contains(imeStudentTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
			studentTableView.setItems(listaStudTemp);

		} else if (prezimeStudentTextField.getText().isEmpty() == false) {
			filtriraniStudList = listaStudentaSQL.stream()
					.filter(m -> m.getPrezime().toLowerCase().contains(prezimeStudentTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
			studentTableView.setItems(listaStudTemp);


		} else if (sifraStudentTextField.getText().isEmpty() == false) {
			filtriraniStudList = listaStudentaSQL.stream()
					.filter(m -> m.getJmbag().toLowerCase().contains(sifraStudentTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
			studentTableView.setItems(listaStudTemp);

		}

		else if (datumStudentDatePicker.getValue() != null) {
			filtriraniStudList = listaStudentaSQL.stream()
					.filter(m -> m.getDatumRodenja().format(DateTimeFormatter.ofPattern(Metode.FORMAT_DATE)).contains(datumStudentDatePicker.getValue().format(DateTimeFormatter.ofPattern(Metode.FORMAT_DATE))))
					.collect(Collectors.toList());

			datumStudentDatePicker.setValue(null);
			ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
			studentTableView.setItems(listaStudTemp);
		}

		 else if (odjelTextField.getText().isEmpty() == false) {
				filtriraniStudList = listaStudentaSQL.stream()
						.filter(m -> m.getOdjel().toLowerCase().contains(odjelTextField.getText().toString().toLowerCase()))
						.collect(Collectors.toList());

				ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
				studentTableView.setItems(listaStudTemp);
		}
		else {

			filtriraniStudList = listaStudentaSQL;

			ObservableList<Student> listaStudTemp = FXCollections.observableArrayList(filtriraniStudList);
			studentTableView.setItems(listaStudTemp);
		}


	}


}