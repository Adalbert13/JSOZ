package com.adalbert.JSOZ.model;

public enum SposobZaliczenia implements ModelPersistable {
	egzamin("Egzamin"), zaliczenieNaOcene("Zaliczenie na ocenę"), zaliczenie("Zaliczenie");
	
	private String nazwa;
	
	private SposobZaliczenia(String nazwa) {
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
