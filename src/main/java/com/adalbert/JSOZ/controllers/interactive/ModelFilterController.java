package com.adalbert.JSOZ.controllers.interactive;

import java.util.List;

import javax.swing.JComboBox;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.ModelPersistable;

/**
 * Klasa opisująca abstrakcyjny kontroler filtra
 * @author Wojciech Kania
 */
public abstract class ModelFilterController<T extends ModelPersistable>{
	
	/**
	 * Identyfikator kontrolera filtra
	 */
	protected String id;
	/**
	 * Kontroler okna filtrów
	 */
	protected FilteringWindowController controller;
	/**
	 * Obsługiwany przez kontroler filtra widok
	 */
	protected JComboBox<String> view;
	/**
	 * Lista obiektów klas opisywanych przez model 
	 */
	protected List<T> values;
	/**
	 * Wybrany obiekt klasy opisywanej przez model
	 */
	protected T selected;
	
	/**
	 * Konstruktor filtra, ustawiający jego identyfikator oraz przekazujący referencję na kontroler wyższego poziomu - kontroler okna filtrów
	 * @param id Unikalny identyfikator filtra
	 * @param controller Kontroler wyższego poziomu, kontroler okna filtrów
	 */
	public ModelFilterController(String id, FilteringWindowController controller) {
		this.setId(id);
		this.controller = controller;
		this.selected = null;
	}

	/**
	 * Zwraca wartości opisywane przez model
	 * @return Lista obiektów klas opisywanych przez model
	 */
	public List<T> getValues() {
		return values;
	}

	/**
	 * Ustawia wartości klas opisywanych przez model
	 * @param values Lista wartości klas opisywanych przez model
	 */
	public void setValues(List<T> values) {
		this.values = values;
	}
	
	/**
	 * Metoda zwrotna wywoływana przy zmianie wartości wybranej widoku obsługiwanego przez kontroler.
	 * <br>Spośród wartości modelu zawieranych przez kontroler filtra, wybiera tę, której przekształcenie w łańcuch tekstowy (metoda toString) pasuje do wybranej wartości widoku
	 * <br>Wywołuje metodę filterChanged() w kontrolerze okna filtrów
	 * @param stringValue Tekstowa wartość reprezentująca obiekt klasy opisywanej przez model
	 */
	public void selectedValueCallback(String stringValue) {
		ModelPersistable oldSelected = selected;
		if (id.equals(stringValue))
			setSelected(null);
		else
			for (T value : values)
				if (value.toString().equals(stringValue))
					setSelected(value);
		controller.filterChanged(oldSelected, selected);
	}
	
	/**
	 * Zwraca identyfikator kontrolera filtra
	 * @return Identyfikator kontrolera filtra
	 */
	public String getId() {
		return id;
	}

	/**
	 * Ustawia identyfikator kontrolera filtra
	 * @param id Identyfikator kontrolera filtra
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Zwraca wybraną wartość opisywaną przez model
	 * @return Obiekt klasy opisywanej przez model
	 */
	public T getSelected() {
		return selected;
	}

	/**
	 * Ustawia wybraną wartość opisywaną przez model
	 * @param selected Obiekt klasy opisywanej przez model
	 */
	public void setSelected(T selected) {
		this.selected = selected;
	}

	/**
	 * Zwraca kontrolowany widok
	 * @return Kontrolowany widok
	 */
	public JComboBox<String> getView() {
		return view;
	}

	/**
	 * Ustawia kontrolowany widok
	 * @param view Kontrolowany widok
	 */
	public void setView(JComboBox<String> view) {
		this.view = view;
	}
	
	/**
	 * Anuluje wybór wartości przez filtr i ustawia go na elemencie początkowym
	 */
	public void resetFilter() {
		this.selected = null;
		this.view.setSelectedItem(getId());
	}
	
	/**
	 * Opisuje strategię odświeżania informacji w filtru, zależną od typu filtrowanych wartości
	 */
	public abstract void update();

}
