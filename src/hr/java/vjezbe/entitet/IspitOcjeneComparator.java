package hr.java.vjezbe.entitet;

import java.util.Comparator;

/**
 * Predstavlja kompariranje dvaju ispita po ocjenama.
 * @author Filip
 *
 */
public class IspitOcjeneComparator implements Comparator<Ispit> {

	@Override
	public int compare(Ispit ispit1, Ispit ispit2) {
		if (ispit1.getOcjena() > ispit2.getOcjena()) {
			return 1;
		}
		else if (ispit1.getOcjena() < ispit2.getOcjena()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
