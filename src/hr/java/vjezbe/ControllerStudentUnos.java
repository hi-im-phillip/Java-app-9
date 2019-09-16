package hr.java.vjezbe;

import java.util.List;
import java.util.OptionalLong;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.MetodeDatoteka;
import hr.java.vjezbe.entitet.Student;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControllerStudentUnos {


	@FXML
	private TextField studentSifraUnosTextField;
	@FXML
	private TextField studentImeUnosTextField;
	@FXML
	private TextField studentPrezimeUnosTextField;
	@FXML
	private DatePicker studentDatumUnosDatePicker;

	MetodeDatoteka metodeDatoteka = new MetodeDatoteka();
//	List<String> listaStudentaString = metodeDatoteka.getTxtAndRetrnList(ControllerStudent.STUDENTI);
//	List<Student> listaStudenta = metodeDatoteka.fillStudFromFile(listaStudentaString);

	BazaPodataka bazaPodataka = new BazaPodataka();
	List<Student> listaStudentaSQL = bazaPodataka.getAllStud();

	public void entryStud() {

		Student newStud = null;
		OptionalLong maxId = listaStudentaSQL.stream().mapToLong(profesor -> profesor.getId()).max();

		if (studentSifraUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("A da uneseš šifru, a?");
		} else if (studentImeUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Bilo bi najbolje da uneseš ime studenta!");
		} else if (studentPrezimeUnosTextField.getText().isEmpty() == true) {
			metodeDatoteka.errorMsg("Probaj unijet prezime!");
		} else if (studentDatumUnosDatePicker.getValue() == null) {
			metodeDatoteka.errorMsg("A di je datum roðenja?");
		}
		else {
			newStud = new Student(studentImeUnosTextField.getText(), studentPrezimeUnosTextField.getText(),
					              studentSifraUnosTextField.getText(), studentDatumUnosDatePicker.getValue( ), maxId.getAsLong() + 1);
			listaStudentaSQL.add(newStud);
			bazaPodataka.insertNewStud(newStud);
			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
		}

	//	return newStud;

	}

//	public void writeNewProfToFile() throws IOException {
//
//		Student newStud = entryStud();
//		if (newStud != null) {
//
//			FileWriter fWriter = new FileWriter(ControllerStudent.STUDENTI, true);
//			fWriter.write('\n');
//			fWriter.write(String.valueOf(newStud.getId()));
//			fWriter.write('\n');
//			fWriter.write(newStud.getIme());
//			fWriter.write('\n');
//			fWriter.write(newStud.getPrezime());
//			fWriter.write('\n');
//			fWriter.write(newStud.getDatumRodenja().format(DateTimeFormatter.ofPattern(Metode.FORMAT_DATE)));
//			fWriter.write('\n');
//			fWriter.write(newStud.getJmbag());
//
//			fWriter.close();
//			metodeDatoteka.infoMsg("Podaci su uspiješno uneseni!");
//			studentSifraUnosTextField.setText("");
//			studentImeUnosTextField.setText("");
//			studentPrezimeUnosTextField.setText("");
//			studentDatumUnosDatePicker.setValue(null);;
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
