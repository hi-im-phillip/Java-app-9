package hr.java.vjezbe.iznimke;

/**
 * Predstavlja gresku kod nemogucnosti i izracunavanja prosjeka studenta
 * @author Filip
 *
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2199507508252092489L;

	public NemoguceOdreditiProsjekStudentaException() {
		super("Neispravan unos!");
	}
	
	public NemoguceOdreditiProsjekStudentaException(String poruka) {
		super(poruka);
	}

	
	public NemoguceOdreditiProsjekStudentaException(Throwable uzrok) {
		super(uzrok);
	}
	
	public NemoguceOdreditiProsjekStudentaException(String poruka, Throwable uzrok) {
		super(poruka,uzrok);
	}
	
}
