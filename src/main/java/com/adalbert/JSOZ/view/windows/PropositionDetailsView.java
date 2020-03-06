package com.adalbert.JSOZ.view.windows;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.adalbert.JSOZ.controllers.windows.PropositionDetailsWindowController;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.view.interactive.LabelButton;
import com.adalbert.JSOZ.view.panels.ButtonWrapperPanel;
import com.adalbert.JSOZ.view.panels.DetailsPanel;
import com.adalbert.JSOZ.view.panels.FiltersPanel;
import com.adalbert.JSOZ.view.panels.HeaderPanel;
import com.adalbert.JSOZ.view.utils.KursCellRenderer;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class PropositionDetailsView extends JPanel {

	private PropositionDetailsWindowController controller;
	
	@SuppressWarnings("unchecked")
	public PropositionDetailsView(PropositionDetailsWindowController controller) {
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
		mainPanel.setLayout(new GridLayout(0,1));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		int gridy = 0;
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.gridx = 0;
		JPanel detailsContainer = new JPanel();
		detailsContainer.setBackground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		detailsContainer.setLayout(gridBagLayout);
		JLabel title = new JLabel("Propozycja zamiennika #" + controller.dajRozpatrywanaPropozycje().getZamiennikKod() + ":");
		title.setBorder(BorderFactory.createEmptyBorder(5,0,20,0));
		title.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.BOLD, 14));
		constraints.gridy = gridy++;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		detailsContainer.add(title, constraints);
		
		constraints.insets = new Insets(20,0,0,0);
		constraints.gridy = gridy++;
		detailsContainer.add(new DetailsPanel(controller.dajDetalePropozycji(), 2), constraints);
		
		JPanel containedCoursesPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(2,0);
		gridLayout.setHgap(30);
		gridLayout.setVgap(10);
		containedCoursesPanel.setLayout(gridLayout);
		containedCoursesPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		containedCoursesPanel.setBackground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		
		JLabel panelLabel = new JLabel("Kursy zawierane:", JLabel.LEFT);
		panelLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelLabel.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.PLAIN, 16));
		containedCoursesPanel.add(panelLabel);
		JList<Kurs> listaKursow = new JList<Kurs>(controller.getModelListyKursow());
		listaKursow.setCellRenderer(new KursCellRenderer());
		containedCoursesPanel.add(listaKursow);
		ButtonWrapperPanel<LabelButton> wrapperPanel = new ButtonWrapperPanel<>(containedCoursesPanel, Arrays.asList(new LabelButton[] { 
						new LabelButton("edit", "Edytuj", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/editIconJSOZ.jpg"))), JLabel.CENTER, controller), 
						new LabelButton("expand", "Rozwiń", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/expandIconJSOZ.jpg"))), JLabel.CENTER, controller)}), ButtonWrapperPanel.HORIZONTAL);
		
		constraints.gridy = gridy++;
		detailsContainer.add(wrapperPanel, constraints);	
		constraints.gridy = gridy++;
		detailsContainer.add(new DetailsPanel(controller.dajDetaleKursuZamienianego(), 2), constraints);
		constraints.gridy = gridy++;
		detailsContainer.add(new DetailsPanel(controller.dajDetaleProponujacego(), 2), constraints);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton acceptButton = new JButton("Zaakceptuj");
		acceptButton.addActionListener((event) -> controller.handleEvent("accept"));
		acceptButton.setBackground(ViewFinals.ACCEPT_BUTTON_COLOR);
		acceptButton.setForeground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		JButton declineButton = new JButton("Odrzuć");
		declineButton.addActionListener((event) -> controller.handleEvent("decline"));
		declineButton.setBackground(ViewFinals.DECLINE_BUTTON_COLOR);
		declineButton.setForeground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		buttonsPanel.add(acceptButton);
		buttonsPanel.add(declineButton);
		
		constraints.gridy = gridy++;
		detailsContainer.add(buttonsPanel, constraints);
		
		JScrollPane pane = new JScrollPane(detailsContainer);
		pane.setPreferredSize(new Dimension (mainPanel.getWidth(), mainPanel.getHeight()));
		
		mainPanel.add(pane);
		this.add(mainPanel);
	}
	
}
