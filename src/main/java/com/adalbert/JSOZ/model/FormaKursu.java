package com.adalbert.JSOZ.model;

public enum FormaKursu implements ModelPersistable {
	wyklad("Wykład"), cwiczenia("Ćwiczenia"), laboratium("Laboratium"), projekt("Projekt"), seminarium("Seminarium");
	
	private String nazwa;
	
	private FormaKursu(String nazwa) {
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
