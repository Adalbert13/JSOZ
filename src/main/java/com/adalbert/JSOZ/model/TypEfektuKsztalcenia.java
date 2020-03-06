package com.adalbert.JSOZ.model;

public enum TypEfektuKsztalcenia implements ModelPersistable {
	wiedza("Wiedza"), umiejetnosc("Umiejętność"), kompetencja("Kompetencja");
	
	private String nazwa;
	
	private TypEfektuKsztalcenia(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	
	@Override
	public String toString() {
		return nazwa;
	}
}
