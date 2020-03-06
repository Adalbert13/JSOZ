package com.adalbert.JSOZ;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Student;
import com.adalbert.JSOZ.model.Zamiennik;

class StudentTest {

	private static Kierunek mockedKierunek;
	private static Zamiennik mockedZamiennik;
	private Student student;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mockedKierunek = Mockito.mock(Kierunek.class);
		mockedZamiennik = Mockito.mock(Zamiennik.class);
	}

	@BeforeEach
	void setUp() throws Exception {
		student = new Student();
	}

	@Test
	void testDodajKierunkiDoEdukujacych_NiepoprawnyKierunek() {
		Kierunek kierunek = null;
		assertThrows(IllegalArgumentException.class, () -> student.dodajKierunekDoEdukujacych(kierunek));
	}
	
	@Test
	void testDodajKierunkiDoEdukujacych_ZwyklaSytuacja() {
		student.dodajKierunekDoEdukujacych(mockedKierunek);
		assertEquals(mockedKierunek, student.getEdukujaceKierunki().get(0));
	}
	
	@Test
	void testGetEdukujaceKierunki_ZadenKierunek() {
		assertArrayEquals(new Kierunek[] {}, student.getEdukujaceKierunki().toArray());
	}
	
	@Test
	void testGetEdukujaceKierunki_ZwyklaSytuacja() {
		for (int i = 0; i < 3; i++)
			student.dodajKierunekDoEdukujacych(mockedKierunek);
		assertArrayEquals(new Kierunek[] {mockedKierunek,mockedKierunek,mockedKierunek}, student.getEdukujaceKierunki().toArray());
	}
	
	@Test
	void testDodajZamiennikDoProponowanych_NiepoprawnyZamiennik() {
		Zamiennik zamiennik = null;
		assertThrows(IllegalArgumentException.class, () -> student.dodajZamiennikDoProponowanych(zamiennik));
	}

	@Test
	void testDodajZamiennikDoProponowanych_ZwyklaSytuacja() {
		student.dodajZamiennikDoProponowanych(mockedZamiennik);
		Mockito.verify(mockedZamiennik).setProponujacyZamiennik(student);
		assertEquals(mockedZamiennik, student.getPropozycjeZamiennikow().get(0));
	}
	
}
