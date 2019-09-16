package hr.java.vjezbe.entitet;

import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova> {


	private List<T> sveucilista;




	public Sveuciliste(List<T> listaSveucilista) {
		this.sveucilista = listaSveucilista;
	}

	public void dodajObrazovnuUstanovu(T element){

		this.sveucilista.add(element);

	}

	public T dohvatiObrazovnuUstanovu(int index){

		T objekt = null;

		for (int i = 0; i < sveucilista.size(); i++) {
			if (this.sveucilista.isEmpty()) {
				System.out.println("Nema elemenata. ");
			}

			else if (sveucilista.size() < index) {
				System.out.println("Preveliki index");
			}else {
				objekt = this.sveucilista.get(index);
			}

		}

		return objekt;
	}

	public List<T> getSveucilista() {
		return sveucilista;
	}

	public void setSveucilista(List<T> sveucilista) {
		this.sveucilista = sveucilista;
	}

}
