package com.adalbert.JSOZ;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adalbert.JSOZ.model.Kurs;

class KursTest {
	
	private Kurs kurs;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		kurs = new Kurs();
	}

	@Test
	void testDodajSlowoKluczowe_Null() {
		String slowoKluczowe = null;
		assertThrows(IllegalArgumentException.class, () -> kurs.dodajSlowoKluczowe(slowoKluczowe));
	}
	
	@Test
	void testDodajSlowoKluczowe_NotNull() {
		String slowoKluczowe = "Słowo";
		kurs.dodajSlowoKluczowe(slowoKluczowe);
		assertArrayEquals(new String[] {slowoKluczowe}, kurs.getSlowaKluczowe().toArray());
	}

	@Test
	void testDodajKursDoGrupy_Null() {
		Kurs innyKurs = null;
		assertThrows(IllegalArgumentException.class, () -> kurs.dodajKursDoGrupy(innyKurs));
	}
	
	@Test
	void testDodajKursDoGrupy_LiczbaEcts() {
		List<Kurs> inneKursy = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Kurs innyKurs = Mockito.mock(Kurs.class);
			Mockito.when(innyKurs.getEcts()).thenReturn(i);
			inneKursy.add(innyKurs);
		}
		assertEquals(0, kurs.getEcts());			// Liczba wynosi 0, ponieważ testujemy abstrakcyjny kurs
		for (Kurs kursDoDodania : inneKursy) 
			kurs.dodajKursDoGrupy(kursDoDodania);
		assertEquals(10, kurs.getEcts());
	}
	
	@Test
	void testDodajKursDoGrupy_ZawartoscGrupy() {
		List<Kurs> inneKursy = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Kurs innyKurs = Mockito.mock(Kurs.class);
			Mockito.when(innyKurs.getEcts()).thenReturn(i);
			inneKursy.add(innyKurs);
		}
		assertArrayEquals(new Kurs[] {}, kurs.getKursyWGrupie().toArray());
		for (Kurs kursDoDodania : inneKursy) 
			kurs.dodajKursDoGrupy(kursDoDodania);
		assertArrayEquals(new Kurs[] {inneKursy.get(0), inneKursy.get(1), inneKursy.get(2)}, kurs.getKursyWGrupie().toArray());
	}

	@Test
	void testCzyKursJestGrupa_NieJest() {
		assertFalse(kurs.czyKursJestGrupa());
	}
	
	@Test
	void testCzyKursJestGrupa_Jest() {
		kurs.dodajKursDoGrupy(Mockito.mock(Kurs.class));
		assertTrue(kurs.czyKursJestGrupa());
	}

}
