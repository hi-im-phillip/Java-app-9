package hr.java.vjezbe.iznimke;

public class BrojStudentaPoPredmetu extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6493899601495420865L;

	public BrojStudentaPoPredmetu() {
		super("Profesor mora imati minimalno jedan predmet ili više na sebi!");
	}
	
	public BrojStudentaPoPredmetu(String poruka) {
		super(poruka);
	}

	
	public BrojStudentaPoPredmetu(Throwable uzrok) {
		super(uzrok);
	}
	
	public BrojStudentaPoPredmetu(String poruka, Throwable uzrok) {
		super(poruka,uzrok);
	}

}
