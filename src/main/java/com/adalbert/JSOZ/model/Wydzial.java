package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.hibernate.query.Query;


@Entity
@Table(name="wydzialy")
public class Wydzial implements ModelPersistable {

	@Id
	@Column(name = "nazwa")
	private String nazwa;
	
	@OneToMany(mappedBy="prowadzacyWydzial", fetch=FetchType.EAGER)
	private List<Kierunek> prowadzoneKierunki;
	
	public Wydzial() {
		prowadzoneKierunki = new ArrayList<>();
	}
	
	public Wydzial(String nazwa) {
		this();
		this.nazwa = nazwa;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Kierunek> getProwadzoneKierunki() {
		return prowadzoneKierunki;
	}

	public void setProwadzoneKierunki(List<Kierunek> prowadzoneKierunki) {
		this.prowadzoneKierunki = prowadzoneKierunki;
	}
	
	@Override
	public String toString() {
		return nazwa;
	}
}
