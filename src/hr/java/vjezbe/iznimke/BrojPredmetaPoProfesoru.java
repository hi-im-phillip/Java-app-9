package hr.java.vjezbe.iznimke;

public class BrojPredmetaPoProfesoru extends RuntimeException {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -1548850994647789499L;

		public BrojPredmetaPoProfesoru() {
			super("Profesor mora imati minimalno jedan predmet ili više na sebi!");
		}
		
		public BrojPredmetaPoProfesoru(String poruka) {
			super(poruka);
		}

		
		public BrojPredmetaPoProfesoru(Throwable uzrok) {
			super(uzrok);
		}
		
		public BrojPredmetaPoProfesoru(String poruka, Throwable uzrok) {
			super(poruka,uzrok);
		}
		
	}


