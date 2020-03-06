package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "studenci")
public class Student extends Uzytkownik implements ModelPersistable {

	@Id
	@Column(name = "nrIndeksu")
	private int nrIndeksu;
	
	@Column(name = "imie")
	private String imie;
	
	@Column(name = "nazwisko")
	private String nazwisko;
	
	@Column(name = "trybStudiow")
	private TrybStudiow trybStudiow;
	
	@OneToMany(mappedBy = "proponujacyZamiennik")
	private List<Zamiennik> propozycjeZamiennikow;
	
	@ManyToMany(mappedBy = "edukowaniStudenci")
	private List<Kierunek> edukujaceKierunki; 
	
	public Student() {
		propozycjeZamiennikow = new ArrayList<>();
		edukujaceKierunki = new ArrayList<>();
	}
	
	public Student(int nrIndeksu) {
		this();
		this.nrIndeksu = nrIndeksu;
	}
 
	public int getNrIndeksu() {
		return nrIndeksu;
	}

	public void setNrIndeksu(int nrIndeksu) {
		this.nrIndeksu = nrIndeksu;
	}

	public TrybStudiow getTrybStudiow() {
		return trybStudiow;
	}

	public void setTrybStudiow(TrybStudiow trybStudiow) {
		this.trybStudiow = trybStudiow;
	}

	public List<Zamiennik> getPropozycjeZamiennikow() {
		return propozycjeZamiennikow;
	}

	public void setPropozycjeZamiennikow(List<Zamiennik> propozycjeZamiennikow) {
		this.propozycjeZamiennikow = propozycjeZamiennikow;
	}

	public List<Kierunek> getEdukujaceKierunki() {
		return edukujaceKierunki;
	}

	public void setEdukujaceKierunki(List<Kierunek> edukujaceKierunki) {
		this.edukujaceKierunki = edukujaceKierunki;
	}
	
	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}
	
	// ********************************************************************************
	// ********** Metody funkcjonalne *************************************************
	// ********************************************************************************
	
	public void dodajKierunekDoEdukujacych(Kierunek edukujacyKierunek) {
		if (edukujacyKierunek == null)
			throw new IllegalArgumentException("Edukujący kierunek nie może być null'em!");
		edukujaceKierunki.add(edukujacyKierunek);
		if (edukujacyKierunek.getEdukowaniStudenci() != null && !edukujacyKierunek.getEdukowaniStudenci().contains(this))
			edukujacyKierunek.dodajStudentaDoEdukowanych(this);
	}
	
	public void dodajZamiennikDoProponowanych(Zamiennik zamiennik) {
		if (zamiennik == null)
			throw new IllegalArgumentException("Proponowany zamiennik nie może być null'em!");
		propozycjeZamiennikow.add(zamiennik);
		zamiennik.setProponujacyZamiennik(this);
	}
	
}
