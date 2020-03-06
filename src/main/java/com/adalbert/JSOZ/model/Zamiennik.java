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
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="zamienniki")
public class Zamiennik implements ModelPersistable {

	@Id
	@Column(name="zamiennikKod")
	private String zamiennikKod;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="semestrRozpoczecia")
	private Semestr semestrRozpoczecia;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="semestrZakonczenia")
	private Semestr semestrZakonczenia;
	
	@Column(name="notatka")
	private String notatka;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="zamienianyKurs")
	private Kurs zamienianyKurs;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(
	        name="zamienniki_slowaKluczowe",
	        joinColumns=@JoinColumn(name="zamiennikKod")
	)
	private List<String> slowaKluczowe;
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(
			name="zamienniki_kursy",
			joinColumns = { @JoinColumn(name = "zamiennikKod") },
			inverseJoinColumns = { @JoinColumn(name = "kursKod") }
	)
	private List<Kurs> zawieraneKursy;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="proponujacyZamiennik")
	private Student proponujacyZamiennik;
	
	@Column(name="czyPropozycja")
	private boolean czyPropozycja;
	
	public Zamiennik() {
		zawieraneKursy = new ArrayList<>();
		slowaKluczowe = new ArrayList<>();
	}
	
	public Zamiennik(String zamiennikKod, Kurs zamienianyKurs) {
		this();
		this.zamiennikKod = zamiennikKod;
		this.zamienianyKurs = zamienianyKurs;
		this.zawieraneKursy = new ArrayList<>();
	}

	public String getZamiennikKod() {
		return zamiennikKod;
	}

	public void setZamiennikKod(String zamiennikKod) {
		this.zamiennikKod = zamiennikKod;
	}

	public Semestr getSemestrRozpoczecia() {
		return semestrRozpoczecia;
	}

	public void setSemestrRozpoczecia(Semestr semestrRozpoczecia) {
		this.semestrRozpoczecia = semestrRozpoczecia;
	}

	public Semestr getSemestrZakonczenia() {
		return semestrZakonczenia;
	}

	public void setSemestrZakonczenia(Semestr semestrZakonczenia) {
		this.semestrZakonczenia = semestrZakonczenia;
	}

	public String getNotatka() {
		return notatka;
	}

	public void setNotatka(String notatka) {
		this.notatka = notatka;
	}

	public Kurs getZamienianyKurs() {
		return zamienianyKurs;
	}

	public void setZamienianyKurs(Kurs zamienianyKurs) {
		this.zamienianyKurs = zamienianyKurs;
	}

	public List<String> getSlowaKluczowe() {
		return slowaKluczowe;
	}

	public void setSlowaKluczowe(List<String> slowaKluczowe) {
		this.slowaKluczowe = slowaKluczowe;
	}
	
	public boolean czyPropozycja() {
		return czyPropozycja;
	}

	public Student getProponujacyZamiennik() {
		return proponujacyZamiennik;
	}
	
	public void setZawieraneKursy(List<Kurs> zawieraneKursy) {
		this.zawieraneKursy = zawieraneKursy;
	}
	
	public List<Kurs> getZawieraneKursy() {
		return zawieraneKursy;
	}
	
	// ********************************************************************************
	// ********** Metody funkcjonalne *************************************************
	// ********************************************************************************

	// przekazać studenta, by zamiennik stał się propozycją zamiennika, przekazać null by stał się zamiennikiem
	public void setProponujacyZamiennik(Student proponujacyZamiennik) {
		this.proponujacyZamiennik = proponujacyZamiennik;
		if (proponujacyZamiennik != null)
			this.czyPropozycja = true;
		else 
			this.czyPropozycja = false;
	}

	public static List<Zamiennik> dajPropozycjeZamiennika(DataAccess dataAccess) {
		Session session = dataAccess.getSession();
		TypedQuery<Zamiennik> typedQuery = session.createQuery("SELECT z FROM Zamiennik z WHERE proponujacyZamiennik IS NOT NULL", Zamiennik.class);
		List<Zamiennik> results = typedQuery.getResultList();
		session.close();
		return results;
	}
	
	public List<Plan> dajPlanyOpisujaceKursyZawierane() {
		List<Plan> outcome = new ArrayList<>();
		for (Kurs zawieranyKurs : getZawieraneKursy())
			if (!outcome.contains(zawieranyKurs.getOpisujacyPlan()))
				outcome.add(zawieranyKurs.getOpisujacyPlan());
		return outcome;
	}
	
	public List<Kierunek> dajKierunkiUstalajacePlany() {
		List<Kierunek> outcome = new ArrayList<>();
		for (Kurs zawieranyKurs : getZawieraneKursy()) {
			if (!outcome.contains(zawieranyKurs.getOpisujacyPlan().getUstalajacyKierunek()))
				outcome.add(zawieranyKurs.getOpisujacyPlan().getUstalajacyKierunek());
		}
		return outcome;
	}
	
	public List<Wydzial> dajWydzialyProwadzaceKierunki() {
		List<Wydzial> outcome = new ArrayList<>();
		for (Kurs zawieranyKurs : getZawieraneKursy()) {
			if (!outcome.contains(zawieranyKurs.getOpisujacyPlan().getUstalajacyKierunek().getProwadzacyWydzial()))
				outcome.add(zawieranyKurs.getOpisujacyPlan().getUstalajacyKierunek().getProwadzacyWydzial());
		}
		return outcome;
	}
	
	public StopienKsztalcenia dajStopienKsztalcenia() {
		if (getZawieraneKursy().size() != 0)
			return getZawieraneKursy().get(0).getOpisujacyPlan().getStopienKsztalcenia();
		else 
			throw new IllegalStateException("Zamiennik musi mieć kursy zawierane!");
	}
	
	public TrybStudiow dajTrybStudiow() {
		if (getZawieraneKursy().size() != 0)
			return getZawieraneKursy().get(0).getOpisujacyPlan().getTrybStudiow();
		else 
			throw new IllegalStateException("Zamiennik musi mieć kursy zawierane!");
	}
	
	public FormaKursu dajFormeZamiennika() {
		if (getZawieraneKursy().size() != 0) {
			for (int i = 0; i < getZawieraneKursy().size()-1; i++)
				if (getZawieraneKursy().get(i).getFormaKursu() != getZawieraneKursy().get(i+1).getFormaKursu())
					return null;
			return getZawieraneKursy().get(0).getFormaKursu();
		}
		else 
			throw new IllegalStateException("Zamiennik musi mieć kursy zawierane!");
	}
	
	public SposobZaliczenia dajSposobZaliczenia() {
		if (getZawieraneKursy().size() != 0) {
			for (int i = 0; i < getZawieraneKursy().size()-1; i++)
				if (getZawieraneKursy().get(i).getSposobZaliczenia() != getZawieraneKursy().get(i+1).getSposobZaliczenia())
					return null;
			return getZawieraneKursy().get(0).getSposobZaliczenia();
		}
		else 
			throw new IllegalStateException("Zamiennik musi mieć kursy zawierane!");
	}
	
	public void dodajKursDoZawieranych(Kurs kurs) {
		if (czyKursMozeBycZawierany(kurs))
			zawieraneKursy.add(kurs);
		obliczSemestryGraniczne();
	}
	
	public boolean czyKursMozeBycZawierany(Kurs kurs) {
		if (kurs.getOpisujacyPlan().getStopienKsztalcenia() != zamienianyKurs.getOpisujacyPlan().getStopienKsztalcenia()) 
			return false;
		if (kurs.getOpisujacyPlan().getTrybStudiow() == TrybStudiow.niestacjonarny && zamienianyKurs.getOpisujacyPlan().getTrybStudiow() == TrybStudiow.stacjonarny) 
			return false;
		if (kurs.equals(zamienianyKurs))
			return false;
		if (zawieraneKursy.contains(kurs))
			return false;
		return true;
	}
	
	public boolean czySpelniaKryteriaNaZamiennik() {
		boolean czyKonczySieEgzaminem = false;
		int sumaEcts = 0;
		for (Kurs kurs : zawieraneKursy) {
			if (kurs.getSposobZaliczenia() == SposobZaliczenia.egzamin)
				czyKonczySieEgzaminem = true;
			sumaEcts += kurs.getEcts();
		}
		if ((zamienianyKurs.getSposobZaliczenia() == SposobZaliczenia.egzamin && !czyKonczySieEgzaminem) || sumaEcts < zamienianyKurs.getEcts())
			return false;
		return true;
	}
	
	private void obliczSemestryGraniczne() {
		if (zawieraneKursy.size() == 0)
			throw new IllegalStateException("Zamiennik musi posiadać zawierane kursy!");
		Semestr semestrRozpoczecia = new Semestr(0, Integer.MIN_VALUE, TypSemestru.Zimowy);
		for (Kurs zawieranyKurs : zawieraneKursy) {
			if (zawieranyKurs.getRozpoczynajacySemestr().getRok() > semestrRozpoczecia.getRok())
				semestrRozpoczecia = zawieranyKurs.getRozpoczynajacySemestr();
			else if (zawieranyKurs.getRozpoczynajacySemestr().getRok() == semestrRozpoczecia.getRok() && zawieranyKurs.getRozpoczynajacySemestr().getTyp() == TypSemestru.Letni)
				semestrRozpoczecia = zawieranyKurs.getRozpoczynajacySemestr();
		}
		this.semestrRozpoczecia = semestrRozpoczecia;
		Semestr semestrZakonczenia = new Semestr(0, Integer.MAX_VALUE, TypSemestru.Letni);
		for (Kurs zawieranyKurs : zawieraneKursy) {
			if (zawieranyKurs.getKonczacySemestr().getRok() < semestrZakonczenia.getRok())
				semestrZakonczenia = zawieranyKurs.getKonczacySemestr();
			else if (zawieranyKurs.getKonczacySemestr().getRok() == semestrZakonczenia.getRok() && zawieranyKurs.getKonczacySemestr().getTyp() == TypSemestru.Zimowy)
				semestrZakonczenia = zawieranyKurs.getKonczacySemestr();
		}
		this.semestrZakonczenia = semestrZakonczenia;
	}
	
	public void usunKursZZawieranych(Kurs kursDoUsuniecia) {
		for (int i = 0; i < zawieraneKursy.size(); i++) {
			if (kursDoUsuniecia.equals(zawieraneKursy.get(i))) {
				zawieraneKursy.remove(i);
				break;
			}
		}
		obliczSemestryGraniczne();
	}
	
}
