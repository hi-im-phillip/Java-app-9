package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Predstavlja entitet studenta koji je definiran JMBAG-om i datumom rodenja
 *
 * @author Filip
 *
 */
public class Student extends Osoba implements Serializable{


	private String jmbag;
    private LocalDate datumRodenja;
    private String odjel;

	/**
     * Inicijalizira podatak o ime studentu, prezime studentu, jmbag studenta i datum rodenja studenta
     *
     * @param imeStudent
     * @param prezimeStudent
     * @param jmbagStudent
     * @param datumRodenjaStudenta
     */
    public Student(String imeStudent, String prezimeStudent, String jmbagStudent, LocalDate datumRodenjaStudenta, long someId) {
    	super(imeStudent, prezimeStudent, someId);
    	setIme(imeStudent);
    	setPrezime(prezimeStudent);
    	setId(someId);
    	this.jmbag = jmbagStudent;
    	this.datumRodenja = datumRodenjaStudenta;
    }



	/**
	 * @return vraæa JMBAG studenta
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @param jmbag Postavlja JMBAG studenta
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * @return Vraca datum rodenja studenta
	 */
	public LocalDate getDatumRodenja() {
		return datumRodenja;
	}

	/**
	 * @param datumRodenja Postavlja datum rodenja studenta
	 */
	public void setDatumRodenja(LocalDate datumRodenja) {
		this.datumRodenja = datumRodenja;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
//    public String toString() {
//
//    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
//		String datumRodenjaString = getDatumRodenja().format(formatter);
//
//    	return String.format("JMBAG studenta je %s, ime i prezime studenta je %s %s, i datum rodenja je %s%n", getJmbag(), getIme(), getPrezime(), datumRodenjaString);
//    }

	@Override
	public String toString() {
		return String.format("%s %s", getIme(), getPrezime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datumRodenja == null) ? 0 : datumRodenja.hashCode());
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Student other = (Student) obj;
		if (datumRodenja == null) {
			if (other.datumRodenja != null) {
				return false;
			}
		} else if (!datumRodenja.equals(other.datumRodenja)) {
			return false;
		}
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}

	public String getOdjel() {
		return odjel;
	}



	public void setOdjel(String odjel) {
		this.odjel = odjel;
	}


}
