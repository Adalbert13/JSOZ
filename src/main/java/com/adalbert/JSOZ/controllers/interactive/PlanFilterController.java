package com.adalbert.JSOZ.controllers.interactive;

import java.awt.event.ItemListener;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Kierunek;
import com.adalbert.JSOZ.model.Plan;
import com.adalbert.JSOZ.model.Wydzial;

public class PlanFilterController extends ModelFilterController<Plan> {

	public PlanFilterController(String id, FilteringWindowController controller) {
		super(id, controller);
	}

	@Override
	public void update() {
		ItemListener itemListener = view.getItemListeners()[0];
		view.removeItemListener(itemListener);
		view.removeAllItems();
		view.addItem(getId());
		for (Plan value : getValues()) {
			Kierunek chosenKierunek = (Kierunek) controller.getFilterController("Kierunek").getSelected();
			if (chosenKierunek != null) {
				for (Plan plan : chosenKierunek.getUstalanePlany()) {
					if (plan.getPlanKod().equals(value.getPlanKod()))
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
