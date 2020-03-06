package com.adalbert.JSOZ.controllers.interactive;

import java.awt.event.ItemListener;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.FormaKursu;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.StopienKsztalcenia;
import com.adalbert.JSOZ.model.TrybStudiow;

public class FormaFilterController extends ModelFilterController<FormaKursu> {
	
	public FormaFilterController(String id, FilteringWindowController controller) {
		super(id, controller);
	}

	@Override
	public void update() {
		ItemListener itemListener = view.getItemListeners()[0];
		view.removeItemListener(itemListener);
		view.removeAllItems();
		view.addItem(getId());
		for (FormaKursu value : getValues())
			view.addItem(value.toString());
		if (selected != null)
			view.setSelectedItem(selected.toString());
		view.addItemListener(itemListener);
	}
}
