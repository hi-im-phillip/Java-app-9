package hr.java.vjezbe;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Ispit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ControllerIspit implements Initializable {

	public static final String ISPITI = "dat\\ispit.txt";
	public static final String DATE_TIME_FX = "dd.MM.yyyy.'T'HH:mm";

	@FXML
	private TableView<Ispit> ispitTableView;
	@FXML
	private TableColumn<Ispit, String> predmetColumn;
	@FXML
	private TableColumn<Ispit, String> studentColumn;
	@FXML
	private TableColumn<Ispit, Integer> ocjenaColumn;
	@FXML
	private TableColumn<Ispit, String> vrijemeIspitaColumn;

	@FXML
	private TextField predmetTextField;
	@FXML
	private TextField studentTextField;
	@FXML
	private TextField ocjenaTextField;
	@FXML
	private DatePicker vrijemeIspitaDatePicker;



//	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
//	List<String> listaIspitaString = metodeDatoteka.getTxtAndRetrnList(ISPITI);
//	List<String> listaProfString = metodeDatoteka.getTxtAndRetrnList(ControllerProfesor.PROFESORI);
//	List<String> listaPredString = metodeDatoteka.getTxtAndRetrnList(ControllerPredmet.PREMDETI);
//	List<String> listaStudString = metodeDatoteka.getTxtAndRetrnList(ControllerStudent.STUDENTI);
//
//	List<Profesor> listaProfesora = metodeDatoteka.fillProfFromFile(listaProfString);
//	List<Student> listaStudenta = metodeDatoteka.fillStudFromFile(listaStudString);
//	List<Predmet> listaPredmeta = metodeDatoteka.fillPredFromFile(listaPredString, listaProfesora, listaStudenta);
//	List<Ispit> listaIspita = metodeDatoteka.fillIspitFromFile(listaIspitaString, listaPredmeta, listaStudenta);

	BazaPodataka bazaPodataka = new BazaPodataka();
	List<Ispit> listaIspitaSQL = bazaPodataka.getAllIspit();

	ObservableList<Ispit> listaIspitaObs = FXCollections.observableArrayList(listaIspitaSQL);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		predmetColumn.setCellValueFactory(new PropertyValueFactory<>("predmet"));
		studentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
		ocjenaColumn.setCellValueFactory(new PropertyValueFactory<>("ocjena"));
		//vrijemeIspitaColumn.setCellValueFactory(new PropertyValueFactory<>("datumIVrijeme"));
		vrijemeIspitaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ispit,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Ispit, String> ispit) {
				SimpleStringProperty property = new SimpleStringProperty();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FX);
				property.setValue(ispit.getValue().getDatumIVrijeme().format(formatter));
				return property;
			}
		});

		ispitTableView.setItems(listaIspitaObs);

	}

	public void fill() {

		//	profesorTableView.getItems().stream().filter(item -> item.getIme().equals(sifraColumn.toString())).findAny().ifPresent(item -> {profesorTableView.getSelectionModel().select(item);profesorTableView.scrollTo(item);});;

		List<Ispit> filtriraniIspit = new ArrayList<>();

		if (predmetTextField.getText().isEmpty() == false) {
			filtriraniIspit = listaIspitaSQL.stream()
					.filter(m -> m.getPredmet().toString().toLowerCase().contains(predmetTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Ispit> listaIspitaTemp = FXCollections.observableArrayList(filtriraniIspit);
			ispitTableView.setItems(listaIspitaTemp);

		} else if (studentTextField.getText().isEmpty() == false) {
			filtriraniIspit = listaIspitaSQL.stream()
					.filter(m -> m.getStudent().toString().toLowerCase().contains(studentTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Ispit> listaIspitaTemp = FXCollections.observableArrayList(filtriraniIspit);
			ispitTableView.setItems(listaIspitaTemp);


		} else if (ocjenaTextField.getText().isEmpty() == false) {
			filtriraniIspit = listaIspitaSQL.stream()
					.filter(m -> m.getOcjena().toString().toLowerCase().contains(ocjenaTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Ispit> listaIspitaTemp = FXCollections.observableArrayList(filtriraniIspit);
			ispitTableView.setItems(listaIspitaTemp);

		}
		else if (vrijemeIspitaDatePicker.getValue() != null) {
			filtriraniIspit = listaIspitaSQL.stream()
					.filter(m -> m.getDatumIVrijeme().format(DateTimeFormatter.ofPattern(DATE_TIME_FX)).contains(vrijemeIspitaDatePicker.getValue().format(DateTimeFormatter.ofPattern(DATE_TIME_FX))))
					.collect(Collectors.toList());

			vrijemeIspitaDatePicker.setValue(null);
			ObservableList<Ispit> listaIspitaTemp = FXCollections.observableArrayList(filtriraniIspit);
			ispitTableView.setItems(listaIspitaTemp);
		}
		else {

			filtriraniIspit = listaIspitaSQL;

			ObservableList<Ispit> listaIspitaTemp = FXCollections.observableArrayList(filtriraniIspit);
			ispitTableView.setItems(listaIspitaTemp);
		}
	}
}
