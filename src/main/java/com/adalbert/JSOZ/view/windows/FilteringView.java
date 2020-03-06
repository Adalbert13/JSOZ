package com.adalbert.JSOZ.view.windows;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.adalbert.JSOZ.controllers.interactive.ModelFilterController;
import com.adalbert.JSOZ.controllers.windows.FilteringWindowController;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.panels.ButtonWrapperPanel;
import com.adalbert.JSOZ.view.panels.DetailsPanel;
import com.adalbert.JSOZ.view.panels.FiltersPanel;
import com.adalbert.JSOZ.view.panels.HeaderPanel;
import com.adalbert.JSOZ.view.utils.DetailsPanelInfo;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class FilteringView extends JPanel {
	
	private JPanel zamiennikiContainer;
	private JScrollPane zamiennikiContainerPane;
	private JPanel mainPanel;
	private List<DetailsPanel> zamiennikiPanels;
	private FilteringWindowController controller;
	
	public FilteringView(FilteringWindowController controller) {
		this.zamiennikiPanels = new ArrayList<>();
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		assembleView();
		this.setVisible(true);
	}
	
	public void assembleView() {
		
		this.add(new HeaderPanel(controller));
		mainPanel = new JPanel();
		mainPanel.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		FiltersPanel filtersPanel = new FiltersPanel(controller.getFilterControllers());
		JButton filterButton = new JButton("Filtruj");
		filterButton.addActionListener((event) -> {
			controller.handleEvent("filter");
		});
		JButton clearFiltersButton = new JButton("Wyczyść filtry");
		clearFiltersButton.addActionListener((event) -> {
			controller.handleEvent("clearFilters");
		});
		ButtonWrapperPanel<JButton> buttonWrapper = new ButtonWrapperPanel<>(filtersPanel, Arrays.asList(new JButton[] {filterButton, clearFiltersButton}), ButtonWrapperPanel.VERTICAL); 
		buttonWrapper.setBorderDrawn(false);
		mainPanel.add(buttonWrapper, constraints);
		
		zamiennikiContainer = new JPanel();
		zamiennikiContainer.setBackground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		zamiennikiContainer.setLayout(new BoxLayout(zamiennikiContainer, BoxLayout.Y_AXIS));
		zamiennikiContainer.setAlignmentX(RIGHT_ALIGNMENT);
		zamiennikiContainer.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 3;
		constraints.gridx = 1;
		constraints.gridy = 0;
		zamiennikiContainerPane = new JScrollPane(zamiennikiContainer);
		mainPanel.add(zamiennikiContainerPane, constraints);
		this.add(mainPanel);
	}
	
	public void updateZamiennikContainer() {
		zamiennikiContainer.removeAll();
		zamiennikiPanels.clear();
		zamiennikiContainer.setLayout(new BoxLayout(zamiennikiContainer, BoxLayout.Y_AXIS));
		for (Zamiennik znalezionyZamiennik : controller.dajFiltrowaneZamienniki()) {
			DetailsPanelInfo info = new DetailsPanelInfo();
			info.setTitle(znalezionyZamiennik.getZamiennikKod());
			info.addCategoryWithValues("Stopień kształcenia: ", Stream.of(znalezionyZamiennik.dajStopienKsztalcenia()+"").collect(Collectors.toList()));
			info.addCategoryWithValues("Słowa kluczowe: ", znalezionyZamiennik.getSlowaKluczowe());
			info.addCategoryWithValues("Tryb studiowania: ", Stream.of(znalezionyZamiennik.dajTrybStudiow()+"").collect(Collectors.toList()));
			DetailsPanel coursePanel = new DetailsPanel(info, 2, false);
			zamiennikiPanels.add(coursePanel);
			zamiennikiContainer.add(coursePanel);
			zamiennikiContainer.add(Box.createVerticalStrut(20));
		}
		revalidate();
		repaint();
		
	}
}
