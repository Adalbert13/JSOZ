package com.adalbert.JSOZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.adalbert.JSOZ.model.FormaKursu;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.KierunkowyEfektKsztalcenia;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.Semestr;
import com.adalbert.JSOZ.model.SposobZaliczenia;
import com.adalbert.JSOZ.model.StopienKsztalcenia;
import com.adalbert.JSOZ.model.Student;
import com.adalbert.JSOZ.model.TrybStudiow;
import com.adalbert.JSOZ.model.TypEfektuKsztalcenia;
import com.adalbert.JSOZ.model.TypSemestru;
import com.adalbert.JSOZ.model.Wydzial;
import com.adalbert.JSOZ.model.Zamiennik;

public class LoadRandomData
{
	protected SessionFactory sessionFactory;
	private static Random random;
	 
    protected void setup() {
    	Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
    	sessionFactory = configuration.buildSessionFactory();
    	random = new Random();
    }
 
    protected void exit() {
    	sessionFactory.close();
    }
 
    protected void create() {
    	Session session = sessionFactory.openSession();
    	session.beginTransaction();
    	
    	List<Wydzial> wydzialy = new ArrayList<>();
    	for (int i = 0; i < 5; i++)
    		wydzialy.add(new Wydzial(randomString(4)));
    	for (Wydzial wydzial : wydzialy)
    		session.saveOrUpdate(wydzial);
    	
    	Wydzial testowanyWydzial = new Wydzial("Informatyka i Zarządzanie");
    	session.saveOrUpdate(testowanyWydzial);
    	
    	List<Kierunek> kierunki = new ArrayList<>();
    	for (Wydzial wydzial : wydzialy)
    		for (int i = 0; i < 4; i++)
    			kierunki.add(new Kierunek(randomString(5), randomString(20), wydzial));
    	for (Kierunek kierunek : kierunki)
    		session.saveOrUpdate(kierunek);
    	
    	Kierunek testowanyKierunek = new Kierunek("WIZ-INF", "Informatyka", testowanyWydzial);
    	session.saveOrUpdate(testowanyKierunek);
    	
    	List<Semestr> semestry = new ArrayList<>();
    	for (int rok = 2010; rok < 2030; rok++) {
    		semestry.add(new Semestr(Integer.parseInt(rok+"1"), rok, TypSemestru.Zimowy));
    		semestry.add(new Semestr(Integer.parseInt(rok+"2"), rok, TypSemestru.Letni));
    	}
    	for (Semestr semestr : semestry)
    		session.saveOrUpdate(semestr);
    	
    	List<Plan> plany = new ArrayList<>();
    	for (Kierunek kierunek : kierunki) 
    		for (int i = 0; i < 2; i++) {
    			plany.add(new Plan(randomString(6), kierunek, semestry.get(0), semestry.get(semestry.size()-1)));
    			plany.get(plany.size() - 1).setTrybStudiow(TrybStudiow.values()[random.nextInt(TrybStudiow.values().length)]);
    			plany.get(plany.size() - 1).setStopienKsztalcenia(StopienKsztalcenia.values()[random.nextInt(StopienKsztalcenia.values().length)]);
    		}
    	for (Plan plan : plany)
    		session.saveOrUpdate(plan);
    	
    	Plan testowanyPlan = new Plan("WIZINF01", testowanyKierunek, semestry.get(0), semestry.get(semestry.size()-1));
    	testowanyPlan.setTrybStudiow(TrybStudiow.stacjonarny);
    	testowanyPlan.setStopienKsztalcenia(StopienKsztalcenia.I);
    	session.saveOrUpdate(testowanyPlan);
    	
    	
    	List<Kurs> kursy = new ArrayList<>();
    	for (Plan plan : plany) 
    		for (int i = 0; i < 10; i++) {
    			kursy.add(new Kurs(randomString(6), randomString(25), plan, random.nextInt(6)));
    			kursy.get(kursy.size() - 1).setSposobZaliczenia(SposobZaliczenia.values()[random.nextInt(SposobZaliczenia.values().length)]);
    	    	kursy.get(kursy.size() - 1).setFormaKursu(FormaKursu.values()[random.nextInt(FormaKursu.values().length)]);
    	    	kursy.get(kursy.size() - 1).setSlowaKluczowe(Arrays.asList(new String[] {randomString(6), randomString(6), randomString(6)}));
    		}
    	for (Kurs kurs : kursy)
    		session.saveOrUpdate(kurs);
    	
    	List<Kurs> testowaneKursy = new ArrayList<>();
    	for (int i = 0; i < 10; i++) {
    		Kurs testowanyKurs = new Kurs("WIZINF01_" + i, "Kurs " + i, testowanyPlan, (i+1));
    		testowanyKurs.setSposobZaliczenia(SposobZaliczenia.zaliczenieNaOcene);
    		testowanyKurs.setFormaKursu(FormaKursu.laboratium);
    		testowanyKurs.setSlowaKluczowe(Arrays.asList(new String[] {"Slowo 1", "Slowo 2", "Slowo 3"}));
    		testowaneKursy.add(testowanyKurs);
    	}
    	for (Kurs kurs : testowaneKursy) 
    		session.saveOrUpdate(kurs);
    	
    	List<Student> studenci = new ArrayList<>();
    	for (Kierunek kierunek : kierunki) {
    		for (int i = 0; i < 10; i++) {
    			Student student = new Student(random.nextInt(999999));
    			student.dodajKierunekDoEdukujacych(kierunki.get(random.nextInt(kierunki.size())));
    			student.setNazwisko(randomString(10));
    			student.setImie(randomString(8));
    			studenci.add(student);
    		}
    	}
    	for (Student student : studenci)
    		session.saveOrUpdate(student);
    	
    	List<Zamiennik> zamienniki = new ArrayList<>();
    	for (Kurs kurs : kursy) {
    		if (random.nextInt(10) < 4) {
    			Zamiennik zamiennikDoDodania = new Zamiennik(randomString(6), kurs);
    			zamiennikDoDodania.setSlowaKluczowe(Arrays.asList(new String[] {randomString(6), randomString(6), randomString(6)}));
    			for (int i = 0; i < 3; i++) {
	    			int randomCourseIndex = random.nextInt(kursy.size());
	    			if (!zamiennikDoDodania.getZawieraneKursy().contains(kursy.get(randomCourseIndex)) && zamiennikDoDodania.czyKursMozeBycZawierany(kursy.get(randomCourseIndex)))
	    				zamiennikDoDodania.dodajKursDoZawieranych(kursy.get(randomCourseIndex));
	    			else {
	    				i--; continue;
	    			}
    			}
    			if (zamiennikDoDodania.czySpelniaKryteriaNaZamiennik()) {
	    			if (random.nextInt(10) < 4)
	    				studenci.get(random.nextInt(studenci.size())).dodajZamiennikDoProponowanych(zamiennikDoDodania);
	    			zamienniki.add(zamiennikDoDodania);
    			}
    		}
    	}
    	for (Zamiennik zamiennik : zamienniki)
    		session.saveOrUpdate(zamiennik);
    	
    	List<Zamiennik> testowaneZamienniki = new ArrayList<>();
    	for (int i = 2; i < 10; i++) {
    		Zamiennik zamiennikDoDodania = new Zamiennik("ZAM0" + i, testowaneKursy.get(i));
    		zamiennikDoDodania.dodajKursDoZawieranych(testowaneKursy.get(i-2));
    		zamiennikDoDodania.dodajKursDoZawieranych(testowaneKursy.get(i-1));
        	zamiennikDoDodania.setSlowaKluczowe(Arrays.asList(new String[] {"Slowo 1", "Slowo 2", "Slowo 3"}));
        	studenci.get(random.nextInt(studenci.size())).dodajZamiennikDoProponowanych(zamiennikDoDodania);
        	testowaneZamienniki.add(zamiennikDoDodania);
    	}
    	for (Zamiennik zamiennik : testowaneZamienniki) 
    		session.saveOrUpdate(zamiennik);
    	
    	session.getTransaction().commit();
    	session.close();
    }
    
    public static String randomString(int length) {
    	StringBuilder bob = new StringBuilder();
    	for (int i = 0; i < length; i++) 
    		bob.append((char) (random.nextInt('Z'-'A' + 1) + 'A'));
    	return bob.toString();
    }
  
    public static void main(String[] args) {
    	 LoadRandomData manager = new LoadRandomData();
    	 manager.setup();
    	 manager.create();
    	 manager.exit();
    }
}
