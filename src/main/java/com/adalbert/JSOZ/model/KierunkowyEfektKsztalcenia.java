package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "keki")
public class KierunkowyEfektKsztalcenia implements ModelPersistable {

	@Id
	@Column(name = "kekID")
	private String kekID;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="definiujacyKierunek")
	private Kierunek definiujacyKierunek;
	
	@Column(name = "typ")
	private TypEfektuKsztalcenia typ;
	
	@ManyToMany(mappedBy = "realizowaneKEKi")
	private List<Kurs> realizujaceKursy;
	
	public KierunkowyEfektKsztalcenia() {
		realizujaceKursy = new ArrayList<>();
	}
	
	public KierunkowyEfektKsztalcenia(String kekID, Kierunek definiujacyKierunek) {
		this();
		this.kekID = kekID;
		this.definiujacyKierunek = definiujacyKierunek;
	}

	public Kierunek getDefiniujacyKierunek() {
		return definiujacyKierunek;
	}

	public void setDefiniujacyKierunek(Kierunek definiujacyKierunek) {
		this.definiujacyKierunek = definiujacyKierunek;
	}

	public TypEfektuKsztalcenia getTyp() {
		return typ;
	}

	public void setTyp(TypEfektuKsztalcenia typ) {
		this.typ = typ;
	}

	public String getKekID() {
		return kekID;
	}

	public void setKekID(String id) {
		this.kekID = id;
	}

	public List<Kurs> getRealizujaceKursy() {
		return realizujaceKursy;
	}

	public void setRealizujaceKursy(List<Kurs> realizujaceKursy) {
		this.realizujaceKursy = realizujaceKursy;
	}
	
}
