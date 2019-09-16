package hr.java.vjezbe.iznimke;

/**
 * Predstavlja gresku kod postojanja vise mladjih studenta
 * @author Filip
 *
 */
public class PostojiViseNajmladjihStudenataException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4815319869172975408L;

	public PostojiViseNajmladjihStudenataException() {
		super("Neispravan unos!");
	}
	
	public PostojiViseNajmladjihStudenataException(String poruka) {
		super(poruka);
	}

	
	public PostojiViseNajmladjihStudenataException(Throwable uzrok) {
		super(uzrok);
	}
	
	public PostojiViseNajmladjihStudenataException(String poruka, Throwable uzrok) {
		super(poruka,uzrok);
	}
	
}


