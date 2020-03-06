package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plany")
public class Plan implements ModelPersistable {

	@Id
	@Column(name = "planKod")
	private String planKod;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="semestrRozpoczecia")
	private Semestr semestrRozpoczecia;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="semestrZakonczenia")
	private Semestr semestrZakonczenia;
	
	@Column(name = "trybStudiow")
	private TrybStudiow trybStudiow;
	
	@Column(name = "stopienKsztalcenia")
	private StopienKsztalcenia stopienKsztalcenia;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ustalajacyKierunek")
	private Kierunek ustalajacyKierunek;
	
	@OneToMany(mappedBy="opisujacyPlan")
	private List<Kurs> opisywaneKursy;
	
	public Plan() {
		opisywaneKursy = new ArrayList<>();
	}
	
	public Plan(String kod, Kierunek ustalajacyKierunek, Semestr semestrRozpoczecia, Semestr semestrZakonczenia) {
		this();
		this.planKod = kod;
		this.ustalajacyKierunek = ustalajacyKierunek;
		this.semestrRozpoczecia = semestrRozpoczecia;
		this.semestrZakonczenia = semestrZakonczenia;
	}

	public String getPlanKod() {
		return planKod;
	}

	public void setPlanKod(String kod) {
		this.planKod = kod;
	}

	public TrybStudiow getTrybStudiow() {
		return trybStudiow;
	}

	public void setTrybStudiow(TrybStudiow trybStudiow) {
		this.trybStudiow = trybStudiow;
	}

	public StopienKsztalcenia getStopienKsztalcenia() {
		return stopienKsztalcenia;
	}

	public void setStopienKsztalcenia(StopienKsztalcenia stopienKsztalcenia) {
		this.stopienKsztalcenia = stopienKsztalcenia;
	}

	public Kierunek getUstalajacyKierunek() {
		return ustalajacyKierunek;
	}

	public void setUstalajacyKierunek(Kierunek ustalajacyKierunek) {
		this.ustalajacyKierunek = ustalajacyKierunek;
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

	public List<Kurs> getOpisywaneKursy() {
		return opisywaneKursy;
	}

	public void setOpisywaneKursy(List<Kurs> opisywaneKursy) {
		this.opisywaneKursy = opisywaneKursy;
	}
	
	@Override
	public String toString() {
		return planKod;
	}
}
