package com.adalbert.JSOZ.model;

public enum TypSemestru implements ModelPersistable {
	Letni("Letni"), Zimowy("Zimowy");
	
	private String nazwa;
	
	private TypSemestru(String nazwa) {
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
