package hr.java.vjezbe;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.entitet.MetodeDatoteka;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ControllerPredmetUnos implements Initializable {

	@FXML
	private TextField sifraPredUnosTextField;
	@FXML
	private TextField nazivPredUnosTextField;
	@FXML
	private TextField ectsBodoviPredUnosTextField;
	@FXML
	private TextField odabirProfesoraTextfield;
//	@FXML
//	private TextField studentiPredUnosTextField;
//	@FXML
//	private MenuButton odabirProfesoraMenuButton;
	@FXML
	private Button btnSpremi;
	@FXML
	ChoiceBox<Profesor> choiceBoxProf = new ChoiceBox<>();


	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
	List<String> listaPredString = metodeDatoteka.getTxtAndRetrnList(ControllerPredmet.PREMDETI);
	List<String> listaProfString = metodeDatoteka.getTxtAndRetrnList(ControllerProfesor.PROFESORI);
	List<String> listaStudString = metodeDatoteka.getTxtAndRetrnList(ControllerStudent.STUDENTI);

	List<Profesor> listaProfesora = metodeDatoteka.fillProfFromFile(listaProfString);
	List<Student> listaStudenta = metodeDatoteka.fillStudFromFile(listaStudString);
	List<Predmet> listaPredmeta = metodeDatoteka.fillPredFromFile(listaPredString, listaProfesora, listaStudenta);




	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		for (int i = 0; i < listaProfesora.size(); i++) {
//			choiceBoxProf.getItems().add(listaProfesora.get(i).getIme());
//		}

		choiceBoxProf.getItems().addAll(listaProfesora);
		choiceBoxProf.setValue(listaProfesora.get(0));

	}

	private Predmet entryStud() {

		Predmet newPred = null;
		OptionalLong maxId = listaPredmeta.stream().mapToLong(profesor -> profesor.getId()).max();

		if (sifraPredUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("A da uneseš šifru, a?");
		} else if (nazivPredUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Bilo bi najbolje da uneseš ime studenta!");
		} else if (ectsBodoviPredUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Probaj unijet prezime!");
		}
//		 else if (odabirProfesoraTextfield.getText().isEmpty() == true || Integer.valueOf(odabirProfesoraTextfield.getText()) >  (listaProfesora.size())) {
//			metodeDatoteka.errorMsg("Odaberi profesora ili broj za profesora ne postoji");
//		}

		else {
			newPred = new Predmet(sifraPredUnosTextField.getText(), nazivPredUnosTextField.getText(),
					Integer.valueOf(ectsBodoviPredUnosTextField.getText()), choiceBoxProf.getValue(), maxId.getAsLong() + 1);
			listaPredmeta.add(newPred);
		}

		return newPred;

	}

	public void writeNewPredToFile() throws IOException {



		btnSpremi.setOnAction(e -> getChoice(choiceBoxProf));
		Profesor profesor = choiceBoxProf.getValue();
		Integer odabraniProfesor = (((int) profesor.getId()) - 1);

		Predmet newPred = entryStud();

		if (newPred != null) {

	//		int odabirProfesora = (Integer.valueOf(odabirProfesoraTextfield.getText()) - 1);
			FileWriter fWriter = new FileWriter(ControllerPredmet.PREMDETI, true);
			fWriter.write('\n');
			fWriter.write(String.valueOf(newPred.getId()));
			fWriter.write('\n');
			fWriter.write(newPred.getSifra());
			fWriter.write('\n');
			fWriter.write(newPred.getNaziv());
			fWriter.write('\n');
			fWriter.write(newPred.getBrojEctsBodova().toString());
			fWriter.write('\n');
//			fWriter.write(Integer.valueOf(odabirProfesoraTextfield.getText()) - 1);
//			fWriter.write(String.valueOf(odabirProfesora));
			fWriter.write(String.valueOf(odabraniProfesor));
			fWriter.write('\n');
			fWriter.write(String.valueOf(1));


			fWriter.close();
			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
			sifraPredUnosTextField.setText("");
			nazivPredUnosTextField.setText("");
			ectsBodoviPredUnosTextField.setText("");
		//	odabirProfesoraTextfield.setText("");
			choiceBoxProf.setValue(listaProfesora.get(0));


		} else {

			System.out.println("Nisu uneseni svi podaci!");

		}


	}

	private void getChoice(ChoiceBox<Profesor> choiceBox) {
		Profesor profesor = choiceBox.getValue();
		//return profesor;
	}



}
