package com.adalbert.JSOZ.view.panels;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adalbert.JSOZ.view.utils.DetailsPanelInfo;
import com.adalbert.JSOZ.view.utils.ViewFinals;

/**
 * Klasa opisująca panel informacji tekstowych
 * @author Wojciech Kania
 */
@SuppressWarnings("serial")
public class DetailsPanel extends JPanel {

	/**
	 * Tytuł panelu detali
	 */
	private String title;
	/**
	 * Lista etykiet zawierających kolejne informacje z panelu
	 */
	private List<JLabel> details;
	/**
	 * Liczba kolumn wyświetlanego panelu
	 */
	private int columns;
	/**
	 * Flaga, wskazująca czy w pierwszym wierszu panelu ma znajdować się tylko tytuł (true) czy zostaną też tam spakowane informacje (false), domyślnie false
	 */
	private boolean omitFirstRow;
	/**
	 * Flaga, wskazująca czy ramka dookoła panelu powinna być narysowana (true) czy nie (false), domyślnie false
	 */
	private boolean borderDrawn;
	
	/**
	 * Domyślna liczba kolumn w przypadku braku specyfikacji przez tworzącego obiekt
	 */
	private static int COLUMNS_DEFAULT = 2;
	
	/**
	 * Podstawowy konstruktor przyjmujący dane do konstrukcji panelu detali
	 * @param info Obiekt przechowujący komplet podstawowych danych do konstrukcji panelu detali
	 */
	public DetailsPanel(DetailsPanelInfo info) {
		this.title = info.getTitle();
		this.columns = COLUMNS_DEFAULT;
		this.omitFirstRow = true;
		this.borderDrawn = true;
		assembleData(info);
		assembleView();
	}
	
	/**
	 * Konstruktor przyjmujący dane oraz liczbę kolumn do konstrukcji panelu detali
	 * @param info Obiekt przechowujący komplet podstawowych danych do konstrukcji panelu detali
	 * @param columns Liczba kolumn
	 */
	public DetailsPanel(DetailsPanelInfo info, int columns) {
		this.title = info.getTitle();
		this.columns = columns;
		this.omitFirstRow = true;
		this.borderDrawn = true;
		assembleData(info);
		assembleView();
	}
	
	/**
	 * Konstruktor przyjmujący dane, liczbę kolumn oraz flagę omijania pierwszego wiersza panelu do konstrukcji panelu detali
	 * @param info Obiekt przechowujący komplet podstawowych danych do konstrukcji panelu detali
	 * @param columns Liczba kolumn
	 * @param omitFirstRow Flaga omijania pierwszego wiersza
	 */
	public DetailsPanel(DetailsPanelInfo info, int columns, boolean omitFirstRow) {
		this.title = info.getTitle();
		this.columns = columns;
		this.omitFirstRow = omitFirstRow;
		this.borderDrawn = true;
		assembleData(info);
		assembleView();
	}
	
	/**
	 * Metoda tworząca tekstowe etykiety graficzne z kategoryzowanych informacji
	 * @param info Obiekt przechowujący kategorie informacji do pokazania
	 */
	private void assembleData(DetailsPanelInfo info) {
		details = new ArrayList<JLabel>();
		for (int categoryIndex = 0; categoryIndex < info.getCategories().size(); categoryIndex++) {
			JLabel newLabel = new JLabel(info.getCategoryName(categoryIndex));
			if (info.getCategoriesValues(categoryIndex) == null)
				newLabel.setText(newLabel.getText() + "nie dotyczy");
			else
				for (int i = 0; i < info.getCategoriesValues(categoryIndex).size(); i++)
					newLabel.setText(newLabel.getText() + info.getCategoriesValues(categoryIndex).get(i) + (i == info.getCategoriesValues(categoryIndex).size() -1 ? "" : ", "));
			details.add(newLabel);
		}
	}
	
	/**
	 * Metoda konstruująca graficzne odwzorowanie panelu
	 */
	private void assembleView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(gridBagLayout);
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.setBackground(ViewFinals.BACKGROUND_LIGHT_GRAY_COLOR);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.NONE;
		JLabel panelLabel = new JLabel(title + ":", JLabel.LEFT);
		panelLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelLabel.setFont(new Font(ViewFinals.COMMON_FONT_STYLE, Font.PLAIN, 16));
		this.add(panelLabel, constraints);
		
		
		for (int column = 1; omitFirstRow && column < columns; column++) {
			constraints.gridx = column;
			this.add(Box.createGlue(), constraints);
		}
		
		constraints.gridy = omitFirstRow ? 1 : 0;
		constraints.gridx = omitFirstRow ? 0 : 1;
		for (JLabel detail : details) {
			detail.setBorder(BorderFactory.createEmptyBorder(10,10,5,15));
			add(detail, constraints);
			constraints.gridx++;
			if (constraints.gridx % columns == 0) {
				constraints.gridy++;
				constraints.gridx = 0;
			}
		}
		
	}
	
	/**
	 * Zmodyfikowana metoda przerysowująca komponent, obramowująca go jeśli została ustawiona odpowiednia flaga
	 * @param g Obiekt pozwalający na rysowanie po graficznej reprezentacji panelu
	 * @see Graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (borderDrawn) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}
	}
	
	/**
	 * Ustawia liczbę kolumn panelu
	 * @param columns Liczba kolumn
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/**
	 * Zwraca flagę rysowania obramowania
	 * @return Flaga rysowania obramowania
	 */
	public boolean isBorderDrawn() {
		return borderDrawn;
	}

	/**
	 * Ustawia flagę rysowania obramowania
	 * @param borderDrawn Flaga rysowania obramowania
	 */
	public void setBorderDrawn(boolean borderDrawn) {
		this.borderDrawn = borderDrawn;
	}
	
}
