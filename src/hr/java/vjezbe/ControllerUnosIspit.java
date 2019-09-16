package hr.java.vjezbe;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.MetodeDatoteka;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControllerUnosIspit implements Initializable {

//	@FXML
//	private TextField predmetIspitUnosTextField;
//	@FXML
//	private TextField studentIspitUnosTextField;
	@FXML
	private TextField ocjenaIspitUnosTextField;
	@FXML
	private DatePicker vrijemeIspitaIspitUnosDatePicker;
	@FXML
	private Button btnSpremni;
	@FXML
	ChoiceBox<Predmet> choiceBoxPredmet;
	@FXML
	ChoiceBox<Student> choiceBoxStudent;

	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
	List<String> listaIspitaString = metodeDatoteka.getTxtAndRetrnList(ControllerIspit.ISPITI);
	List<String> listaProfString = metodeDatoteka.getTxtAndRetrnList(ControllerProfesor.PROFESORI);
	List<String> listaPredString = metodeDatoteka.getTxtAndRetrnList(ControllerPredmet.PREMDETI);
	List<String> listaStudString = metodeDatoteka.getTxtAndRetrnList(ControllerStudent.STUDENTI);

	List<Profesor> listaProfesora = metodeDatoteka.fillProfFromFile(listaProfString);
	List<Student> listaStudenta = metodeDatoteka.fillStudFromFile(listaStudString);
	List<Predmet> listaPredmeta = metodeDatoteka.fillPredFromFile(listaPredString, listaProfesora, listaStudenta);
	List<Ispit> listaIspita = metodeDatoteka.fillIspitFromFile(listaIspitaString, listaPredmeta, listaStudenta);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		choiceBoxPredmet.getItems().addAll(listaPredmeta);
		choiceBoxStudent.getItems().addAll(listaStudenta);

		choiceBoxPredmet.setValue(listaPredmeta.get(0));
		choiceBoxStudent.setValue(listaStudenta.get(0));

	}

	private Ispit entryIspit() {

		Ispit newIspit = null;
		OptionalLong maxId = listaIspita.stream().mapToLong(ispit -> ispit.getId()).max();

//		if (predmetIspitUnosTextField.getText().isEmpty() == true) {
//			metodeDatoteka.errorMsg("A da uneseš predmet, a?");
//		} else if (studentIspitUnosTextField.getText().isEmpty() == true) {
//			metodeDatoteka.errorMsg("Bilo bi najbolje da uneseš studenta!");
//		}
		 if (ocjenaIspitUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Probaj unijet prezime!");
		}
//		else if (odabirProfesoraTextfield.getText().isEmpty() == true || Integer.valueOf(odabirProfesoraTextfield.getText()) >  (listaProfesora.size())) {
//			metodeDatoteka.errorMsg("Odaberi profesora ili broj za profesora ne postoji");
//		}

		else {
			newIspit = new Ispit(choiceBoxPredmet.getValue(), choiceBoxStudent.getValue(),
					 Integer.valueOf(ocjenaIspitUnosTextField.getText()), vrijemeIspitaIspitUnosDatePicker.getValue().atStartOfDay(), maxId.getAsLong() + 1);
			listaIspita.add(newIspit);
		}

		return newIspit;
	}

	public void writeNewIspitToFile() throws IOException {

		btnSpremni.setOnAction(e -> getChoicePred(choiceBoxPredmet));
		btnSpremni.setOnAction(e -> getChoiceStud(choiceBoxStudent));

		Integer odabraniPredmet = (((int) choiceBoxPredmet.getValue().getId()) - 1);
		Integer odabraniStudent = (((int) choiceBoxStudent.getValue().getId()) - 1);

		Ispit newIspit = entryIspit();
		if (newIspit != null) {

			FileWriter fWriter = new FileWriter(ControllerIspit.ISPITI, true);
			fWriter.write('\n');
			fWriter.write(String.valueOf(newIspit.getId()));
			fWriter.write('\n');
			fWriter.write(String.valueOf(odabraniPredmet));
			fWriter.write('\n');
			fWriter.write(String.valueOf(odabraniStudent));
			fWriter.write('\n');
			fWriter.write(newIspit.getOcjena().toString());
			fWriter.write('\n');
			fWriter.write(newIspit.getDatumIVrijeme().format(DateTimeFormatter.ofPattern(ControllerIspit.DATE_TIME_FX)));

			fWriter.close();
			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
//			predmetIspitUnosTextField.setText("");
//			studentIspitUnosTextField.setText("");
			ocjenaIspitUnosTextField.setText("");
			vrijemeIspitaIspitUnosDatePicker.setValue(null);

		} else {

			System.out.println("Nisu uneseni svi podaci!");

		}

	}

	private void getChoicePred(ChoiceBox<Predmet> choiceBox) {
		Predmet predmet = choiceBox.getValue();
		//return profesor;
	}

	private void getChoiceStud(ChoiceBox<Student> choiceBox) {
		Student student = choiceBox.getValue();
		//return profesor;
	}

}
