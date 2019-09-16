package hr.java.vjezbe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Predmet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerPredmet implements Initializable {

	public static final String PREMDETI = "dat\\predmet.txt";
	@FXML
	private TableView<Predmet> predmetTableView;
	@FXML
	private TableColumn<Predmet, String> sifraColumn;
	@FXML
	private TableColumn<Predmet, String> nazivColumn;
	@FXML
	private TableColumn<Predmet, Integer> ectsColumn;
	@FXML
	private TableColumn<Predmet, String> nositeljColumn;

	@FXML
	private TextField sifraTextField;
	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField ectsBodoviTextField;
	@FXML
	private TextField nositeljTextField;

//	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
//	List<String> listaPredString = metodeDatoteka.getTxtAndRetrnList(PREMDETI);
//	List<String> listaProfString = metodeDatoteka.getTxtAndRetrnList(ControllerProfesor.PROFESORI);
//	List<String> listaStudString = metodeDatoteka.getTxtAndRetrnList(ControllerStudent.STUDENTI);
//
//	List<Profesor> listaProfesora = metodeDatoteka.fillProfFromFile(listaProfString);
//	List<Student> listaStudenta = metodeDatoteka.fillStudFromFile(listaStudString);
//	List<Predmet> listaPredmeta = metodeDatoteka.fillPredFromFile(listaPredString, listaProfesora, listaStudenta);

	BazaPodataka bazaPodataka = new BazaPodataka();

	List<Predmet> listaPredmetaSQL = bazaPodataka.getAllPred();

	ObservableList<Predmet> listaPredObsr = FXCollections.observableArrayList(listaPredmetaSQL);



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		sifraColumn.setCellValueFactory(new PropertyValueFactory<>("sifra"));
		nazivColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));
		ectsColumn.setCellValueFactory(new PropertyValueFactory<>("brojEctsBodova"));
		nositeljColumn.setCellValueFactory(new PropertyValueFactory<>("nositelj"));

		predmetTableView.setItems(listaPredObsr);


	}

	public void fill() {

		//	profesorTableView.getItems().stream().filter(item -> item.getIme().equals(sifraColumn.toString())).findAny().ifPresent(item -> {profesorTableView.getSelectionModel().select(item);profesorTableView.scrollTo(item);});;

		List<Predmet> filtriraniPredList = new ArrayList<>();

		if (nazivTextField.getText().isEmpty() == false) {
			filtriraniPredList = listaPredmetaSQL.stream()
					.filter(m -> m.getNaziv().toLowerCase().contains(nazivTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Predmet> listaPredTemp = FXCollections.observableArrayList(filtriraniPredList);
			predmetTableView.setItems(listaPredTemp);

		} else if (sifraTextField.getText().isEmpty() == false) {
			filtriraniPredList = listaPredmetaSQL.stream()
					.filter(m -> m.getSifra().toLowerCase().contains(sifraTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Predmet> listaPredTemp = FXCollections.observableArrayList(filtriraniPredList);
			predmetTableView.setItems(listaPredTemp);


		} else if (ectsBodoviTextField.getText().isEmpty() == false) {
			filtriraniPredList = listaPredmetaSQL.stream()
					.filter(m -> m.getBrojEctsBodova().toString().toLowerCase().contains(ectsBodoviTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Predmet> listaPredTemp = FXCollections.observableArrayList(filtriraniPredList);
			predmetTableView.setItems(listaPredTemp);

		} else if (nositeljTextField.getText().isEmpty() == false) {
			filtriraniPredList = listaPredmetaSQL.stream()
					.filter(m -> m.getNositelj().toString().toLowerCase().contains(nositeljTextField.getText().toString().toLowerCase()))
					.collect(Collectors.toList());

			ObservableList<Predmet> listaPredTemp = FXCollections.observableArrayList(filtriraniPredList);
			predmetTableView.setItems(listaPredTemp);
		}
		else {

			filtriraniPredList = listaPredmetaSQL;

			ObservableList<Predmet> listaPredTemp = FXCollections.observableArrayList(filtriraniPredList);
			predmetTableView.setItems(listaPredTemp);
		}


	}


}
