package com.adalbert.JSOZ;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Student;

class KierunekTest {

	private static Student mockedStudent;
	private Kierunek kierunek;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mockedStudent = Mockito.mock(Student.class);
	}

	@BeforeEach
	void setUp() throws Exception {
		kierunek = new Kierunek();
	}

	@Test
	void testDodajStudentaDoEdukowanych_NiepoprawnyStudent() {
		Student student = null;	
		assertThrows(IllegalArgumentException.class, () -> kierunek.dodajStudentaDoEdukowanych(student));
	}
	
	@Test
	void testDodajStudentaDoEdukowanych_ZwyklaSytuacja() {
		kierunek.dodajStudentaDoEdukowanych(mockedStudent);
		Mockito.verify(mockedStudent).dodajKierunekDoEdukujacych(kierunek);
		assertArrayEquals(new Student[] {mockedStudent}, kierunek.getEdukowaniStudenci().toArray());
	}
	
	@Test
	void testSetNazwa() {
		kierunek.setNazwa("Nazwa kierunku");
		assertEquals("Nazwa kierunku", kierunek.getNazwa());
	}
	
	@Test
	void testToString_NazwaNull() {
		assertNull(kierunek.toString());
	}
	
	@Test
	void testToString_NazwaNadana() {
		kierunek.setNazwa("Nazwa kierunku");
		assertEquals("Nazwa kierunku", kierunek.toString());
	}

}
