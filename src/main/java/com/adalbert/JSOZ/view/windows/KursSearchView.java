package com.adalbert.JSOZ.view.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.adalbert.JSOZ.controllers.windows.KursSearchController;
import com.adalbert.JSOZ.model.Kurs;
import com.adalbert.JSOZ.model.Zamiennik;
import com.adalbert.JSOZ.view.panels.ButtonWrapperPanel;
import com.adalbert.JSOZ.view.panels.DetailsPanel;
import com.adalbert.JSOZ.view.panels.FiltersPanel;
import com.adalbert.JSOZ.view.panels.HeaderPanel;
import com.adalbert.JSOZ.view.utils.DetailsPanelInfo;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class KursSearchView extends JPanel {
	
	private JPanel coursesContainer;
	private JScrollPane coursesContainerPane;
	private JPanel mainPanel;
	private List<DetailsPanel> coursesPanels;
	private KursSearchController controller;
	
	public KursSearchView(KursSearchController controller) {
		this.coursesPanels = new ArrayList<>();
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
		mainPanel.setLayout(new BorderLayout());
		
		coursesContainer = new JPanel();
		coursesContainer.setBackground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		coursesContainer.setLayout(new BoxLayout(coursesContainer, BoxLayout.Y_AXIS));
		coursesContainer.setAlignmentX(RIGHT_ALIGNMENT);
		coursesContainer.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		coursesContainerPane = new JScrollPane(coursesContainer);
		mainPanel.add(coursesContainerPane);
		this.add(mainPanel);
	}
	
	public void updateCoursesContainer() {
		coursesContainer.removeAll();
		coursesPanels.clear();
		coursesContainer.setLayout(new BoxLayout(coursesContainer, BoxLayout.Y_AXIS));
		for (Kurs znalezionyKurs : controller.getZnalezioneKursy()) {
			DetailsPanelInfo info = new DetailsPanelInfo();
			info.setTitle(znalezionyKurs.getKursKod());
			info.addCategoryWithValue("Nazwa: ", znalezionyKurs.getNazwa());
			info.addCategoryWithValue("Stopień kształcenia: ", znalezionyKurs.getOpisujacyPlan().getStopienKsztalcenia().toString());
			info.addCategoryWithValues("Słowa kluczowe: ", znalezionyKurs.getSlowaKluczowe());
			info.addCategoryWithValue("Tryb studiowania: ", znalezionyKurs.getOpisujacyPlan().getTrybStudiow().toString());
			info.addCategoryWithValue("Liczba punktów ECTS: ", znalezionyKurs.getEcts()+"");
			info.addCategoryWithValue("Forma kursu: ", znalezionyKurs.getFormaKursu().getNazwa());
			info.addCategoryWithValue("Sposób zaliczenia: ", znalezionyKurs.getSposobZaliczenia().getNazwa());
			DetailsPanel coursePanel = new DetailsPanel(info, 3, false);
			coursePanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controller.dodajKursDoZawieranych(znalezionyKurs);
					controller.handleEvent("back");
				}
			});
			coursesPanels.add(coursePanel);
			coursesContainer.add(coursePanel);
			coursesContainer.add(Box.createVerticalStrut(20));
		}
		revalidate();
		repaint();
	}
}
