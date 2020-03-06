package com.adalbert.JSOZ.controllers.windows;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.adalbert.JSOZ.model.DataAccess;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.utils.DetailsPanelInfo;
import com.adalbert.JSOZ.view.utils.KursCellRenderer;
import com.adalbert.JSOZ.view.windows.ListView;
import com.adalbert.JSOZ.view.windows.PropositionDetailsView;
import com.adalbert.JSOZ.view.windows.WindowManager;

import javafx.util.Pair;

public class PropositionDetailsWindowController implements EventHandler {

	private DataAccess dataAccess;
	private WindowManager windowManager;
	private PropositionDetailsView view;
	private Zamiennik rozpatrywanaPropozycja;
	private DefaultListModel<Kurs> modelListyKursow;
	
	public PropositionDetailsWindowController(DataAccess dataAccess, WindowManager windowManager) {
		this.dataAccess = dataAccess;
		this.windowManager = windowManager;
	}
	
	public void assembleView() {
		mockujWybraniePropozycjiZamiennika();
		modelListyKursow = new DefaultListModel<Kurs>();
		for (int i = 0; i < rozpatrywanaPropozycja.getZawieraneKursy().size(); i++) 
			modelListyKursow.add(i, rozpatrywanaPropozycja.getZawieraneKursy().get(i));
		view = new PropositionDetailsView(this);
	}
	
	public DetailsPanelInfo dajDetalePropozycji() {
		DetailsPanelInfo info = new DetailsPanelInfo();
		info.setTitle("Podstawowe informacje");
		info.addCategoryWithValue("Stopień kształcenia: ", rozpatrywanaPropozycja.dajStopienKsztalcenia().getNazwa());
		info.addCategoryWithValue("Tryb studiowania: ", rozpatrywanaPropozycja.dajTrybStudiow().getNazwa());
		info.addCategoryWithValues("Kierunki: ", Stream.of(rozpatrywanaPropozycja.dajKierunkiUstalajacePlany().toArray()).map((elem) -> elem.toString()).collect(Collectors.toList()));
		info.addCategoryWithValues("Słowa kluczowe: ", rozpatrywanaPropozycja.getSlowaKluczowe());
		return info;
	}
	
	public DetailsPanelInfo dajDetaleKursuZamienianego() {
		DetailsPanelInfo info = new DetailsPanelInfo();
		info.setTitle("Kurs zamieniany");
		info.addCategoryWithValue("Nazwa kursu: ", rozpatrywanaPropozycja.getZamienianyKurs().getNazwa());
		info.addCategoryWithValues("Słowa kluczowe: ", rozpatrywanaPropozycja.getZamienianyKurs().getSlowaKluczowe());;
		return info;
	}
	
	public DetailsPanelInfo dajDetaleProponujacego() {
		DetailsPanelInfo info = new DetailsPanelInfo();
		info.setTitle("Proponujący");
		info.addCategoryWithValue("Nazwisko i imię: ", rozpatrywanaPropozycja.getProponujacyZamiennik().getNazwisko() + " " + rozpatrywanaPropozycja.getProponujacyZamiennik().getImie());
		info.addCategoryWithValue("Numer indeksu: ", ""+rozpatrywanaPropozycja.getProponujacyZamiennik().getNrIndeksu());
		return info;
	}
	
	public Zamiennik dajRozpatrywanaPropozycje() {
		return rozpatrywanaPropozycja;
	}
	
	public void mockujWybraniePropozycjiZamiennika() {
		List<Zamiennik> mozliwePropozycje = (List<Zamiennik>) Zamiennik.dajPropozycjeZamiennika(dataAccess);
		rozpatrywanaPropozycja = mozliwePropozycje.get((int)(Math.random()*10000) % mozliwePropozycje.size());
	}

	public void handleEvent(String componentId) {
		switch (componentId) {
			case "back":
				windowManager.popView();
				break;
			case "edit":
				ListWindowController listWindowController = new ListWindowController(dataAccess, windowManager, this);
				listWindowController.assembleView();
				windowManager.pushView(listWindowController.getView());
				break;
			default:
				JOptionPane.showMessageDialog(view, "Nie zaimplementowano w prototypie!", "Brak implementacji", JOptionPane.OK_OPTION);
				break;
		}
	}

	public DataAccess getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	public PropositionDetailsView getView() {
		return view;
	}

	public void setView(PropositionDetailsView view) {
		this.view = view;
	}

	public DefaultListModel<Kurs> getModelListyKursow() {
		return modelListyKursow;
	}

	public void setModelListyKursow(DefaultListModel<Kurs> modelListyKursow) {
		this.modelListyKursow = modelListyKursow;
	}

}
