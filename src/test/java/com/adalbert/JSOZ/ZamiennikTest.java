package com.adalbert.JSOZ;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.Student;
import com.adalbert.JSOZ.model.Zamiennik;

public class ZamiennikTest {
	
	private static Plan mockedPlan;
	private static Student mockedStudent;
	private static Kurs mockedKurs;
	private Zamiennik zamiennik;
	
	
	@BeforeAll
	public static void setUpBeforeClass() {
		mockedStudent = Mockito.mock(Student.class);
		mockedPlan = Mockito.mock(Plan.class);
		mockedKurs = Mockito.mock(Kurs.class);
		Mockito.when(mockedKurs.getOpisujacyPlan()).thenReturn(mockedPlan);
	}
	
	@BeforeEach
	public void setUp() {
		zamiennik = new Zamiennik();
	}
	
	@Test
	public void testSetProponujacyZamiennik() {
		zamiennik.setProponujacyZamiennik(mockedStudent);
		assertNotNull(zamiennik.getProponujacyZamiennik());
	}
	
	@Test
	public void testSetZawieraneKursy() {
		List<Kurs> mockedKursy = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			mockedKursy.add(mockedKurs);
		zamiennik.setZawieraneKursy(mockedKursy);
		assertEquals(mockedKursy, zamiennik.getZawieraneKursy());
	}
	
	@Test
	public void testDajPlanyOpisujaceKursyZawierane_BrakKursow() {
		assertEquals(0, zamiennik.getZawieraneKursy().size());
	}
	
	@Test
	public void testDajPlanyOpisujaceKursyZawierane_ZwyklyPrzypadek() {
		List<Kurs> mockedKursy = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			mockedKursy.add(mockedKurs);
		zamiennik.setZawieraneKursy(mockedKursy);
		assertEquals(Arrays.asList(new Plan[] {mockedPlan}), zamiennik.dajPlanyOpisujaceKursyZawierane());
	}

}
