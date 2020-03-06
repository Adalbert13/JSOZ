package com.adalbert.JSOZ.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.adalbert.JSOZ.controllers.windows.EventHandler;
import com.adalbert.JSOZ.view.interactive.LabelButton;
import com.adalbert.JSOZ.view.utils.PlaceholderRenderer;
import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {

	private EventHandler eventHandler;
	
	public HeaderPanel(EventHandler controller) {
		this.eventHandler = controller;
		assembleView();
	}
	
	private void assembleView() {
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(boxLayout);
		this.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
	
		JLabel label = null;
		try { label = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/logo.png")))); } catch (Exception ex) { label = new JLabel(PlaceholderRenderer.getIcon(PlaceholderRenderer.simpleFunction)); }
		label.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
		this.add(label);
		
		JTextField searchBox = new JTextField(28);
		searchBox.setText("Wyszukiwanie...");
		searchBox.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.PLAIN, 12));
		searchBox.setForeground(new Color(200,200,200));
		searchBox.setEditable(false);
		searchBox.setFocusable(false);
		searchBox.setMaximumSize(new Dimension(searchBox.getWidth(), 30));
		searchBox.setMinimumSize(new Dimension(228, 30));
		this.add(searchBox);
		
		LabelButton searchButton = null;
		try { searchButton = new LabelButton("search", "", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/searchIconJSOZ.jpg"))), JLabel.CENTER, eventHandler); } 
		catch (Exception ex) { searchButton = new LabelButton("search", "", PlaceholderRenderer.getIcon(PlaceholderRenderer.simpleFunction), JLabel.CENTER, eventHandler); }
		searchButton.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		searchButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,200));
		this.add(searchButton);

		LabelButton backButton = null;
		try { backButton = new LabelButton("back", "Powrót", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/backIconJSOZ.jpg"))), JLabel.CENTER, eventHandler); } 
		catch (Exception ex) { backButton = new LabelButton("back", "Powrót", PlaceholderRenderer.getIcon(PlaceholderRenderer.simpleFunction), JLabel.CENTER, eventHandler); }
		backButton.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		backButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,5));
		this.add(backButton);
		
		LabelButton settingsButton = null;
		try { settingsButton = new LabelButton("settings", "Ustawienia", new ImageIcon(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("images/settingsIconJSOZ.jpg"))), JLabel.CENTER, eventHandler); } 
		catch (Exception ex) { settingsButton = new LabelButton("settings", "Ustawienia", PlaceholderRenderer.getIcon(PlaceholderRenderer.simpleFunction), JLabel.CENTER, eventHandler); }
		settingsButton.setBackground(ViewFinals.BACKGROUND_RED_COLOR);
		settingsButton.setBorder(BorderFactory.createEmptyBorder(20,20,20,60));
		
		this.add(settingsButton);
		
		this.add(Box.createHorizontalGlue());
	}
	
}
