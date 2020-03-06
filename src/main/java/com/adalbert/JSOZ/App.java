package com.adalbert.JSOZ;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.controllers.windows.PropositionDetailsWindowController;
import com.adalbert.JSOZ.model.DataAccess;
import com.adalbert.JSOZ.view.windows.MainView;
import com.adalbert.JSOZ.view.windows.WindowManager;

public class App {

	private static WindowManager windowManager;
	private static DataAccess dataAccess;
	
	public static void main(String[] args) {
		if (getDataAccess() != null || getWindowManager() != null)
			System.exit(-1);
		setDataAccess(new DataAccess());
		setWindowManager(new WindowManager());
		getDataAccess().setup();
		getWindowManager().addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent event) {
				getDataAccess().exit();
		    }
		});
		getWindowManager().pushView(new MainView());
		
	}
	
	public static void openFilteringWindow() {
		FilteringWindowController filteringWindowController = new FilteringWindowController(getDataAccess(), getWindowManager());
		filteringWindowController.assembleFilters();
		filteringWindowController.assembleView();
		getWindowManager().pushView(filteringWindowController.getView());
	}
	
	public static void openDetailsWindow() {
		PropositionDetailsWindowController propositionDetailsController = new PropositionDetailsWindowController(getDataAccess(), getWindowManager());
		propositionDetailsController.assembleView();
		getWindowManager().pushView(propositionDetailsController.getView());
	}
	
	public static void exit() {
		getDataAccess().exit();
		System.exit(0);
	}

	public static DataAccess getDataAccess() {
		return dataAccess;
	}

	public static void setDataAccess(DataAccess dataAccess) {
		App.dataAccess = dataAccess;
	}

	public static WindowManager getWindowManager() {
		return windowManager;
	}

	public static void setWindowManager(WindowManager windowManager) {
		App.windowManager = windowManager;
	}
	
}
