package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet diplomski koji naslijeduje sucelje visokoskolska
 * @author Filip
 *
 */
public interface Diplomski extends Visokoskolska {
	
	/**
	 * Odreduje studenta po kriterija najuspijesnijeg na ispitima, obrani diplomskog i izradi diplomskog. 
	 * U slucaju da ih vise jednako ocijenjenih ima, uzima se najmladi. Vraca tog studenta. 
	 * @return
	 */
	public Student odrediStudentaZaRektorovuNagradu();

}
