package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Predstavlja entitet veleucilista koja naslijeduje obrazovnu ustanovu te implementira visokoskolsku
 * @author Filip
 *
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

	private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);

	/**
	 * Inicijalizira podatak o polju predmeta obrazovne ustanove, polju profesora obrazovne ustanove, polju studenta obrazovne ustanove, ispitu obrazovne ustanove
	 *
	 * @param predmetiObrazovneUstanove
	 * @param profesoriObrazovneUstanove
	 * @param theChosenOnesStudents
	 * @param ispitiObrazovneUstanove
	 */
	public VeleucilisteJave(List<Predmet> predmetiObrazovneUstanove, List<Profesor> profesoriObrazovneUstanove,
			List<Student> theChosenOnesStudents, List<Ispit> ispitiObrazovneUstanove, long someId) {
		super(predmetiObrazovneUstanove, profesoriObrazovneUstanove, theChosenOnesStudents, ispitiObrazovneUstanove, someId);
		setId(someId);
		setPredmet(predmetiObrazovneUstanove);
		setProfesor(profesoriObrazovneUstanove);
		setStudent(theChosenOnesStudents);
		setIspit(ispitiObrazovneUstanove);

	}

	@Override
	public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

//		Scanner scanner = new Scanner(System.in);
//		Integer ocjenaPismenogRada;
//		Integer ocjenaObraneZavrsnogRada;
//		Metode metode = new Metode();
//		Integer konacnaOcjena = 0;
//		Integer max = 0;
//		Student[] najboljiStudent = new Student[getIspit().length];
//		Integer[] ocjene = new Integer[getIspit().length];
//

//		for (int i = 0; i < getPredmet().length; i++) {
//            Student stud = getPredmet()[i].getStudent()[0];
//
//            konacnaOcjena = bDecimal.intValue();
//
//
//			ocjene[i] = konacnaOcjena;
//
//            if (konacnaOcjena > max) {
//            	max = konacnaOcjena;
//            	najboljiStudent[i] = stud;
//            	if (i != 0) {
//            		if (ocjene[i] > ocjene[i - 1]) {
//            			najboljiStudent[i - 1] = stud;
//            			najboljiStudent = ArrayUtils.removeElement(najboljiStudent, najboljiStudent[i]);
//            		}
//            	}
//
//            }
//
//		}


		List<Ispit> ispits = getIspit();

		SortedSet<Ispit> setStudenta = new TreeSet<>(new IspitOcjeneComparator());
		setStudenta.addAll(ispits);

		Student najboljsiStudent = setStudenta.last().getStudent();

		return najboljsiStudent;



	}
// filtiranepostudentu predajemo, te on unutar izracunava prosjek ocjene i sa tim prosjekom konacni rezultat
	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> studentIspit, Integer ocjenaPismenogRada, Integer ocjenaObraneZavrsnogRada) {


		BigDecimal bDecimal = odrediProsjekOcjenaNaIspitima(studentIspit);

		Integer prosjekOcjena = bDecimal.intValue();

		Integer konacnaOcjena = ((2 * prosjekOcjena + ocjenaPismenogRada + ocjenaObraneZavrsnogRada) / 4);

		BigDecimal bigDecimal = BigDecimal.valueOf(konacnaOcjena);



		return bigDecimal;


	}


	@Override
	public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispit, Student student) {

	List<Ispit> ispit3 = new ArrayList<>();

//		for (int i = 0; i < ispit.size(); i++) {
//			try {
//				metode.checkForNegativeExam(ispit.get(i));
//				int j = 0;
//	            if (ispit.get(i).getStudent().getIme().equals(student.getIme()) && ispit.get(i).getStudent().getPrezime().equals(student.getPrezime())) {
//	      //      	ispit3 = Arrays.copyOf(ispit3, i + 1);
//	            	Ispit ispit2 = ispit.get(i);
//	            	ispit3.add(ispit2);
//	            	j++;
//				}
//			} catch (NemoguceOdreditiProsjekStudentaException e) {
//
//			}
//
//		}
//		ispit3 = metode.removeNullsInIspitArray(ispit3);

//		ispit.forEach(t -> t.getStudent().getIme().equals(student.getIme()));
//		ispit.forEach(item -> {if (item.getStudent().getIme().equals(student.getIme())) {ispit3.add(item);
//
//		}});
		ispit.stream()
		     .filter(k -> k.getStudent().getPrezime().equals(student.getPrezime()) && k.getStudent().getIme().equals(student.getIme()))
		     .forEach(ispit3::add);



		return ispit3;
	}


	@Override
	public BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispit) {
		Integer sum = null;
		Integer prosjekOcjena = null;
		BigDecimal bigDecimal = null;
		Integer counter = 0;

//		for (int i = 0; i < ispit.size() && ispit[i] != null; i++) {
//			if (ispit[i].getOcjena() >= 2) {
//				sum =+ ispit[i].getOcjena();
//				counter++;
//			}
//
//		}

		for (int i = 0; i < ispit.size(); i++) {
			if (ispit.get(i).getOcjena() >= 2) {
				sum =+ ispit.get(i).getOcjena();
				counter++;
			}

		}

		prosjekOcjena = sum / counter;

		bigDecimal = BigDecimal.valueOf(prosjekOcjena);

		return bigDecimal;

	}

}
//Scanner scanner = new Scanner(System.in);
//Integer ocjenaPismenog = null;
//Integer ocjenaObrane = null;
//BigDecimal konacnaOcjena = null;
//Student[] student = new Student[getStudent().length];
//
//
//for (int i = 0; i < getStudent().length; i++) {
//	student = getStudent();
//
//	System.out.println("Unesite ocjenu završnog rada za studenta" + student[i].getIme());
//	ocjenaPismenog = scanner.nextInt();
//
//	System.out.println("Unesite ocjenu obrane završnog rada za studenta:" + student[i].getIme());
//	ocjenaObrane = scanner.nextInt();
//
//
//
//	System.out.println("Konaèna ocjena studija studenta " + student[i].getIme() + " je "  + konacnaOcjena);
//
//
//}
//konacnaOcjena = izracunajKonacnuOcjenuStudijaZaStudenta(getIspit(), ocjenaPismenog, ocjenaObrane);
//
//scanner.close();
//