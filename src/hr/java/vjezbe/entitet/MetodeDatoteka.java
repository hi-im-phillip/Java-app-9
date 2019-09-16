package hr.java.vjezbe.entitet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MetodeDatoteka {


	public  List<String> getTxtAndRetrnListAsOneString(String txtFajlaKojaSeDohvaca){

		List<String> listaStringa = new ArrayList<>();
		Path path = Path.of(txtFajlaKojaSeDohvaca);

		try {
			String txt = Files.readString(path, Charset.forName("Windows-1250"));
			listaStringa.add(txt);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaStringa;
	}

	public  List<String> getTxtAndRetrnList(String txtFajlaKojaSeDohvaca){

		List<String> listaStringova = new ArrayList<>();

		try (BufferedReader in = new BufferedReader(new FileReader(txtFajlaKojaSeDohvaca, Charset.forName("Windows-1250")))) {
		String line;
		while ((line = in.readLine()) != null) {
			listaStringova.add(line);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
		return listaStringova;
	}

	public List<Profesor> fillProfFromFile(List<String> listaStringovaProf) {

		Profesor profesor = null;
		List<Profesor> listaProfesora = new ArrayList<>();
		Integer listToRead = 5, id = null;
		String sifra = null, titula = null, imeProfesora = null, prezimeProfesora;

		for (int i = 0; i < listaStringovaProf.size(); i++) {
			String line = listaStringovaProf.get(i);
			switch (i % listToRead) {
			case 0:
				id = Integer.parseInt(line);
				break;
			case 1:
				sifra = line;
				break;
			case 2:
				titula = line;
				break;
			case 3:
				imeProfesora = line;
				break;
			case 4:
				prezimeProfesora = line;
				profesor = new Profesor(sifra, imeProfesora, prezimeProfesora, titula, id);
				listaProfesora.add(profesor);
				break;
			default:
				break;
			}

		}

		return listaProfesora;

	}

	public List<Predmet> fillPredFromFile(List<String> listaStringovaPred, List<Profesor> listaProf, List<Student> listaStudenta) {
		List<Predmet> listaPredmeta = new ArrayList<>();
		Predmet predmet = null;
		Integer id = 0, brojEctsBodova = 0, odabirProfesora = 0;
		String sifra = "", nazivPredmeta = "";
		Integer linesToRead = 6;
		String line = null;


		for (int i = 0; i < listaStringovaPred.size(); i++) {
			line = listaStringovaPred.get(i);
			switch (i % linesToRead) {
			case 0:
				id = Integer.parseInt(line);
				break;
			case 1:
				sifra = line;
				break;
			case 2:
				nazivPredmeta = line;
				break;
			case 3:
				brojEctsBodova = Integer.parseInt(line);
				break;
			case 4:
				odabirProfesora = Integer.parseInt(line);
				break;
			case 5:
				String[] numbers = line.split(" ");
				List<Student> listica = new ArrayList<>();
				for (int j = 0; j < numbers.length; j++) {
					Integer integer = Integer.parseInt(numbers[j]);
					listica.add(listaStudenta.get(integer - 1));
				}
				Set<Student> setSaStudentima = new HashSet<>(listica);
				predmet = new Predmet(sifra, nazivPredmeta, brojEctsBodova, listaProf.get(odabirProfesora), id);
				predmet.setStudent(setSaStudentima);
				listaPredmeta.add(predmet);
			default:
				break;
			}

		}

		return listaPredmeta;
	}

	public List<Student> fillStudFromFile(List<String> listaStringovaStud) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Metode.FORMAT_DATE);
		List<Student> listaStudenti = new ArrayList<>();
		Student student = null;
		Integer id = 0, linesToRead = 5;
		String jmbag = "", imeStudenta = "", prezimeStudenta = "", line = "";
		LocalDate datumRodenjaStud = null;

		for (int i = 0; i < listaStringovaStud.size(); i++) {
			line = listaStringovaStud.get(i);
			switch (i % linesToRead) {
			case 0:
				id = Integer.parseInt(line);
				break;
			case 1:
				imeStudenta = line;
				break;
			case 2:
				prezimeStudenta = line;
				break;
			case 3:
				datumRodenjaStud = LocalDate.parse(line, formatter);
				break;
			case 4:
				jmbag = line;
				student = new Student(imeStudenta, prezimeStudenta, jmbag, datumRodenjaStud, id);
				listaStudenti.add(student);
			default:
				break;
			}
		}

		return listaStudenti;
	}

	public List<Ispit> fillIspitFromFile(List<String> listaStringaIspit, List<Predmet> listaPredmeta, List<Student> listaStudenta) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Metode.FORMAT_DATE_TIME);
		List<Ispit> listaIspita = new ArrayList<>();
		Integer odabirPredmeta = 0, odabirProfesora = 0, id = 0, ocjenaIspita = null, linesToRead = 5;
		LocalDateTime datumIVrijemeIspita = null;
		String line = "";
		Ispit ispit = null;

		for (int i = 0; i < listaStringaIspit.size(); i++) {
			line = listaStringaIspit.get(i);
			switch (i % linesToRead) {
			case 0:
				id = Integer.parseInt(line);
				break;
			case 1:
				odabirPredmeta = Integer.parseInt(line);
				break;
			case 2:
				odabirProfesora = Integer.parseInt(line);
				break;
			case 3:
				ocjenaIspita = Integer.parseInt(line);
				break;
			case 4:
				datumIVrijemeIspita = LocalDateTime.parse(line, formatter);
				ispit = new Ispit(listaPredmeta.get(odabirPredmeta), listaStudenta.get(odabirProfesora), ocjenaIspita, datumIVrijemeIspita, id);
				listaIspita.add(ispit);
			default:
				break;
			}
		}
		return listaIspita;
	}

	public ObrazovnaUstanova fillObrUstanovaFromFile(List<String> listaStringaObrUstanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenta,
			                                               List<Ispit> listaIspita) {

		List<ObrazovnaUstanova> listaObrUstanova = new ArrayList<>();
		ObrazovnaUstanova obrazovnaUstanova = null;
		String nazivUstanove = "", line = "";
		Integer biranjeUstanove = 0, pismenaOcjena = 0, usmenaOcjena = 0, id = 0, linesToRead = 5;

		for (int i = 0; i < listaStringaObrUstanova.size(); i++) {
			line = listaStringaObrUstanova.get(i);
			switch (i % linesToRead) {
			case 0:
				id = Integer.parseInt(line);
				break;
			case 1:
				biranjeUstanove = Integer.parseInt(line);
				break;
			case 2:
				nazivUstanove = line;
				break;
			case 3:
				pismenaOcjena = Integer.parseInt(line);
				break;
			case 4:
				usmenaOcjena = Integer.parseInt(line);
				if (biranjeUstanove == 1) {
					obrazovnaUstanova = new VeleucilisteJave(listaPredmeta, listaProfesora, listaStudenta, listaIspita, id);
					obrazovnaUstanova.setNaziv(nazivUstanove);
				} else {
					obrazovnaUstanova = new FakultetRacunarstva(listaPredmeta, listaProfesora, listaStudenta, listaIspita, id);
					obrazovnaUstanova.setNaziv(nazivUstanove);
				}
				listaObrUstanova.add(obrazovnaUstanova);
				break;
			default:
				break;
			}
		}

		return obrazovnaUstanova;
	}

	public void serializationObrUstanove(ObrazovnaUstanova obrazovneUstanove, String pathTofile) {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(pathTofile);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(obrazovneUstanove);
			objectOutputStream.close();
			fileOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		obrazovneUstanove = null;

	}

	public void deSerializationObrUst(ObrazovnaUstanova obrazovnaUstanova, String pathTofile) {

		try {
			FileInputStream fileInputStream = new FileInputStream(pathTofile);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			obrazovnaUstanova = (ObrazovnaUstanova) objectInputStream.readObject();

			fileInputStream.close();
			objectInputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


	}

	public void errorMsg(String textMsgErr) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Pogreška");
		alert.setHeaderText("Pogreška kod unosa zapisa!");
		alert.setContentText(textMsgErr);
		alert.showAndWait();
	}

	public void infoMsg(String textMsgInfo) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacion");
		alert.setHeaderText("Moral bi ti nekaj reci");
		alert.setContentText(textMsgInfo);

		alert.showAndWait();
	}

}
