package com.adalbert.JSOZ.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "semestry")
public class Semestr implements ModelPersistable {

	@Id
	@Column(name = "semestrID")
	private int semestrID;
	
	@Column(name = "rok")
	private int rok;

	@Column(name = "typ")
	private TypSemestru typ;
	
	public Semestr() {}
	
	public Semestr(int semestrID, int rok, TypSemestru typ) {
		this.semestrID = semestrID;
		this.rok = rok; 
		this.typ = typ;
	}
	
	public int getRok() {
		return rok;
	}
	
	public void setRok(int rok) {
		this.rok = rok;
	}
	
	public TypSemestru getTyp() {
		return typ;
	}

	public void setTyp(TypSemestru typ) {
		this.typ = typ;
	}

	public int getSemestrID() {
		return semestrID;
	}

	public void setSemestrID(int semestrID) {
		this.semestrID = semestrID;
	}
	
	@Override
	public String toString() {
		return rok + "/" + typ.getNazwa();
	}
	
}
