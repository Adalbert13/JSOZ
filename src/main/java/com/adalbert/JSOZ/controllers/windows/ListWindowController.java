package com.adalbert.JSOZ.controllers.windows;

import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.adalbert.JSOZ.model.DataAccess;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.windows.ListView;
import com.adalbert.JSOZ.view.windows.WindowManager;

public class ListWindowController implements EventHandler {
	
	private DataAccess dataAccess;
	private WindowManager windowManager;
	private PropositionDetailsWindowController controller;
	private List<Kurs> listaKursowZawieranych;
	private ListView view;

	public ListWindowController(DataAccess dataAccess, WindowManager windowManager, PropositionDetailsWindowController controller) {
		this.controller = controller;
		this.dataAccess = dataAccess;
		this.windowManager = windowManager;
		listaKursowZawieranych = controller.dajRozpatrywanaPropozycje().getZawieraneKursy();
	}
	
	public void assembleView() {
		view = new ListView(this);
	}
	
	public Zamiennik dajRozpatrywanaPropozycje() {
		return controller.dajRozpatrywanaPropozycje();
	}
	
	public DefaultListModel<Kurs> getModelListyKursow() {
		return controller.getModelListyKursow();
	}
	
	public void usunKursZZawieranych() {
		int input = JOptionPane.showConfirmDialog(view, "Czy na pewno chcesz usunąć stąd ten kurs?", "Potwierdzenie", JOptionPane.OK_CANCEL_OPTION);
		if (input == 0) {
			Kurs kursDoUsuniecia = (Kurs) view.getListaKursow().getSelectedValue();
			controller.getModelListyKursow().remove(view.getListaKursow().getSelectedIndex());
			dajRozpatrywanaPropozycje().usunKursZZawieranych(kursDoUsuniecia);
			if (!dajRozpatrywanaPropozycje().czySpelniaKryteriaNaZamiennik()) {
				dajRozpatrywanaPropozycje().dodajKursDoZawieranych(kursDoUsuniecia);
				controller.getModelListyKursow().addElement(kursDoUsuniecia);
				JOptionPane.showMessageDialog(view, "Anulowano operację, gdyż zamiennik stałby się niepoprawny!", "Niepoprawny zamiennik", JOptionPane.OK_OPTION);
			} 
			else
				controller.getDataAccess().saveOrUpdate(Arrays.asList(new Zamiennik[] {dajRozpatrywanaPropozycje()}));
		}
	}
	
	public void dodajKursDoZawieranych (Kurs kurs) {
		if (dajRozpatrywanaPropozycje().czyKursMozeBycZawierany(kurs))  {
			controller.getModelListyKursow().addElement(kurs);
			dajRozpatrywanaPropozycje().dodajKursDoZawieranych(kurs);
			controller.getDataAccess().saveOrUpdate(Arrays.asList(new Zamiennik[] {dajRozpatrywanaPropozycje()}));
		}
		else 
			JOptionPane.showMessageDialog(view, "Anulowano operację, gdyż zamiennik stałby się niepoprawny!", "Niepoprawny zamiennik", JOptionPane.OK_OPTION);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(String componentId) {
		switch (componentId) {
			case "back":
				windowManager.popView();
				break;
			case "add":
				KursSearchController kursSearchController = new KursSearchController(dataAccess, windowManager, this);
				kursSearchController.assembleView();
				windowManager.pushView(kursSearchController.getView());
				break;
			case "remove":
				if (view.getListaKursow().getSelectedValue() != null)
					usunKursZZawieranych();
				else
					JOptionPane.showMessageDialog(view, "Nie wybrałeś kursu!", "Brak zdecydowania", JOptionPane.OK_OPTION);
				break;
			default:
				JOptionPane.showMessageDialog(view, "Nie zaimplementowano w prototypie!", "Brak implementacji", JOptionPane.OK_OPTION);
				break;
		}
	}

	public List<Kurs> getListaKursowZawieranych() {
		return listaKursowZawieranych;
	}

	public void setListaKursowZawieranych(List<Kurs> listaKursowZawieranych) {
		this.listaKursowZawieranych = listaKursowZawieranych;
	}

	public ListView getView() {
		return view;
	}

	public void setView(ListView view) {
		this.view = view;
	}

}
