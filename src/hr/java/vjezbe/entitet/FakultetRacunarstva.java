package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

/**
 * Predstavlja entitet fakulteta koji naslijeduje obrazovnu ustanovu te implementira sucelje diplomski
 * @author Filip
 *
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

	/**
	 * Inicijalizira podatak o polju predmeta obrazovne ustanove, polju profesora obrazovne ustanove, polju studenta obrazovne ustanove, ispitu obrazovne ustanove
	 *
	 * @param predmetObrazovneUstanove
	 * @param profesorObrazovneUstanove
	 * @param studentObrazovneUstanove
	 * @param ispitObrazovneUstanove
	 */
	public FakultetRacunarstva(List<Predmet> predmetObrazovneUstanove, List<Profesor> profesorObrazovneUstanove,
			List<Student> studentObrazovneUstanove, List<Ispit> ispitObrazovneUstanove, long someId) {
		super(predmetObrazovneUstanove, profesorObrazovneUstanove, studentObrazovneUstanove, ispitObrazovneUstanove, someId);
		setId(someId);
		setPredmet(predmetObrazovneUstanove);
		setProfesor(profesorObrazovneUstanove);
		setStudent(studentObrazovneUstanove);
		setIspit(ispitObrazovneUstanove);

	}

	@Override
	public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

		//		List<Ispit> ispits = getIspit();
		//
		//		Arrays.sort(ispits, new IspitOcjeneComparator());
		//
		//		Student najboljsiStudent = ispits[getIspit().length - 1].getStudent();
		//
		//		return najboljsiStudent;


		List<Ispit> ispits = getIspit();

		SortedSet<Ispit> setStudenta = new TreeSet<>(new IspitOcjeneComparator());
		setStudenta.addAll(ispits);

		Student najboljsiStudent = setStudenta.last().getStudent();

		return najboljsiStudent;
	}


	@Override
	public Student odrediStudentaZaRektorovuNagradu() {
		List<Student> najboljiStudent = new ArrayList<>();
		Set<Student> setNajboljihStudenta = new HashSet<>();
		List<Integer> ocjene = new ArrayList<>();
		Integer max = 0;


		for (int i = 0; i < getPredmet().size(); i++) {

			Student stud = ((List<Student>) getPredmet().get(i).getStudent()).get(i);

			//	Ispit[] filtriraniIspiti = filtrirajIspitePoStudentu(getIspit(), stud);
			BigDecimal prosjekOcjene = odrediProsjekOcjenaNaIspitima(getIspit());
			Integer intProsjekOcjene = prosjekOcjene.intValue();


			ocjene.add(intProsjekOcjene);

			if (intProsjekOcjene > max) {
				max = intProsjekOcjene;
				najboljiStudent.add(stud);
				if (i != 0) {
					if (ocjene.get(i) > ocjene.get(i - 1)) {
						najboljiStudent.add(i - 1 ,stud);
						najboljiStudent.remove(i); //= ArrayUtils.removeElement(najboljiStudent, najboljiStudent[i]);
					}
					else if (ocjene.get(i) == ocjene.get(i-1)) {
						if (najboljiStudent.get(i).getDatumRodenja().isAfter(najboljiStudent.get(i - 1).getDatumRodenja())) {
							najboljiStudent.add(i - 1, stud);
							najboljiStudent.remove(i); // = ArrayUtils.removeElement(najboljiStudent, najboljiStudent[i]);
						}

					}

				}

			}

		}


		return najboljiStudent.get(0);
	}


	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> studentIspit, Integer ocjenaPismenogRada, Integer ocjenaObraneDiplomskogRada) {


		BigDecimal bDecimal = odrediProsjekOcjenaNaIspitima(studentIspit);

		Integer prosjekOcjena = bDecimal.intValue();

		Integer konacnaOcjena = ((3 * prosjekOcjena + ocjenaPismenogRada + ocjenaObraneDiplomskogRada) / 5);

		BigDecimal bigDecimal = BigDecimal.valueOf(konacnaOcjena);


		return bigDecimal;

	}


	@Override
	public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispit, Student student) {
		Metode metode = new Metode();
		List<Ispit> ispit3 = new ArrayList<>();
		int j = 0;

		for (int i = 0; i < ispit.size(); i++) {
	//		ispit3 = Arrays.copyOf(ispit3, j + 1);
			try {
				metode.checkForNegativeExam(ispit.get(i));

				if (ispit.get(i).getStudent().getIme().equals(student.getIme()) && ispit.get(i).getStudent().getPrezime().equals(student.getPrezime())) {

					Ispit ispit2 = ispit.get(i);
					ispit3.add(ispit2);
					j++;
				}
			} catch (NemoguceOdreditiProsjekStudentaException e) {

			}

		}
//		ispit3 = metode.removeNullsInIspitArray(ispit3);

		return ispit3;
	}


	@Override
	public BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispit) {
		Integer sum = null;
		Integer prosjekOcjena = null;
		BigDecimal bigDecimal = null;
		Integer counter = 0;
		Metode metode = new Metode();

		for (int i = 0; i < ispit.size(); i++) {

			try {
				metode.checkForNegativeExam(ispit.get(i));
				if (ispit.get(i).getOcjena() >= 2) {
					sum =+ ispit.get(i).getOcjena();
					counter++;
				}
			} catch (NemoguceOdreditiProsjekStudentaException e) {

			}


		}

		prosjekOcjena = sum / counter;

		bigDecimal = BigDecimal.valueOf(prosjekOcjena);

		return bigDecimal;

	}



}
