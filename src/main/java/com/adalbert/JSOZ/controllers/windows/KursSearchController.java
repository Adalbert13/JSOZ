package com.adalbert.JSOZ.controllers.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.ModelPersistable;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.StopienKsztalcenia;
import com.adalbert.JSOZ.model.TrybStudiow;
import com.adalbert.JSOZ.model.Wydzial;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.windows.FilteringView;
import com.adalbert.JSOZ.view.windows.KursSearchView;
import com.adalbert.JSOZ.view.windows.WindowManager;

public class KursSearchController implements EventHandler {

	private DataAccess dataAccess;
	private KursSearchView view;
	private WindowManager windowManager;
	private ListWindowController controller;
	
	private List<Kurs> znalezioneKursy;
	
	public KursSearchController(DataAccess dataAccess, WindowManager windowManager, ListWindowController controller) {
		this.dataAccess = dataAccess;
		this.windowManager = windowManager;
		this.controller = controller;
		mockujWyszukiwanieKursow();
	}
	
	public void dodajKursDoZawieranych (Kurs kurs) {
		controller.dodajKursDoZawieranych(kurs);
	}
	
	@SuppressWarnings("rawtypes")
	public void assembleView() {
		setView(new KursSearchView(this));
		view.updateCoursesContainer();
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isObjectsStringInList(List listOfObjects, Object searchedObject) {
		for (Object object : listOfObjects)
			if (object.toString().equals(searchedObject.toString()))
				return true;
		return false;
	}
	
	public void mockujWyszukiwanieKursow() {
		znalezioneKursy = dataAccess.loadRandomData(Kurs.class, 10);
	}

	@SuppressWarnings("rawtypes")
	public void handleEvent(String componentId) {
		switch (componentId) {
			case "search":
				mockujWyszukiwanieKursow();
				view.updateCoursesContainer();
				break;
			case "back":
				windowManager.popView();
				break;
			default:
				JOptionPane.showMessageDialog(view, "Nie zaimplementowano w prototypie!", "Brak implementacji", JOptionPane.OK_OPTION);
				break;
		}
	}


	public List<Kurs> getZnalezioneKursy() {
		return znalezioneKursy;
	}

	public void setZnalezioneZamienniki(List<Kurs> znalezioneKursy) {
		this.znalezioneKursy = znalezioneKursy;
	}

	public KursSearchView getView() {
		return view;
	}

	public void setView(KursSearchView view) {
		this.view = view;
	}

	
	
}
