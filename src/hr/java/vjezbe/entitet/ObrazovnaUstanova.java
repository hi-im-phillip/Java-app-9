package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.List;

/**
 * Predstavlja entitet obrazovne ustanove koja je definira nazivom, poljem predmeta, poljem profesora, poljem studenta, poljem ispita
 * @author Filip
 *
 */
public abstract class ObrazovnaUstanova extends Entitet implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String naziv;
	private List<Predmet> predmet;
	private List<Profesor> profesor;
	private List<Student> student;
	private List<Ispit> ispit;

	/**
	 * Inicijalizira podatak o polju predmeta obrazovne ustanove, polju profesora obrazovne ustanove, polju studenta obrazovne ustanove, ispitu obrazovne ustanove
	 *
	 * @param predmetiObrazovneUstanove
	 * @param profesoriObrazovneUstanove
	 * @param theChosenOnesStudents
	 * @param ispitiObrazovneUstanove
	 */
	public ObrazovnaUstanova(List<Predmet> predmetiObrazovneUstanove, List<Profesor> profesoriObrazovneUstanove,
			                 List<Student> theChosenOnesStudents, List<Ispit> ispitiObrazovneUstanove, long someId) {
		super(someId);
		setId(someId);
		this.predmet = predmetiObrazovneUstanove;
		this.profesor = profesoriObrazovneUstanove;
		this.student = theChosenOnesStudents;
		this.ispit = ispitiObrazovneUstanove;

	}

	/**
	 * Vraca naziv obrazovne ustanove
	 * @return
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv obrazovne ustanove
	 * @param naziv
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Vraca polje predmeta obrazovne ustanove
	 * @return
	 */
	public List<Predmet> getPredmet() {
		return predmet;
	}

	/**
	 * Postavlja polje predmeta obrazovne ustanove
	 * @param predmet
	 */
	public void setPredmet(List<Predmet> predmet) {
		this.predmet = predmet;
	}

	/**
	 * Vraca polje profesora obrazovne ustanove
	 * @return
	 */
	public List<Profesor> getProfesor() {
		return profesor;
	}

	/**
	 * Postavlja polje profesora obrazovne ustanove
	 * @param profesor
	 */
	public void setProfesor(List<Profesor> profesor) {
		this.profesor = profesor;
	}

	/**
	 * Vraca polje studenta obrazovne ustanove
	 * @return
	 */
	public List<Student> getStudent() {
		return student;
	}

	/**
	 * Postavlja polje studenta obrazovne ustanove
	 * @param student
	 */
	public void setStudent(List<Student> student) {
		this.student = student;
	}

	/**
	 * Vraca polje ispita obrazovne ustanove
	 * @return
	 */
	public List<Ispit> getIspit() {
		return ispit;
	}

	/**
	 * Postavlja polje ispita obrazovne ustanove
	 * @param ispit
	 */
	public void setIspit(List<Ispit> ispit) {
		this.ispit = ispit;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %s %s", getNaziv(), getIspit(), getPredmet(), getProfesor(), getStudent());
	}

	/**
	 * Predviðeno za odredivanje najuspjesnijeg studenta na odredenoj godini, vraca tog najuspijesnijeg studenta
	 * @param godina
	 * @return
	 */
	public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

}
