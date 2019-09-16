package hr.java.vjezbe.entitet;

public enum Ocjena {
	
	NEDOVOLJAN(1, "Nedovoljan"), 
	DOVOLJAN(2, "Dovoljan"), 
	DOBAR(3, "Dobar"), 
	VRLO_DOBAR(4, "Vrlo Dobar"), 
	ODLICAN(5, "Odlican");
	
	private Integer ocjena;
	private String opis;
	
	private Ocjena(Integer ocjenaBroj, String ocjenaOpis) {
		this.ocjena = ocjenaBroj;
		this.opis = ocjenaOpis;
	}

	public Integer getOcjena() {
		return ocjena;
	}


	public String getOpis() {
		return opis;
	}
	
}
