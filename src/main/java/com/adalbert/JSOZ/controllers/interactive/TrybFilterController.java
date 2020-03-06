package com.adalbert.JSOZ.controllers.interactive;

import java.awt.event.ItemListener;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.TrybStudiow;

public class TrybFilterController extends ModelFilterController<TrybStudiow> {
	
	public TrybFilterController(String id, FilteringWindowController controller) {
		super(id, controller);
	}

	@Override
	public void update() {
		ItemListener itemListener = view.getItemListeners()[0];
		view.removeItemListener(itemListener);
		view.removeAllItems();
		view.addItem(getId());
		for (TrybStudiow value : getValues())
			view.addItem(value.toString());
		if (selected != null)
			view.setSelectedItem(selected.toString());
		view.addItemListener(itemListener);
	}
}
