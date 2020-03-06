package com.adalbert.JSOZ.view.windows;

import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.ViewFactory;

import com.adalbert.JSOZ.App;
import com.adalbert.JSOZ.view.utils.PlaceholderRenderer;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class MainView extends JPanel {

	public MainView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton filteringView = new JButton("Okno filtrowania zamienników");
		filteringView.addActionListener((e) -> {
			App.openFilteringWindow();
		});
		filteringView.setAlignmentX(CENTER_ALIGNMENT);
		JButton detailsView = new JButton("Okno edycji propozycji zamiennika");
		detailsView.addActionListener((e) -> {
			App.openDetailsWindow();
		});
		detailsView.setAlignmentX(CENTER_ALIGNMENT);
		JButton exit = new JButton("Koniec");
		exit.addActionListener((e) -> {
			App.exit();
		});
		exit.setAlignmentX(CENTER_ALIGNMENT);
		JLabel label = null;
		try { 
			label = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/logo.png")))); 
		} catch (Exception ex) { 
			ex.printStackTrace();
			label = new JLabel(PlaceholderRenderer.getIcon(PlaceholderRenderer.simpleFunction)); 
		}
		label.setAlignmentX(CENTER_ALIGNMENT);
		setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		add(Box.createVerticalStrut(80));
		add(label);
		add(Box.createVerticalStrut(80));
		add(filteringView);
		add(Box.createVerticalStrut(40));
		add(detailsView);
		add(Box.createVerticalStrut(40));
		add(exit);
		add(Box.createVerticalGlue());
	}
	
}
