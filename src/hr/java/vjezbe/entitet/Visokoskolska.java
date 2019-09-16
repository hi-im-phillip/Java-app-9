package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Predstavlja entitet visokoskolska. 
 * izracunajKonacnuOcjenuStudijaZaStudenta
 * filtrirajIspitePoStudentu
 * @author Filip
 *
 */
public interface Visokoskolska {

	/**
	 * Predajemo polje ispita, ocjenu pismenog rada te ocjenu obrane zavrsnog te vraca konacnu ocjenu za odrenog studenta u BigDecimal objektu.
	 * 
	 * @param studentIspit
	 * @param ocjenaPismenogRada
	 * @param ocjenaObraneZavrsnogRada
	 * @return
	 */
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> studentIspit, Integer ocjenaPismenogRada, Integer ocjenaObraneZavrsnogRada);


	/**
	 * Predajemo polje ispita koje zelimo da se izracuna prosjek na tim ispitima te vraca prosjek u BigDecimal objektu.
	 * @param ispit
	 * @return
	 */
	public default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispit) {
		Integer sum = null;
		Integer prosjekOcjena = null;
		BigDecimal bigDecimal = null;

		List<Ispit> ispitici = filtrirajPolozeneIspite(ispit);

		//		for (int i = 0; i < ispitici.size() && ispitici[i] != null; i++) {
		//			
		//			sum =+ ispitici[i].getOcjena();
		//			
		//		}	

		for (int i = 0; i < ispitici.size(); i++) {

			sum =+ ispitici.get(i).getOcjena();

		}	
		prosjekOcjena = sum / ispitici.size();

		bigDecimal = BigDecimal.valueOf(prosjekOcjena);

		return bigDecimal;

	}

	/**
	 * Predajemo zeljene ispite koje zelimo filtrirati samo one koji su ocijenjeni pozitivinom ocjenom. 
	 * Vraca te pozivitno ocijenjene ispite.
	 * @param ispit
	 * @return
	 */
	private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispit) {

		//		Integer counter = 0;

		//		for (int i = 0; i < ispit.size(); i++) {
		//			if (ispit.get(i).getOcjena() >= 2) {
		//				counter++;			
		//			}
		//		}	
		List<Ispit> ispits3 = new ArrayList<>(); 	

		for (int j = 0; j < ispit.size(); j++) {
			if (ispit.get(j).getOcjena() >= 2) {
				Ispit ispit2 = ispit.get(j);	
				ispits3.add(ispit2);
			}


		}

		return ispits3;

	}


	/**
	 * Predaju se zeljeno polje ispita i student po kojem se trebaju filtirati te vraca to filtrirano polje ispita po studentu.
	 * Kasnije se koristi u metodi izracunaj konacnu ocjenu
	 * @param ispit
	 * @param student
	 * @return
	 */
	default public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispit, Student student) {

		//	    Integer counter = 0;
		//	    
		//		for (int i = 0; i < ispit.size(); i++) {
		//			if (ispit.get(i).getStudent().getIme().equals(student.getIme()) && ispit.get(i).getStudent().getPrezime().equals(student.getPrezime())) {
		//            	counter++;
		//			}
		//		}

		List<Ispit> ispit3 = new ArrayList<>();

		for (int i = 0; i < ispit.size(); i++) {

			if (ispit.get(i).getStudent().getIme().equals(student.getIme()) && ispit.get(i).getStudent().getPrezime().equals(student.getPrezime())) {
				Ispit ispit2 = ispit.get(i);
				ispit3.add(ispit2);
			}		

		}

		return ispit3;
	}

}

//pitamo pitanje ?, je li age veci od 50? da ili ne
// ako je vece odnosno da(true) -> ispisi prvi statement
// ako je manje odnosno ne(false) -> ipisi drugi statement
//	System.out.println(age > 50 ? "You are old" : "You are young");
