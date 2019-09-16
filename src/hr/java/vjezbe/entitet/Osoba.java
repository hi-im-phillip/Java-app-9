package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet osobe koja je definirana imenom i prezimenom
 * @author Filip
 *
 */
public abstract class Osoba extends Entitet {

	private String ime;
	private String prezime;


	/**
	 * Inicijalizira podatak o imenu i prezimenu osobe
	 * @param imeOsobe
	 * @param prezimeOsobe
	 */
	public Osoba(String imeOsobe, String prezimeOsobe, long someId) {
		super(someId);
		setId(someId);
		this.ime = imeOsobe;
		this.prezime = prezimeOsobe;
	}


	/**
	 * Vraca ime osobe
	 * @return
	 */
	public String getIme() {
		return ime;
	}


	/**
	 * Postavlja ime osobe
	 * @param ime
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}


	/**
	 * Vraca prezime osobe
	 * @return
	 */
	public String getPrezime() {
		return prezime;
	}


	/**
	 * Postavlja prezime osobe
	 * @param prezime
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



}
