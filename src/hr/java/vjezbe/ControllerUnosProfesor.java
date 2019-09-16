package hr.java.vjezbe;

import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.MetodeDatoteka;
import hr.java.vjezbe.entitet.Profesor;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerUnosProfesor {


	@FXML
	private TextField profesorSifraUnosTextField;
	@FXML
	private TextField profesorImeUnosTextField;
	@FXML
	private TextField profesorPrezimeUnosTextField;
	@FXML
	private TextField profesorTitulaUnosTextField;


	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
//	List<String> listaProfesoraString = metodeDatoteka.getTxtAndRetrnList(ControllerProfesor.PROFESORI);
//	List<Profesor> listaProfesora = metodeDatoteka.fillProfFromFile(listaProfesoraString);

	BazaPodataka bazaPodataka = new BazaPodataka();
	List<Profesor> listaProfSQL = bazaPodataka.getAllProfs();


	public void entryProf() {

		Profesor newProf = null;
		OptionalLong maxId = listaProfSQL.stream().mapToLong(profesor -> profesor.getId()).max();

		if (profesorSifraUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("A da uneseš šifru, a?");
		} else if (profesorImeUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Bilo bi najbolje da uneseš ime profesora!");
		} else if (profesorPrezimeUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Probaj unijet prezime!");
		} else if (profesorTitulaUnosTextField.getText().isBlank() == true) {
			metodeDatoteka.errorMsg("Šta je profesor bez titule?");
		}
		else {
			newProf = new Profesor(profesorSifraUnosTextField.getText(), profesorImeUnosTextField.getText(),
					profesorPrezimeUnosTextField.getText(), profesorTitulaUnosTextField.getText(), maxId.getAsLong() + 1);
			listaProfSQL.add(newProf);
			bazaPodataka.spremiNovogProfesora(newProf);
			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
		}

	//	return newProf;

	}

//	public void writeNewProfToFile() throws IOException {
//
//		Profesor newProf = entryProf();
//		if (newProf != null) {
//
//			FileWriter fWriter = new FileWriter(ControllerProfesor.PROFESORI, true);
//			fWriter.write('\n');
//			fWriter.write(String.valueOf(newProf.getId()));
//			fWriter.write('\n');
//			fWriter.write(newProf.getSifra());
//			fWriter.write('\n');
//			fWriter.write(newProf.getTitula());
//			fWriter.write('\n');
//			fWriter.write(newProf.getIme());
//			fWriter.write('\n');
//			fWriter.write(newProf.getPrezime());
//
//
//			fWriter.close();
//			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
//			profesorSifraUnosTextField.setText("");
//			profesorImeUnosTextField.setText("");
//			profesorPrezimeUnosTextField.setText("");
//			profesorTitulaUnosTextField.setText("");
//
//
//		} else {
//
//			System.out.println("Nisu uneseni svi podaci!");
//
//		}
//
//
//	}



}
