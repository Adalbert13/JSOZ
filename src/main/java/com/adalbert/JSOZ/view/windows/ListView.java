package com.adalbert.JSOZ.view.windows;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.adalbert.JSOZ.controllers.windows.ListWindowController;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.view.panels.HeaderPanel;
import com.adalbert.JSOZ.view.utils.KursCellRenderer;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class ListView extends JPanel {

	private ListWindowController controller;
	private JList<Kurs> listaKursow;
	
	@SuppressWarnings("unchecked")
	public ListView(ListWindowController controller) {
		this.controller = controller;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new HeaderPanel(controller));
		assembleView();
		this.setVisible(true);
	}
	
	public void assembleView() {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,40,30,40));
		mainPanel.setLayout(new BorderLayout());
		
		JPanel listPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		listPanel.setLayout(layout);
		
		JLabel title = new JLabel("Propozycja zamiennika #" + controller.dajRozpatrywanaPropozycje().getZamiennikKod() + ":");
		title.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.BOLD, 14));
		layout.putConstraint(SpringLayout.WEST, title, 20, SpringLayout.WEST, listPanel);
		layout.putConstraint(SpringLayout.NORTH, title, 20, SpringLayout.NORTH, listPanel);
		listPanel.add(title);
		
		JLabel subtitle = new JLabel("Kursy zawierane:");
		subtitle.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.BOLD, 12));
		layout.putConstraint(SpringLayout.WEST, subtitle, 40, SpringLayout.WEST, listPanel);
		layout.putConstraint(SpringLayout.NORTH, subtitle, 30, SpringLayout.SOUTH, title);
		listPanel.add(subtitle);
		
		listaKursow = new JList<Kurs>(controller.getModelListyKursow());
		listaKursow.setCellRenderer(new KursCellRenderer());
		layout.putConstraint(SpringLayout.WEST, listaKursow, 60, SpringLayout.WEST, listPanel);
		layout.putConstraint(SpringLayout.NORTH, listaKursow, 20, SpringLayout.SOUTH, subtitle);
		listPanel.add(listaKursow);
		
		JButton przyciskDodajacy = new JButton("Dodaj kurs");
		przyciskDodajacy.setBackground(ViewFinals.ACCEPT_BUTTON_COLOR);
		przyciskDodajacy.setForeground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		layout.putConstraint(SpringLayout.WEST, przyciskDodajacy, 70, SpringLayout.WEST, listPanel);
		layout.putConstraint(SpringLayout.NORTH, przyciskDodajacy, 30, SpringLayout.SOUTH, listaKursow);
		przyciskDodajacy.addActionListener((event) -> {
			controller.handleEvent("add");
		});
		listPanel.add(przyciskDodajacy);
		
		JButton przyciskUsuwajacy = new JButton("Usuń kurs");
		przyciskUsuwajacy.setBackground(ViewFinals.DECLINE_BUTTON_COLOR);
		przyciskUsuwajacy.setForeground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		layout.putConstraint(SpringLayout.WEST, przyciskUsuwajacy, 40, SpringLayout.EAST, przyciskDodajacy);
		layout.putConstraint(SpringLayout.NORTH, przyciskUsuwajacy, 30, SpringLayout.SOUTH, listaKursow);
		przyciskUsuwajacy.addActionListener((event) -> {
			controller.handleEvent("remove");
		});
		listPanel.add(przyciskUsuwajacy);
		
		mainPanel.add(listPanel);
		this.add(mainPanel);
	}

	public JList<Kurs> getListaKursow() {
		return listaKursow;
	}

	public void setListaKursow(JList<Kurs> listaKursow) {
		this.listaKursow = listaKursow;
	}
	
}
