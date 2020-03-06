package com.adalbert.JSOZ.controllers.windows;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.adalbert.JSOZ.controllers.interactive.FormaFilterController;
import com.adalbert.JSOZ.controllers.interactive.KierunekFilterController;
import com.adalbert.JSOZ.controllers.interactive.ModelFilterController;
import com.adalbert.JSOZ.controllers.interactive.PlanFilterController;
import com.adalbert.JSOZ.controllers.interactive.StopienFilterController;
import com.adalbert.JSOZ.controllers.interactive.TrybFilterController;
import com.adalbert.JSOZ.controllers.interactive.WydzialFilterController;
import com.adalbert.JSOZ.model.DataAccess;
import com.adalbert.JSOZ.model.FormaKursu;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.ModelPersistable;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.StopienKsztalcenia;
import com.adalbert.JSOZ.model.TrybStudiow;
import com.adalbert.JSOZ.model.Wydzial;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.windows.FilteringView;
import com.adalbert.JSOZ.view.windows.WindowManager;

public class FilteringWindowController implements EventHandler {

	private DataAccess dataAccess;
	@SuppressWarnings("rawtypes")
	private List<ModelFilterController> filterControllers;
	private FilteringView view;
	private WindowManager windowManager;
	
	private List<Zamiennik> znalezioneZamienniki;
	private List<ModelPersistable> constraints;
	
	
	public FilteringWindowController(DataAccess dataAccess, WindowManager windowManager) {
		this.dataAccess = dataAccess;
		this.filterControllers = new ArrayList<>();	
		this.constraints = new ArrayList<>();
		this.windowManager = windowManager;
		
		mockujWyszukiwanieZamiennikow();
	}
	
	@SuppressWarnings("unchecked")
	public void assembleFilters() {
		getFilterControllers().add(new TrybFilterController("Tryb Studiów", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(Arrays.asList(TrybStudiow.values()));
		
		getFilterControllers().add(new StopienFilterController("Stopień Kształcenia", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(Arrays.asList(StopienKsztalcenia.values()));
		
		getFilterControllers().add(new FormaFilterController("Forma Kursu", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(Arrays.asList(FormaKursu.values()));
		
		getFilterControllers().add(new WydzialFilterController("Wydział", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(dataAccess.loadAllData(Wydzial.class));
		
		getFilterControllers().add(new KierunekFilterController("Kierunek", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(dataAccess.loadAllData(Kierunek.class));
		
		getFilterControllers().add(new PlanFilterController("Plan", this));
		getFilterControllers().get(filterControllers.size() - 1).setValues(dataAccess.loadAllData(Plan.class));
	}
	
	@SuppressWarnings("rawtypes")
	public void assembleView() {
		setView(new FilteringView(this));
		view.updateZamiennikContainer();
		for (ModelFilterController filterController : filterControllers) 
			filterController.update();
	}
	
	@SuppressWarnings("rawtypes")
	public void filterChanged(ModelPersistable oldValue, ModelPersistable newValue) {
		constraints.remove(oldValue);
		if (newValue != null)
			constraints.add(newValue);
		for (ModelFilterController filterController : filterControllers) 
			filterController.update();
	}
	
	public List<Zamiennik> dajFiltrowaneZamienniki() {
		List<Zamiennik> outcome = new ArrayList<>();
		boolean toAddOrNotToAdd;
		for (Zamiennik znalezionyZamiennik : znalezioneZamienniki) {
			toAddOrNotToAdd = true;
			for (ModelPersistable constraint : constraints) {
				if (constraint instanceof Plan) {
					if (!isObjectsStringInList(znalezionyZamiennik.dajPlanyOpisujaceKursyZawierane(), constraint))
						toAddOrNotToAdd = false;
				} else if (constraint instanceof Kierunek) {
					if (!isObjectsStringInList(znalezionyZamiennik.dajKierunkiUstalajacePlany(), constraint))
						toAddOrNotToAdd = false;
				} else if (constraint instanceof Wydzial) {
					if (!isObjectsStringInList(znalezionyZamiennik.dajWydzialyProwadzaceKierunki(), constraint))
						toAddOrNotToAdd = false;
				} else if (constraint instanceof TrybStudiow) {
					if (znalezionyZamiennik.dajTrybStudiow() != null &&  znalezionyZamiennik.dajTrybStudiow() != ((TrybStudiow) constraint))
						toAddOrNotToAdd = false;
				} else if (constraint instanceof StopienKsztalcenia) {
					if (znalezionyZamiennik.dajStopienKsztalcenia() != null && znalezionyZamiennik.dajStopienKsztalcenia() != ((StopienKsztalcenia) constraint))
						toAddOrNotToAdd = false;
				} else if (constraint instanceof FormaKursu) {
					if (znalezionyZamiennik.dajFormeZamiennika() != null && znalezionyZamiennik.dajFormeZamiennika() != ((FormaKursu) constraint))
						toAddOrNotToAdd = false;
				}
			}
			if (toAddOrNotToAdd)
				outcome.add(znalezionyZamiennik);
		}
		return outcome;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isObjectsStringInList(List listOfObjects, Object searchedObject) {
		for (Object object : listOfObjects)
			if (object.toString().equals(searchedObject.toString()))
				return true;
		return false;
	}
	
	public void mockujWyszukiwanieZamiennikow() {
		znalezioneZamienniki = dataAccess.loadAllData(Zamiennik.class);
	}

	@SuppressWarnings("rawtypes")
	public void handleEvent(String componentId) {
		switch (componentId) {
			case "search":
				mockujWyszukiwanieZamiennikow();
				view.updateZamiennikContainer();
				break;
			case "back":
				windowManager.popView();
				break;
			case "filter":
				view.updateZamiennikContainer();
				break;
			case "clearFilters":
				constraints = new ArrayList<>();
				for (ModelFilterController filterController : filterControllers)
					filterController.resetFilter();
				view.updateZamiennikContainer();
				break;
			default:
				JOptionPane.showMessageDialog(view, "Nie zaimplementowano w prototypie!", "Brak implementacji", JOptionPane.OK_OPTION);
				break;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List<ModelFilterController> getFilterControllers() {
		return filterControllers;
	}
	
	@SuppressWarnings("rawtypes")
	public ModelFilterController getFilterController(String id) {
		for (ModelFilterController filterController : filterControllers) {
			if (filterController.getId().equals(id))
				return filterController;
		}
		return null;
	}

	public List<Zamiennik> getZnalezioneZamienniki() {
		return znalezioneZamienniki;
	}

	public void setZnalezioneZamienniki(List<Zamiennik> znalezioneZamienniki) {
		this.znalezioneZamienniki = znalezioneZamienniki;
	}

	public FilteringView getView() {
		return view;
	}

	public void setView(FilteringView view) {
		this.view = view;
	}

	
	
}
