package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.Set;

/**
 * Predstavlja entitet predmet koji je definiran sifrom, nazivom, brojem ECTS bodova, nositeljem, poljem studenata
 *
 * @author Filip
 *
 */
public class Predmet extends Entitet implements Serializable {

	private String sifra;
	private String naziv;
	private Integer brojEctsBodova;
	private Profesor nositelj;
	private Set<Student> student;

	/**
	 * Inicijalizira podatak o sifri, nazivu predmeta, broju ECTS bodova, nositelju predmeta, polju studenta za taj predmet
	 *
	 * @param sifraPredmet
	 * @param nazivPredmet
	 * @param brojEctsBodovaPredmet
	 * @param nositeljPredmet
	 * @param listaStudentaPoPredmetu
	 */
	public Predmet(String sifraPredmet, String nazivPredmet, Integer brojEctsBodovaPredmet, Profesor nositeljPredmet, long id) {
		super(id);
		this.sifra = sifraPredmet;
		this.naziv = nazivPredmet;
		this.brojEctsBodova = brojEctsBodovaPredmet;
		this.nositelj = nositeljPredmet;
		setId(id);
	//	this.student = listaStudentaPoPredmetu;

	}

	/**
	 * Vraæa sifru predmeta
	 * @return
	 */
	public String getSifra() {
		return sifra;
	}

	/**
	 * Postavlja sifru predmeta
	 * @param sifra
	 */
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	/**
	 * Vraca naziv predmeta
	 * @return
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv predmeta
	 * @param naziv
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Vraca broj ECTS bodova predmeta
	 * @return
	 */
	public Integer getBrojEctsBodova() {
		return brojEctsBodova;
	}

	/**
	 * Postavlja broj ECTS bodova predmeta
	 * @param brojEctsBodova
	 */
	public void setBrojEctsBodova(Integer brojEctsBodova) {
		this.brojEctsBodova = brojEctsBodova;
	}

	/**
	 * Vraca nositelja predmeta
	 * @return
	 */
	public Profesor getNositelj() {
		return nositelj;
	}

	/**
	 * Postavlja nositelja predmeta
	 * @param nositelj
	 */
	public void setNositelj(Profesor nositelj) {
		this.nositelj = nositelj;
	}

	/**
	 * Vraca polje studenta
	 * @return
	 */
	public Set<Student> getStudent() {
		return student;
	}

	/**
	 * Postavlja polje studenta
	 * @param student
	 */
	public void setStudent(Set<Student> student) {
		this.student = student;
	}

//	public String toString() {
//		return String.format("Sifra predmeta je %s, naziv predmeta je %s, broj ECTS bodova su %d, profesor je %s", getSifra(), getNaziv(), getBrojEctsBodova(), getNositelj());
//	}

	/**
	 * Vraca formatirani naziv predmeta, koristimo kod objekta za ispis naziva predmeta
	 * @return
	 */
	@Override
	public String toString() {
		return String.format("%s", getNaziv());
	}

}
