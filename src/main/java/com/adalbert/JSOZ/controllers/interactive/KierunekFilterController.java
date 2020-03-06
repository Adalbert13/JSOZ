package com.adalbert.JSOZ.controllers.interactive;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Wydzial;

public class KierunekFilterController extends ModelFilterController<Kierunek> {

	public KierunekFilterController(String id, FilteringWindowController controller) {
		super(id, controller);
	}

	@Override
	public void update() {
		ItemListener itemListener = view.getItemListeners()[0];
		view.removeItemListener(itemListener);
		view.removeAllItems();
		view.addItem(getId());
		for (Kierunek value : getValues()) {
			if (controller.getFilterController("Wydział").getSelected() != null) {
				for (Kierunek kierunek : ((Wydzial)controller.getFilterController("Wydział").getSelected()).getProwadzoneKierunki()) {
					if (kierunek.getNazwa().equals(value.getNazwa()))
						view.addItem(value.toString());
				}
			} else
				selected = null;
		}
		if (selected != null)
			view.setSelectedItem(selected.toString());
		view.addItemListener(itemListener);
	}
	
}
