package com.adalbert.JSOZ.model;

public enum StopienKsztalcenia implements ModelPersistable {
	I("I stopień kształcenia"), II("II stopień kształcenia"), III("III stopień kształcenia");
	
	private String nazwa;
	
	private StopienKsztalcenia(String nazwa) {
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
