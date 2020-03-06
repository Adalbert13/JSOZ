package com.adalbert.JSOZ.model;

public enum TrybStudiow implements ModelPersistable {
	stacjonarny("Tryb stacjonarny"), niestacjonarny("Tryb niestacjonarny");
	
	private String nazwa;
	
	private TrybStudiow(String nazwa) {
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
