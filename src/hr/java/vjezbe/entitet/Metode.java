package hr.java.vjezbe.entitet;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.BrojPredmetaPoProfesoru;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.NijeSavrseniString;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;


/**
 * Predstavlja entitet metoda
 * @author Filip
 *
 */
public class Metode {

	private static final Logger logger = LoggerFactory.getLogger(Metode.class);
	public static final int BROJ_PROFESORA = 2;
	public static final int BROJ_PREDMETA = 3;
	public static final int BROJ_ISPITA = 2;
	public static final int BROJ_STUDENTA = 3;
	public static final int BROJ_USTANOVA = 1;
	public static final String FORMAT_DATE_TIME = "dd.MM.yyyy.'T'HH:mm";
	public static final String FORMAT_DATE = "dd.MM.yyyy.";

	/**
	 * Prima skener kojim skeniramo podatke upitom korisnika te zapisujemo u objekt te na kraju objekte postavljamo u polje objekata te vracamo
	 * @param scanner
	 * @return
	 */
	public List<Profesor> fillProfesor(Scanner scanner) {

		MetodeTryCatch metodeTryCatch = new MetodeTryCatch();
		List<Profesor> listaProfesor = new ArrayList<>();
		long someId = 0;

		String sifraProfesor = "";
		String imeProfesor = "";
		String prezimeProfesor = "";
		String titulaProfesora = "";
		String msgProfesorSifra = "Unesite šifru profesora:  ";
		String msgProfesorName = "Unesite ime profesora: ";
		String msgProfesorSurname = "Unesite prezime profesora: ";
		String msgProfesorTitula = "Unesite titulu profesora: ";
		boolean keepLoop = false;
		Profesor profesor2 = null;

		for(int i = 0; i < BROJ_PROFESORA; i++) {

			System.out.println("Unesite " + (i + 1) + "." + " profesora: ");
			sifraProfesor = emptyCheckerString(scanner, msgProfesorSifra);
			logger.info("Korisnik je unijo sljedeæu šifru za " +  (i + 1) + ". profesora: " + sifraProfesor);

			while(!metodeTryCatch.checkIfStringIsInteger(sifraProfesor)) {
				logger.error("Korisnik je krivo unio šifru za profesora.");
				System.out.println("Samo brojevi dozvoljeni!");
				sifraProfesor = emptyCheckerString(scanner, msgProfesorSifra);
				logger.info("Korisnik je unijo sljedeæu šifru za " +  (i + 1) + ". profesora: " + sifraProfesor);
			}

			// treba otkriti kako da pregleda sve u arraylisti al pritom da ne baca indexoutofbound
			//		for (int k = 0; k < BROJ_PROFESORA && listaProfesor.contains(listaProfesor.get(k)); k++) {
			//				profesor2 = listaProfesor.get(k);
			//					while (profesor2.getSifra().equals(sifraProfesor)) {
			//						logger.error("Sifra koju je korisnik unio je veæ zauzeta");
			//						System.out.println("Ta sifra ti je vec zauzeta! Daj drugu neku.");
			//						sifraProfesor = emptyCheckerString(scanner, msgProfesorSifra);
			//						logger.info("Korisnik je unio sljedeæu šifru: " + sifraProfesor);
			//
			//				}
			//
			//			}
			do {
				try {

					titulaProfesora = emptyCheckerString(scanner, msgProfesorTitula);
					checkIfAlpha(titulaProfesora);
					//					checkIfLength(titulaProfesora);
					logger.info("Korisnik je unio sljedeæu vrijednost za titulu: " + titulaProfesora);
					keepLoop = false;

				} catch (NijeSavrseniString e) {
					System.out.println(e.getMessage());
					keepLoop = true;
				}
			} while (keepLoop);

			do {
				try {
					imeProfesor = emptyCheckerString(scanner, msgProfesorName);
					checkIfAlpha(imeProfesor);
					logger.info("Korisnik je unio sljedeæu vrijednost za ime profesora: " + imeProfesor);
					keepLoop = false;

				} catch (NijeSavrseniString e) {
					System.out.println(e.getMessage());
					keepLoop = true;
				}
			} while (keepLoop);

			prezimeProfesor = emptyCheckerString(scanner, msgProfesorSurname);
			logger.info("Korisnik je unio sljedeæu vrijednost za prezime profesora: " + prezimeProfesor);

			listaProfesor.add(i, new Profesor(sifraProfesor, imeProfesor, prezimeProfesor, titulaProfesora, someId));

		}
		return  listaProfesor;
	}


	/**
	 * Prima polje profesora te skener kojim skeniramo podatke upitom korisnika te zapisujemo u objekt te na kraju objekte postavljamo u polje objekata te vracamo
	 * @param scanner
	 * @param profesorLista
	 * @return
	 */
	public List<Predmet> fillPredmet(Scanner scanner, List<Profesor> profesorLista) {

		List<Predmet> listaPredmeta = new ArrayList<>();
		Profesor profesorOdabir;
		String sifraPredmet = "";
		String nazivPredmet = "";
		Integer brojEctsBodovaPredmet = 0;
		Integer studentPredmetInt = 0;
		String msgChooseTeach = "Odaberite profesora: ";
		long id = 0;



		for(int i = 0; i < BROJ_PREDMETA; i++) {

			int h = 1;
			System.out.println("Unesite " + (i + 1) + "." + " predmet: ");

			String msgPredmetSifra = "Unesite šifru predmeta: ";
			sifraPredmet = emptyCheckerString(scanner, msgPredmetSifra);
			logger.info("Korisnik je unio sljedeæu šifru za " + (i + 1) + ". predmet :" + sifraPredmet);

			//			treba otkriti kako da pregleda sve u arraylisti al pritom da ne baca indexoutofbound
			//			for (int k = 0; k < predmet.length && predmet[k] != null; k++) {
			//				Predmet predmet2 = predmet[k];
			//				while (predmet2.getSifra().equals(sifraPredmet)) {
			//					logger.error("Sifra koju je korisnik unio je veæ zauzeta.");
			//					System.out.println("Nije dostupna unesena šifra. Probaj drugu.");
			//					sifraPredmet = emptyCheckerString(scanner, msgPredmetSifra);
			//					logger.info("Korisnik je unio sljedeæu šifru za " + (i + 1) + ". predmet :" + sifraPredmet);
			//				}
			//
			//			}

			String msgPredmetName = "Unesite naziv predmeta: ";
			nazivPredmet = emptyCheckerString(scanner, msgPredmetName);

			//			treba otkriti kako da pregleda sve u arraylisti al pritom da ne baca indexoutofbound
			//			for (int j = 0; j < predmet.length && predmet[j] != null; j++) {
			//				Predmet predmet2 = predmet[j];
			//				while (predmet2.getNaziv().equals(nazivPredmet)) {
			//					logger.error("Naziv predmeta koju je korisnik unio je veæ zauzeta.");
			//					System.out.println("Ime je veæ unešeno. Probaj drugo.");
			//					nazivPredmet = emptyCheckerString(scanner, msgPredmetName);
			//					logger.info("Korisnik je unio sljedeæi naziv za " + (i + 1) + ". predmet :" + sifraPredmet);
			//				}
			//
			//			}
			String msgPredmetECTS = "Unesite broj ECTS bodova za predmet " + nazivPredmet + ":";
			brojEctsBodovaPredmet = emptyCheckerInteger(scanner, msgPredmetECTS);
			logger.info("Korisnik je unio sljedeæe ECTS bodove za " + (i + 1) + ". predmet :" + sifraPredmet);

			System.out.println("Odaberite profesora: ");
			for (Profesor profac : profesorLista) {
				System.out.println(h++ + ". " + profac.getIme() + " " + profac.getPrezime());
			}
			Integer odabirProfesor = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseTeach);
			if (odabirProfesor < profesorLista.size() && odabirProfesor != 0) {
				logger.info("Korisnik je odabrao sljedeæeg profesora " + profesorLista.get(odabirProfesor - 1).getPrezime() + " " + profesorLista.get(odabirProfesor - 1).getIme());
			}

			while (odabirProfesor > BROJ_PROFESORA || odabirProfesor <= 0) {
				logger.error("Korisnik je unio nedozvoljen broj odabira profesora");
				System.out.println("Nedozvoljen broj.");
				System.out.println("Odaberite profesora: ");
				odabirProfesor = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseTeach);
				if (odabirProfesor < profesorLista.size() && odabirProfesor != 0) {
					logger.info("Korisnik je odabrao sljedeæeg profesora " + profesorLista.get(odabirProfesor - 1).getPrezime() + " " + profesorLista.get(odabirProfesor - 1).getIme());
				}
			}

