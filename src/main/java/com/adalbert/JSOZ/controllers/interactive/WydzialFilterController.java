package com.adalbert.JSOZ.controllers.interactive;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Wydzial;

public class WydzialFilterController extends ModelFilterController<Wydzial> {

	public WydzialFilterController(String id, FilteringWindowController controller) {
		super(id, controller);
	}

	@Override
	public void update() {
		ItemListener itemListener = view.getItemListeners()[0];
		view.removeItemListener(itemListener);
		view.removeAllItems();
		view.addItem(getId());
		for (Wydzial value : getValues()) {
			view.addItem(value.toString());
		}
		if (selected != null)
			view.setSelectedItem(selected.toString());
		view.addItemListener(itemListener);
	}
	
}
