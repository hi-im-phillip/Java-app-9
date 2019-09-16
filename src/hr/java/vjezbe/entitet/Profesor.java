package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Predstavlja entitet profesor koji je definiran sifrom i titulom
 * @author Filip
 *
 */
public class Profesor extends Osoba implements Serializable {

	private String sifra;
	private String titula;

	/**
	 * Inicijalizira podatak o sifri profesora, ime i prezime profesora i tituli
	 *
	 * @param sifraProfesor
	 * @param imeProfesor
	 * @param prezimeProfesor
	 * @param titulaProfesora
	 */
	public Profesor(String sifraProfesor, String imeProfesor, String prezimeProfesor, String titulaProfesora, long someId) {
		super(imeProfesor, prezimeProfesor, someId);
		setIme(imeProfesor);
		setPrezime(prezimeProfesor);
		setId(someId);
		this.sifra = sifraProfesor;
		this.titula = titulaProfesora;

	}


	/**
	 * Vraca sifru profesora
	 *
	 * @return
	 */
	public String getSifra() {
		return sifra;
	}

	/**
	 * Postavlja sifru profesora
	 * @param sifra
	 */
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}


	/**
	 * Vraca titulu profesora
	 * @return
	 */
	public String getTitula() {
		return titula;
	}

	/**
	 * Postavlja titulu profesora
	 * @param titula
	 */
	public void setTitula(String titula) {
		this.titula = titula;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return String.format("Sifra je %s, ime profesora je %s, prezime profesora je %s, titula je %s ", getSifra(), getIme(), getPrezime(), getTitula());
//	}

	@Override
	public String toString() {
		return String.format("%s %s", getIme(), getPrezime());
	}



}
