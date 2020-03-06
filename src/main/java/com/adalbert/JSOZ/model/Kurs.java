package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "kursy")
public class Kurs implements ModelPersistable {

	@Id
	@Column(name = "kursKod")
	private String kursKod;
	
	@Column(name = "nazwa")
	private String nazwa;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(
	        name="kursy_slowaKluczowe",
	        joinColumns=@JoinColumn(name="kursykod")
	)
	private List<String> slowaKluczowe;
	
	@Column(name = "formaKursu")
	private FormaKursu formaKursu;
	
	@Column(name = "sposobZaliczenia")
	private SposobZaliczenia sposobZaliczenia;
	
	@Column(name = "ects")
	private int ects;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="konczacySemestr")
	private Semestr konczacySemestr;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="rozpoczynajacySemestr")
	private Semestr rozpoczynajacySemestr;
	
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name="kursy_semestry",
			joinColumns = { @JoinColumn(name = "kursyKod") },
			inverseJoinColumns = { @JoinColumn(name = "semestrysemestrID") }
	)
	private List<Semestr> semestryRealizacji;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="opisujacyPlan")
	private Plan opisujacyPlan;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name="kursy_keki",
			joinColumns = { @JoinColumn(name = "kursykurskod") },
			inverseJoinColumns = { @JoinColumn(name = "kekikekID") }
	)
	private List<KierunkowyEfektKsztalcenia> realizowaneKEKi;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="zawierajacaGrupa")
	private Kurs zawierajacaGrupa;
	
	@OneToMany(mappedBy="zawierajacaGrupa")
	private List<Kurs> kursyWGrupie;
	
	@Column(name="minimalneEcts")
	private int minimalneEcts;
	
	@OneToMany(mappedBy = "zamienianyKurs")
	private List<Zamiennik> zamienniki;
	
	public Kurs() {
		slowaKluczowe = new ArrayList<>();
		zamienniki = new ArrayList<>();
		kursyWGrupie = new ArrayList<>();
		realizowaneKEKi = new ArrayList<>();
		semestryRealizacji = new ArrayList<>();
	}
	
	public Kurs(String kursKod, String nazwa, Plan opisujacyPlan, int ects) {
		this();
		this.kursKod = kursKod;
		this.opisujacyPlan = opisujacyPlan;
		this.nazwa = nazwa;
		this.ects = ects;
		this.rozpoczynajacySemestr = opisujacyPlan.getSemestrRozpoczecia();
		this.konczacySemestr = opisujacyPlan.getSemestrZakonczenia();
	}
	
	public String getKursKod() {
		return kursKod;
	}
	
	public void setKursKod(String kursKod) {
		this.kursKod = kursKod;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public List<String> getSlowaKluczowe() {
		return slowaKluczowe;
	}
	
	public void setSlowaKluczowe(List<String> slowaKluczowe) {
		this.slowaKluczowe = slowaKluczowe;
	}
	
	public FormaKursu getFormaKursu() {
		return formaKursu;
	}
	
	public void setFormaKursu(FormaKursu formaKursu) {
		this.formaKursu = formaKursu;
	}
	
	public SposobZaliczenia getSposobZaliczenia() {
		return sposobZaliczenia;
	}
	
	public void setSposobZaliczenia(SposobZaliczenia sposobZaliczenia) {
		this.sposobZaliczenia = sposobZaliczenia;
	}
	
	public int getEcts() {
		return ects;
	}
	
	public void setEcts(int ects) {
		this.ects = ects;
	}

	public Semestr getKonczacySemestr() {
		return konczacySemestr;
	}

	public void setKonczacySemestr(Semestr konczacySemestr) {
		this.konczacySemestr = konczacySemestr;
	}

	public Semestr getRozpoczynajacySemestr() {
		return rozpoczynajacySemestr;
	}

	public void setRozpoczynajacySemestr(Semestr rozpoczynajacySemestr) {
		this.rozpoczynajacySemestr = rozpoczynajacySemestr;
	}

	public List<Semestr> getSemestryRealizacji() {
		return semestryRealizacji;
	}

	public void setSemestryRealizacji(List<Semestr> semestryRealizacji) {
		this.semestryRealizacji = semestryRealizacji;
	}

	public List<KierunkowyEfektKsztalcenia> getRealizowaneKEKi() {
		return realizowaneKEKi;
	}

	public void setRealizowaneKEKi(List<KierunkowyEfektKsztalcenia> realizowaneKEKi) {
		this.realizowaneKEKi = realizowaneKEKi;
	}

	public Plan getOpisujacyPlan() {
		return opisujacyPlan;
	}

	public void setOpisujacyPlan(Plan opisujacyPlan) {
		this.opisujacyPlan = opisujacyPlan;
	}

	public Kurs getZawierajacaGrupa() {
		return zawierajacaGrupa;
	}

	public void setZawierajacaGrupa(Kurs zawierajacaGrupa) {
		this.zawierajacaGrupa = zawierajacaGrupa;
	}

	public List<Kurs> getKursyWGrupie() {
		return kursyWGrupie;
	}

	public void setKursyWGrupie(List<Kurs> kursyWGrupie) {
		this.kursyWGrupie = kursyWGrupie;
	}

	public int getMinimalneEcts() {
		return minimalneEcts;
	}

	public void setMinimalneEcts(int minimalneEcts) {
		this.minimalneEcts = minimalneEcts;
	}
	

	public List<Zamiennik> getZamienniki() {
		return zamienniki;
	}

	public void setZamienniki(List<Zamiennik> zamienniki) {
		this.zamienniki = zamienniki;
	}
	
	// ********************************************************************************
	// ********** Metody funkcjonalne *************************************************
	// ********************************************************************************
	
	public void dodajSlowoKluczowe(String slowoKluczowe) {
		if (slowoKluczowe == null)
			throw new IllegalArgumentException("Nie mogę dodać do słów kluczowych nie istniejącego słowa!");
		slowaKluczowe.add(slowoKluczowe);
	}
	
	public void dodajKursDoGrupy(Kurs kursWGrupie) {
		if (kursWGrupie == null)
			throw new IllegalArgumentException("Nie mogę dodać do grupy nie istniejącego kursu!");
		this.kursyWGrupie.add(kursWGrupie);
		this.ects = 0;
		for (Kurs kurs : kursyWGrupie)
			this.ects += kurs.getEcts();
		kursWGrupie.setZawierajacaGrupa(this);
		
	}
	
	public boolean czyKursJestGrupa() {
		return kursyWGrupie.size() > 0;
	}
	
	@Override
	public String toString() {
		return nazwa;
	}

}
