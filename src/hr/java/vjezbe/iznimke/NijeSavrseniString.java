package hr.java.vjezbe.iznimke;

public class NijeSavrseniString extends RuntimeException{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562834531703030758L;

	public NijeSavrseniString() {
		super("Neispravan unos!");
	}
	
	public NijeSavrseniString(String poruka) {
		super(poruka);
	}

	
	public NijeSavrseniString(Throwable uzrok) {
		super(uzrok);
	}
	
	public NijeSavrseniString(String poruka, Throwable uzrok) {
		super(poruka,uzrok);
	}

}
