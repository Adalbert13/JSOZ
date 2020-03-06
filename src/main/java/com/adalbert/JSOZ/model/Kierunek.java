package com.adalbert.JSOZ.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * Klasa opisująca kierunek
 * @author Wojciech Kania
 */
@Entity
@Table(name = "kierunki")
public class Kierunek implements ModelPersistable {
	
	/**
	 * Unikalny kod kierunku
	 */
	@Id
	@Column(name="kierunekKod")
	private String kierunekKod;
	
	
	/**
	 * Nazwa kierunku
	 */
	@Column(name ="nazwa")
	private String nazwa;

	/**
	 * Wydział, który prowadzi kierunek
	 */
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="prowadzacyWydzial")
	private Wydzial prowadzacyWydzial;
    
    /**
	 * Lista planów ustalanych przez kierunek
	 */
    @OneToMany(mappedBy="ustalajacyKierunek", fetch=FetchType.EAGER)
	private List<Plan> ustalanePlany;
    
    /**
	 * Lista kierunkowych efektów kształcenia definiowanych przez kierunek
	 */
    @OneToMany(mappedBy = "definiujacyKierunek")	
    private List<KierunkowyEfektKsztalcenia> definiowaneKEKi;
    
    /**
	 * Lista studentów edukowanych na kierunku
	 */
    @ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
			name="kierunki_studenci",
			joinColumns = { @JoinColumn(name = "kierunekKod") },
			inverseJoinColumns = { @JoinColumn(name = "nrIndeksu") }
	)
    private List<Student> edukowaniStudenci;
     
    /**
	 * Konstruktor inicjalizujący pola będące kolekcjami
	 */
    public Kierunek() {
    	edukowaniStudenci = new ArrayList<>();
    	definiowaneKEKi = new ArrayList<>();
    	ustalanePlany = new ArrayList<>();
    }

    /**
	 * Konstruktor inicjalizujący pola wymagane przez model
	 * @param kierunekKod Unikalny kod kierunku
	 * @param nazwa Nazwa kierunku
	 * @param wydzial Wydział na którym prowadzony jest kierunek
	 */
	public Kierunek(String kierunekKod, String nazwa, Wydzial wydzial) {
		this();
		this.setKierunekKod(kierunekKod);
		this.nazwa = nazwa;
		this.prowadzacyWydzial = wydzial;
	}
	
	/**
	 * Zwraca nazwę kierunku
	 * @return Nazwa kierunku
	 */
	public String getNazwa() {
		return nazwa;
	}

	/**
	 * Ustawia nazwę kierunku
	 * @param nazwa Nazwa kierunku
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	/**
	 * Zwraca prowadzący wydział
	 * @return Prowadzący wydział
	 */
	public Wydzial getProwadzacyWydzial() {
		return prowadzacyWydzial;
	}

	/**
	 * Ustawia prowadzący wydział
	 * @param prowadzacyWydzial Prowadzący wydział
	 */
	public void setProwadzacyWydzial(Wydzial prowadzacyWydzial) {
		this.prowadzacyWydzial = prowadzacyWydzial;
	}

	/**
	 * Zwraca listę definiowanych kierunkowych efektów kształcenia
	 * @return Lista definiowanych kierunkowych efektów kształcenia
	 */
	public List<KierunkowyEfektKsztalcenia> getDefiniowaneKEKi() {
		return definiowaneKEKi;
	}

	/**
	 * Ustawia listę definiowanych kierunkowych efektów kształcenia
	 * @param definiowaneKEKi Lista definiowanych kierunkowych efektów kształcenia
	 */
	public void setDefiniowaneKEKi(List<KierunkowyEfektKsztalcenia> definiowaneKEKi) {
		this.definiowaneKEKi = definiowaneKEKi;
	}

	/**
	 * Zwraca listę ustalanych planów
	 * @return Lista ustalanych planów
	 */
	public List<Plan> getUstalanePlany() {
		return ustalanePlany;
	}
	
	/**
	 * Ustawia listę ustalanych planów
	 * @param ustalanePlany Lista ustalanych planów
	 */
	public void setUstalanePlany(List<Plan> ustalanePlany) {
		this.ustalanePlany = ustalanePlany;
	}

	/**
	 * Zwraca listę edukowanych studentów
	 * @return Lista edukowanych studentów
	 */
	public List<Student> getEdukowaniStudenci() {
		return edukowaniStudenci;
	}

	/**
	 * Ustawia listę edukowanych studentów
	 * @param edukowaniStudenci Lista edukowanych studentów
	 */
	public void setEdukowaniStudenci(List<Student> edukowaniStudenci) {
		this.edukowaniStudenci = edukowaniStudenci;
	}	
	
	/**
	 * Zwraca unikalny kod kierunku
	 * @return Unikalny kod kierunku
	 */
	public String getKierunekKod() {
		return kierunekKod;
	}

	/**
	 * Ustawia kod kierunku
	 * @param kierunekKod Kod kierunku
	 */
	public void setKierunekKod(String kierunekKod) {
		this.kierunekKod = kierunekKod;
	}
	
	// ********************************************************************************
	// ********** Metody funkcjonalne *************************************************
	// ********************************************************************************
	
	/**
	 * Dodaje studenta do edukowanych, jednocześnie zmieniając informacje o edukujących kierunkach w studencie
	 * @param edukowanyStudent Student dodawany do edukowanych
	 * @throws IllegalArgumentException Jeżeli referencja na edukowanego studenta jest null'em
	 */
	public void dodajStudentaDoEdukowanych(Student edukowanyStudent) {
		if (edukowanyStudent == null)
			throw new IllegalArgumentException("Edukowany student nie może być null'em!");
		edukowaniStudenci.add(edukowanyStudent);
		if (edukowanyStudent.getEdukujaceKierunki() != null && !edukowanyStudent.getEdukujaceKierunki().contains(this))
			edukowanyStudent.dodajKierunekDoEdukujacych(this);
	}
	
	/**
	 * @return Zwraca łańcuch tekstowy będący nazwą kierunku
	 */
	@Override
	public String toString() {
		return nazwa;
	}
	
}
