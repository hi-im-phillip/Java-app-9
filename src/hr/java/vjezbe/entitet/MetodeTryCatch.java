package hr.java.vjezbe.entitet;



/**
 * @author Filip
 *
 */
public class MetodeTryCatch {

	/**
	 * 
	 * @param checkingString
	 * @return
	 */
	public boolean checkIfStringIsInteger(String checkingString) {
		
		try {			
			Integer.parseInt(checkingString);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}

	}
	
	

}
