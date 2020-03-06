package com.adalbert.JSOZ;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.adalbert.JSOZ.controllers.interactive.ModelFilterController;
import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.ModelPersistable;

class ModelFilterControllerTest {

	private static FilteringWindowController filteringWindowController;
	private ModelFilterController<Kurs> modelFilterController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		filteringWindowController = Mockito.mock(FilteringWindowController.class);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		modelFilterController = new ModelFilterController<Kurs>("id", filteringWindowController) {
			@Override
			public void update() {}
		};
	}

	@Test
	void testSelectedValueCallback_WybranoKurs() {
		modelFilterController.setSelected(null);
		Kurs mockedKurs = Mockito.mock(Kurs.class);
		Mockito.when(mockedKurs.toString()).thenReturn("Nazwa kursu");
		
		List<Kurs> wartosciFiltra = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			if (i % 3 == 0)
				wartosciFiltra.add(mockedKurs);
			else {
				Kurs mockedWartoscFiltra = Mockito.mock(Kurs.class);
				Mockito.when(mockedWartoscFiltra.toString()).thenReturn("Kurs "+i);
				wartosciFiltra.add(mockedWartoscFiltra);
			}
		}
		modelFilterController.setValues(wartosciFiltra);
		
		modelFilterController.selectedValueCallback(mockedKurs.toString());
		Mockito.verify(filteringWindowController).filterChanged(null, mockedKurs);
		assertEquals(mockedKurs, modelFilterController.getSelected());
	}
	
	@Test
	void testSelectedValueCallback_BrakWyboru() {
		ArgumentCaptor<ModelPersistable> oldSelected = ArgumentCaptor.forClass(Kurs.class);
		ArgumentCaptor<ModelPersistable> newSelected = ArgumentCaptor.forClass(Kurs.class);
		
		assertNull(modelFilterController.getValues());
		modelFilterController.selectedValueCallback("id");
		Mockito.verify(filteringWindowController, Mockito.times(2)).filterChanged(oldSelected.capture(), newSelected.capture());
		assertNull(newSelected.getValue());
	}
	
	@Test
	void testSelectedValueCallback_BrakWartosciFiltrow() {
		assertThrows(NullPointerException.class, () -> modelFilterController.selectedValueCallback("Nazwa kursu"));
	}

	@Test
	void testSetSelected() {
		Kurs selected = Mockito.mock(Kurs.class);
		modelFilterController.setSelected(selected);
		assertEquals(selected, modelFilterController.getSelected());
	}

}