			profesorOdabir = profesorLista.get(odabirProfesor - 1);
			System.out.println("Vaš odabir profesora je " + odabirProfesor + ". " + profesorLista.get(odabirProfesor - 1).getTitula() + "."
					+ profesorLista.get(odabirProfesor - 1).getIme() + " " + profesorLista.get(odabirProfesor - 1).getPrezime());

			//			String msgPredmetBrStud = "Unesite broj studenta za predmet " + nazivPredmet + ":";
			//			studentPredmetInt = emptyCheckerInteger(scanner, msgPredmetBrStud);
			//			logger.info("Korisnik je unio broj" + studentPredmetInt + " za broj studenata.");
			//	studentPredmet = new Student[studentPredmetInt];
			//			Integer ukupanBrojStudentaPoPredmetu =+ studentPredmetInt;

			//		List<Student> listaStudentaPoPredmetu = new ArrayList<>(studentPredmetInt);

			//	Set<Student> setStudentaPoPredmetu = new HashSet<>(studentPredmetInt);


			listaPredmeta.add(i, new Predmet(sifraPredmet, nazivPredmet, brojEctsBodovaPredmet, profesorOdabir, id));

		}

		return listaPredmeta;
	}


	public Map<Profesor, List<Predmet>> createProfPredMap(List<Predmet> predmet, List<Profesor> profesor){

		Map<Profesor, List<Predmet>> mapaProfPred = new HashMap<>();


		for (int j = 0; j < profesor.size(); j++) {
			List<Predmet> predmetPoProfesoru = new ArrayList<>();
			for (int i = 0; i < predmet.size(); i++) {
				Predmet predmet2 = predmet.get(i);
				if (predmet.get(i).getNositelj().getPrezime().equals(profesor.get(j).getPrezime()) && predmet.get(i).getNositelj().getIme().equals(profesor.get(j).getIme())) {
					predmetPoProfesoru.add(predmet2);
				}
			}

			checkNumberOfPredOnProf(predmetPoProfesoru);
			for (int i = 0; i < predmetPoProfesoru.size(); i++) {
				for (int k = 0; k < profesor.size(); k++) {
					Profesor prof2 = profesor.get(k);
					if (prof2.getPrezime().equals(predmetPoProfesoru.get(i).getNositelj().getPrezime()) && prof2.getIme().equals(predmetPoProfesoru.get(i).getNositelj().getIme())) {
						mapaProfPred.put(prof2, predmetPoProfesoru);
					}
				}
			}
		}

		return mapaProfPred;
	}


	/**
	 * Prima polje predmet te skener kojim skeniramo podatke upitom korisnika te zapisujemo u objekt te na kraju objekte postavljamo u polje objekata te vracamo
	 * @param scanner
	 * @param predmetLista
	 * @return
	 */
	public List<Student> fillStudent(Scanner scanner, List<Predmet> predmetLista) {

		int sumaStudenta = 0;
		long someId = 0;
		String imeStudent = "";
		String prezimeStudent = "";
		String jmbagStudent = "";
		LocalDate datumRodenjaStudenta = null;
		Integer brojStudentaPoPred = 0;

		int ukupanBrojStud = 0;
		//		int lengthStudentObjArray = 0;

		//		List<Student> students = new ArrayList<>();
		//		for (Predmet predmetObjekt : predmetLista) {
		//			try {
		//				lengthStudentObjArray = getCapacity(students);
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//			sumaStudenta += lengthStudentObjArray;
		//		}

		for (int i = 0; i < predmetLista.size(); i++) {
			String msgNmbrStuds = "Unesite broj studenata za " + (i + 1) + ". predmet:";
			brojStudentaPoPred = emptyCheckerInteger(scanner, msgNmbrStuds);
			ukupanBrojStud = brojStudentaPoPred + ukupanBrojStud;
		}


		List<Student> studentLista = new ArrayList<Student>();

		for(int i = 0; i < ukupanBrojStud; i++) {

			System.out.println("Unesite " + (i + 1) + "." + " studenta:");


			String msgStudentName = "Unesite ime studenta: ";
			imeStudent = emptyCheckerString(scanner, msgStudentName);
			logger.info("Korisnik je unio sljedeæe ime za " + (i + 1) + ". studenta: " + imeStudent);

			String msgStudentSurname = "Unesite prezime studenta: ";
			prezimeStudent = emptyCheckerString(scanner, msgStudentSurname);
			logger.info("Korisnik je unio sljedeæe prezime za " + (i + 1) + ". studenta: " + imeStudent);

			String msgDateStudent = "Unesite datum roðenja za studenta " + prezimeStudent + " " + imeStudent + " u formatu (dd.MM.yyyy.)";
			datumRodenjaStudenta = dateChecker(scanner, msgDateStudent);
			logger.info("Korisnik je unio sljedeæi datum za " + (i + 1) + ". studenta: " + datumRodenjaStudenta);

			String msgStudentJMBAG = "Unesite JMBAG studenta: ";
			jmbagStudent = emptyCheckerString(scanner, msgStudentJMBAG);
			logger.info("Korisnik je unio sljedeæi JMBAG za " + (i + 1) + ". studenta: " + jmbagStudent);

			//			for (int k = 0; k < sumaStudenta && studentLista.get(k) != null; k++) {
			//				Student student2 = studentLista.get(k);
			//				while (student2.getJmbag().equals(jmbagStudent)) {
			//					logger.error("Korisnik je unio postojeæi JMBAG studenta.");
			//					System.out.println("JMBAG veæ postoji. Upiši ti drugi.");
			//					jmbagStudent = emptyCheckerString(scanner, msgStudentJMBAG);
			//					logger.info("Korisnik je unio sljedeæi JMBAG za " + (i + 1) + ". studenta: " + jmbagStudent);
			//				}
			//			}


			studentLista.add(new Student(imeStudent, prezimeStudent, jmbagStudent, datumRodenjaStudenta, someId));

		}

		return studentLista;
	}


	/**
	 * Prima polje predmet, polje student te skener kojim skeniramo podatke upitom korisnika te zapisujemo u objekt te na kraju objekte postavljamo u polje objekata te vracamo
	 * @param scanner
	 * @param predmetLista
	 * @param studentLista
	 * @return
	 */
	public List<Ispit> fillIspit(Scanner scanner, List<Predmet> predmetLista, List<Student> studentLista) {

		long id = 0;
		Predmet predmetIspita;
		Student studentIspita;
		List<Ispit> listaIspita = new ArrayList<>();
		Integer ocjenaIspita = 0;
		String msgChooseSubject = "Odaberite predmet: ";
		String msgChooseStudent = "Odaberite studenta: ";

		for (int i = 0; i < BROJ_ISPITA; i++) {

			int s = 1;
			int p = 1;

			System.out.println("Unesite " + (i+1) + ". " + "ispitni rok: ");

			System.out.println("Odaberite predmet: ");
			for (Predmet predmetObj : predmetLista) {

				System.out.println(p++ + ". " + predmetObj.getNaziv());
			}
			Integer odabirPredmeta = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseSubject);
			logger.info("Korisnik je unio sljedeæu vrijednost za odabir predmeta na " + (i + 1) + ". ispitnom roku: " + odabirPredmeta);
			while (odabirPredmeta > predmetLista.size() || odabirPredmeta <= 0) {
				logger.error("Korisnik je unio nepostojeæi ispitni rok.");
				System.out.println("Morate odabrati jedan od ponuðenih predmeta.");
				System.out.println("Odaberite predmet: ");
				odabirPredmeta = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseSubject);
				if (odabirPredmeta < predmetLista.size() && odabirPredmeta != 0) {
					logger.info("Korisnik je unio sljedeæu vrijednost za odabir predmeta na " + (i + 1) + ". ispitnom roku: " + odabirPredmeta);
				}
			}
			predmetIspita = predmetLista.get(odabirPredmeta - 1);
			System.out.println("Vaš odabir predmeta je " + odabirPredmeta + ". " + predmetLista.get(odabirPredmeta - 1).getNaziv());
			if (odabirPredmeta < predmetLista.size() && odabirPredmeta != 0) {
				logger.info("Korisnik je unio sljedeæu vrijednost za odabir predmeta na " + (i + 1) + ". ispitnom roku: " + odabirPredmeta);
			}
			System.out.println("Odaberite studenta: ");
			for (Student studentObj : studentLista)
			{
				System.out.println(s++ + ". " + studentObj.getIme() + " " + studentObj.getPrezime());
			}
			Integer odabirStudenta = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseStudent);
			while (odabirStudenta > studentLista.size() || odabirStudenta == 0) {
				logger.error("Korisnik je unio nepostojeæi broj za odabir studenta.");
				System.out.println("Morate odabrati nešto od ponuðenog.");
				System.out.println("Odaberite studenta: ");
				odabirStudenta = emptyCheckerIntegerWOFirstMsg(scanner, msgChooseStudent);
			}
			studentIspita = studentLista.get(odabirStudenta - 1);
			logger.info("Korisnik je odabrao sljedeæeg studenta na " + (i + 1) + ". ispitnom roku: " + studentLista.get(odabirStudenta - 1).getIme() + " " + studentLista.get(odabirStudenta - 1).getPrezime());
			//	predmetLista.get(odabirPredmeta - 1).setStudent(Arrays.copyOf(studentLista, 1));
			//	predmetLista.get(odabirPredmeta - 1).setStudent(Arrays.copyOfRange(studentLista, odabirStudenta - 1, odabirStudenta));

			List<Student> chosenStudents = new ArrayList<>();
			Set<Student> chosenStudentsSet = new HashSet<>();
			chosenStudentsSet.add(studentIspita);
			chosenStudents.add(studentIspita);

			predmetLista.get(odabirPredmeta - 1).setStudent(chosenStudentsSet);
			System.out.println("Vaš odabir studenta je " + odabirStudenta + ". " + studentLista.get(odabirStudenta - 1).getIme() + " " + studentLista.get(odabirStudenta - 1).getPrezime());

			// IN PROGRESS
			//			for (Predmet predmet2 : predmet) {
			//				 Arrays.sort(predmet, (a, b) -> a.getNaziv().compareTo(b.getNaziv()));
			//					System.out.println(r++ + ". " + Arrays.asList(predmet2.getNaziv().replace("[]", "")));
			//			}
			//			Arrays.sort(predmet, (a, b) -> a.getNaziv().compareTo(b.getNaziv()));
			//			System.out.println(r++ + ". " + Arrays.asList(predmet.toString()));

			String msgIspitOcj = "Unesite ocjenu na ispitu (1-5): ";
			ocjenaIspita = emptyCheckerInteger(scanner, msgIspitOcj);

			while (ocjenaIspita > 5 || ocjenaIspita == 0) {
				logger.error("Korisnik je krivo unio ocjenu na ispitu.");
				System.out.println("Krivo unešena ocjena!. Pokušaj ponovo.");
				ocjenaIspita = emptyCheckerInteger(scanner, msgIspitOcj);
			}

			logger.info("Korisnik je unio sljedeæu ocjenu na " + (i + 1) + ". ispitnom roku: " + ocjenaIspita);
			String msgDatumIVrijemeIspit = "Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):";
			LocalDateTime datumIVrijemeIspita = dateTimeChecker(scanner, msgDatumIVrijemeIspit);
			logger.info("Korisnik je unio sljedeæi datum na " + (i + 1) + ". ispitnom roku: " + datumIVrijemeIspita);

			listaIspita.add(i, new Ispit(predmetIspita, studentIspita, ocjenaIspita, datumIVrijemeIspita, id));

		}
		return listaIspita;
	}


	/**
	 * Prima polje predmet, polje profesor, polje student, polje ispit te skener kojim skeniramo podatke upitom korisnika
	 * te zapisujemo u objekt te na kraju objekte postavljamo u polje objekata te vracamo
	 * @param scanner
	 * @param predmetiObrazovneUstanove
	 * @param profesoriObrazovneUstanove
	 * @param studentiObrazovneUstanove
	 * @param ispitiObrazovneUstanove
	 * @return
	 */
	public List<ObrazovnaUstanova> fillObrazovnaUstanova(Scanner scanner, List<Predmet> predmetiObrazovneUstanove, List<Profesor> profesoriObrazovneUstanove,
			List<Student> studentiObrazovneUstanove, List<Ispit> ispitiObrazovneUstanove) {

		String nazivObrUst;
		Integer biranjeUstanove;
		int l = 1;
		long someId = 0;
		Integer ocjenaPismenogRada;
		Integer ocjenaObraneDiplomskogRada;
		Integer ocjenaobraneZavrsnogRada;

		List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();
		ObrazovnaUstanova obrazovnaUstanova = null;

		List<Student> theChosenOnesStudents = new ArrayList<>();

		for (int i = 0; i < BROJ_USTANOVA; i++) {

			String msgBiranje = "Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti" + " (1 - " + VeleucilisteJave.class.getSimpleName() +
					", 2 - " + FakultetRacunarstva.class.getSimpleName() + "): ";
			biranjeUstanove = emptyCheckerInteger(scanner, msgBiranje);

			while (biranjeUstanove > 2 && biranjeUstanove <= 0) {
				logger.error("Korisnik je krivo odabrao obrazovnu ustanovu: " + biranjeUstanove);
				System.out.println("Krivo odabran broj. Probaj ponovo!");
				biranjeUstanove = emptyCheckerInteger(scanner, msgBiranje);
			}
			logger.info("Korisnik je odabrao: " + biranjeUstanove);

			String msgNazivObrUst = "Unesite naziv obrazovne ustanove:";
			nazivObrUst = emptyCheckerString(scanner, msgNazivObrUst);
			logger.info("Korisnik je unio sljedeæi naziv za obrazovnu ustanovu: " + nazivObrUst);

			if (biranjeUstanove == 1) {

				for (int j = 0; j < studentiObrazovneUstanove.size(); j++) {

					try {

						checkForNegativeExam(ispitiObrazovneUstanove.get(j));

						//					Arrays.copyOf(theChosenOnesStudents, l);

						theChosenOnesStudents.add(ispitiObrazovneUstanove.get(j).getStudent());

						obrazovnaUstanova = new VeleucilisteJave(predmetiObrazovneUstanove, profesoriObrazovneUstanove, theChosenOnesStudents, ispitiObrazovneUstanove, someId);

						obrazovnaUstanova.setNaziv(nazivObrUst);

						String msgOcjenaPism = "Unesite ocjenu završnog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ":";
						ocjenaPismenogRada = emptyCheckerInteger(scanner, msgOcjenaPism);
						logger.info("Korisnik je unio sljedeæu ocjenu zavrsnog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": " + ocjenaPismenogRada);

						String msgOcjenaZavrRad = "Unesite ocjenu obrane završnog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ":";
						ocjenaobraneZavrsnogRada = emptyCheckerInteger(scanner, msgOcjenaZavrRad);
						logger.info("Korisnik je unio sljedeæu ocjenu obrane diplomskog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": " + ocjenaobraneZavrsnogRada);

						List<Ispit> ispitiJednogStudenta = ((VeleucilisteJave)obrazovnaUstanova).filtrirajIspitePoStudentu(ispitiObrazovneUstanove, theChosenOnesStudents.get(l - 1));
						logger.info("Ispiti jednog studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime()
								+ " su " + ispitiJednogStudenta.toString());

						BigDecimal konacnaOcjenaBd = ((VeleucilisteJave)obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(ispitiJednogStudenta, ocjenaPismenogRada, ocjenaobraneZavrsnogRada);
						logger.info("Konaèna ocjena studija studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime()
								+ " je " + konacnaOcjenaBd);

						System.out.println("Konaèna ocjena studija studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + " je " + konacnaOcjenaBd);

						l++;
					} catch (NemoguceOdreditiProsjekStudentaException e) {
						logger.info(e.getMessage(), e);
						System.out.println(e.getMessage());
					}

				}

				Student student = ((VeleucilisteJave)obrazovnaUstanova).odrediNajuspjesnijegStudentaNaGodini(ispitiObrazovneUstanove.get(i).getDatumIVrijeme().getYear());
				logger.info("Najbolji student " + ispitiObrazovneUstanove.get(i).getDatumIVrijeme().getDayOfYear() + ". godine je " + student.getIme() + " " + student.getPrezime() + " " + student.getJmbag());
				System.out.println("Najbolji student " + ispitiObrazovneUstanove.get(i).getDatumIVrijeme().getDayOfYear() + ". godine je " + student.getIme() + " " + student.getPrezime() + " " + student.getJmbag());


			} else {

				try {

					checkForAgeOfStudents(studentiObrazovneUstanove);

					for (int j = 0; j < studentiObrazovneUstanove.size(); j++) {

						try {

							checkForNegativeExam(ispitiObrazovneUstanove.get(j));

							//		Arrays.copyOf(theChosenOnesStudents, l);

							theChosenOnesStudents.add(l - 1, ispitiObrazovneUstanove.get(j).getStudent());

							obrazovnaUstanova = new FakultetRacunarstva(predmetiObrazovneUstanove, profesoriObrazovneUstanove, theChosenOnesStudents, ispitiObrazovneUstanove, someId);

							obrazovnaUstanova.setNaziv(nazivObrUst);

							String msgOcjenaPism = "Unesite ocjenu diplomskog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": ";
							ocjenaPismenogRada = emptyCheckerInteger(scanner, msgOcjenaPism);
							logger.info("Korisnik je unio sljedeæu ocjenu diplomskog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": " + ocjenaPismenogRada);

							String msgOcjenaZavrRad = "Unesite ocjenu obrane diplomskog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": ";
							ocjenaObraneDiplomskogRada = emptyCheckerInteger(scanner, msgOcjenaZavrRad);
							logger.info("Korisnik je unio sljedeæu ocjenu obrane diplomskog rada za studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime() + ": " + ocjenaObraneDiplomskogRada);

							List<Ispit> ispitiJednogStudenta = ((FakultetRacunarstva)obrazovnaUstanova).filtrirajIspitePoStudentu(ispitiObrazovneUstanove, theChosenOnesStudents.get(l - 1));
							logger.info("Ispiti jednog studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime()
									+ " su " + ispitiJednogStudenta.toString());

							BigDecimal bigDecimal = ((FakultetRacunarstva)obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(ispitiJednogStudenta, ocjenaPismenogRada, ocjenaObraneDiplomskogRada);
							logger.info("Konaèna ocjena studija studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime()
									+ " je " + bigDecimal);
							System.out.println("Konaèna ocjena studija studenta " + theChosenOnesStudents.get(l - 1).getIme() + " " + theChosenOnesStudents.get(l - 1).getPrezime()
									+ " je " + bigDecimal);
							l++;

						} catch (NemoguceOdreditiProsjekStudentaException e) {
							logger.info(e.getMessage(), e);
							System.out.println(e.getMessage());
						}

					}

					obrazovneUstanove.add(obrazovnaUstanova);

					for (ObrazovnaUstanova obrazovnaUstanova2 : obrazovneUstanove) {

						Student najuspjesnijiStudent = ((FakultetRacunarstva)obrazovnaUstanova2).odrediNajuspjesnijegStudentaNaGodini(obrazovneUstanove.get(i).getIspit().get(0).getDatumIVrijeme().getYear());
						logger.info("Najbolji student 2018 godini je " + najuspjesnijiStudent.getIme() + " " + najuspjesnijiStudent.getPrezime());
						System.out.println("Najbolji student 2018 godini je " + najuspjesnijiStudent.getIme() + " " + najuspjesnijiStudent.getPrezime());

						Student rektorovaStudent = ((FakultetRacunarstva)obrazovnaUstanova2).odrediStudentaZaRektorovuNagradu();
						logger.info("Student koji je osvojio rektorovu nagradu je " + rektorovaStudent + " JMBAG: " + rektorovaStudent.getJmbag());
						System.out.println("Student koji je osvojio rektorovu nagradu je " + rektorovaStudent + " JMBAG: " + rektorovaStudent.getJmbag());
					}


				} catch (PostojiViseNajmladjihStudenataException e) {
					logger.info(e.getMessage(), e);
					System.out.println(e.getMessage());
				}

			}

		}

		return obrazovneUstanove;

	}



	/**
	 * Prima polje ispita i ispisuje one studenta koji su na ispitu postigli ocjenu odlicans
	 * @param listaIspitStudenta
	 */
	public void checkerOcjenaStudenta(List<Ispit> listaIspitStudenta) {

		String ocjenaIspitString;
		Ocjena ocjena = Ocjena.values()[0];

		for (Ispit ispiti : listaIspitStudenta) {
			if (ispiti.getOcjena() == 5) {
				switch (ispiti.getOcjena()) {
				case 1:
					ocjenaIspitString = ocjena.getOpis();
					break;
				case 2:
					ocjenaIspitString = ocjena.getOpis();
					break;
				case 3:
					ocjenaIspitString = ocjena.getOpis();
					break;
				case 4:
					ocjenaIspitString = ocjena.getOpis();
					break;
				case 5:
					ocjenaIspitString = ocjena.getOpis();
					break;
				default:
					ocjenaIspitString = ocjena.getOpis();
					break;
				}
				System.out.println("Student " + ispiti.getStudent().getIme() + " " + ispiti.getStudent().getPrezime() + " je ostvario ocjenu " + ocjenaIspitString
						+ " na predmetu " + ispiti.getPredmet().getNaziv());
			}
		}
	}


	/**
	 * Prima skener, i poruku koju zelimo ispisati ukoliko korisnik ostavi prazno polje kod upisa podataka tipa string
	 * @param scanner
	 * @param message
	 * @return
	 */
	public String emptyCheckerString(Scanner scanner, String message) {

		String scanString;
		System.out.println(message);
		scanString = scanner.nextLine();

		while (scanString.length() == 0) {

			System.out.println("Prazno polje!");
			System.out.println(message);
			scanString = scanner.nextLine();
		}
		return scanString;
	}


	/**
	 * Prima skener i poruku koju zelimo ispisati ukoliko korisnik ostavi prazno polje kod upisa podataka tipa integer, te baca gresku ukoliko ne unese brojku
	 * @param scanner
	 * @param message
	 * @return
	 */
	public Integer emptyCheckerInteger(Scanner scanner, String message) {

		String scanString = "";
		Integer scanInteger = 0;
		boolean keepLoop = false;

		System.out.println(message);
		scanString = scanner.nextLine();

		do {
			while (scanString.length() == 0) {

				System.out.println("Prazno polje!");
				System.out.println(message);
				scanString = scanner.nextLine();
			}

			try {

				scanInteger = Integer.parseInt(scanString);
				keepLoop = false;

			} catch (NumberFormatException e) {

				System.out.println("Unijeli ste krivi format. Probaj mozda sa brojkama?");
				scanString = scanner.nextLine();
				keepLoop = true;
			}
		} while (keepLoop);

		return scanInteger;

	}

	/**
	 * Prima skener i poruku koju zelimo da ispise ukoliko korisnik unese krivi format datuma ili netocan datum
	 * te vraca prihvaceni datum
	 * @param scanner
	 * @param msg
	 * @return
	 */
	public LocalDate dateChecker(Scanner scanner, String msg) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);

		String date = "01.01.1925.";
		LocalDate todayParsed = LocalDate.parse(date, formatter);
		System.out.println(msg);
		String datumRodenjaStudentaString = scanner.nextLine();
		LocalDate datumRodenjaStudenta = null;
		boolean keepLoop = false;

		do {
			try {

				datumRodenjaStudenta = LocalDate.parse(datumRodenjaStudentaString, formatter);
				keepLoop = false;

			} catch (DateTimeParseException e) {
				System.out.println("Krivi unos formata za datum");
				System.out.println(msg);
				datumRodenjaStudentaString = scanner.nextLine();
				keepLoop = true;
			}

		} while (keepLoop);

		while (datumRodenjaStudenta.isBefore(todayParsed)) {
			System.out.println("Prestar si ti za studiranje! Pokušaj opet.");
			System.out.println(msg);
			datumRodenjaStudentaString = scanner.nextLine();

			do {

				try {
					datumRodenjaStudenta = LocalDate.parse(datumRodenjaStudentaString, formatter);
					keepLoop = false;

				} catch (DateTimeException e) {
					System.out.println("Krivi unos formata za datum");
					System.out.println(msg);
					datumRodenjaStudentaString = scanner.nextLine();
					keepLoop = true;
				}

			} while (keepLoop);
		}



		return datumRodenjaStudenta;
	}

	/**
	 * Prima skener i poruku koju zelimo ispisati ukoliko dode do upisa krivog formata datuma i vremena ili pogresnog unosa podataka
	 * te vraca prihvaceni datum i vrijeme
	 *
	 * @param scanner
	 * @param msg
	 * @return
	 */
	public LocalDateTime dateTimeChecker(Scanner scanner, String msg) {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
		LocalDateTime today = LocalDateTime.now(ZoneId.systemDefault());

		String todayformated = today.format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME));
		LocalDateTime todayParsed = LocalDateTime.parse(todayformated, formatter);
		boolean keepLoop = true;

		System.out.println(msg);
		String datumIVrijemeIspitaString = scanner.nextLine();
		LocalDateTime datumIVrijemeIspita = null;

		do {

			try {

				datumIVrijemeIspita = LocalDateTime.parse(datumIVrijemeIspitaString, formatter);
				keepLoop = false;

			} catch (DateTimeException e) {

				System.out.println("Krivi unos formata za datum.");
				System.out.println(msg);
				datumIVrijemeIspitaString = scanner.nextLine();
				keepLoop = true;

			}

		} while (keepLoop);

		while (datumIVrijemeIspita.isAfter(todayParsed)) {
			System.out.println("Pa kak' možeš unijet datum poslije današnjeg za polozeni ispit? Daj se skoncentriraj.");
			System.out.println(msg);
			datumIVrijemeIspitaString = scanner.nextLine();
			do {

				try {

					datumIVrijemeIspita = LocalDateTime.parse(datumIVrijemeIspitaString, formatter);
					keepLoop = false;

				} catch (DateTimeException e) {

					System.out.println("Krivi unos formata za datum.");
					System.out.println(msg);
					datumIVrijemeIspitaString = scanner.nextLine();
					keepLoop = true;
				}

			} while (keepLoop);
		}


		return datumIVrijemeIspita;
	}


	/**
	 * Prima studenta i polje studenta. Studenta kojeg unesemo dodajemo u predano polje te nam vraca polje sa unesenim studentom.
	 * @param student
	 * @param studentArray
	 * @return
	 */
	public ArrayList<Student> addStudent(Student student, ArrayList<Student> studentArray) {


		//	ArrayUtils.add(array, element)

		for (int i = 0; i < studentArray.size(); i++) {
			studentArray.add(i, student);

		}
		return studentArray;
	}


	/**
	 * Prima polje studenta u kojem zelimo maknuti objekt student na prvom mjestu tog polja i vraca to novo polje
	 * @param student
	 * @return
	 */
	public Student[] resetStudentArray(Student[] student) {

		student = ArrayUtils.removeElement(student, 0);

		return student;

	}


	public  Ispit[] filtrirajIspitePoStudentu(Ispit[] ispit, Student student) {

		Integer counter = 0;

		for (int i = 0; i < ispit.length; i++) {
			if (ispit[i].getStudent().getIme().equals(student.getIme()) && ispit[i].getStudent().getPrezime().equals(student.getPrezime())) {
				counter++;
			}
		}

		Ispit[] ispit3 = new Ispit[counter];

		for (int i = 0; i < ispit.length; i++) {

			if (ispit[i].getStudent().getIme().equals(student.getIme()) && ispit[i].getStudent().getPrezime().equals(student.getPrezime())) {
				Ispit ispit2 = ispit[i];
				ispit3[i] = ispit2;
			}
		}

		return ispit3;
	}


	//	public void checkForNegativeExams(Ispit[] examForCheck) throws NemoguceOdreditiProsjekStudentaException {
	//
	//		boolean itPassed = true;
	//
	//		for (int i = 0; i < examForCheck.length; i++) {
	//			Ispit ispit = examForCheck[i];
	//			if (ispit.getOcjena() == 1) {
	//				itPassed = false;
	//
	//				throw new NemoguceOdreditiProsjekStudentaException("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() +
	//						" zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
	//			}
	//			i++;
	//		}
	//
	//
	//
	//	}

	/**
	 * Predamo ispit koji zelimo provjeriti da li ima negativnih ispita. Ukoliko ima bacit ce gresku da je nemoguce odredit prosjek studenta
	 * @param examForCheck
	 * @throws NemoguceOdreditiProsjekStudentaException
	 */
	public void checkForNegativeExam(Ispit examForCheck) throws NemoguceOdreditiProsjekStudentaException {

		if (examForCheck.getOcjena() == 1) {
			throw new NemoguceOdreditiProsjekStudentaException("Student " + examForCheck.getStudent().getIme() + " " + examForCheck.getStudent().getPrezime() +
					" zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
		}
	}

	/**
	 * Predamo polje studenta koje zelimo provjerit i ukoliko ima vise mladjih jednakih datumom studenta bacit ce gresku da postoji vise mladjih studenta
	 * @param studentsForCheck
	 * @throws PostojiViseNajmladjihStudenataException
	 */
	public void checkForAgeOfStudents(List<Student> studentsForCheck) throws PostojiViseNajmladjihStudenataException {

		LocalDate najmladjiStudentDatum = null;
		int counter = 0;
		int youngestStudentIndex = 0;
		List<Integer> arrayOfYoungestStudentsIndex = new ArrayList<>();
		List<Student> youngBucks = new ArrayList<>();

		for (int i = 0; i < studentsForCheck.size(); i++) {
			Student student = studentsForCheck.get(i);
			if (najmladjiStudentDatum == null) {
				najmladjiStudentDatum = student.getDatumRodenja();
			}
			if (student.getDatumRodenja().isBefore(najmladjiStudentDatum) && i != 0) {
				najmladjiStudentDatum = student.getDatumRodenja();
			}
		}

		for (int i = 0; i < studentsForCheck.size(); i++) {
			Student student = studentsForCheck.get(i);
			if (najmladjiStudentDatum.isEqual(student.getDatumRodenja())) {
				//	if (i != 0) {
				youngestStudentIndex = findIndexForStudentArray(studentsForCheck, student);
				arrayOfYoungestStudentsIndex.add(youngestStudentIndex);
				youngBucks.add(counter, student);
				counter++;
				//	}
			}
		}

		String listString = youngBucks.stream()
				.map(Object::toString)
				.collect(Collectors.joining(", "));

		//		Student[] youngBucksWONulls = removeNullsInStudentArray(youngBucks);
		if (counter >= 2) {
			throw new PostojiViseNajmladjihStudenataException("Postoje više mlaðih studenata a to su sljedeæi" + listString); //Arrays.toString(youngBucks));
		}
	}

	/**
	 * Prima polje studenta i objekt student. Potrazit ce da li postoji taj objekt u tom polju, ako postoji vratit ce index gdje se nalazi u tom polju.
	 * @param studentArray
	 * @param student
	 * @return
	 */
	public int findIndexForStudentArray(List<Student> studentArray, Student student) {

		int theIndex = 0;

		for (int i = 0; i < studentArray.size(); i++) {
			if (studentArray.get(i) == student) {
				theIndex = i;
				break;
			}
		}
		return theIndex;
	}


	/**
	 * Prima polje ispit. U tom polju ako postoje nullovi, izbacit ce ih i vratit to novo polje ispit.
	 *
	 * @param ispitArrayWithNulls
	 * @return
	 */
	public Ispit[] removeNullsInIspitArray(Ispit[] ispitArrayWithNulls) {

		Ispit[] removedNulls = Arrays.stream(ispitArrayWithNulls)
				.filter(value -> value != null && value.getPredmet().getNaziv().length() > 0)
				.toArray(size -> new Ispit[size]);

		return removedNulls;
	}


	/**
	 * Prima polje student. U tom polju ako postoje nullovi, izbacit ce ih i vratit to novo polje studenta.
	 * @param studentArrayWithNulls
	 * @return
	 */
	public Student[] removeNullsInStudentArray(Student[] studentArrayWithNulls) {

		Student[] removedNulls = Arrays.stream(studentArrayWithNulls)
				.filter(value -> value != null && value.getIme().length() > 0)
				.toArray(size -> new Student[size]);

		return removedNulls;
	}

	/**
	 * Prima string koji provjera da li u njemu ima karaktera slova, ukoliko nade da nije karakter slova bacit ce gresku nije savrseni string.
	 * @param checkingString
	 * @throws NijeSavrseniString
	 */
	public void checkIfAlpha(String checkingString) {

		char[] chars = checkingString.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				throw new NijeSavrseniString("Samo slova su dozvoljena!");

			}
		}
	}

	/**
	 * Prima string koji zelimo provjerit da li je duljina 4 karaktera ili veca ako nije bacit ce gresku da nije savrsen string
	 * @param checkingString
	 * @throws NijeSavrseniString
	 */
	public void checkIfLength(String checkingString) {

		if (checkingString.length() < 4) {
			throw new NijeSavrseniString("Ma premalo ti je to karaktera.");
		}
	}

	/**
	 * Prima string koji zelimo provjerit tako da pregleda svaki karakter toga string i ako nije karakter slova vrati boolean false
	 * @param checkingString
	 * @return
	 */
	public boolean isAlpha(String checkingString) {

		char[] chars = checkingString.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;

			}
		}
		return true;
	}

	/**
	 * Prima skener i poruku koja ce se samo ispisati ako se dogodi greska odnosno ako je polje unosa prazno ili krivi format unosa
	 * te vraca uspjesan integer
	 * @param scanner
	 * @param message
	 * @return
	 */
	public Integer emptyCheckerIntegerWOFirstMsg(Scanner scanner, String message) {

		String scanString = "";
		Integer scanInteger = 0;
		boolean keepLoop = false;


		scanString = scanner.nextLine();

		do {
			while (scanString.length() == 0) {

				System.out.println("Prazno polje!");
				System.out.println(message);
				scanString = scanner.nextLine();
			}

			try {

				scanInteger = Integer.parseInt(scanString);
				keepLoop = false;

			} catch (NumberFormatException e) {

				System.out.println("Unijeli ste krivi format. Probaj mozda sa brojkama?");
				scanString = scanner.nextLine();
				keepLoop = true;
			}
		} while (keepLoop);

		return scanInteger;

	}

	static int getCapacity(List<?> l) throws Exception {
		Field dataField = ArrayList.class.getDeclaredField("elementData");
		dataField.setAccessible(true);
		return ((Object[]) dataField.get(l)).length;
	}

	public void printerMapValueProfPred(List<Profesor> profesor, List<Predmet> predmet, Map mapa) {

		for (int k = 0; k < mapa.keySet().size(); k++) {
			int z = 1;
			Profesor profesor2 = profesor.get(k);
			System.out.println("Profesor " + profesor2.getPrezime() + " " + profesor2.getIme() + " predaje sljedeæe predmete: ");

			for (int p = 0; p < predmet.size(); p++) {
				Predmet predmet2 = predmet.get(p);
				if (profesor2.getPrezime().equals(predmet2.getNositelj().getPrezime())) {
					System.out.println((z) + ") " + predmet2.getNaziv());
					z++;
				}
			}
		}
	}

	public void checkNumberOfPredOnProf(List<Predmet> predmet) {
		if (predmet.isEmpty()) {
			throw new BrojPredmetaPoProfesoru();
		}

	}

	public void checkNumberOfStudOnPred(List<Predmet> predmet) {
		for (Predmet predmet2 : predmet) {
			if (predmet2.getStudent().size() >= 1) {

			}
		}
	}


	public <T> T fillObrazovnaUstanovaTest(Scanner scanner, List<Predmet> predmetiObrazovneUstanove, List<Profesor> profesoriObrazovneUstanove,
			List<Student> studentiObrazovneUstanove, List<Ispit> ispitiObrazovneUstanove) {

		String nazivObrUst = "";
		Integer biranjeUstanove = 0;
		Sveuciliste<ObrazovnaUstanova> sveuciliste = null;
		long someId = 0;
		ObrazovnaUstanova obrazovnaUstanova = null;
		List<Student> theChosenOnesStudents = new ArrayList<>();
		List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();
		Student najboljsiStud = null;

		for (int i = 0; i < ispitiObrazovneUstanove.size(); i++) {

			try {

				checkForNegativeExam(ispitiObrazovneUstanove.get(i));
				theChosenOnesStudents.add(ispitiObrazovneUstanove.get(i).getStudent());

			} catch (NemoguceOdreditiProsjekStudentaException e) {
				System.out.println(e.getMessage());
				logger.info(e.getMessage());
			}
		}

		String msgBiranje = "Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti" + " (1 - " + VeleucilisteJave.class.getSimpleName() +
				", 2 - " + FakultetRacunarstva.class.getSimpleName() + "): ";
		biranjeUstanove = emptyCheckerInteger(scanner, msgBiranje);

		while (biranjeUstanove > 2 && biranjeUstanove <= 0) {
			logger.error("Korisnik je krivo odabrao obrazovnu ustanovu: " + biranjeUstanove);
			System.out.println("Krivo odabran broj. Probaj ponovo!");
			biranjeUstanove = emptyCheckerInteger(scanner, msgBiranje);
		}
		logger.info("Korisnik je odabrao: " + biranjeUstanove);

		String msgNazivObrUst = "Unesite naziv obrazovne ustanove:";
		nazivObrUst = emptyCheckerString(scanner, msgNazivObrUst);
		logger.info("Korisnik je unio sljedeæi naziv za obrazovnu ustanovu: " + nazivObrUst);


		if (biranjeUstanove == 1) {

			obrazovnaUstanova = new VeleucilisteJave(predmetiObrazovneUstanove, profesoriObrazovneUstanove, theChosenOnesStudents, ispitiObrazovneUstanove, someId);
			obrazovnaUstanova.setNaziv(nazivObrUst);

		} else {

			obrazovnaUstanova = new FakultetRacunarstva(predmetiObrazovneUstanove, profesoriObrazovneUstanove, theChosenOnesStudents, ispitiObrazovneUstanove, someId);
			obrazovnaUstanova.setNaziv(nazivObrUst);

		}

	//	for (int i = 0; i < studentiObrazovneUstanove.size(); i++) {

			//obrazovneUstanove.add(obrazovnaUstanova);
			List<T> listaSveucilista = new ArrayList<>();

			if (obrazovnaUstanova instanceof VeleucilisteJave) {
				checkForObrUstBestStudVele(obrazovnaUstanova);
				najboljsiStud = ((VeleucilisteJave)obrazovnaUstanova).odrediNajuspjesnijegStudentaNaGodini(2018);
				System.out.println("Najuspijesniji student je bio " + najboljsiStud.getPrezime() + " " + najboljsiStud.getPrezime());

			}else {

				checkForObrUstBestStudFak(obrazovnaUstanova);
				najboljsiStud = ((FakultetRacunarstva)obrazovnaUstanova).odrediNajuspjesnijegStudentaNaGodini(2018);
				System.out.println("Najuspijesniji student je bio " + najboljsiStud.getPrezime() + " " + najboljsiStud.getPrezime());
			}
	//	}

		return (T) obrazovnaUstanova;

	}

	public void checkForObrUstBestStudVele(ObrazovnaUstanova obrazovnaUstanova) {

		Integer ocjenaPismenogRada = 0;
		Integer ocjenaobraneZavrsnogRada = 0;
		List<Student> student = obrazovnaUstanova.getStudent();
		Scanner scanner = new Scanner(System.in);

		for (int i = 0; i < student.size(); i++) {

			String msgOcjenaPism = "Unesite ocjenu završnog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ":";
			ocjenaPismenogRada = emptyCheckerInteger(scanner, msgOcjenaPism);
			logger.info("Korisnik je unio sljedeæu ocjenu zavrsnog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ": " + ocjenaPismenogRada);

			String msgOcjenaZavrRad = "Unesite ocjenu obrane završnog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ":";

			ocjenaobraneZavrsnogRada = emptyCheckerInteger(scanner, msgOcjenaZavrRad);

			logger.info("Korisnik je unio sljedeæu ocjenu obrane završnog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ": " + ocjenaobraneZavrsnogRada);

			List<Ispit> ispitiJednogStudenta = ((VeleucilisteJave)obrazovnaUstanova).filtrirajIspitePoStudentu(obrazovnaUstanova.getIspit() , student.get(i));
			logger.info("Ispiti jednog studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime()
					+ " su " + ispitiJednogStudenta.toString());

			BigDecimal konacnaOcjenaBd = ((VeleucilisteJave)obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(ispitiJednogStudenta, ocjenaPismenogRada, ocjenaobraneZavrsnogRada);
			logger.info("Konaèna ocjena završnog studija studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime()
					+ " je " + konacnaOcjenaBd);

			System.out.println("Konaèna ocjena završnog studija studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + " je " + konacnaOcjenaBd);

		}


	}

	public void checkForObrUstBestStudFak(ObrazovnaUstanova obrazovnaUstanova) {

		Integer ocjenaPismenogRada = 0;
		Integer ocjenaobraneDiplomRada = 0;
		List<Student> student = obrazovnaUstanova.getStudent();
		Scanner scanner = new Scanner(System.in);

		for (int i = 0; i < student.size(); i++) {



			String msgOcjenaPism = "Unesite ocjenu diplomskog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ":";
			ocjenaPismenogRada = emptyCheckerInteger(scanner, msgOcjenaPism);
			logger.info("Korisnik je unio sljedeæu ocjenu diplomskog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ": " + ocjenaPismenogRada);

			String msgOcjenaZavrRad = "Unesite ocjenu obrane diplomskog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ":";

			ocjenaobraneDiplomRada = emptyCheckerInteger(scanner, msgOcjenaZavrRad);

			logger.info("Korisnik je unio sljedeæu ocjenu obrane diplomskog rada za studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + ": " + ocjenaobraneDiplomRada);

			List<Ispit> ispitiJednogStudenta = ((FakultetRacunarstva)obrazovnaUstanova).filtrirajIspitePoStudentu(obrazovnaUstanova.getIspit() , student.get(i));
			logger.info("Ispiti jednog studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime()
					+ " su " + ispitiJednogStudenta.toString());

			BigDecimal konacnaOcjenaBd = ((FakultetRacunarstva)obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(ispitiJednogStudenta, ocjenaPismenogRada, ocjenaobraneDiplomRada);
			logger.info("Konaèna ocjena diplomskog studija studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime()
					+ " je " + konacnaOcjenaBd);

			System.out.println("Konaèna ocjena diplomskog studija studenta " + student.get(i).getIme() + " " + student.get(i).getPrezime() + " je " + konacnaOcjenaBd);

		}


	}

	public void printSubjectsWithTheirStudents(List<Predmet> predmet) {
		predmet.stream().filter(t -> t.getStudent() != null).forEach(s -> System.out.println("Predmet " + s.getNaziv() + " su upisali sljedeci studenti: \n" + s.getStudent().toString()));
	}

	public int calculateSumInList(List<Student> list) {
		return list.stream().mapToInt(s -> s.getIme().length() + s.getPrezime().length()).sum();
	}


}

