package com.adalbert.JSOZ.view.interactive;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.adalbert.JSOZ.controllers.windows.EventHandler;

@SuppressWarnings("serial")
public class LabelButton extends JLabel implements MouseListener {

	private EventHandler eventHandler;
	private String id;
	
	public LabelButton(String id, String label, Icon icon, int position, EventHandler eventHandler) {
		super(label, icon, position);
		this.eventHandler = eventHandler;
		this.addMouseListener(this);
		this.id = id;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		eventHandler.handleEvent(id);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	

}
