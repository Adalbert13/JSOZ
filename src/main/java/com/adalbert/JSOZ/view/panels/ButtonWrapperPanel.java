package com.adalbert.JSOZ.view.panels;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.adalbert.JSOZ.view.utils.ViewFinals;

@SuppressWarnings("serial")
public class ButtonWrapperPanel<T extends Component> extends JPanel {

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	
	private JPanel wrappedPanel;
	private List<T> buttons;
	private int orientation;
	private boolean borderDrawn;
	
	public ButtonWrapperPanel(JPanel wrappedPanel, List<T> buttons, int orientation) {
		this.wrappedPanel = wrappedPanel;
		this.buttons = buttons;
		this.orientation = orientation;
		this.borderDrawn = true;
		assembleView();
	}
	
	private void assembleView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(gridBagLayout);
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.setBackground(wrappedPanel.getBackground());
			
		if (orientation == HORIZONTAL) {
			wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.weightx = ViewFinals.BUTTON_PANEL_CONTENT_WEIGHT;
			constraints.gridx = 0;
			constraints.gridy = 0;
		} else {
			constraints.fill = GridBagConstraints.VERTICAL;
			constraints.weighty = ViewFinals.BUTTON_PANEL_CONTENT_WEIGHT;
			constraints.gridx = 0;
			constraints.gridy = 0;
		}
		this.add(wrappedPanel, constraints);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(wrappedPanel.getBackground());
		BoxLayout boxLayout = null;
		if (this.orientation == HORIZONTAL)
			boxLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		else 
			boxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		buttonPanel.setLayout(boxLayout);
		if (this.orientation == HORIZONTAL)
			buttonPanel.add(Box.createHorizontalStrut(20));
		else 
			buttonPanel.add(Box.createVerticalStrut(20));
		for (T button : buttons) {
			buttonPanel.add(button);
			if (this.orientation == HORIZONTAL)
				buttonPanel.add(Box.createHorizontalStrut(20));
			else 
				buttonPanel.add(Box.createVerticalStrut(20));
		}
		if (this.orientation == HORIZONTAL)
			buttonPanel.add(Box.createHorizontalGlue());
		else 
			buttonPanel.add(Box.createVerticalGlue());
		if (orientation == HORIZONTAL) {
			constraints.weightx = 1 - ViewFinals.BUTTON_PANEL_CONTENT_WEIGHT;
			constraints.gridx = 1;
			constraints.gridy = 0;
		} else {
			constraints.weighty = 1 - ViewFinals.BUTTON_PANEL_CONTENT_WEIGHT;
			constraints.gridx = 0;
			constraints.gridy = 1;
		}
		this.add(buttonPanel, constraints);
	}

	public boolean isBorderDrawn() {
		return borderDrawn;
	}

	public void setBorderDrawn(boolean borderDrawn) {
		this.borderDrawn = borderDrawn;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (borderDrawn) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}
	}
}
