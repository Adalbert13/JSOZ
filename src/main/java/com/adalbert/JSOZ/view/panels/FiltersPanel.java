package com.adalbert.JSOZ.view.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.util.*;

import com.adalbert.JSOZ.controllers.interactive.ModelFilterController;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class FiltersPanel extends JPanel {

	@SuppressWarnings("rawtypes")
	private List<ModelFilterController> filterControllers;
	
	@SuppressWarnings("rawtypes")
	public FiltersPanel(List<ModelFilterController> filterControllers) {
		this.filterControllers = filterControllers;
		assembleData();
		assembleView();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void assembleData() {
		for (ModelFilterController filterController : filterControllers) {
			JComboBox comboBox = new JComboBox();
			comboBox.addItemListener((event) -> {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					filterController.selectedValueCallback((String) comboBox.getSelectedItem());
				}
			});
			filterController.setView(comboBox);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void assembleView() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(gridBagLayout);
		this.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		this.add(Box.createVerticalStrut(10));
		
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(15,10,15,10);
		constraints.anchor = GridBagConstraints.LINE_START;
		JLabel panelLabel = new JLabel("Filtry:", JLabel.LEFT);
		panelLabel.setForeground(ViewFinals.TEXT_CREME_COLOR);
		panelLabel.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.PLAIN, 14));
		this.add(panelLabel, constraints);
		
		int gridy = 1;
		for (ModelFilterController filterController : filterControllers) {
			JComboBox<String> filter = filterController.getView();
			constraints.gridy = gridy++;
			filter.setPrototypeDisplayValue("#########################");
			this.add(filter, constraints);
			this.add(Box.createVerticalStrut(20));
		}
	}
	
}
